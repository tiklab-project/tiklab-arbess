package net.tiklab.matflow.execute.dao;


import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.execute.entity.PipelineExecLogEntity;
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
        String sql = "delete from  pip_pipeline_history_log ";
        sql = sql.concat(" where  pip_pipeline_history_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }

    /**
     * 根据历史id查询日志
     * @param historyId 历史id
     * @return 历史信息
     */
    public List<PipelineExecLogEntity> findAllLog(String historyId){
        String sql = "select  pip_pipeline_history_log.* from  pip_pipeline_history_log ";
        sql = sql.concat(" where  pip_pipeline_history_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(PipelineExecLogEntity.class));
    }

    /**
     * 根据阶段id查询日志
     * @param stagsId 历史id
     * @return 历史信息
     */
    public List<PipelineExecLogEntity> findAllStagesLog(String historyId,String stagsId){
        String sql = "select  pip_pipeline_history_log.* from  pip_pipeline_history_log ";
        sql = sql.concat(" where  pip_pipeline_history_log.stages_id = '"+stagsId +"'"
                + "and  pip_pipeline_history_log.history_id = '" + historyId +"'" );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(PipelineExecLogEntity.class));
    }

}
