package com.doublekit.pipeline.example.service.codeGit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 码云API
 */
public interface CodeGiteeApiService {

    /**
     * 拼装code地址
     * @return 返回code地址
     */
    String getCode() ;

    /**
     * 通过code获取accessToken
     * @param code code
     * @return accessToken
     */
    String getAccessToken(String code) throws IOException;

    /**
     * 获取登录名
     * @return 登录名
     */
    String getUserMessage();


    String getProof();

    /**
     * 获取对应code下所有的仓库
     * @return 仓库集合
     */
    List<String> getAllStorehouse();

    /**
     * 获取对应仓库下的所有分支
     * @param projectName 仓库名
     * @return 分支信息
     */
    List<String> getBranch(String projectName);


    String getGiteeUrl(String projectName);

}
