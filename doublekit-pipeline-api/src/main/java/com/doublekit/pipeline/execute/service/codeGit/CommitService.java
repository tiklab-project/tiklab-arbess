package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.pipeline.execute.model.CodeGit.Commit;

import java.util.List;

public interface CommitService {

    /**
     * 获取提交信息
     * @param pipelineId 流水线id
     * @return 提交信息
     */
    List<List<Commit>> getSubmitMassage(String pipelineId);
}
