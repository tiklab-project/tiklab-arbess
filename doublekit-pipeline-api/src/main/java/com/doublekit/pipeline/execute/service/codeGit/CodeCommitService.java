package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.execute.model.CodeGit.GitCommit;

import java.util.List;

@JoinProvider(model = GitCommit.class)
public interface CodeCommitService {

    /**
     * 获取提交信息
     * @param pipelineId 流水线id
     * @return 提交信息
     */
    List<List<GitCommit>> getSubmitMassage(String pipelineId);
}
