package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import com.doublekit.pipeline.instance.model.PipelineCodeLog;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class CodeAchieve {


    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineCodeService pipelineCodeService;

    CommonAchieve commonAchieve = new CommonAchieve();

    // git克隆
    private int gitClone(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){

        String codeId = pipelineConfigure.getTaskId();
        Pipeline pipeline = pipelineConfigure.getPipeline();

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        //设置代码路径
        String path = "D:\\clone\\" + pipeline.getPipelineName();
        File file = new File(path);
        //调用删除方法删除旧的代码
        commonAchieve.deleteFile(file);
        PipelineCode pipelineCode = pipelineCodeService.findOneCode(codeId);
        Proof proof = pipelineCodeService.CodeProof(codeId);
        String codeAddress =pipelineCode.getCodeAddress();
        String codeBranch = pipelineCode.getCodeBranch();
        UsernamePasswordCredentialsProvider credentialsProvider = commonAchieve.usernamePassword(proof.getProofUsername(), proof.getProofPassword());

        //更新日志
        String s = "开始拉取代码 : " + "\n"  + "FileAddress : " + file + "\n"  + "Uri : " + codeAddress + "\n"  + "Branch : " + codeBranch + "\n"  ;

        //克隆代码
        try {
            clone(file, codeAddress, credentialsProvider, codeBranch);
        } catch (GitAPIException e) {

            return 0;
        }
        String log = s + "proofType : " +proof.getProofType() + "\n" + "clone成功。。。。。。。。。。。。。。。" + "\n";

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
    private void clone(File gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws GitAPIException {
        Git.cloneRepository().setURI(gitUrl)
                .setCredentialsProvider(credentialsProvider)
                .setDirectory(gitAddress)
                .setBranch(branch)
                .call().close();
    }

    // //执行状态
    // private void codeState(PipelineExecLog pipelineExecLog,long beginTime,String e,List<PipelineExecLog> pipelineExecLogList){
    //     PipelineCodeLog codeLog = pipelineExecLog.getCodeLog();
    //     codeLog.setCodeRunStatus(10);
    //     if (e != null){
    //         codeLog.setCodeRunStatus(1);
    //         codeLog.setCodeRunLog(codeLog.getCodeRunLog()+"\n拉取代码异常\n"+e);
    //         commonAchieve.error(pipelineExecLog, "拉取代码异常\n"+e,pipelineExecLog.getPipelineId());
    //     }
    //     long overTime = new Timestamp(System.currentTimeMillis()).getTime();
    //     int time = (int) (overTime - beginTime) / 1000;
    //     codeLog.setCodeRunTime(time);
    //     pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
    //     pipelineExecLog.setCodeLog(codeLog);
    //     pipelineExecLogService.updateLog(pipelineExecLog);
    //     pipelineExecLogList.add(pipelineExecLog);
    // }

}
