package com.tiklab.matflow.setting.proof.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.tiklab.matflow.setting.proof.model.Proof;

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
     * @param pipelineId 条件
     * @return 凭证列表
     */
    //List<Proof> findPipelineProof(ProofQuery proofQuery);
    List<Proof> findPipelineProof(String userId,String pipelineId,int type);

    /**
     * 查询所有凭证
     * @return 凭证列表
     */
    @FindAll
    List<Proof> findAllProof();

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
