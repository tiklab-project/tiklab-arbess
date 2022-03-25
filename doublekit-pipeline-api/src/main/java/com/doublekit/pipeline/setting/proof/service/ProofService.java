package com.doublekit.pipeline.setting.proof.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.setting.proof.model.Proof;

import java.util.List;

/**
 * 流水线凭证
 */
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
     * @param proofId 凭证id
     * @return 凭证信息
     */
    @FindOne
     Proof findOneProof(String proofId);

    Proof fondOneName(String proofName);

    //获取构建凭证
    List<Proof> findAllGitProof();

    //获取部署凭证
    @FindAll
    List<Proof> findAllDeployProof();


    @FindList
    List<Proof> selectProofList(List<String> idList);
}
