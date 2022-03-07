package com.doublekit.pipeline.systemSettings.securitySetting.proof.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;

import java.util.List;

@JoinProvider(model = Proof.class)
public interface ProofService {


    /**
     * 添加凭证
     * @param proof 凭证信息
     * @return 凭证id
     */
     String createProof(Proof proof);

    /**
     * 删除凭证
     * @param id 凭证id
     */
     void deleteProof(String id);

    /**
     * 更新凭证
     * @param proof 凭证信息
     */
     void updateProof(Proof proof);

    /**
     * 查询凭证
     * @param id 凭证id
     * @return 凭证信息
     */
    @FindOne
     Proof selectProof(String id);

    //获取构建凭证
    List<Proof> findAllGitProof();

    //获取部署凭证
    List<Proof> findAllDeployProof();

    /**
     * 根据凭证id查询名称
     * @param proofId 凭证id
     * @return 凭证名称
     */
    String selectProofName(String  proofId);

    @FindList
    List<Proof> selectProofList(List<String> idList);
}
