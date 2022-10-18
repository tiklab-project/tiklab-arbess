package net.tiklab.matflow.setting.service;

import net.tiklab.matflow.setting.model.ProofTask;

import java.util.List;

public interface ProofTaskService {

    /**
     * 创建凭证关联表
     * @param proofTask 信息
     * @return 创建Id
     */
     String createProofTask(ProofTask proofTask);

    /**
     * 删除凭证关联表
     * @param proofTaskId 信息
     */
     void deleteProofTask(String proofTaskId);

    /**
     * 创建凭证关联表
     * @param proofTask 信息
     */
     void updateProofTask(ProofTask proofTask);

    /**
     * 查询凭证关联表
     * @param proofTaskId 信息
     * @return 创建Id
     */
     ProofTask findProofTask(String proofTaskId);

    /**
     * 查询所有凭证关联表
     * @return 创建Id
     */
     List<ProofTask> findAllProofTask();
    
}
