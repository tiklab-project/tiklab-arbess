package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.pipeline.execute.model.CodeGit.GitCommit;

import java.util.List;

public interface GitCommitService {

    /**
     * 获取提交信息
     * @param pipelineId 流水线id
     * @return 提交信息
     */
    List<List<GitCommit>> getSubmitMassage(String pipelineId);
}
