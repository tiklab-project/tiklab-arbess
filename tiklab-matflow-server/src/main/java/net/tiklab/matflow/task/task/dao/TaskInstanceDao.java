package net.tiklab.matflow.task.task.dao;

import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.task.task.entity.TaskInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 任务日志数据访问
 */

@Repository
public class TaskInstanceDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param taskInstanceEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createLog(TaskInstanceEntity taskInstanceEntity){
        return jpaTemplate.save(taskInstanceEntity, String.class);
    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deleteLog(String id){
        jpaTemplate.delete(TaskInstanceEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param taskInstanceEntity 更新后流水线日志信息
     */
    public void updateLog(TaskInstanceEntity taskInstanceEntity){
        jpaTemplate.update(taskInstanceEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public TaskInstanceEntity findOne(String id){
        return jpaTemplate.findOne(TaskInstanceEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<TaskInstanceEntity> findAllLog(){ return jpaTemplate.findAll(TaskInstanceEntity.class); }


    public List<TaskInstanceEntity> findAllLogList(List<String> idList){
        return jpaTemplate.findList(TaskInstanceEntity.class,idList);
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
    public List<TaskInstanceEntity> findAllLog(String historyId){
        String sql = "select  pip_pipeline_history_log.* from  pip_pipeline_history_log ";
        sql = sql.concat(" where  pip_pipeline_history_log.history_id = '"+historyId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(TaskInstanceEntity.class));
    }

    /**
     * 根据阶段id查询日志
     * @param stagsId 历史id
     * @return 历史信息
     */
    public List<TaskInstanceEntity> findAllStagesLog(String historyId, String stagsId){
        String sql = "select  pip_pipeline_history_log.* from  pip_pipeline_history_log ";
        sql = sql.concat(" where  pip_pipeline_history_log.stages_id = '"+stagsId +"'"
                + "and  pip_pipeline_history_log.history_id = '" + historyId +"'" );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(TaskInstanceEntity.class));
    }

}
