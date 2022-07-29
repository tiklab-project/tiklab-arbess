package com.tiklab.matfiow.execute.service.codeGit;

import com.tiklab.matfiow.execute.model.CodeGit.CodeCheckAuth;

public interface CodeCheckService {

    /**
     * 验证连通性
     * @return 是否联通
     */
    Boolean checkAuth(CodeCheckAuth codeCheckAuth);
}
