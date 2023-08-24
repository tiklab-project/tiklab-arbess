package io.tiklab.matflow.pipeline.instance.dao;


import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.instance.entity.PipelineInstanceEntity;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PipelineInstanceDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线历史
     * @param pipelineInstanceEntity 流水线历史信息
     * @return 流水线id
     */
    public String createInstance(PipelineInstanceEntity pipelineInstanceEntity) {

        return jpaTemplate.save(pipelineInstanceEntity, String.class);

    }

    /**
     * 删除流水线历史
     * @param id 流水线历史id
     */
    public void deleteInstance(String id) {
        jpaTemplate.delete(PipelineInstanceEntity.class, id);
    }

    /**
     * 更新流水线历史
     * @param pipelineInstanceEntity 更新后流水线历史信息
     */
    public void updateInstance(PipelineInstanceEntity pipelineInstanceEntity) {

        jpaTemplate.update(pipelineInstanceEntity);

    }

    /**
     * 查询流水线历史
     * @param id 查询id
     * @return 流水线历史信息
     */
    public PipelineInstanceEntity findOneInstance(String id) {

        return jpaTemplate.findOne(PipelineInstanceEntity.class, id);
    }


    public List<PipelineInstanceEntity> findInstanceList(List<String> idList) {

        return jpaTemplate.findList(PipelineInstanceEntity.class, idList);
    }

    /**
     * 查询所有流水线历史
     * @return 流水线历史列表
     */
    public List<PipelineInstanceEntity> findAllInstance() {
        return jpaTemplate.findAll(PipelineInstanceEntity.class);
    }

    /**
     * 筛选历史
     * @param pipelineInstanceQuery 筛选条件
     * @return 历史
     */
    public Pagination<PipelineInstanceEntity> findPageInstance(PipelineInstanceQuery pipelineInstanceQuery){
        QueryBuilders queryBuilders = QueryBuilders.createQuery(PipelineInstanceEntity.class)
                .eq("pipelineId", pipelineInstanceQuery.getPipelineId())
                .eq("runStatus", pipelineInstanceQuery.getState())
                .eq("userId", pipelineInstanceQuery.getUserId())
                .like("findNumber",pipelineInstanceQuery.getNumber());

            if (pipelineInstanceQuery.getType() != 0){
                queryBuilders.eq("runWay", pipelineInstanceQuery.getType() );
            }

            if (Objects.isNull(pipelineInstanceQuery.getNumber())){
                queryBuilders.like("findNumber",pipelineInstanceQuery.getNumber());
            }
        QueryCondition pipelineExecInstanceList =  queryBuilders.pagination(pipelineInstanceQuery.getPageParam())
                .orders(pipelineInstanceQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(pipelineExecInstanceList, PipelineInstanceEntity.class);
    }


    public List<PipelineInstanceEntity> findInstanceList(PipelineInstanceQuery pipelineInstanceQuery){
        QueryBuilders queryBuilders = QueryBuilders.createQuery(PipelineInstanceEntity.class)
                .eq("pipelineId", pipelineInstanceQuery.getPipelineId())
                .eq("runStatus", pipelineInstanceQuery.getState())
                .eq("userId", pipelineInstanceQuery.getUserId())
                .like("findNumber",pipelineInstanceQuery.getNumber());

        if (pipelineInstanceQuery.getType() != 0){
            queryBuilders.eq("runWay", pipelineInstanceQuery.getType() );
        }

        if (Objects.isNull(pipelineInstanceQuery.getNumber())){
            queryBuilders.like("findNumber",pipelineInstanceQuery.getNumber());
        }
        QueryCondition pipelineExecInstanceList =  queryBuilders.pagination(pipelineInstanceQuery.getPageParam())
                .orders(pipelineInstanceQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(pipelineExecInstanceList, PipelineInstanceEntity.class);
    }

    /**
     * 查询用户所有
     * @param pipelineInstanceQuery 条件
     * @return 历史
     */
    public Pagination<PipelineInstanceEntity> findAllPageInstance(PipelineInstanceQuery pipelineInstanceQuery){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance ";
        sql = sql.concat(" where pipeline_id in ( ");
        String ids = "";
        String[] idList = pipelineInstanceQuery.getIds();

        for (int i = 0; i < idList.length; i++) {
            if (i == idList.length-1){
                ids = ids.concat("'" + idList[i]+ "'");
            }else {
                ids = ids.concat("'" + idList[i]+ "',");
            }
        }
        sql = sql.concat(ids+") ");

        if (pipelineInstanceQuery.getType() != 0){
            sql = sql.concat(" and run_way = " + pipelineInstanceQuery.getType());
        }

        if (PipelineUtil.isNoNull(pipelineInstanceQuery.getState())){
            sql = sql.concat(" and run_status = '" + pipelineInstanceQuery.getState()+ "'");
        }

        if (PipelineUtil.isNoNull(pipelineInstanceQuery.getPipelineId())){
            sql = sql.concat(" and pipeline_id = '" + pipelineInstanceQuery.getPipelineId()+ "'");
        }
        if (!Objects.isNull(pipelineInstanceQuery.getNumber() )){
            sql = sql.concat(" and find_number like '%" + pipelineInstanceQuery.getNumber()+ "%'");
        }


        sql = sql.concat(" order by create_time desc " +
                ", case 'run_status' when 'run' then 1 end");//run排在第一

        return jpaTemplate.getJdbcTemplate().findPage(sql, null,
                pipelineInstanceQuery.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineInstanceEntity.class));
    }


    /**
     * 根据流水线id查询所有历史
     * @param pipelineId 流水线id
     * @return 历史集合
     */
    public List<PipelineInstanceEntity> findAllInstance(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        sql = sql.concat(" where pipeline_id   = '"+pipelineId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }

    /**
     * 根据流水线id最近一次的构建历史
     * @param pipelineId 流水线id
     * @return 最近一次的构建历史
     */
    public PipelineInstanceEntity findLastInstance(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        sql = sql.concat(" where pipeline_id   = '" + pipelineId + "' ");
        sql = sql.concat(" order by create_time desc ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        BeanPropertyRowMapper rowMapper = new BeanPropertyRowMapper(PipelineInstanceEntity.class);
        List<PipelineInstanceEntity> list = jdbcTemplate.query(sql, rowMapper);
        if (list.isEmpty()){
            return null;
        }
        return  list.get(0);
    }

    /**
     * 最近运行的流水线进行时间排序，并分组
     * @return
     */
    public List<String> findUserPipelineInstance(){
        String sql = "SELECT pipeline_id, MAX(create_time)" +
                " FROM pip_pipeline_instance" +
                " GROUP BY pipeline_id" +
                " ORDER BY MAX(create_time) DESC";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
        if (queryForList.isEmpty()){
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : queryForList) {
            String id = (String)stringObjectMap.get("pipeline_id");
            list.add(id);
        }
        return list;
    }


    /**
     * 获取最近一次构建信息不包括正在运行的
     * @param pipelineId 流水线id
     * @return 构建信息
     */
    public List<PipelineInstanceEntity> findLatelyInstance(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        sql = sql.concat(" where pipeline_id = '"+pipelineId+"' " +
                " and run_status != 'run'"+
                " order by create_time desc" +
                " OFFSET 0 limit 1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }

}






















