package net.tiklab.pipeline.execute.service;

import net.tiklab.pipeline.execute.model.CodeCheckAuth;

public interface CodeCheckService {

    /**
     * 验证连通性
     * @return 是否联通
     */
    Boolean checkAuth(CodeCheckAuth codeCheckAuth);
}
