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

@Service
@Exporter
public class PipelineCloneServiceImpl implements PipelineCloneService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    ProofService proofService;

    @Override
    public void gitClone(String pipelineId) throws Exception {

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectTimeId(pipelineId);

        Pipeline pipeline = pipelineService.selectPipeline(pipelineId);

        // 判断是否存在配置
        if (pipelineConfigure != null){

            //判断是否从git拉取代码
            if (pipelineConfigure.getConfigureCodeSource().equals("b")){

                //调用删除方法
                delete(pipelineId);

                //获取凭证信息
                Proof proof = proofService.selectProof(pipelineConfigure.getProofId());

                if (proof !=null){

                    //设置代码路径
                    String path = "D:\\clone\\"+pipeline.getPipelineName();

                    //获取凭证
                    UsernamePasswordCredentialsProvider credentialsProvider = usernamePassword(proof.getProofUsername(), proof.getProofPassword());

                    //克隆
                    clone(new File(path), pipelineConfigure.getConfigureCodeSourceAddress(), credentialsProvider, "master");

                    //获取构建命令
                    String order = "mvn"+" " + pipelineConfigure.getConfigureStructureOrder();

                    if (pipelineConfigure.getConfigureStructureOrder() != null) {

                        //调用构建和输出日志方法
                        write(path,structure(path, order));
                    }
                }
            }else{
                //获取本地pom文件地址
                String path = pipelineConfigure.getConfigureStructureAddress();

                //获取构建命令
                String order = "mvn" +" "+ pipelineConfigure.getConfigureStructureOrder();

                if (pipelineConfigure.getConfigureStructureOrder()!= null){
                    //调用构建和输出日志方法
                    write(path,structure(path, order));
                }
            }
        }
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
                //等待100毫秒
                Thread.sleep(100);

            } catch (Exception e) {
                throw new Exception("删除命令错误" + e);
            }


        }
    }

    /**
     * 执行构建命令
     * @param path 命令执行地址
     * @param order 命令
     * @return 输出流
     * @throws IOException 执行错误
     */
    private Process structure(String path,String order) throws IOException {
        //调用命令行
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
     * @param path 日志输出地址
     * @param process 输出流
     * @throws IOException 输出异常
     */
    private void write(String path ,Process process) throws IOException {

        FileOutputStream outputStream = null;

        BufferedOutputStream bufferedOutputStream = null;
        try {

            //字节流转化为字符流
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());

            int line;

            //输出到文件
            outputStream = new FileOutputStream(path+"\\log.txt");

            //字节缓冲输出流
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            //输出日志
            while ((line = inputStreamReader.read()) != -1){

                bufferedOutputStream.write(line);
            }
        } catch (IOException e) {
            throw new IOException("日志创建文件或输出错误" + e);
        }finally {
            if (bufferedOutputStream != null){
                bufferedOutputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
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
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     * @throws GitAPIException 拉取异常
     */
    private static void clone(File gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws Exception {
        Git call =null ;
        try {
             call = Git.cloneRepository().setURI(gitUrl)
                    .setCredentialsProvider(credentialsProvider)
                    .setDirectory(gitAddress)
                    .setBranch(branch)
                    .call();
        } catch (GitAPIException e) {
            throw new Exception("clone命令错误" + e);
        }finally {
            if (call != null) {
                call.close();
            }
        }
    }
}
