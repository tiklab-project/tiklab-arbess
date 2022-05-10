package com.doublekit.pipeline.execute.service.codeGit;

public interface CodeCitLabApiService {

    /**
     * 获取code
     * @return code地址
     */
    String code();

    /**
     * 通过code获取accessToken
     * @param code code
     * @return accessToken
     */
    String accessToken(String code);


    /**
     * 获取经过身份验证的用户的代码空间
     * @param accessToken 凭证
     * @return 代码空口按摩
     */
    String codeSpace(String accessToken);
}
