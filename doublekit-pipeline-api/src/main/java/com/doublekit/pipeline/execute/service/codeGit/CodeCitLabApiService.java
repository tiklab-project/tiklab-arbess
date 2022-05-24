package com.doublekit.pipeline.execute.service.codeGit;

import java.util.List;
import java.util.Map;

public interface CodeCitLabApiService {

    /**
     * 获取code
     * @return code地址
     */
    String getCode();

    /**
     * 通过code获取accessToken
     * @param code code
     * @return accessToken
     */
    String getAccessToken(String code);


    /**
     * 获取经过身份验证的用户的代码空间
     * @param accessToken 凭证
     * @return 代码空口按摩
     */
    String getUserMessage(String accessToken);

    /**
     * 获取用户的存储库
     * @param accessToken 凭证
     * @return 仓库信息
     */
    Map<String, String> getAllStorehouse(String accessToken);

    /**
     * 获取仓库所有分支
     * @param name 仓库名
     * @param accessToken 凭证
     * @return 分支
     */
    List<String> getBranch(String accessToken, String name);
}
