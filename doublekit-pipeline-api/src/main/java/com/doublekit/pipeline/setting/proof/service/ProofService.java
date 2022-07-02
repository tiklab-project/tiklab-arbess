package com.doublekit.pipeline.setting.proof.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.model.ProofQuery;

import java.util.HashSet;
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
     * @param proofQuery 条件
     * @return 凭证列表
     */
    List<Proof> findPipelineProof(ProofQuery proofQuery);

    /**
     * 查询所有凭证
     * @return 凭证列表
     */
    @FindAll
    List<Proof> findAllProof();


    /**
     * 查询用户所有凭证
     * @param userId 用户id
     * @return 凭证
     */
    HashSet<Proof> findAllUserProof(String userId);

    /**
     * 获取相同的授权凭证
     * @param scope 凭证类型
     * @param userName 用户名
     * @return 凭证信息
     */
    Proof findAllAuthProof(int scope,String userName);


    @FindList
    List<Proof> selectProofList(List<String> idList);

}
