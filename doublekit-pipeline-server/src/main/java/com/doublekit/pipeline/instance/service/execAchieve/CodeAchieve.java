package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import com.doublekit.pipeline.example.service.PipelineCodeServiceImpl;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class CodeAchieve {

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    CommonAchieve commonAchieve;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    public int codeStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
        return gitClone(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
    }

    // git克隆
    private int gitClone(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
        pipelineExecHistory.setRunLog("任务开始执行。。。。。。。");
        pipelineExecHistoryList.add(pipelineExecHistory);
        PipelineCode pipelineCode = pipelineCodeService.findOneCode(pipelineConfigure.getTaskId());
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        Pipeline pipeline = pipelineConfigure.getPipeline();
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        //设置代码路径
        String path = "D:\\clone\\" + pipeline.getPipelineName();
        File file = new File(path);
        //调用删除方法删除旧的代码
        commonAchieve.deleteFile(file);
        Proof proof = pipelineCode.getProof();
        if (proof == null){
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"凭证为空。。。。",pipelineExecHistoryList);
            return 0;
        }
        String codeAddress =pipelineCode.getCodeAddress();
        String codeBranch = pipelineCode.getCodeBranch();
        UsernamePasswordCredentialsProvider credentialsProvider = commonAchieve.usernamePassword(proof.getProofUsername(), proof.getProofPassword());

        //更新日志
        String s = "开始拉取代码 : " + "\n" + "FileAddress : " + file + "\n"  + "Uri : " + codeAddress + "\n"  + "Branch : " + codeBranch + "\n"   ;
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        pipelineExecHistoryList.add(pipelineExecHistory);

        //克隆代码
        try {
            clone(file, codeAddress, credentialsProvider, codeBranch);
        } catch (GitAPIException e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
            return 0;
        }
        String log = s + "proofType : " +proof.getProofType() + "\n" + "clone成功。。。。。。。。。。。。。。。" + "\n";
        pipelineExecLog.setRunLog(log);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log);
        pipelineExecHistoryList.add(pipelineExecHistory);
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
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

}
