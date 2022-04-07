package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineConfigureServiceImpl;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.example.model.CodeGit.CodeGiteeApi;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.model.PipelineTest;
import com.doublekit.pipeline.example.service.codeGit.CodeGiteeApiService;
import com.doublekit.pipeline.instance.model.*;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import com.taobao.api.internal.spi.SpiUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import ch.ethz.ssh2.Connection;

import javax.print.attribute.standard.PrinterLocation;

@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    //存放过程状态
    List<PipelineExecLog> pipelineExecLogList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //启动
    @Override
    public int  start(String pipelineId){
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        ////判断同一任务是否在运行
        //if (pipelineIdList != null){
        //    for (String id : pipelineIdList) {
        //        if (id .equals(pipelineId)){
        //            return 100;
        //        }
        //    }
        //}
        //执行构建
        executorService.submit(() -> begin(pipelineId));
        return 1;
    }

    //查询构建状态
    @Override
    public PipelineExecLog findInstanceState(String pipelineId){

        if (pipelineExecLogList != null){
            for (PipelineExecLog pipelineExecLog : pipelineExecLogList) {
                if (pipelineExecLog.getPipelineId().equals(pipelineId)){
                    return  pipelineExecLog;
                }
            }
        }
        return null;
    }


    // 构建开始
    private String begin(String pipelineId) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //把执行构建的流水线加入进来
        pipelineIdList.add(pipelineId);
        PipelineConfigure pipelineConfigure = pipelineConfigureService.findPipelineIdConfigure(pipelineId);
        //创建日志
        PipelineExecLog pipelineExecLog = pipelineExecLogService.createLog();
        pipelineExecLog.setPipelineId(pipelineId);
        pipelineExecLogList.add(pipelineExecLog);
        // 判断配置信息
        whetherNull(pipelineConfigure, pipelineExecLog);

        //创建历史
        PipelineExecHistory history = new PipelineExecHistory();
        history.setHistoryWay(1);
        history.setHistoryCreateTime(time);
        if (pipelineConfigure.getPipelineCode() != null){
            history.setHistoryBranch(pipelineConfigure.getPipelineCode().getCodeBranch());
        }
        history.setPipelineId(pipelineId);
        history.setPipelineName(pipelineConfigure.getPipeline().getPipelineCreateUser());
        history.setHistoryStatus(pipelineExecLog.getLogRunStatus());
        history.setHistoryTime(pipelineExecLog.getLogRunTime());
        history.setLogId(pipelineExecLog.getLogId());
        pipelineExecLogService.createHistory(history);

        return "1";
    }

    // git克隆
    private int gitClone(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog){

        //设置状态
        pipelineExecLogList.add(pipelineExecLog);
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineCodeLog codeLog = pipelineExecLog.getCodeLog();
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        //设置代码路径
        String path = "D:\\clone\\" + pipeline.getPipelineName();
        File file = new File(path);
        //调用删除方法删除旧的代码
        deleteFile(file);
        //获取凭证信息
        PipelineCode pipelineCode = pipelineConfigure.getPipelineCode();
        if (pipelineCode != null){
            Proof proof = pipelineConfigureService.findCodeProof(pipelineConfigure);
            String codeAddress = pipelineConfigure.getPipelineCode().getCodeAddress();
            String codeBranch = pipelineConfigure.getPipelineCode().getCodeBranch();
            UsernamePasswordCredentialsProvider credentialsProvider = usernamePassword(proof.getProofUsername(), proof.getProofPassword());

            //更新日志
            String s = "开始拉取代码 : " + "\n"  + "FileAddress : " + file + "\n"  + "Uri : " + codeAddress + "\n"  + "Branch : " + codeBranch + "\n"  ;
            codeLog.setCodeRunLog(s);
            pipelineExecLog.setLogRunLog(s);
            pipelineExecLogList.add(pipelineExecLog);

            //克隆代码
            try {
                clone(file, codeAddress, credentialsProvider, codeBranch);
            } catch (GitAPIException e) {
                codeLog.setCodeRunStatus(1);
                codeLog.setCodeRunLog(codeLog.getCodeRunLog()+"\n拉取代码异常"+e.toString());
                pipelineExecLog.setCodeLog(codeLog);
                pipelineExecLogService.updateLog(pipelineExecLog);
                error(pipelineExecLog, "拉取代码异常"+e.toString(),pipeline.getPipelineId());
                return 0;
            }

            //更新状态以及日志
            String log = s + "proofType : " +proof.getProofType() + "\n" + "clone成功。。。。。。。。。。。。。。。" + "\n";
            codeLog.setCodeRunLog(log);
            pipelineExecLog.setLogRunLog(log);
            long overTime = new Timestamp(System.currentTimeMillis()).getTime();
            int time = (int) (overTime - beginTime) / 1000;
            codeLog.setCodeRunTime(time);
            pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
        }
        codeLog.setCodeRunStatus(10);
        pipelineExecLog.setCodeLog(codeLog);
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecLogList.add(pipelineExecLog);
        return 1;
    }

    // 单元测试
    private int unitTesting(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog) {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineTestLog testLog = pipelineExecLog.getTestLog();
        testLog.setTestRunLog("");
        String testOrder = pipelineConfigure.getPipelineTest().getTestOrder();
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = testOrder.split("\n");
        for (String s : split) {
            try {
                Process process = process(path, s, null);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + a);
                InputStreamReader  inputStreamReader = new InputStreamReader(process.getInputStream());
                //测试执行失败
                Map<String, String> map = log(inputStreamReader, pipelineExecLog);
                if (map.get("state").equals("0")){
                    long overTime = new Timestamp(System.currentTimeMillis()).getTime();
                    testLog.setTestRunLog(testLog.getTestRunLog()+map.get("log"));
                    int time = (int) (overTime - beginTime) / 1000;
                    testLog.setTestRunTime(time);
                    testLog.setTestRunStatus(1);
                    testLog.setTestRunLog(testLog.getTestRunLog()+"\n"+map.get("log")+"测试失败。");
                    pipelineExecLog.setTestLog(testLog);
                    pipelineExecLogService.updateLog(pipelineExecLog);
                    error(pipelineExecLog,"测试执行失败。", pipelineConfigure.getPipeline().getPipelineId());
                    return 0;
                }
                //成功
                long overTime = new Timestamp(System.currentTimeMillis()).getTime();
                int time = (int) (overTime - beginTime) / 1000;
                testLog.setTestRunLog(a+testLog.getTestRunLog()+map.get("log"));
                testLog.setTestRunTime(time);
                testLog.setTestRunStatus(10);
                pipelineExecLog.setTestLog(testLog);
                pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
                pipelineExecLogService.updateLog(pipelineExecLog);
                pipelineExecLogList.add(pipelineExecLog);
            } catch (IOException e) {
                long overTime = new Timestamp(System.currentTimeMillis()).getTime();
                int time = (int) (overTime - beginTime) / 1000;
                testLog.setTestRunTime(time);
                testLog.setTestRunStatus(1);
                pipelineExecLogList.add(pipelineExecLog);
                error(pipelineExecLog,"测试失败。。。。。"+ e, pipelineConfigure.getPipeline().getPipelineId());
                return 0;
            }
        }
        return 1;
    }

    // 构建
    private int structure(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog)  {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineStructureLog structureLog = pipelineExecLog.getStructureLog();
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
                //构建失败
                InputStreamReader  inputStreamReader = new InputStreamReader(process.getInputStream());
                Map<String, String> map = log(inputStreamReader, pipelineExecLog);
                if (map.get("state").equals("0")){
                    structureError(pipelineExecLog,beginTime);
                    error(pipelineExecLog,"构建失败。。。。", pipelineConfigure.getPipeline().getPipelineId());
                    return 0;
                }

                long overTime = new Timestamp(System.currentTimeMillis()).getTime();
                int time = (int) (overTime - beginTime) / 1000;
                structureLog.setStructureRunStatus(10);
                structureLog.setStructureRunLog(a+map.get("log"));
                structureLog.setStructureRunTime(time);
                pipelineExecLog.setStructureLog(structureLog);
                pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
                pipelineExecLogService.updateLog(pipelineExecLog);
                pipelineExecLogList.add(pipelineExecLog);
            } catch (IOException e) {
                structureError(pipelineExecLog,beginTime);
                error(pipelineExecLog,"构建异常："+ e, pipelineConfigure.getPipeline().getPipelineId());
                return 0;
            }
        }
        return 1;
    }

    //构建错误
    private void structureError(PipelineExecLog pipelineExecLog,long beginTime){
        PipelineStructureLog structureLog = pipelineExecLog.getStructureLog();
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        structureLog.setStructureRunStatus(1);
        structureLog.setStructureRunTime(time);
        pipelineExecLog.setStructureLog(structureLog);
        pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecLogList.add(pipelineExecLog);
    }

     // 部署
    private int deploy(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog) {
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineDeployLog deployLog = pipelineExecLog.getDeployLog();
        String deployTargetAddress = pipelineConfigure.getPipelineDeploy().getDeployTargetAddress();
        String deployAddress = pipelineConfigure.getPipelineDeploy().getDeployAddress();
        pipelineExecLogList.add(pipelineExecLog);
        Proof proof = pipelineConfigureService.findDeployProof(pipelineConfigure);

        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        String s = "部署到服务器" + proof.getProofIp() + "。。。。。。。。";
        deployLog.setDeployRunLog(s);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+s);
        //文件地址
        String[] split = deployTargetAddress.split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";

        //发送文件名
        String address = address(path,split[1]);
        path  = path + "\\" +address ;

        //发送文件位置
        deployAddress = deployAddress +"/"+ address;
        try {
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"开始发送文件:"+path);
            deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n"+"开始发送文件:"+path);
            //发送文件
            sshSftp(proof,deployAddress,path);
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"文件:"+address+"发送成功！");
            deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n"+"文件:"+address+"发送成功！");
            //执行shell
            String shell = pipelineConfigure.getPipelineDeploy().getDeployShell();
            if (shell != null){
                String[] s1 = shell.split("\n");
                for (String value : s1) {
                     sshOrder(proof, value, pipelineExecLog);
                }
            }

        } catch (JSchException | SftpException | IOException e) {
            long overTime = new Timestamp(System.currentTimeMillis()).getTime();
            deployLog.setDeployRunTime((int) (overTime - beginTime) / 1000);
            deployLog.setDeployRunStatus(1);
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"文件:"+address+"发送失败！");
            deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n"+"文件:"+address+"发送失败！" + e);
            error(pipelineExecLog,e.toString(),pipeline.getPipelineId());
            return 0;
        }
        //更新状态
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        deployLog.setDeployRunTime(time);
        deployLog.setDeployRunStatus(10);
        pipelineExecLog.setDeployLog(deployLog);
        pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"服务器部署:"+proof.getProofIp()+"成功!");
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
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     * @throws RuntimeException 拉取异常
     */
    private void clone(File gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws GitAPIException {
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
    private boolean deleteFile(File dir){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteFile(new File(dir, child));
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
         *  执行ssh命令
     * @param proof 凭证信息
     * @param order 执行命令
     * @param pipelineExecLog 日志信息
     * @return 执行状态
     * @throws IOException 日志读写异常
     */
    private  Map<String, String> sshOrder(Proof proof,String order, PipelineExecLog pipelineExecLog) throws IOException {
        Connection  conn = new Connection(proof.getProofIp(),proof.getProofPort());
        conn.connect();
        conn.authenticateWithPassword(proof.getProofUsername(), proof.getProofPassword());
        ch.ethz.ssh2.Session session = conn.openSession();
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + "\n" + order);
        session.execCommand(order);
        InputStreamReader inputStreamReader = new InputStreamReader(session.getStdout());
        Map<String, String> map = log(inputStreamReader, pipelineExecLog);
        session.close();
        inputStreamReader.close();
        return map;
    }
    
    private int docker(PipelineConfigure pipelineConfigure,PipelineExecLog pipelineExecLog) {
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineDeploy pipelineDeploy = pipelineConfigure.getPipelineDeploy();
        Proof proof = pipelineConfigureService.findDeployProof(pipelineConfigure);
        PipelineDeployLog deployLog = pipelineExecLog.getDeployLog();
        //模块名
        String[] split = pipelineDeploy.getDeployTargetAddress().split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"模块地址path: "+path);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"文件后缀："+split[1]);
        //文件名称
        String zipName = address(path,split[1]);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"压缩包文件名为fileName： "+zipName);
        //本机文件地址
        String fileAddress  = path + "\\" +zipName ;
        String  fileName = null;
        logger.info("压缩包文件地址 ： " +fileAddress);
        if (zipName != null) {
            String[] split1 = zipName.split(".zip");
            String[] split2 = split1[0].split("-distribution");
            fileName = split2[0];
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"解压文件名称"+split2[0]);
        }
        String liunxAddress = pipelineConfigure.getPipelineDeploy().getDeployAddress();
        //发送文件位置
        String deployAddress = liunxAddress+ "/" +zipName ;
        logger.info("部署到Liunx文件地址 ： " +deployAddress);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"部署到Liunx文件地址 ： " +deployAddress);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"发送文件中。。。。。");
        try {
            sshSftp(proof,deployAddress,fileAddress);
        } catch (JSchException | SftpException | IOException e) {
            deployLog.setDeployRunLog("文件发送错误"+e);
            error(pipelineExecLog,"发送文件错误："+e,pipeline.getPipelineId());
        }
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1,"rm -rf "+" "+liunxAddress+ "/" +fileName);
        map.put(2,"unzip"+" "+deployAddress);
        map.put(3,"docker stop $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
        map.put(4,"docker rm $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
        map.put(5,"docker image rm"+" "+pipeline.getPipelineName());
        map.put(6,"find"+" "+liunxAddress+"/"+fileName+" "+ "-name '*.sh' | xargs dos2unix");
        map.put(7,"cd"+" "+fileName+";"+"docker image build -t"+" "+pipeline.getPipelineName()+"  .");
        map.put(8,"docker run -itd -p 8080:8080"+" "+pipeline.getPipelineName());
        for (int i = 1; i <= 8; i++) {
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"第"+i+"步 ："+ map.get(i));
//            Map<String, String> log = sshOrder(proof, map.get(i), pipelineExecLog);

        }
        return 1;
    }

    /**
     * 执行产生的日志
     * @param inputStreamReader 执行信息
     * @param pipelineExecLog 日志信息
     * @throws IOException 字符流装换异常
     */
    private Map<String, String> log(InputStreamReader inputStreamReader , PipelineExecLog pipelineExecLog) throws IOException {
        Map<String, String> map = new HashMap<>();
        String s;
        //InputStreamReader  inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        String logRunLog = "";
        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog = logRunLog + s + "\n";
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+s+"\n");
            pipelineExecLogList.add(pipelineExecLog);
            if (logRunLog.contains("BUILD FAILURE")){
                pipelineExecLogList.add(pipelineExecLog);
                map.put("state","0");
                map.put("log",logRunLog);
                return map;
            }
        }
        map.put("state","1");
        map.put("log",logRunLog);
        inputStreamReader.close();
        bufferedReader.close();
        return map;
    }

    /**
     * 获取符合条件的文件名
     * @param path 文件地址
     * @param s 文件后缀名
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
        //恢复中断状态
        try {
            Thread.sleep(1000);
        } catch (InterruptedException s) {
            Thread.currentThread().interrupt();
        }
        // 清除集合缓存
        pipelineExecLogList.removeIf(log -> log.getPipelineId().equals(pipelineId));
    }

    // 判断配置信息
    private void whetherNull(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog){


        int gitClone = gitClone(pipelineConfigure, pipelineExecLog);
        if (gitClone == 1){
            int i = unitTesting(pipelineConfigure, pipelineExecLog);
            if (i == 1){
                int structure = structure(pipelineConfigure, pipelineExecLog);
                if (structure == 1 ){
                    int deploy = 0;
                     deploy = docker(pipelineConfigure, pipelineExecLog);
                    if (deploy == 1 ){
                        success(pipelineExecLog,pipelineConfigure.getPipeline().getPipelineId());
                    }
                }
            }
        }

    }
















}
