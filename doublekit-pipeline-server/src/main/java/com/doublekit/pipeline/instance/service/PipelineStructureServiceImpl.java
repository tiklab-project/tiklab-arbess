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
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
@Exporter
public class PipelineStructureServiceImpl implements PipelineStructureService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineLogService pipelineLogService;

    //格式化时间
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");

    //存放过程状态
    List<PipelineLog> pipelineLogList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();


    //开始构建
    @Override
    public String  pipelineStructure(String pipelineId) throws Exception {

        //判断同一任务是否在运行
        if (pipelineIdList != null){

            for (String id : pipelineIdList) {

                if (id .equals(pipelineId)){

                    return "100";
                }
            }
        }

        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //执行构建

        executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                return structure(pipelineId);
            }
        });

        Thread.sleep(4000);

        return "1";
    }

    //查询构建状态
    @Override
    public PipelineLog selectStructureState(){

        if (pipelineLogList != null){

            for (PipelineLog log : pipelineLogList) {

                if (log.getLogRunStatus() == 30 || log.getLogRunStatus() == 1){

                    pipelineLogList.clear();
                }
                return log;
            }
        }
        return null;
    }

    //历史表添加信息
    @Override
    public PipelineHistory pipelineHistoryTwo(String pipelineId){

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
    private String  structure(String pipelineId){

        PipelineLog pipelineLog =new PipelineLog();

        //把执行构建的流水线加入进来
        pipelineIdList.add(pipelineId);

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        String logId = pipelineLogService.createPipelineLog(pipelineLog);

        pipelineLog.setLogId(logId);

        //无配置信息
        if (pipelineConfigure.getConfigureCodeSource().equals("a") && pipelineConfigure.getConfigureCodeStructure().equals("b")){

            allEmpty(pipelineLog);

        //构建配置为空
        }else if (pipelineConfigure.getConfigureCodeSource().equals("b") && pipelineConfigure.getConfigureCodeStructure().equals("b")){

            structureNull(pipeline, pipelineConfigure, pipelineLog);

        //配置都有
        }else if (pipelineConfigure.getConfigureCodeSource().equals("b") && pipelineConfigure.getConfigureCodeStructure().equals("a")){

            noNull(pipeline, pipelineConfigure, pipelineLog);

        // 克隆配置为空
        }else if (pipelineConfigure.getConfigureCodeSource().equals("a") && pipelineConfigure.getConfigureCodeStructure().equals("a")){

            cloneNull(pipeline, pipelineConfigure, pipelineLog);

        }

        pipelineLogList.add(pipelineLog);

        //更新数据库
        pipelineLogService.updatePipelineLog(pipelineLog);

        //执行完成移除构建id
        if (pipelineIdList != null) {
            pipelineIdList.removeIf(id -> id.equals(pipelineId));
        }
        //创建历史表
        return pipelineLogService.pipelineHistoryThree(pipelineId, logId);

    }

    /**
     * 克隆
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     * @return 构建状态
     */
    private int  gitClone(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) {

        String logId =pipelineLog.getLogId();

            String pipelineId = pipeline.getPipelineId();

            //开始运行时间
            String last = dateFormat.format(new Date());

            //设置代码路径
            String path = "D:\\clone\\" + pipeline.getPipelineName();

            //判断是否从git拉取代码
            if (pipelineConfigure.getConfigureCodeSource().equals("b")) {

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

                        error(pipelineLog, e.toString());

                        return 0;

                    }

                    String proofType = "proofType : " +proof.getProofType() + "\n";

                    String success = "拉取成功。。。。。。。。。。。。。。。" + "\n";

                    String log = s + proofType + success;

                    pipelineLog.setLogId(logId);

                    pipelineLog.setLogRunLog(log);

                    try {

                        pipelineLog.setLogCodeTime((int)time(dateFormat.format(new Date()), last));

                    } catch (ParseException e) {

                        pipelineLog.setLogCodeState(1);

                        error(pipelineLog, e.toString());

                        return 0;
                    }

                    pipelineLogList.add(pipelineLog);

                    return 1;
                }
        }
        return 0;
    }

    /**
     * 构建
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     * @return 构建状态

     */
    private int write(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) {

        String logId =pipelineLog.getLogId();

        //开始运行时间
        String last = dateFormat.format(new Date());

        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();

        String sourceAddress = pipelineConfigure.getConfigureStructureAddress();

        //获取构建命令
        String order = "mvn"+" " + pipelineConfigure.getConfigureStructureOrder();

        //判断是否从git拉取代码
        if (pipelineConfigure.getConfigureCodeSource().equals("b")) {

            if (pipelineConfigure.getConfigureStructureOrder() != null) {

                String s;

                //调用构建和输出日志方法
                Process process ;

                try {

                    process = structure(path, order,sourceAddress);

                } catch (IOException e) {

                    pipelineLog.setLogPackState(1);

                    error(pipelineLog,e.toString());

                    return 0;
                }

                InputStreamReader inputStreamReader;

                BufferedReader bufferedReader;

                if (process != null) {

                    inputStreamReader = new InputStreamReader(process.getInputStream());

                    bufferedReader = new BufferedReader(inputStreamReader);

                    String a = "开始执行 : " + " ' " + pipelineConfigure.getConfigureStructureOrder() + " ' " + " 命令" + "\n";

                    pipelineLog.setLogRunLog(pipelineLog.getLogRunLog() + a);

                    String logRunLog = pipelineLog.getLogRunLog();

                    try {
                        while ((s = bufferedReader.readLine()) != null) {

                            logRunLog = logRunLog + s + "\n";

                            pipelineLog.setLogId(logId);

                            pipelineLog.setLogPackState(0);

                            pipelineLog.setLogRunLog(logRunLog);

                            //获取构建所用时长
                            try {
                                pipelineLog.setLogPackTime((int) time(dateFormat.format(new Date()), last));

                            } catch (ParseException e) {

                                pipelineLog.setLogPackState(1);

                                error(pipelineLog, e.toString());

                                return 0;
                            }

                            pipelineLogList.add(pipelineLog);

                        }
                    } catch (IOException e) {

                        pipelineLog.setLogPackState(1);

                        error(pipelineLog, e.toString());

                        return 0;
                    }

                    try {
                        inputStreamReader.close();

                        bufferedReader.close();

                    } catch (IOException e) {
                        pipelineLog.setLogPackState(1);

                        error(pipelineLog, e.toString());

                        return 0;
                    }


                    return 1;
                }
                else {

                    pipelineLog.setLogPackState(1);

                    error(pipelineLog,"构建命令错误");

                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * 部署
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     * @return 构建状态
     */
    private int deploy(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) {

        String logId =pipelineLog.getLogId();

        pipelineLog.setLogDeployTime(1);

        pipelineLogList.add(pipelineLog);

        Proof proof = pipelineConfigureService.getProofIdDeploy(pipeline.getPipelineId());

        //开始运行时间
        String last = dateFormat.format(new Date());

        //
        String[] split = split(pipelineConfigure.getConfigureTargetAddress());

        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";

        //发送文件名
        String address = address(path,split[1]);

        path  = path + "\\" +address ;

        //发送文件位置
        String configureDeployAddress = pipelineConfigure.getConfigureDeployAddress();

        configureDeployAddress = configureDeployAddress +"/"+ address;

        //调用发送方法
        try {
            sshSftp(pipelineConfigure.getConfigureDeployIp(),proof,configureDeployAddress,path);
        } catch (JSchException | SftpException | IOException e) {

            pipelineLog.setLogDeployState(1);

            error(pipelineLog,e.toString());

            return 0;
        }

        pipelineLog.setLogId(logId);

        //获取构建所用时长
        String format = dateFormat.format(new Date());

        try {
            pipelineLog.setLogDeployTime((int) time(format, last));
        } catch (ParseException e) {

            pipelineLog.setLogDeployState(1);

            error(pipelineLog,e.toString());

            return 0;
        }

        pipelineLogList.add(pipelineLog);

        return 1;

    }

    /**
     * 调用构建命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
    private Process structure(String path,String order,String sourceAddress) throws IOException {

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
     * 获取时间差
     * @param now 现在
     * @param last 过去
     * @return 时间差
     * @throws ParseException 时间转换异常
     */
    private long time(String now ,String last) throws ParseException {

        long    l = dateFormat.parse(now).getTime()-dateFormat.parse(last).getTime();

        long day=l/(24*60*60*1000);

        long hour=(l/(60*60*1000)-day*24);

        long min=((l/(60*1000))-day*24*60-hour*60);

        long s = (l/1000-day*24*60*60-hour*60*60-min*60);

        if (min>0){
            return min*60 + s;
        }
        return s;
    }

    /**
     * 没有配置
     * @param pipelineLog 日志
     */
    private void allEmpty(PipelineLog pipelineLog){

        pipelineLog.setLogCodeState(10);

        pipelineLog.setLogCodeTime(1);

        pipelineLog.setLogPackState(10);

        pipelineLog.setLogPackTime(1);

        pipelineLog.setLogDeployState(10);

        pipelineLog.setLogDeployTime(1);

        pipelineLog.setLogRunStatus(30);

    }

    /**
     * 克隆配置为空
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     */
    private void cloneNull(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog)  {

        //调用构建方法
        int write = write(pipeline, pipelineConfigure, pipelineLog);

        pipelineLog.setLogPackState(10);

        if (write == 0){
            pipelineLog.setLogPackState(0);
        }

        //调用部署方法
        int deploy = deploy(pipeline, pipelineConfigure, pipelineLog);

        pipelineLog.setLogDeployState(10);

        pipelineLog.setLogRunStatus(30);

        if (deploy == 0){

            pipelineLog.setLogDeployState(1);

            pipelineLog.setLogRunStatus(1);
        }

    }

    /**
     * 配置都有
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     */
    private void noNull(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog)  {

        //调用克隆方法
        int gitClone = gitClone(pipeline, pipelineConfigure, pipelineLog);

        if (gitClone == 0){
            return;
        }

        pipelineLog.setLogCodeState(10);
        //调用构建方法
        int write = write(pipeline, pipelineConfigure, pipelineLog);

        if (write == 0){
            return;
        }
        pipelineLog.setLogPackState(10);

        //调用部署方法
        int deploy = deploy(pipeline, pipelineConfigure, pipelineLog);

        if (deploy != 0){

            pipelineLog.setLogDeployState(10);

            pipelineLog.setLogRunStatus(30);

            String logRunLog = pipelineLog.getLogRunLog();

            pipelineLog.setLogRunLog(logRunLog+"\n" + "Implement Result : SUCCESS");

        }
    }

    /**
     * 构建配置为空
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     */
    private void structureNull(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog)  {

        //调用克隆方法
        int gitClone = gitClone(pipeline, pipelineConfigure, pipelineLog);

        pipelineLog.setLogCodeState(10);

        if (gitClone == 0){
            pipelineLog.setLogCodeState(1);
        }

        //调用部署方法
        int deploy = deploy(pipeline, pipelineConfigure, pipelineLog);

        pipelineLog.setLogDeployState(10);

        pipelineLog.setLogRunStatus(30);

        if (deploy == 0){

            pipelineLog.setLogDeployState(1);

            pipelineLog.setLogRunStatus(1);
        }

    }


    /**
     * ssh 连接发送文件
     * @param ip ip地址
     * @param proof 凭证信息
     * @param nowPath 部署文件地址
     * @param lastPath 本机文件地址
     */
    private static void sshSftp(String ip, Proof proof,String nowPath,String lastPath) throws JSchException, SftpException, IOException {

        //采用指定的端口连接服务器
        Session  session = new JSch().getSession(proof.getProofUsername(), ip ,proof.getProofPort());

        //如果服务器连接不上，则抛出异常
        if (session == null) {

            throw new JSchException(ip + "连接异常。。。。");
        }

        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");

        //设置登陆主机的密码
        session.setPassword(proof.getProofPassword());//设置密码

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
        outputStream.flush();

        outputStream.close();

        inputStream.close();
    }

    /**
     * 拆分字符串获取需要检索的文件
     * @param path 支付串
     * @return true false
     */
    private static boolean matche(String path,String s){

        String regex = ".*."+s;

        return path.matches(regex);

    }

    /**
     * 获取模块名
     * @param path 地址
     * @return 模块名
     */
    private static String[] split(String path){

        if (path != null){

            return path.split(" ");
        }
        return null;
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

        if (fa != null){

            for (File fs : fa) {

                //判断符合条件的文件
                if (matche(fs.getName(),s)) {

                    if (!fs.isDirectory()) {

                        list.add(fs.getName());
                    }
                }
            }
            //获取符合条件的
            if (list.size() ==1){
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 输出错误信息
     * @param pipelineLog 日志
     * @param e 错误日志
     */
    private  void  error(PipelineLog pipelineLog ,String e){

        pipelineLog.setLogRunLog("\n" + e + "\n"+ " RUN RESULT : FAIL");

        if (pipelineLog.getLogRunLog() != null){

            pipelineLog.setLogRunLog(pipelineLog.getLogRunLog()+ "\n" + e + "\n" + " RUN RESULT : FAIL");
        }

        pipelineLog.setLogRunStatus(1);

        pipelineLogList.add(pipelineLog);

        pipelineLogService.updatePipelineLog(pipelineLog);
    }
}
