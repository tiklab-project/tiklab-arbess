package com.doublekit.pipeline.setting.proof.dao;

import com.doublekit.dal.jdbc.JdbcTemplate;
import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.setting.proof.entity.ProofEntity;
import com.doublekit.pipeline.setting.proof.model.ProofQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ProofDao {

    private static final Logger logger = LoggerFactory.getLogger(PipelineDao.class);

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
     * @param proofId 凭证id
     */
    public void deleteProof(String proofId){
        jpaTemplate.delete(ProofEntity.class,proofId);
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
     * @param proofId 凭证id
     * @return 凭证信息
     */
    public ProofEntity findOneProof(String proofId){
        return jpaTemplate.findOne(ProofEntity.class, proofId);
    }

    /**
     * 查询所有凭证
     * @return 凭证列表
     */
    public List<ProofEntity> selectAllProof(){

        return jpaTemplate.findAll(ProofEntity.class);
    }

    public List<ProofEntity> findAllProof(String userId,StringBuilder s){

        String sql = " select pipeline_proof.* from pipeline_proof ";
        sql = sql.concat(" where pipeline_proof.user_id = '" + userId +"'"
                + " or pipeline_proof.type = 1 "
                + " or pipeline_proof.pipeline_id "
                + " in ( "+ s +" )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProofEntity.class));
    }


    public List<ProofEntity> findPipelineAllProof(String userId ,String pipelineId){
        String sql = " select pipeline_proof.* from pipeline_proof ";
        sql = sql.concat(" where pipeline_proof.pipeline_id =  '"+pipelineId+"' "
                + " or pipeline_proof.user_id = '"+userId+"' "
                + " or pipeline_proof.type = 1 ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProofEntity.class));
    }

   public List<ProofEntity> findPipelineProof(ProofQuery proofQuery,StringBuilder s){
       String userId = proofQuery.getUserId();
       int type = proofQuery.getType();
       String sql = " select pipeline_proof.* from pipeline_proof ";
       sql = sql.concat(" where pipeline_proof.proof_scope  = " + type
               + " and pipeline_proof.proof_id "
               + " in (select pipeline_proof.proof_id from pipeline_proof "
               + " where pipeline_proof.user_id  = '"+ userId +"' "
               + " or pipeline_proof.type = 1 "
               + " or pipeline_proof.pipeline_id"
               + " in ("+s+"))");
       JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
       return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProofEntity.class));
   }

    public List<ProofEntity> selectAllProofList(List<String> idList){
        return jpaTemplate.findList(ProofEntity.class,idList);
    }
}
