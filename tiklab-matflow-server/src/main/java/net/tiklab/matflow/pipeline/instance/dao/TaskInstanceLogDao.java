package net.tiklab.matflow.pipeline.instance.dao;


import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.pipeline.instance.entity.TaskInstanceLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TaskInstanceLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param taskInstanceLogEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createLog(TaskInstanceLogEntity taskInstanceLogEntity){
        return jpaTemplate.save(taskInstanceLogEntity, String.class);
    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deleteLog(String id){
        jpaTemplate.delete(TaskInstanceLogEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param taskInstanceLogEntity 更新后流水线日志信息
     */
    public void updateLog(TaskInstanceLogEntity taskInstanceLogEntity){
        jpaTemplate.update(taskInstanceLogEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public TaskInstanceLogEntity findOne(String id){
        return jpaTemplate.findOne(TaskInstanceLogEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<TaskInstanceLogEntity> findAllLog(){ return jpaTemplate.findAll(TaskInstanceLogEntity.class); }


    public List<TaskInstanceLogEntity> findAllLogList(List<String> idList){
        return jpaTemplate.findList(TaskInstanceLogEntity.class,idList);
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
    public List<TaskInstanceLogEntity> findAllLog(String historyId){
        String sql = "select  pip_pipeline_history_log.* from  pip_pipeline_history_log ";
        sql = sql.concat(" where  pip_pipeline_history_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(TaskInstanceLogEntity.class));
    }

    /**
     * 根据阶段id查询日志
     * @param stagsId 历史id
     * @return 历史信息
     */
    public List<TaskInstanceLogEntity> findAllStagesLog(String historyId, String stagsId){
        String sql = "select  pip_pipeline_history_log.* from  pip_pipeline_history_log ";
        sql = sql.concat(" where  pip_pipeline_history_log.stages_id = '"+stagsId +"'"
                + "and  pip_pipeline_history_log.history_id = '" + historyId +"'" );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(TaskInstanceLogEntity.class));
    }

}
