package com.doublekit.pipeline.instance.service.git;

import java.io.IOException;
import java.util.List;

/**
 * 码云API
 */
public interface GiteeService {

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


    String getProof(String configureId);

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
