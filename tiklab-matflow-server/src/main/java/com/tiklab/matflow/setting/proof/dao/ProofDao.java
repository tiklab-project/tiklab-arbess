package com.tiklab.matflow.setting.proof.dao;


import com.tiklab.dal.jdbc.JdbcTemplate;
import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.definition.dao.MatFlowDao;
import com.tiklab.matflow.setting.proof.entity.ProofEntity;
import com.tiklab.matflow.setting.proof.entity.ProofTaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ProofDao {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowDao.class);

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

    public List<ProofEntity> findMatFlowProof(StringBuilder s){
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        String sql = " select matFlow_proof.* from matFlow_proof ";
        String concat = sql.concat("where matFlow_proof.type = 1");
        List<ProofEntity> list = jdbcTemplate.query(concat, new BeanPropertyRowMapper(ProofEntity.class));
        if (s.toString().equals("")){
            return list;
        }
        sql = sql.concat(" where matFlow_proof.type  = 2"
                +" and matFlow_proof.proof_id "
                +" in ( "
                +" select matFlow_proof_task.proof_id from matFlow_proof_task "
                +" where matFlow_proof_task.matFlow_id "
                +" in ("+ s +")"
                + ")");
        List<ProofEntity> lists = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProofEntity.class));
        lists.addAll(list);
        return  lists;
    }

    /**
     * 获取不同类型的凭证
     * @param matFlowId 流水线id
     * @param type 类型
     * @return 凭证列表
     */
    public List<ProofEntity> findMatFlowProof(String matFlowId,int type){
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        String scope = "";
           scope = switch (type) {
            case 1 -> scope.concat(" and matFlow_proof.proof_scope = 1 or matFlow_proof.proof_scope = 4");
            case 2, 3, 5 -> scope.concat(" and matFlow_proof.proof_scope = " + type);
            default -> scope;
        };

        //项目凭证
        String type1 = "select matFlow_proof.* from matFlow_proof  where matFlow_proof.type  = 2";
        type1 = type1.concat(  " and matFlow_proof.proof_id "
                +" in (select matFlow_proof_task.proof_id from matFlow_proof_task "
                +" where matFlow_proof_task.matFlow_id  = '"+ matFlowId+"') "+scope);
        List<ProofEntity> lists = jdbcTemplate.query(type1, new BeanPropertyRowMapper(ProofEntity.class));

        //全局凭证
        String types = "select matFlow_proof.* from matFlow_proof where matFlow_proof.type = 1";
        types = types.concat(scope);
        List<ProofEntity> proofEntityList = jdbcTemplate.query(types, new BeanPropertyRowMapper(ProofEntity.class));

        lists.addAll(proofEntityList);

        return  lists;
    }

    /**
     * 删除凭证关联信息
     * @param proofId 凭证id
     */
    public void deleteProofTask(String proofId){
        String sql = " delete from matFlow_proof_task ";
        sql=sql.concat(" where matFlow_proof_task.proof_id = '"+ proofId +"'" );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }

    /**
     * 删除凭证关联信息
     * @param proofId 凭证id
     */
    public List<ProofTaskEntity> findAllProofTask(String proofId){
        String sql = " select matFlow_proof_task.* from matFlow_proof_task ";
        sql=sql.concat(" where matFlow_proof_task.proof_id = '"+ proofId +"'" );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProofTaskEntity.class));
    }

    public List<ProofEntity> selectAllProofList(List<String> idList){
        return jpaTemplate.findList(ProofEntity.class,idList);
    }
}
