package net.tiklab.matflow.pipeline.instance.dao;


import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.instance.entity.PipelineExecInstanceEntity;
import net.tiklab.matflow.pipeline.instance.model.PipelineAllInstanceQuery;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import net.tiklab.matflow.support.until.PipelineUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineExecInstanceDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线历史
     * @param pipelineExecInstanceEntity 流水线历史信息
     * @return 流水线id
     */
    public String createHistory(PipelineExecInstanceEntity pipelineExecInstanceEntity) {

        return jpaTemplate.save(pipelineExecInstanceEntity, String.class);

    }

    /**
     * 删除流水线历史
     * @param id 流水线历史id
     */
    public void deleteHistory(String id) {
        jpaTemplate.delete(PipelineExecInstanceEntity.class, id);
    }

    /**
     * 更新流水线历史
     * @param pipelineExecInstanceEntity 更新后流水线历史信息
     */
    public void updateHistory(PipelineExecInstanceEntity pipelineExecInstanceEntity) {

        jpaTemplate.update(pipelineExecInstanceEntity);

    }

    /**
     * 查询流水线历史
     * @param id 查询id
     * @return 流水线历史信息
     */
    public PipelineExecInstanceEntity findOneHistory(String id) {

        return jpaTemplate.findOne(PipelineExecInstanceEntity.class, id);
    }


    public List<PipelineExecInstanceEntity> findHistoryList(List<String> idList) {

        return jpaTemplate.findList(PipelineExecInstanceEntity.class, idList);
    }

    /**
     * 查询所有流水线历史
     * @return 流水线历史列表
     */
    public List<PipelineExecInstanceEntity> findAllHistory() {
        return jpaTemplate.findAll(PipelineExecInstanceEntity.class);
    }

    /**
     * 筛选历史
     * @param pipelineInstanceQuery 筛选条件
     * @return 历史
     */
    public Pagination<PipelineExecInstanceEntity> findPageHistory(PipelineInstanceQuery pipelineInstanceQuery){
        QueryBuilders builders = QueryBuilders.createQuery(PipelineExecInstanceEntity.class);
            if (PipelineUntil.isNoNull(pipelineInstanceQuery.getPipelineId())){
                builders.eq("pipelineId", pipelineInstanceQuery.getPipelineId());
            }
            if (pipelineInstanceQuery.getState() != 0) {
                builders.eq("runStatus", pipelineInstanceQuery.getState());
            }
            if (pipelineInstanceQuery.getType() != 0){
                builders.eq("runWay", pipelineInstanceQuery.getType() );
            }
            if (pipelineInstanceQuery.getUserId() != null){
                builders.eq("userId", pipelineInstanceQuery.getUserId() );
            }
        QueryCondition pipelineExecHistoryList =  builders.pagination(pipelineInstanceQuery.getPageParam())
                .orders(pipelineInstanceQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(pipelineExecHistoryList, PipelineExecInstanceEntity.class);
    }

    /**
     * 查询用户所有
     * @param pipelineHistoryQuery 条件
     * @return 历史
     */
    public Pagination<PipelineExecInstanceEntity> findAllPageHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        String pipelineId = pipelineHistoryQuery.getPipelineId();
        String sql = "select pip_pipeline_history.* from pip_pipeline_history ";
        sql = sql.concat(" where ( pip_pipeline_history.pipeline_id = ");

        if (PipelineUntil.isNoNull(pipelineId)){
            sql = sql.concat("'" + pipelineId + "'");
        }else {
            List<Pipeline> pipelineList = pipelineHistoryQuery.getPipelineList();
            sql = sql.concat("'" +pipelineList.get(0).getId()+ "'");
            for (int i = 1; i < pipelineList.size(); i++) {
                sql = sql.concat(" or pip_pipeline_history.pipeline_id = '" + pipelineList.get(i).getId()+ "'");
            }
        }
        sql = sql.concat(")");
        if (pipelineHistoryQuery.getType() != 0){
            sql = sql.concat(" and pip_pipeline_history.run_way = " + pipelineHistoryQuery.getType());
        }

        if (pipelineHistoryQuery.getState() != 0){
            sql = sql.concat(" and pip_pipeline_history.run_status = " + pipelineHistoryQuery.getState());
        }

        sql = sql.concat(" order by pip_pipeline_history.create_time desc , case 'pip_pipeline_history.run_status' when 30 then 1 end");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null,
                pipelineHistoryQuery.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineExecInstanceEntity.class));
    }

    /**
     * 查询用户所有正在运行的流水线
     * @param pipelineHistoryQuery 条件
     * @return 历史
     */
    public Pagination<PipelineExecInstanceEntity> findUserRunPageHistory(PipelineAllInstanceQuery pipelineHistoryQuery){

        String sql = "select pip_pipeline_history.* from pip_pipeline_history ";
        sql = sql.concat(" where ( pip_pipeline_history.pipeline_id = ");

        List<Pipeline> pipelineList = pipelineHistoryQuery.getPipelineList();
        sql = sql.concat("'" +pipelineList.get(0).getId()+ "'");
        for (int i = 1; i < pipelineList.size(); i++) {
            sql = sql.concat(" or pip_pipeline_history.pipeline_id = '" + pipelineList.get(i).getId()+ "')");
        }
        sql = sql.concat(" order by pip_pipeline_history.create_time desc");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null,
                pipelineHistoryQuery.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineExecInstanceEntity.class));
    }


    /**
     * 查询一定时间内的用户所有流水线历史
     * @return 流水线历史列表
     */
    public List<PipelineExecInstanceEntity> findAllUserHistory(String lastTime, String nowTime, StringBuilder s) {
        String sql = "select pip_pipeline_history.* from pip_pipeline_history ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id "
                + " in("+ s +" )"
                + " and pip_pipeline_history.create_time > '"+ lastTime +"'"
                + " and pip_pipeline_history.create_time < '"+ nowTime + "'"
                + " order by pip_pipeline_history.create_time desc");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecInstanceEntity.class));
    }


    /**
     * 根据流水线id查询所有历史
     * @param pipelineId 流水线id
     * @return 历史集合
     */
    public List<PipelineExecInstanceEntity> findAllHistory(String pipelineId){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id   = '"+pipelineId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecInstanceEntity.class));
    }


    /**
     * 查询用户命令所有流水线历史
     * @param list 所有流水线
     * @return 历史
     */
    public List<PipelineExecInstanceEntity> findUserAllHistory(List<Pipeline> list){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";

        String pipelineId = list.get(0).getId();
        sql = sql.concat(" where pip_pipeline_history.pipeline_id   = '"+ pipelineId +"' ");

        for (int i = 1; i < list.size(); i++) {
            String id = list.get(i).getId();
            sql = sql.concat(" or pip_pipeline_history.pipeline_id   = '"+ id +"' ");
        }
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecInstanceEntity.class));

    }




    /**
     * 获取最近成功的历史信息
     * @param pipelineId 流水线id
     * @return 成功列表
     */
    public List<PipelineExecInstanceEntity> findLatelySuccess(String pipelineId){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id   = '"+pipelineId+"' " +
                " and pip_pipeline_history.run_status != '30'  " +
                " order by pip_pipeline_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecInstanceEntity.class));
    }

    /**
     * 获取最近一次构建信息
     * @param pipelineId 流水线id
     * @return 构建信息
     */
    public List<PipelineExecInstanceEntity> findLatelyHistory(String pipelineId){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id = '"+pipelineId+"' " +
                " order by pip_pipeline_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecInstanceEntity.class));
    }

    /**
     * 获取流水线最近一次的执行信息
     * @param pipelineId 流水线id
     * @return 执行信息
     */
    public List<PipelineExecInstanceEntity> findLastHistory(String pipelineId){
        String sql = "select * from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id = '"+pipelineId+"' " +
                " and pip_pipeline_history.run_status != 30 "+
                " order by pip_pipeline_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecInstanceEntity.class));
    }

    public List<PipelineExecInstanceEntity> findRunHistory(String pipelineId){
        String sql = "select pip_pipeline_history.* from pip_pipeline_history  ";
        sql = sql.concat(" where pip_pipeline_history.pipeline_id = '"+pipelineId+"' " +
                " and pip_pipeline_history.find_state = '0' "+
                " order by pip_pipeline_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineExecInstanceEntity.class));
    }

}






















