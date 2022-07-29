package com.tiklab.matflow.setting.proof.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.setting.proof.dao.ProofTaskDao;
import com.tiklab.matflow.setting.proof.entity.ProofTaskEntity;
import com.tiklab.matflow.setting.proof.model.ProofTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProofTaskServiceImpl implements ProofTaskService{

    @Autowired
    ProofTaskDao proofTaskDao;

    /**
     * 创建凭证关联表
     * @param proofTask 信息
     * @return 创建Id
     */
    @Override
    public String createProofTask(ProofTask proofTask) {
        return proofTaskDao.createProofTask(BeanMapper.map(proofTask, ProofTaskEntity.class));
    }

    /**
     * 删除凭证关联表
     * @param proofTaskId 信息
     */
    @Override
    public void deleteProofTask(String proofTaskId) {
        proofTaskDao.deleteProofTask(proofTaskId);
    }

    /**
     * 创建凭证关联表
     * @param proofTask 信息
     */
    @Override
    public void updateProofTask(ProofTask proofTask) {
        proofTaskDao.updateProofTask(BeanMapper.map(proofTask, ProofTaskEntity.class));
    }

    /**
     * 查询凭证关联表
     * @param proofTaskId 信息
     * @return 创建Id
     */
    @Override
    public ProofTask findProofTask(String proofTaskId) {
        return BeanMapper.map(proofTaskDao.findProofTask(proofTaskId), ProofTask.class);
    }

    /**
     * 查询所有凭证关联表
     * @return 创建Id
     */
    @Override
    public List<ProofTask> findAllProofTask() {
        return BeanMapper.mapList(proofTaskDao.findAllProofTask(), ProofTask.class);
    }

}











