package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.pipeline.execute.model.CodeGit.CodeCheckAuth;

public interface CodeCheckService {

    /**
     * 验证连通性
     * @return 是否联通
     */
    Boolean checkAuth(CodeCheckAuth codeCheckAuth);
}
