package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineLog;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.io.StreamCopyThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Exporter
public class GitCloneServiceImpl implements GitCloneService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineLogService pipelineLogService;

    @Autowired
    ProofService proofService;

    @Autowired
    PipelineHistoryService pipelineHistoryService;

    //格式化时间
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

    PipelineLog pipelineLog =new PipelineLog();

    //存放过程状态
    List<PipelineLog> pipelineLogList = new ArrayList<>();

    //开始构建
    @Override
    public String  pipelineStructure(String pipelineId) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return null;
            }
        });

        executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //清除缓存
                pipelineLogList.clear();

                String logId = pipelineLogService.createPipelineLog(pipelineLog);

                //调用克隆方法
                int gitClone = gitClone(pipelineId, logId);

                if (gitClone == 1){
                    pipelineLog.setLogCodeState(10);
                    pipelineLogList.add(pipelineLog);
                }

                //调用构建方法
                write(pipelineId, logId);

                //调用部署方法
                int deploy = deploy(pipelineId, logId);
                if (deploy == 1){

                    pipelineLog.setLogDeployState(10);

                    pipelineLog.setLogRunStatus(pipelineLog.getLogCodeState()+pipelineLog.getLogPackState()+pipelineLog.getLogDeployState());

                    pipelineLogList.add(pipelineLog);
                }
                return createPipelineHistory(pipelineId, logId);
            }
        });

        return null;
    }

    //查询构建状态
    @Override
    public PipelineLog selectStructureState() throws InterruptedException {

        Thread.sleep(2000);

        if (pipelineLogList != null){

            PipelineLog pipelineLog = pipelineLogList.get(pipelineLogList.size() - 1);

            pipelineLogService.updatePipelineLog(pipelineLog);

            return pipelineLog;
        }
        return null;
    }

    //克隆代码
    @Override
    public int  gitClone(String pipelineId,String logId) throws Exception {

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        // 判断是否存在配置
        if (pipelineConfigure != null) {

            //开始运行时间
            String last = dateFormat.format(new Date());

            //设置代码路径
            String path = "D:\\clone\\" + pipeline.getPipelineName();

            //判断是否从git拉取代码
            if (pipelineConfigure.getConfigureCodeSource().equals("b")) {

                //调用删除方法删除旧的代码
                delete(pipelineId);

                //获取凭证信息
                Proof proof = proofService.selectProof(pipelineConfigure.getProofId());

                if (proof != null) {

                    //获取凭证
                    UsernamePasswordCredentialsProvider credentialsProvider = usernamePassword(proof.getProofUsername(), proof.getProofPassword());

                    //克隆代码
                    int master = gitClone(new File(path), pipelineConfigure.getConfigureCodeSourceAddress(), credentialsProvider, "master");

                    //获取id
                    pipelineLog.setLogId(logId);

                    //获取构建所用时长
                    pipelineLog.setLogCodeTime((int)time(dateFormat.format(new Date()), last));

                    pipelineLogList.add(pipelineLog);

                    //克隆
                    return master;
                }
            }
        }
        return 0 ;
    }

    //执行命令
    @Override
    public int write(String pipelineId,String logId) throws Exception {

        //开始运行时间
        String last = dateFormat.format(new Date());

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

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

                InputStreamReader inputStreamReader = null;

                BufferedReader bufferedReader=null;

                try {
                    //字节流转化为字符流
                    inputStreamReader = new InputStreamReader(process.getInputStream());

                     bufferedReader = new BufferedReader(inputStreamReader);

                    pipelineLog.setLogPackState(10);
                        while ((s = bufferedReader.readLine()) != null){

                            log = log + s ;

                            pipelineLog.setLogId(logId);

                            pipelineLog.setLogRunLog(log);

                            pipelineLog.setLogPackState(10);

                            if (!log.equals(pipelineLog.getLogRunLog())){
                                pipelineLog.setLogPackState(0);
                            }

                            //获取构建所用时长
                            pipelineLog.setLogPackTime((int)time(dateFormat.format(new Date()), last));
                    }
                        pipelineLogList.add(pipelineLog);

                    return 0;

                } finally {
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                }
            }
        }
        return 0;
    }

    //开始部署
    @Override
    public int deploy(String pipelineId,String logId) throws Exception {

        //开始运行时间
        String last = dateFormat.format(new Date());

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

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
    private  int gitClone(File gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws Exception {
        Git call =null ;
        try {
            call = Git.cloneRepository().setURI(gitUrl)
                    .setCredentialsProvider(credentialsProvider)
                    .setDirectory(gitAddress)
                    .setBranch(branch)
                    .call();

            if (call != null){
                return 1;
            }
            return 0;
        } catch (GitAPIException e) {
            throw new Exception("clone命令错误" + e);
        }finally {
            if (call != null) {
                call.close();
            }
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

        long l= 0;

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
     * 创建历史表
     * @param pipelineId 流水线id
     * @param logId 日志id
     * @return 历史id
     */
    private String createPipelineHistory(String pipelineId,String logId){

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        Proof proof = proofService.selectProof(pipelineConfigure.getProofId());

        PipelineLog pipelineLog = pipelineLogService.selectPipelineLog(logId);

        PipelineHistory pipelineHistory = new PipelineHistory();

        pipelineHistory.setPipeline(pipeline);

        pipelineHistory.setPipelineLog(pipelineLog);

        pipelineHistory.setPipelineConfigure(pipelineConfigure);

        pipelineHistory.setProof(proof);

        pipelineHistory.setHistoryCreateTime(dateFormat.format(new Date()));

        return  pipelineHistoryService.foundPipelineHistory(pipelineHistory);
    }

}
