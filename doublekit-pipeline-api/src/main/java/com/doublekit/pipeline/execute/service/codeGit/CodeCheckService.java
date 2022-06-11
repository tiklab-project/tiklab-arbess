package com.doublekit.pipeline.execute.service.codeGit;

public interface CodeCheckService {

    /**
     * 验证连通性
     * @param url 地址
     * @param proofId 凭证
     * @return 是否联通
     */
    Boolean checkAuth(String url , String proofId ,int port);
}
