package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jdbc.JdbcTemplate;
import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineExecLogEntity;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.setting.proof.entity.ProofEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PipelineExecLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param pipelineExecLogEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createLog(PipelineExecLogEntity pipelineExecLogEntity){
        return jpaTemplate.save(pipelineExecLogEntity, String.class);

    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deleteLog(String id){
        jpaTemplate.delete(PipelineExecLogEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param pipelineExecLogEntity 更新后流水线日志信息
     */
    public void updateLog(PipelineExecLogEntity pipelineExecLogEntity){
        jpaTemplate.update(pipelineExecLogEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public PipelineExecLogEntity findOne(String id){
        return jpaTemplate.findOne(PipelineExecLogEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<PipelineExecLogEntity> findAllLog(){ return jpaTemplate.findAll(PipelineExecLogEntity.class); }


    public List<PipelineExecLogEntity> findAllLogList(List<String> idList){
        return jpaTemplate.findList(PipelineExecLogEntity.class,idList);
    }

    /**
     * 删除历史关联的日志
     * @param historyId 历史id
     */
    public void deleteAllLog(String historyId){
        String sql = "delete from pipeline_log ";
        sql = sql.concat(" where pipeline_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }

    /**
     * 根据历史id查询日志
     * @param historyId 历史id
     * @return 历史信息
     */
    public List<PipelineExecLogEntity> findAllLog(String historyId){
        String sql = "select pipeline_log.* from pipeline_log ";
        sql = sql.concat(" where pipeline_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(PipelineExecLogEntity.class));
    }

}
