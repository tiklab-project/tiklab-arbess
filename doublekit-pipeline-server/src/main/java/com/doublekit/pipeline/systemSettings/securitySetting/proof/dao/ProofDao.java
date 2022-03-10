package com.doublekit.pipeline.systemSettings.securitySetting.proof.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.entity.ProofEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ProofDao {

    private static Logger logger = LoggerFactory.getLogger(PipelineDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 添加凭证
     * @param proofEntity 凭证信息
     * @return 凭证id
     */
    public String createProof(ProofEntity proofEntity){
        return jpaTemplate.save(proofEntity, String.class);
    }

    /**
     * 删除凭证
     * @param id 凭证id
     */
    public void deleteProof(String id){
        jpaTemplate.delete(ProofEntity.class,id);
    }

    /**
     * 更新凭证
     * @param proofEntity 凭证信息
     */
    public void updateProof(ProofEntity proofEntity){
        jpaTemplate.update(proofEntity);
    }

    /**
     * 查询凭证
     * @param id 凭证id
     * @return 凭证信息
     */
    public ProofEntity findOneProof(String id){
        return jpaTemplate.findOne(ProofEntity.class, id);
    }

    /**
     * 查询所有凭证
     * @return 凭证列表
     */
    public List<ProofEntity> selectAllProof(){
        return jpaTemplate.findAll(ProofEntity.class);
    }


    public List<ProofEntity> selectAllProofList(List<String> idList){

        return jpaTemplate.findList(ProofEntity.class,idList);
    }
}
