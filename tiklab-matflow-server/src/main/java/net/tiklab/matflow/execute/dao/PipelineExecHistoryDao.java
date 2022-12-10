package net.tiklab.matflow.execute.dao;


import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.matflow.execute.entity.PipelineExecHistoryEntity;
import net.tiklab.matflow.execute.model.PipelineHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineExecHistoryDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线历史
     * @param pipelineExecHistoryEntity 流水线历史信息
     * @return 流水线id
     */
    public String createHistory(PipelineExecHistoryEntity pipelineExecHistoryEntity) {

        return jpaTemplate.save(pipelineExecHistoryEntity, String.class);

    }

    /**
     * 删除流水线历史
     * @param id 流水线历史id
     */
    public void deleteHistory(String id) {
        jpaTemplate.delete(PipelineExecHistoryEntity.class, id);
    }

    /**
     * 更新流水线历史
     * @param pipelineExecHistoryEntity 更新后流水线历史信息
     */
    public void updateHistory(PipelineExecHistoryEntity pipelineExecHistoryEntity) {

        jpaTemplate.update(pipelineExecHistoryEntity);

    }

    /**
     * 查询流水线历史
     * @param id 查询id
     * @return 流水线历史信息
     */
    public PipelineExecHistoryEntity findOneHistory(String id) {

        return jpaTemplate.findOne(PipelineExecHistoryEntity.class, id);
    }


    public List<PipelineExecHistoryEntity> findHistoryList(List<String> idList) {

        return jpaTemplate.findList(PipelineExecHistoryEntity.class, idList);
    }

    /**
     * 查询所有流水线历史
     * @return 流水线历史列表
     */
    public List<PipelineExecHistoryEntity> findAllHistory() {

        return jpaTemplate.findAll(PipelineExecHistoryEntity.class);
    }

    /**
     * 筛选历史
     * @param pipelineHistoryQuery 筛选条件
     * @return 历史
     */
    public Pagination<PipelineExecHistoryEntity> findPageHistory(PipelineHistoryQuery pipelineHistoryQuery){
        QueryBuilders builders = QueryBuilders.createQuery(PipelineExecHistoryEntity.class)
                    .eq("pipelineId", pipelineHistoryQuery.getPipelineId());
            if (pipelineHistoryQuery.getState() != 0) {
                builders.eq("runStatus", pipelineHistoryQuery.getState());
            }
            if (pipelineHistoryQuery.getType() != 0){
                builders.eq("runWay", pipelineHistoryQuery.getType() );
            }
            if (pipelineHistoryQuery.getUserId() != null){
                builders.eq("userId", pipelineHistoryQuery.getUserId() );
            }
        QueryCondition pipelineExecHistoryList =  builders.pagination(pipelineHistoryQuery.getPageParam())
                .orders(pipelineHistoryQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(pipelineExecHistoryList, PipelineExecHistoryEntity.class);
    }

    /**
     * 查询一定时间内的用户所有流水线历史
     * @return 流水线历史列表
     */
    public List<PipelineExecHistoryEntity> findAllUserHistory(String lastTime, String nowTime, StringBuilder s) {
        String sql = "select pip_pipeline_history.* from pip_pipeline_history ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id "
                + " in("+ s +" )"
                + " and pip_pipeline_history.create_time > '"+ lastTime +"'"
                + " and pip_pipeline_history.create_time < '"+nowTime + "'"
                + " order by pip_pipeline_history.create_time desc");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecHistoryEntity.class));
    }


    /**
     * 根据流水线id查询所有历史
     * @param pipelineId 流水线id
     * @return 历史集合
     */
    public List<PipelineExecHistoryEntity> findAllHistory(String pipelineId){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id   = '"+pipelineId+"' " +
                " and pip_pipeline_history.find_state = 1 ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecHistoryEntity.class));
    }

    /**
     * 获取最近成功的历史信息
     * @param pipelineId 流水线id
     * @return 成功列表
     */
    public List<PipelineExecHistoryEntity> findLatelySuccess(String pipelineId){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id   = '"+pipelineId+"' " +
                " and pip_pipeline_history.run_status = '30'  " +
                " order by pip_pipeline_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecHistoryEntity.class));
    }

    /**
     * 获取最近一次构建信息
     * @param pipelineId 流水线id
     * @return 构建信息
     */
    public List<PipelineExecHistoryEntity> findLatelyHistory(String pipelineId){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id = '"+pipelineId+"' " +
                " order by pip_pipeline_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecHistoryEntity.class));
    }


}