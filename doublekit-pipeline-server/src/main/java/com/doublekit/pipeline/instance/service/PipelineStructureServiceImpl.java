package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineLog;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import com.sun.xml.bind.v2.model.core.ID;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.lang.management.PlatformLoggingMXBean;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import ch.ethz.ssh2.Connection;

@Service
@Exporter
public class PipelineStructureServiceImpl implements PipelineStructureService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineLogService pipelineLogService;

    //存放过程状态
    List<PipelineLog> pipelineLogList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();


    //开始构建
    @Override
    public String  pipelineStructure(String pipelineId){

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
    public PipelineLog selectStructureState(String pipelineId){

        if (pipelineLogList != null){
            for (PipelineLog log : pipelineLogList) {
                if (pipelineId.equals(log.getPipelineId())){
                    return log;
                }
            }
        }
        return null;
    }

    //历史表添加信息
    @Override
    public PipelineHistory pipelineHistoryTwo(String pipelineId){
        //格式化时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        PipelineHistory pipelineHistory = new PipelineHistory();
        PipelineHistory History = pipelineConfigureService.pipelineHistoryOne(pipelineId, pipelineHistory);
        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);
        //添加信息
        pipelineHistory.setPipelineConfigure(History.getPipelineConfigure());
        pipelineHistory.setProof(History.getProof());
        pipelineHistory.setPipeline(pipeline);
        pipelineHistory.setHistoryCreateTime(dateFormat.format(new Date()));
        return  pipelineHistory;
    }

    //构建
    private String start(String pipelineId) throws IOException{

        PipelineLog pipelineLog =new PipelineLog();
        //把执行构建的流水线加入进来
        pipelineIdList.add(pipelineId);
        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);
        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);
        String logId = pipelineLogService.createPipelineLog(pipelineLog);
        pipelineLog.setLogId(logId);
        pipelineLog.setPipelineId(pipelineId);
        pipelineLogList.add(pipelineLog);

        //判断配置信息
        whetherNull(pipeline,pipelineConfigure,pipelineLog);

        return "1";

    }

    /**
     * 克隆
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     */
    private int gitClone(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog){

        String logId =pipelineLog.getLogId();

        pipelineLog.setLogCodeState(2);
        pipelineLogList.add(pipelineLog);
        String pipelineId = pipeline.getPipelineId();

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        //设置代码路径
        String path = "D:\\clone\\" + pipeline.getPipelineName();

        File file = new File(path);
        //调用删除方法删除旧的代码
        delete(file);
        //获取凭证信息
        Proof proof = pipelineConfigureService.getProofIdStructure(pipelineId);
        if (proof != null) {
            //获取凭证
            UsernamePasswordCredentialsProvider credentialsProvider = usernamePassword(proof.getProofUsername(), proof.getProofPassword());
            String s = "开始拉取代码 : " + "\n"  + "FileAddress : " + file + "\n"  + "Uri : " + pipelineConfigure.getConfigureCodeSourceAddress() + "\n"   +  "Branch : " + pipelineConfigure.getConfigureBranch() + "\n"  ;

            //克隆代码
            try {
                gitClone(file, pipelineConfigure.getConfigureCodeSourceAddress(), credentialsProvider, pipelineConfigure.getConfigureBranch());
            } catch (GitAPIException e) {
                pipelineLog.setLogCodeState(1);
                error(pipelineLog, "拉取代码异常"+e.toString(),pipeline.getPipelineId());
                return 0;
            }

            //更新状态
            String proofType = "proofType : " +proof.getProofType() + "\n";
            String success = "拉取成功。。。。。。。。。。。。。。。" + "\n";
            String log = s + proofType + success;
            pipelineLog.setLogId(logId);
            pipelineLog.setLogRunLog(log);

            long overTime = new Timestamp(System.currentTimeMillis()).getTime();

            pipelineLog.setLogCodeTime((int)(overTime-beginTime)/1000);
        }
        pipelineLog.setLogCodeState(10);
        pipelineLogList.add(pipelineLog);
        return 1;
    }

    //单元测试
    private int unitTesting(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) {
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = pipelineConfigure.getConfigureTestText().split("\n");
        for (String s : split) {
            try {
                Process process = process(path, s, pipelineConfigure.getConfigureStructureAddress());
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineLog.setLogRunLog(pipelineLog.getLogRunLog() + a);
                log(process,pipelineLog);
                pipelineLog.setLogTestState(10);
                pipelineLogList.add(pipelineLog);
            } catch (IOException e) {
                pipelineLog.setLogTestState(1);
                pipelineLogList.add(pipelineLog);
                error(pipelineLog,"构建异常："+ e,pipelineConfigure.getPipelineId());
                return 0;
            }
        }
        return 1;
    }

    //构建
    private int  structure(Pipeline pipeline, PipelineConfigure pipelineConfigure,PipelineLog pipelineLog)  {
        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = pipelineConfigure.getConfigureStructureOrder().split("\n");
        for (String s : split) {
            try {
                Process process = process(path, s, pipelineConfigure.getConfigureStructureAddress());
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineLog.setLogRunLog(pipelineLog.getLogRunLog() + a);
                log(process,pipelineLog);
                pipelineLog.setLogPackState(10);
                pipelineLogList.add(pipelineLog);
            } catch (IOException e) {
                pipelineLog.setLogPackState(1);
                pipelineLogList.add(pipelineLog);
                error(pipelineLog,"构建异常："+ e,pipelineConfigure.getPipelineId());
                return 0;
            }
        }
        return 1;
    }

     //部署
    private int deploy(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws IOException {

        String logId =pipelineLog.getLogId();
        pipelineLog.setLogDeployTime(1);
        pipelineLogList.add(pipelineLog);
        Proof proof = pipelineConfigureService.getProofIdDeploy(pipeline.getPipelineId());

        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+"\n"+"部署到服务器"+proof.getProofIp()+"。。。。。。。。");
        //文件地址
        String[] split = pipelineConfigure.getConfigureTargetAddress().split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";

        //发送文件名
        String address = address(path,split[1]);
        path  = path + "\\" +address ;

        //发送文件位置
        String configureDeployAddress = pipelineConfigure.getConfigureDeployAddress();
        configureDeployAddress = configureDeployAddress +"/"+ address;

        //调用发送方法
        try {
            pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+"\n"+"开始发送文件:"+path);
            sshSftp(proof,configureDeployAddress,path);
            pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+"\n"+"文件:"+address+"发送成功！");
        } catch (JSchException | SftpException | IOException e) {
            pipelineLog.setLogDeployState(1);
            error(pipelineLog,e.toString(),pipeline.getPipelineId());
            return 0;
        }
        //执行shell
        int shell = shell(proof, pipelineConfigure, pipelineLog);
        if (shell == 0){
            return 0;
        }
        pipelineLog.setLogId(logId);
        //获取构建所用时长
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineLog.setLogDeployTime((int) (overTime-beginTime)/1000);
        pipelineLog.setLogDeployState(10);
        pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+"\n"+"服务器部署:"+proof.getProofIp()+"成功!");
        pipelineLogList.add(pipelineLog);
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
     * @param pipelineLog 日志信息
     * @throws IOException 字符流装换异常
     */
    private void log(Process process ,PipelineLog pipelineLog) throws IOException {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        String s;
        InputStreamReader  inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        String logId =pipelineLog.getLogId();
        String logRunLog = pipelineLog.getLogRunLog();
        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog = logRunLog + s + "\n";
            pipelineLog.setLogId(logId);
            pipelineLog.setLogRunLog(logRunLog);
            long overTime = new Timestamp(System.currentTimeMillis()).getTime();
            //获取构建所用时长
            if (pipelineLog.getLogTestState() == 0){
                pipelineLog.setLogTestTime((int) (overTime-beginTime)/1000);
            }else {
                pipelineLog.setLogPackTime((int) (overTime-beginTime)/1000);
            }
            pipelineLogList.add(pipelineLog);
        }
        inputStreamReader.close();
        bufferedReader.close();
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
     * @param pipelineLog 日志信息
     * @return 执行状态
     * @throws IOException 日志读写异常
     */
    private int shell(Proof proof,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws IOException {

        String s ;
        String shell = pipelineConfigure.getConfigureShell();
        if (shell != null){
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String[] s1 = shell.split("\n");
            pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+ "\n"+"执行shell命令" );
            ch.ethz.ssh2.Session session = null;
            for (String value : s1) {
                Connection  conn = new Connection(proof.getProofIp(),proof.getProofPort());
                conn.connect();
                conn.authenticateWithPassword(proof.getProofUsername(), proof.getProofPassword());
                session = conn.openSession();
                pipelineLog.setLogRunLog(pipelineLog.getLogRunLog() + "\n" + value);
                try {
                    session.execCommand(value);
                } catch (IOException e) {
                    pipelineLog.setLogDeployState(1);
                    error(pipelineLog,value+" 命令错误"+"\n" +e,pipelineConfigure.getPipelineId());
                    return 0;
                }
                InputStream  stderr = session.getStdout();
                inputStreamReader = new InputStreamReader(stderr);
                bufferedReader = new BufferedReader(inputStreamReader);
                while ((s = bufferedReader.readLine()) != null) {
                   pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+"\n"+s);
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
     * @param pipelineLog 日志
     * @param pipelineId 流水线id
     * @param e 错误信息
     */
    private  void  error(PipelineLog pipelineLog ,String e,String pipelineId){

        if (pipelineLog.getLogRunLog() != null){
            pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+ "\n" + e + "\n" + " RUN RESULT : FAIL");
        }else {
            pipelineLog.setLogRunLog("\n" + e + "\n"+ " RUN RESULT : FAIL");
        }
        pipelineLog.setLogRunStatus(1);

        // 清空缓存
        clean(pipelineLog ,pipelineId);
    }

    /**
     * 输出成功信息
     * @param pipelineLog 日志
     * @param pipelineId 流水线id
     */
    private  void  success(PipelineLog pipelineLog ,String pipelineId) {

        if (pipelineLog.getLogRunLog() != null){
            pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+ "\n"  + "\n" + " RUN RESULT : SUCCESS");
        }else {
            pipelineLog.setLogRunLog( "\n"+ " RUN RESULT : SUCCESS");
        }
        pipelineLog.setLogRunStatus(30);

        //清空缓存
        clean(pipelineLog ,pipelineId);

    }

    //清空缓存
    private  void clean(PipelineLog pipelineLog ,String pipelineId){
        pipelineLogList.add(pipelineLog);

        //执行完成移除构建id
        if (pipelineIdList != null) {
            pipelineIdList.removeIf(id -> id.equals(pipelineId));
        }
        pipelineLogService.updatePipelineLog(pipelineLog);

        pipelineLogService.pipelineHistoryThree(pipelineId,pipelineLog.getLogId());

        //恢复中断状态
        try {
            Thread.sleep(100);
        } catch (InterruptedException s) {
            Thread.currentThread().interrupt();
        }
        //清除集合缓存
        pipelineLogList.removeIf(log -> log.getPipelineId().equals(pipelineId));
    }

    //判断配置信息
    private void whetherNull(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws  IOException {

        int  a= pipelineConfigure.getConfigureCodeSource()+pipelineConfigure.getConfigureTestType()+pipelineConfigure.getConfigureCodeStructure() ;
        //所有配置都为空
        if (a == 0){
            pipelineLog.setLogTestState(10);
            pipelineLog.setLogDeployState(10);
            pipelineLog.setLogCodeState(10);
            pipelineLog.setLogPackState(10);
            success(pipelineLog,pipeline.getPipelineId());
            return;
        }

        // //只有git
        // if (pipelineConfigure.getConfigureCodeSource() == 2 &&pipelineConfigure.getConfigureCodeStructure() ==1 && pipelineConfigure.getDeployProofId() == null){
        //     int gitClone = gitClone(pipeline, pipelineConfigure, pipelineLog);
        //     if (gitClone == 1){
        //         success(pipelineLog,pipeline.getPipelineId());
        //     }
        //     return;
        // }
        //
        // //只有构建
        // if(pipelineConfigure.getConfigureCodeSource() == 1 && pipelineConfigure.getConfigureCodeStructure() ==2 && pipelineConfigure.getDeployProofId() == null){
        //     pipelineLog.setLogCodeState(10);
        //     int structure = structure(pipeline, pipelineConfigure, pipelineLog);
        //     if (structure ==1){
        //         success(pipelineLog,pipeline.getPipelineId());
        //     }
        // }
        //
        // //只有部署
        // if(pipelineConfigure.getConfigureCodeSource() == 1 && pipelineConfigure.getConfigureCodeStructure() ==1 && pipelineConfigure.getDeployProofId() != null){
        //     pipelineLog.setLogCodeState(10);
        //     pipelineLog.setLogPackState(10);
        //     int deploy = deploy(pipeline, pipelineConfigure, pipelineLog);
        //     if (deploy == 1){
        //         success(pipelineLog,pipeline.getPipelineId());
        //     }
        // }
        //
        // //git部署
        // if(pipelineConfigure.getConfigureCodeSource() == 2 && pipelineConfigure.getConfigureCodeStructure() ==1 && pipelineConfigure.getDeployProofId() != null){
        //     pipelineLog.setLogPackState(10);
        //     int gitClone = gitClone(pipeline, pipelineConfigure, pipelineLog);
        //     int deploy = deploy(pipeline, pipelineConfigure, pipelineLog);
        //     if (gitClone + deploy == 2){
        //         success(pipelineLog,pipeline.getPipelineId());
        //     }
        // }
        //
        // //git构建
        // if(pipelineConfigure.getConfigureCodeSource() == 2 && pipelineConfigure.getConfigureCodeStructure() ==1 && pipelineConfigure.getDeployProofId() == null){
        //     pipelineLog.setLogDeployState(10);
        //     int gitClone = gitClone(pipeline, pipelineConfigure, pipelineLog);
        //     int structure = structure(pipeline, pipelineConfigure, pipelineLog);
        //     if (gitClone + structure == 2){
        //         success(pipelineLog,pipeline.getPipelineId());
        //     }
        // }
        //
        // // 构建部署
        // if (pipelineConfigure.getConfigureCodeSource() == 1 && pipelineConfigure.getConfigureCodeStructure() ==2 && pipelineConfigure.getDeployProofId() != null){
        //     pipelineLog.setLogCodeState(10);
        //     int structure = structure(pipeline, pipelineConfigure, pipelineLog);
        //     int deploy = deploy(pipeline, pipelineConfigure, pipelineLog);
        //     if (structure + deploy == 2){
        //         success(pipelineLog,pipeline.getPipelineId());
        //     }
        // }

        //配置都有

        if (a==6){
            int gitClone = gitClone(pipeline, pipelineConfigure, pipelineLog);
            int unitTesting = unitTesting(pipeline, pipelineConfigure, pipelineLog);
            int structure = structure(pipeline, pipelineConfigure, pipelineLog);
            int deploy = deploy(pipeline, pipelineConfigure, pipelineLog);
            if (gitClone+unitTesting+structure+deploy == 4){
                success(pipelineLog,pipeline.getPipelineId());
            }
        }

    }




}
