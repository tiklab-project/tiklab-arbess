package com.tiklab.matflow.instance.dao;


import com.tiklab.dal.jdbc.JdbcTemplate;
import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.instance.entity.MatFlowExecLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MatFlowExecLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param matFlowExecLogEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createLog(MatFlowExecLogEntity matFlowExecLogEntity){
        return jpaTemplate.save(matFlowExecLogEntity, String.class);

    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deleteLog(String id){
        jpaTemplate.delete(MatFlowExecLogEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param matFlowExecLogEntity 更新后流水线日志信息
     */
    public void updateLog(MatFlowExecLogEntity matFlowExecLogEntity){
        jpaTemplate.update(matFlowExecLogEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public MatFlowExecLogEntity findOne(String id){
        return jpaTemplate.findOne(MatFlowExecLogEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<MatFlowExecLogEntity> findAllLog(){ return jpaTemplate.findAll(MatFlowExecLogEntity.class); }


    public List<MatFlowExecLogEntity> findAllLogList(List<String> idList){
        return jpaTemplate.findList(MatFlowExecLogEntity.class,idList);
    }

    /**
     * 删除历史关联的日志
     * @param historyId 历史id
     */
    public void deleteAllLog(String historyId){
        String sql = "delete from matflow_log ";
        sql = sql.concat(" where matflow_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }

    /**
     * 根据历史id查询日志
     * @param historyId 历史id
     * @return 历史信息
     */
    public List<MatFlowExecLogEntity> findAllLog(String historyId){
        String sql = "select matflow_log.* from matflow_log ";
        sql = sql.concat(" where matflow_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(MatFlowExecLogEntity.class));
    }

}
