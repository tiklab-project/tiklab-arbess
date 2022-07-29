package com.tiklab.matfiow.setting.proof.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.tiklab.matfiow.setting.proof.entity.ProofTaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProofTaskDao {

    private static final Logger logger = LoggerFactory.getLogger(ProofTaskDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建凭证关联表
     * @param proofTaskEntity 信息
     * @return 创建Id
     */
    public String createProofTask(ProofTaskEntity proofTaskEntity){
        return jpaTemplate.save(proofTaskEntity, String.class);
    }

    /**
     * 删除凭证关联表
     * @param proofTaskId 信息
     */
    public void deleteProofTask(String proofTaskId){
        jpaTemplate.delete(ProofTaskEntity.class,proofTaskId);
    }

    /**
     * 创建凭证关联表
     * @param proofTaskEntity 信息
     */
    public void updateProofTask(ProofTaskEntity proofTaskEntity){
         jpaTemplate.update(proofTaskEntity);
    }

    /**
     * 查询凭证关联表
     * @param proofTaskId 信息
     * @return 创建Id
     */
    public ProofTaskEntity findProofTask(String proofTaskId){
       return jpaTemplate.findOne(ProofTaskEntity.class,proofTaskId);
    }

    /**
     * 查询所有凭证关联表
     * @return 创建Id
     */
    public List<ProofTaskEntity> findAllProofTask(){
        return jpaTemplate.findAll(ProofTaskEntity.class);
    }



}
