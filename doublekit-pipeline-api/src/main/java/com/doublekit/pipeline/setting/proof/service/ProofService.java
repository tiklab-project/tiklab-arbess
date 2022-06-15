package com.doublekit.pipeline.setting.proof.service;


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
     * @param proofId 凭证id
     */
     void deleteProof(String proofId);

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


    /**
     * 查询流水线凭证
     * @param pipelineId 流水线id
     * @return 凭证列表
     */
    List<Proof> findPipelineProof(String pipelineId);

    /**
     * 查询所有凭证
     * @return 凭证列表
     */
    List<Proof> findAllProof();


    /**
     * 查询用户所有凭证
     * @param userId 用户id
     * @return 凭证
     */
    List<Proof> findAll(String userId);


    /**
     * 根据类型查询凭证
     * @param type 类型
     * @return 凭证信息
     */
    List<Proof> findAllProof(int type);


    @FindList
    List<Proof> selectProofList(List<String> idList);

}
