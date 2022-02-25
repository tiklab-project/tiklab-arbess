package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.stream.Collectors;

@Service
@Exporter
public class GitCloneServiceImpl implements GitCloneService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    ProofService proofService;

    //克隆代码
    @Override
    public int  gitClone(String pipelineId) throws Exception {

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        // 判断是否存在配置
        if (pipelineConfigure != null) {

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

                    //克隆
                    return gitClone(new File(path), pipelineConfigure.getConfigureCodeSourceAddress(), credentialsProvider, "master");
                }
            }
        }
        return 0 ;
    }

    //执行命令
    @Override
    public String write(String pipelineId) throws Exception {

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();

        //获取构建命令
        String order = "mvn"+" " + pipelineConfigure.getConfigureStructureOrder();

        //判断是否从git拉取代码
        if (pipelineConfigure.getConfigureCodeSource().equals("b")) {

            if (pipelineConfigure.getConfigureStructureOrder() != null) {

                //调用构建和输出日志方法
                return  write(structure(path, order));
            }
        }
        return null ;
    }

    @Override
    public int deploy(String pipelineId) throws Exception {

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();

        return deployOrder(path,pipelineConfigure.getConfigureDeployAddress());
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
     * 日志输出
     * @param process 输出流
     * @throws IOException 输出异常
     */
    private String write(Process process) throws IOException {

        InputStreamReader inputStreamReader = null;

        try {

            //字节流转化为字符流
           inputStreamReader = new InputStreamReader(process.getInputStream());

            // int line;
            //
            // //输出到文件
            // outputStream = new FileOutputStream(path+"\\log.txt");
            //
            //
            // //字节缓冲输出流
            // bufferedOutputStream = new BufferedOutputStream(outputStream);
            //
            // String a = null;
            // //输出日志
            // while ((line = inputStreamReader.read()) != -1){
            //
            //     bufferedOutputStream.write(line);
            // }

            return new BufferedReader(inputStreamReader)
                    .lines().collect(Collectors.joining(System.lineSeparator()));
        } finally {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

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
     * @param codePath 源文件地址
     * @param newPath 部署地址
     * @return 构建信息
     * @throws Exception 构建命令执行异常
     */
    private int deployOrder(String codePath,String newPath) throws Exception {

            String orders =codePath+"\\*.*"+"D:"+newPath;

            System.out.println(orders);

            Runtime runtime=Runtime.getRuntime();

            Process process;
            try {
                //执行命令
                process = runtime.exec("cmd.exe /c move " + orders);
                if (process != null){
                    return 1;
                }

            } catch (IOException e) {
                throw new Exception("执行部署命令出错"+e);
            }
    return 0;
    }
}
