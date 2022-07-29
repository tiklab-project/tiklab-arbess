package com.tiklab.matfiow.execute.service.codeGit;

import com.doublekit.join.annotation.JoinProvider;
import com.tiklab.matfiow.execute.model.CodeGit.CodeGitHubApi;
import com.tiklab.matfiow.setting.proof.model.Proof;

import java.util.List;

@JoinProvider(model = CodeGitHubApi.class)
public interface CodeGitHubService {

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
     * @param proofId 凭证id
     * @return 仓库信息
     */
    List<String> getAllStorehouse(String proofId);

    /**
     * 获取仓库所有分支
     * @param proofId 仓库名
     * @param projectName 凭证
     * @return 分支
     */
    List<String> getBranch(String proofId,String projectName);

    /**
     * 创建凭证
     * @return 凭证id
     */
    String getProof(Proof proof);

    /**
     * 获取单个仓库
     * @param proof 凭证id
     * @param houseName 仓库名
     * @return 仓库url
     */
    String getOneHouse(Proof proof, String houseName);
}
