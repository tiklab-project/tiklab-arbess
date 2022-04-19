package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
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
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineCodeService pipelineCodeService;

    CommonAchieve commonAchieve = new CommonAchieve();

    // git克隆
    public int gitClone(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
        PipelineCode pipelineCode = pipelineCodeService.findOneCode(pipelineConfigure.getTaskId());
        Pipeline pipeline = pipelineConfigure.getPipeline();
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        //设置代码路径
        String path = "D:\\clone\\" + pipeline.getPipelineName();
        File file = new File(path);
        //调用删除方法删除旧的代码
        commonAchieve.deleteFile(file);
        Proof proof = pipelineCode.getProof();
        String codeAddress =pipelineCode.getCodeAddress();
        String codeBranch = pipelineCode.getCodeBranch();
        UsernamePasswordCredentialsProvider credentialsProvider = commonAchieve.usernamePassword(proof.getProofUsername(), proof.getProofPassword());

        //更新日志
        String s = "开始拉取代码 : " + "\n" + "FileAddress : " + file + "\n"  + "Uri : " + codeAddress + "\n"  + "Branch : " + codeBranch + "\n"   ;
        pipelineExecHistory.setRunLog(s);
        pipelineExecHistoryList.add(pipelineExecHistory);
        //克隆代码
        try {
            clone(file, codeAddress, credentialsProvider, codeBranch);
        } catch (GitAPIException e) {
            codeState(pipelineExecHistory,beginTime,e.toString(),pipelineExecHistoryList);
            return 0;
        }
        String log = s + "proofType : " +proof.getProofType() + "\n" + "clone成功。。。。。。。。。。。。。。。" + "\n";
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log);
        pipelineExecHistoryList.add(pipelineExecHistory);
        codeState(pipelineExecHistory,beginTime,null,pipelineExecHistoryList);
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


    //执行状态
    private void codeState(PipelineExecHistory pipelineExecHistory,long beginTime,String e,List<PipelineExecHistory> pipelineExecHistoryList){
        Pipeline pipeline = pipelineExecHistory.getPipeline();
        PipelineConfigure pipelineConfigure = pipelineConfigureService.findOneConfigure(pipeline.getPipelineId(), 10);
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setLogRunState(10);
        if (e != null){
            pipelineExecLog.setLogRunState(1);
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n拉取代码异常\n"+e);
            commonAchieve.error(pipelineExecHistory, "拉取代码异常\n"+e,pipeline.getPipelineId());
        }
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;

        pipelineExecLog.setLogRunTime(time);
        pipelineExecHistory.setRunTime(pipelineExecHistory.getRunTime()+time);
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setTaskLogType(pipelineConfigure.getTaskType());

        pipelineExecHistoryService.createLog(pipelineExecLog);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);
    }

}
