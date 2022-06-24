package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.execute.model.CodeGit.CodeGiteeApi;
import com.doublekit.pipeline.setting.proof.model.Proof;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 码云API
 */
@JoinProvider(model = CodeGiteeApi.class)
public interface CodeGiteeApiService {

    /**
     * 获取code
     * @return 获取code的Url
     */
    String getCode();

    /**
     * 根据code获取AccessToken
     * @param code code
     * @return AccessToken
     */
    Map<String, String> getAccessToken(String code)  throws IOException ;

    /**
     * 获取用户所有仓库
     * @param ProofId 凭证
     * @return 厂库信息
     */
    List<String> getAllStorehouse(String ProofId);

    /**
     * 获取用户登录信息
     * @param accessToken 凭证
     * @return 用户信息
     */
    String getUserMessage(String accessToken);

    /**
     * 创建凭证
     * @param proofName 仓库名
     * @param accessToken 凭证
     * @return 凭证ID
     */
    String getProof(String proofName,String accessToken);

    /**
     * 获取仓库所有分支
     * @param proofId 凭证ID
     * @param projectName 仓库名
     * @return 所有分支
     */
    List<String> getBranch(String proofId,String projectName);

    /**
     * 获取仓库克隆地址
     * @param proof 凭证ID
     * @param projectName 仓库名称
     * @return 克隆地址
     */
    String getCloneUrl(Proof proof, String projectName);


    /**
     * 获取授权状态
     * @param code 授权凭证
     * @param state 状态
     * @return 0.未授权 1.成功授权 2.取消授权
     */
    int getState(String code,int state);
}
