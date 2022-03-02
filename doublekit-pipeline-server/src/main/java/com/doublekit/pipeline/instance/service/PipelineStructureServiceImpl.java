package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineLog;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public String  pipelineStructure(String pipelineId) throws Exception{

        //判断同一任务是否在运行
        if (pipelineIdList != null){

            for (String id : pipelineIdList) {

                System.out.println(id);

                if (id .equals(pipelineId)){

                    return "100";
                }
            }
        }

        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //执行构建
        executorService.submit(() -> structure(pipelineId));

        Thread.sleep(4000);

        return "1";
    }

    //构建
    private String  structure(String pipelineId) throws Exception {

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

    /**
     * 克隆
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     * @return 构建状态
     * @throws Exception 执行克隆异常
     */
    private int   gitClone(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws Exception {
        String logId =pipelineLog.getLogId();
        // 判断是否存在配置
        if (pipelineConfigure != null) {

            String pipelineId = pipeline.getPipelineId();

            //开始运行时间
            String last = dateFormat.format(new Date());

            //设置代码路径
            String path = "D:\\clone\\" + pipeline.getPipelineName();

            //判断是否从git拉取代码
            if (pipelineConfigure.getConfigureCodeSource().equals("b")) {

                //调用删除方法删除旧的代码
                delete(pipelineId);

                //获取凭证信息
                Proof proof = pipelineConfigureService.getProof(pipelineId);

                if (proof != null) {

                    //获取凭证
                    UsernamePasswordCredentialsProvider credentialsProvider = usernamePassword(proof.getProofUsername(), proof.getProofPassword());

                    //克隆代码
                    gitClone(new File(path), pipelineConfigure.getConfigureCodeSourceAddress(), credentialsProvider, pipelineConfigure.getConfigureBranch());

                    pipelineLog.setLogId(logId);

                    pipelineLog.setLogCodeTime((int)time(dateFormat.format(new Date()), last));

                    pipelineLogList.add(pipelineLog);

                    return 1;
                }
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
     * @throws Exception 执行构建异常
     */
    private int write(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws Exception {

        String logId =pipelineLog.getLogId();
        //开始运行时间
        String last = dateFormat.format(new Date());

        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();

        //获取构建命令
        String order = "mvn"+" " + pipelineConfigure.getConfigureStructureOrder();

        //判断是否从git拉取代码
        if (pipelineConfigure.getConfigureCodeSource().equals("b")) {

            if (pipelineConfigure.getConfigureStructureOrder() != null) {

                String log = "";

                String s;
                //调用构建和输出日志方法
                Process process = structure(path, order);

                try (InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());

                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    //字节流转化为字符流

                    while ((s = bufferedReader.readLine()) != null) {

                        log = log + s + "\n";

                        pipelineLog.setLogId(logId);

                        pipelineLog.setLogPackState(0);

                        pipelineLog.setLogRunLog(log);

                        //获取构建所用时长
                        pipelineLog.setLogPackTime((int) time(dateFormat.format(new Date()), last));

                        pipelineLogList.add(pipelineLog);
                    }

                    return  1;
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
     * @throws Exception 执行部署异常
     */
    private int deploy(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws Exception {

        String logId =pipelineLog.getLogId();

        //开始运行时间
        String last = dateFormat.format(new Date());

        int i = deployOrder(pipeline.getPipelineName(), pipelineConfigure.getConfigureDeployAddress());

        if (i == 1){
            pipelineLog.setLogId(logId);

            Thread.sleep(1000);

            //获取构建所用时长
            String format = dateFormat.format(new Date());

            pipelineLog.setLogDeployTime((int) time(format, last));

            pipelineLogList.add(pipelineLog);

            return 1;

        }
        return 0;
    }

    /**
     * 调用构建命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
    public Process structure(String path,String order) throws IOException {

        Runtime runtime=Runtime.getRuntime();

        Process process;
        try {
            //执行命令
            process = runtime.exec("cmd.exe /c cd " + path + " &&" + " " + order);
            return process;
        } catch (IOException e) {
            throw new IOException("执行构建命令错误" + e);
        }
    }

    /**
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     * @throws GitAPIException 拉取异常
     */
    private void gitClone(File gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws Exception {

        try {
             Git.cloneRepository().setURI(gitUrl)
                    .setCredentialsProvider(credentialsProvider)
                    .setDirectory(gitAddress)
                    .setBranch(branch)
                    .call().close();
        } catch (GitAPIException e) {

            throw new Exception("clone命令错误" + e);
        }
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
     * @param pipelineId 流水线id
     * @throws Exception 命令执行错误
     */
    private void delete(String pipelineId) throws Exception {

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        if (pipeline != null){

            Runtime runtime1=Runtime.getRuntime();

            //删除源文件
            try {

                runtime1.exec("cmd.exe /c  cd D:\\clone && rd /s/q " + pipeline.getPipelineName());

                Thread.sleep(100);
            } catch (Exception e) {
                throw new Exception("删除命令错误" + e);
            }


        }
    }

    /**
     * 调用部署命令
     * @param name 源文件地址
     * @param newPath 部署地址
     * @return 构建信息
     * @throws Exception 构建命令执行异常
     */
    private int deployOrder(String name,String newPath) throws Exception {

        //移动文件命令
        String orders ="xcopy"+" "+"D:\\clone\\"+name+ " "+ "D:"+newPath+"\\"+name+" "+"/H /E /S /K /Y";

        //创建文件夹命令
        String md ="md"+" "+"D:"+newPath+"\\"+name;

        //命令行对象
        Runtime runtime=Runtime.getRuntime();

        Process process;
        try {

            //删除部署路径下的源文件
            runtime.exec("cmd.exe /k rd /s/q "+" "+"D:"+newPath+"\\"+name);

            //创建文件夹
            runtime.exec("cmd.exe /k " +" "+ md);

            //复制文件
            process = runtime.exec("cmd.exe /c " +" "+ orders);

            if (process != null){
                return 1;
            }

        } catch (IOException e) {
            throw new Exception("执行部署命令出错"+e);
        }
        return 0;
    }

    /**
     * 获取时间差
     * @param now 现在
     * @param last 过去
     * @return 时间差
     * @throws ParseException 时间转换异常
     */
    private long time(String now ,String last) throws Exception {

        long l;

        try {
            l = dateFormat.parse(now).getTime()-dateFormat.parse(last).getTime();

        } catch (Exception e) {
            throw new Exception("时间转换异常"+e);
        }
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
     * @throws Exception 执行克隆异常
     */
    private void cloneNull(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws Exception {

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
     * @throws Exception 执行克隆异常
     */
    private void noNull(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws Exception {

        //调用克隆方法
        int gitClone = gitClone(pipeline, pipelineConfigure, pipelineLog);

        pipelineLog.setLogCodeState(10);

        if (gitClone == 0){
            pipelineLog.setLogCodeState(1);
        }

        //调用构建方法
        int write = write(pipeline, pipelineConfigure, pipelineLog);

        pipelineLog.setLogPackState(10);

        if (write == 0){
            pipelineLog.setLogPackState(1);
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
     * 构建配置为空
     * @param pipeline 流水线信息
     * @param pipelineConfigure 配置信息
     * @param pipelineLog 日志
     * @throws Exception 执行克隆异常
     */
    private void structureNull(Pipeline pipeline,PipelineConfigure pipelineConfigure,PipelineLog pipelineLog) throws Exception {

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

}
