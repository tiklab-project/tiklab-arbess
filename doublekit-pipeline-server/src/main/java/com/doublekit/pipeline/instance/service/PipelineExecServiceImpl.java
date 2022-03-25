package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import ch.ethz.ssh2.Connection;

@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    //存放过程状态
    List<PipelineExecLog> pipelineExecLogList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();


    //开始构建
    @Override
    public String  Structure(String pipelineId){
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //判断同一任务是否在运行
        if (pipelineIdList != null){
            for (String id : pipelineIdList) {
                if (id .equals(pipelineId)){
                    return "100";
                }
            }
        }
        //执行构建
        executorService.submit(() -> start(pipelineId));
        return "1";
    }

    //查询构建状态
    @Override
    public PipelineExecLog findStructureState(String pipelineId){

        if (pipelineExecLogList != null){
            for (PipelineExecLog pipelineExecLog : pipelineExecLogList) {
                // if (pipelineId.equals(pipelineExecLog.getPipelineId())){
                //     return pipelineExecLog;
                // }
            }
        }
        return null;
    }

    //历史表添加信息
    @Override
    public PipelineExecHistory addHistoryTwo(String pipelineId){
        //格式化时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        // PipelineExecHistory PipelineExecHistory = pipelineConfigureService.addHistoryOne(pipelineId, pipelineExecHistory);
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        // 添加信息
        // pipelineExecHistory.setPipelineConfigure(PipelineExecHistory.getPipelineConfigure());
        // pipelineExecHistory.setProof(PipelineExecHistory.getProof());
        pipelineExecHistory.setPipeline(pipeline);
        pipelineExecHistory.setHistoryCreateTime(dateFormat.format(new Date()));
        return pipelineExecHistory;
    }

    //构建
    private String start(String pipelineId) throws IOException{

        PipelineExecLog pipelineExecLog =new PipelineExecLog();
        //把执行构建的流水线加入进来
        pipelineIdList.add(pipelineId);
        PipelineConfigure pipelineConfigure = pipelineConfigureService.findPipelineIdConfigure(pipelineId);
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        String logId = pipelineExecLogService.createLog(pipelineExecLog);
        pipelineExecLog.setLogId(logId);
        // pipelineExecLog.setPipelineId(pipelineId);
        pipelineExecLogList.add(pipelineExecLog);

        // 判断配置信息
        whetherNull(pipeline, pipelineConfigure, pipelineExecLog);

        return "1";

    }


    // git克隆
    private int gitClone(Pipeline pipeline, PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog){

        String logId = pipelineExecLog.getLogId();
        // pipelineExecLog.setLogCodeState(2);
        pipelineExecLogList.add(pipelineExecLog);
        String pipelineId = pipelineConfigure.getPipeline().getPipelineId();

        //开始时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //设置代码路径
        String path = "D:\\clone\\" + pipeline.getPipelineName();
        File file = new File(path);
        //调用删除方法删除旧的代码
        delete(file);
        //获取凭证信息
        Proof proof = pipelineConfigureService.findCodeProof(pipelineConfigure);
        if (proof != null) {
            String codeAddress = pipelineConfigure.getPipelineCode().getCodeAddress();
            String codeBranch = pipelineConfigure.getPipelineCode().getCodeBranch();
            //获取凭证
            UsernamePasswordCredentialsProvider credentialsProvider = usernamePassword(proof.getProofUsername(), proof.getProofPassword());
            String s = "开始拉取代码 : " + "\n"  + "FileAddress : " + file + "\n"  +
                    "Uri : " + codeAddress + "\n"   +
                    "Branch : " + codeBranch + "\n"  ;
            pipelineExecLog.setLogRunLog(s);
            pipelineExecLogList.add(pipelineExecLog);
            //克隆代码
            try {
                gitClone(file, codeAddress, credentialsProvider, codeBranch);
            } catch (GitAPIException e) {
                // pipelineExecLog.setLogCodeState(1);
                error(pipelineExecLog, "拉取代码异常"+e.toString(),pipelineId);
                return 0;
            }

            //更新状态
            String proofType = "proofType : " +proof.getProofType() + "\n";
            String success = "拉取成功。。。。。。。。。。。。。。。" + "\n";
            String log = s + proofType + success;
            pipelineExecLog.setLogId(logId);
            pipelineExecLog.setLogRunLog(log);
            long overTime = new Timestamp(System.currentTimeMillis()).getTime();
            // pipelineExecLog.setLogCodeTime((int)(overTime-beginTime)/1000);
        }
        // pipelineExecLog.setLogCodeState(10);
        pipelineExecLogList.add(pipelineExecLog);
        return 1;
    }

    // 单元测试
    private int unitTesting(Pipeline pipeline, PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog) {
        String testOrder = pipelineConfigure.getPipelineTest().getTestOrder();
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = testOrder.split("\n");
        for (String s : split) {
            try {
                Process process = process(path, s, null);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + a);
                int log = log(process, pipelineExecLog);
                if (log == 0){
                    // pipelineExecLog.setLogTestState(1);
                    pipelineExecLogList.add(pipelineExecLog);
                    error(pipelineExecLog,"测试失败：", pipelineConfigure.getPipeline().getPipelineId());
                    return 0;
                }
                // pipelineExecLog.setLogTestState(10);
                pipelineExecLogList.add(pipelineExecLog);
            } catch (IOException e) {
                // pipelineExecLog.setLogTestState(1);
                pipelineExecLogList.add(pipelineExecLog);
                error(pipelineExecLog,"测试失败。。。。。"+ e, pipelineConfigure.getPipeline().getPipelineId());
                return 0;
            }
        }
        return 1;
    }

    // 构建
    private int structure(Pipeline pipeline, PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog)  {

        String structureOrder = pipelineConfigure.getPipelineStructure().getStructureOrder();
        String structureAddress = pipelineConfigure.getPipelineStructure().getStructureAddress();
        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = structureOrder.split("\n");
        for (String s : split) {
            try {
                Process process = process(path, s, structureAddress);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + a);
                int log = log(process, pipelineExecLog);
                if (log == 0){
                    // pipelineExecLog.setLogTestState(1);
                    pipelineExecLogList.add(pipelineExecLog);
                    error(pipelineExecLog,"构建失败。。。。", pipelineConfigure.getPipeline().getPipelineId());
                    return 0;
                }
                // pipelineExecLog.setLogPackState(10);
                pipelineExecLogList.add(pipelineExecLog);
            } catch (IOException e) {
                // pipelineExecLog.setLogPackState(1);
                pipelineExecLogList.add(pipelineExecLog);
                error(pipelineExecLog,"构建异常："+ e, pipelineConfigure.getPipeline().getPipelineId());
                return 0;
            }
        }
        return 1;
    }

     // 部署
    private int deploy(Pipeline pipeline, PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog) throws IOException {

        String deployTargetAddress = pipelineConfigure.getPipelineDeploy().getDeployTargetAddress();
        String deployAddress = pipelineConfigure.getPipelineDeploy().getDeployAddress();
        String logId = pipelineExecLog.getLogId();
        // pipelineExecLog.setLogDeployTime(1);
        pipelineExecLogList.add(pipelineExecLog);
        Proof proof = pipelineConfigureService.findDeployProof(pipelineConfigure);

        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"部署到服务器"+proof.getProofIp()+"。。。。。。。。");
        //文件地址
        String[] split = deployTargetAddress.split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";

        //发送文件名
        String address = address(path,split[1]);
        path  = path + "\\" +address ;

        //发送文件位置
        String configureDeployAddress = deployAddress;
        configureDeployAddress = configureDeployAddress +"/"+ address;

        //调用发送方法
        try {
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"开始发送文件:"+path);
            sshSftp(proof,configureDeployAddress,path);
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"文件:"+address+"发送成功！");
        } catch (JSchException | SftpException | IOException e) {
            // pipelineExecLog.setLogDeployState(1);
            error(pipelineExecLog,e.toString(),pipeline.getPipelineId());
            return 0;
        }
        //执行shell
        int shell = shell(proof, pipelineConfigure, pipelineExecLog);
        if (shell == 0){
            return 0;
        }
        pipelineExecLog.setLogId(logId);
        //获取构建所用时长
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        // pipelineExecLog.setLogDeployTime((int) (overTime-beginTime)/1000);
        // pipelineExecLog.setLogDeployState(10);
        // pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"服务器部署:"+proof.getProofIp()+"成功!");
        pipelineExecLogList.add(pipelineExecLog);
        return 1;
    }

    /**
     * 调用cmd执行构建命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
    private Process process(String path,String order,String sourceAddress) throws IOException {

        Runtime runtime=Runtime.getRuntime();
        Process process;
        if (sourceAddress != null){
            process = runtime.exec("cmd.exe /c cd " + path + "\\"+sourceAddress + " &&" + " " + order);
            return process;
        }
        //执行命令
        process = runtime.exec("cmd.exe /c cd " + path + " &&" + " " + order);
        return process;
    }

    /**
     * 执行产生的日志
     * @param process 执行对象
     * @param pipelineExecLog 日志信息
     * @throws IOException 字符流装换异常
     */
    private int log(Process process , PipelineExecLog pipelineExecLog) throws IOException {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        String s;
        InputStreamReader  inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        String logId = pipelineExecLog.getLogId();
        String logRunLog = pipelineExecLog.getLogRunLog();
        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog = logRunLog + s + "\n";
            pipelineExecLog.setLogId(logId);
            pipelineExecLog.setLogRunLog(logRunLog);

            if (logRunLog.contains("BUILD FAILURE")){
                return 0;
            }

            long overTime = new Timestamp(System.currentTimeMillis()).getTime();
            //获取构建所用时长
            // if (pipelineExecLog.getLogTestState() == 0){
            //     pipelineExecLog.setLogTestTime((int) (overTime-beginTime)/1000);
            // }else {
            //     pipelineExecLog.setLogPackTime((int) (overTime-beginTime)/1000);
            }
            pipelineExecLogList.add(pipelineExecLog);
        // }
        // inputStreamReader.close();
        // bufferedReader.close();
        return 1;
    }

    /**
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     * @throws RuntimeException 拉取异常
     */
    private void gitClone(File gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws GitAPIException {
        Git.cloneRepository().setURI(gitUrl)
            .setCredentialsProvider(credentialsProvider)
            .setDirectory(gitAddress)
            .setBranch(branch)
            .call().close();
    }

    /**
     * 凭证信息（UsernamePassword）方式
     * @param gitUser 用户名
     * @param gitPasswd 密码
     * @return 验证信息
     */
    private static UsernamePasswordCredentialsProvider usernamePassword(String gitUser, String gitPasswd) {

        UsernamePasswordCredentialsProvider credentialsProvider = null;
        if (StringUtils.isNotEmpty(gitUser) && StringUtils.isNotEmpty(gitPasswd)) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(gitUser, gitPasswd);
        }
        return credentialsProvider;
    }

    /**
     * 删除旧的代码
     * @param dir 文件地址
     */
    private boolean delete(File dir){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean success = delete(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        // 目录此时为空，可以删除
        }
        return dir.delete();
    }

    /**
     * ssh 连接发送文件
     * @param proof 凭证信息
     * @param nowPath 部署文件地址
     * @param lastPath 本机文件地址
     */
    private static void sshSftp(Proof proof,String nowPath,String lastPath) throws JSchException, SftpException, IOException {

        //采用指定的端口连接服务器
        Session  session = new JSch().getSession(proof.getProofUsername(), proof.getProofIp() ,proof.getProofPort());
        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new JSchException(proof.getProofIp() + "连接异常。。。。");
        }
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆主机的密码
        session.setPassword(proof.getProofPassword());
        //设置登陆超时时间 10s
        session.connect(10000);
        //调用发送方法
        sshSending(session,nowPath,lastPath);

        session.disconnect();

    }

    /**
     * 发送文件
     * @param session 连接
     * @param nowPath 部署文件地址
     * @param lastPath 本机文件地址
     */
    private static void sshSending(Session session,String nowPath,String lastPath) throws JSchException, IOException, SftpException {

        ChannelSftp channel;
        //创建sftp通信通道
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = channel;

        //发送
        OutputStream outputStream  = sftp.put(nowPath);
        InputStream inputStream =  new FileInputStream(new File(lastPath));

        byte[] b = new byte[1024];
        int n;
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        //关闭流
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     *  执行shell脚本
     * @param proof 凭证信息
     * @param pipelineConfigure 配置信息
     * @param pipelineExecLog 日志信息
     * @return 执行状态
     * @throws IOException 日志读写异常
     */
    private int shell(Proof proof, PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog) throws IOException {

        String s ;
        String shell = pipelineConfigure.getPipelineDeploy().getDeployShell();
        if (shell != null){
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String[] s1 = shell.split("\n");
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+ "\n"+"执行shell命令" );
            ch.ethz.ssh2.Session session = null;
            for (String value : s1) {
                Connection  conn = new Connection(proof.getProofIp(),proof.getProofPort());
                conn.connect();
                conn.authenticateWithPassword(proof.getProofUsername(), proof.getProofPassword());
                session = conn.openSession();
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + "\n" + value);
                try {
                    session.execCommand(value);
                } catch (IOException e) {
                    // pipelineExecLog.setLogDeployState(1);
                    error(pipelineExecLog,value+" 命令错误"+"\n" +e, pipelineConfigure.getPipeline().getPipelineId());
                    return 0;
                }
                InputStream  stderr = session.getStdout();
                inputStreamReader = new InputStreamReader(stderr);
                bufferedReader = new BufferedReader(inputStreamReader);
                while ((s = bufferedReader.readLine()) != null) {
                   pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+s);
                }
            }
            if (session != null) {
                session.close();
            }
            if (inputStreamReader != null ) {
                inputStreamReader.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return 1;
    }

    /**
     * 获取符合条件的文件名
     * @param path 文件地址
     * @return 文件名
     */
    private static String address(String path,String s) {

        File f = new File(path);
        List<String> list = new ArrayList<>();
        File[] fa = f.listFiles();
        String regex = ".*."+s;
        if (fa != null){
            for (File fs : fa) {
                //判断符合条件的文件
                if (fs.getName().matches(regex)) {
                    if (!fs.isDirectory()) {
                        list.add(fs.getName());
                    }
                }
            }
            //获取符合条件的
            if (list.size() == 1){
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 输出错误信息
     * @param pipelineExecLog 日志
     * @param pipelineId 流水线id
     * @param e 错误信息
     */
    private  void  error(PipelineExecLog pipelineExecLog, String e, String pipelineId){

        if (pipelineExecLog.getLogRunLog() != null){
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+ "\n" + e + "\n" + " RUN RESULT : FAIL");
        }else {
            pipelineExecLog.setLogRunLog("\n" + e + "\n"+ " RUN RESULT : FAIL");
        }
        pipelineExecLog.setLogRunStatus(1);

        // 清空缓存
        clean(pipelineExecLog,pipelineId);
    }

    /**
     * 输出成功信息
     * @param pipelineExecLog 日志
     * @param pipelineId 流水线id
     */
    private  void  success(PipelineExecLog pipelineExecLog, String pipelineId) {

        if (pipelineExecLog.getLogRunLog() != null){
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+ "\n"  + "\n" + " RUN RESULT : SUCCESS");
        }else {
            pipelineExecLog.setLogRunLog( "\n"+ " RUN RESULT : SUCCESS");
        }
        pipelineExecLog.setLogRunStatus(30);
        //清空缓存
        clean(pipelineExecLog,pipelineId);

    }

    //清空缓存
    private  void clean(PipelineExecLog pipelineExecLog, String pipelineId){
        pipelineExecLogList.add(pipelineExecLog);

        //执行完成移除构建id
        if (pipelineIdList != null) {
            pipelineIdList.removeIf(id -> id.equals(pipelineId));
        }
        pipelineExecLogService.updateLog(pipelineExecLog);
        // pipelineExecLogService.addHistoryThree(pipelineId, pipelineExecLog.getLogId());

        //恢复中断状态
        try {
            Thread.sleep(1000);
        } catch (InterruptedException s) {
            Thread.currentThread().interrupt();
        }
        //清除集合缓存
        // pipelineExecLogList.removeIf(log -> log.getPipelineId().equals(pipelineId));
    }

    // 判断配置信息
    private void whetherNull(Pipeline pipeline, PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog) throws  IOException {
    //配置都存在
        int gitClone = gitClone(pipeline, pipelineConfigure, pipelineExecLog);
        if (gitClone != 0){
            int unitTesting = unitTesting(pipeline, pipelineConfigure, pipelineExecLog);
            if (unitTesting != 0){
                int structure = structure(pipeline, pipelineConfigure, pipelineExecLog);
                if (structure != 0){
                    int deploy = deploy(pipeline, pipelineConfigure, pipelineExecLog);
                    if (deploy != 0){
                        success(pipelineExecLog,pipeline.getPipelineId());
                    }
                }
            }
        }
    }
















}
