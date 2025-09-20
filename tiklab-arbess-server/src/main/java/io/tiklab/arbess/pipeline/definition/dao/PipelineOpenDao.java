package io.tiklab.arbess.pipeline.definition.dao;


import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.pipeline.definition.entity.PipelineOpenEntity;
import io.tiklab.arbess.pipeline.definition.model.PipelineOpenQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PipelineOpenDao {

    private static final Logger logger = LoggerFactory.getLogger(PipelineOpenDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建次数
     * @param pipelineOpenEntity 次数
     * @return 次数id
     */
    public String createOpen(PipelineOpenEntity pipelineOpenEntity){
        return jpaTemplate.save(pipelineOpenEntity, String.class);
    }

    /**
     * 删除次数
     * @param openId 次数id
     */
    public void deleteOpen(String openId){
        jpaTemplate.delete(PipelineOpenEntity.class, openId);
    }

    /**
     * 更新次数
     * @param pipelineOpenEntity 更新信息
     */
    public void updateOpen(PipelineOpenEntity pipelineOpenEntity){
        jpaTemplate.update(pipelineOpenEntity);
    }

    /**
     * 查询单个次数信息
     * @param openId 次数id
     * @return 次数信息
     */
    public PipelineOpenEntity findOneOpen(String openId){
        return jpaTemplate.findOne(PipelineOpenEntity.class,openId);
    }

    /**
     * 查询用户最近打开的流水线
     * @param userId 用户id
     * @return 最近打开信息
     */
    public List<PipelineOpenEntity> findUserAllOpen(String userId){
        String sql = "select pip_other_open.* from pip_other_open ";
        sql = sql.concat(" where pip_other_open.user_id   = '"+userId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineOpenEntity.class));
    }

    public Integer findUserOpenNumberByTime(String userId,String pipelineId,String time){
        String sql = "SELECT count(*) FROM pip_other_open WHERE \"user_id\" = '" + userId +
                "' and  pipeline_id = '" +pipelineId ;
                // "' and create_time >= '"+ time +"'";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
        if (list.isEmpty()){
            return 0;
        }
        return  list.get(0);
    }


    public Integer findUserOpenNumber(String userId,String pipelineId){
        String sql = "SELECT count(*) FROM pip_other_open WHERE \"user_id\" = '" + userId +
                "' and  pipeline_id = '" +pipelineId +"'";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
        if (list.isEmpty()){
            return 0;
        }
        return  list.get(0);
    }

    public List<String> findUserPipelineOpen(String userId,Integer number){
        String sql = "SELECT pipeline_id, MAX(create_time)" +
                " FROM pip_other_open" +
                " WHERE user_id = '"+userId+"'" +
                " GROUP BY pipeline_id" +
                " ORDER BY MAX(create_time) DESC" +
                " LIMIT "+ number;
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
        if (queryForList.isEmpty()){
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : queryForList) {
            String id = (String)stringObjectMap.get("pipeline_id");
            list.add(id);
        }
        return list;
    }

    public List<String> findUserOpen(String userId,Integer number,String pipelineIds){

        StringBuilder sqlBuffer = new StringBuilder();
        sqlBuffer.append(" SELECT pipeline_id, MAX(create_time)");
        sqlBuffer.append("FROM pip_other_open");
        sqlBuffer.append(" WHERE user_id = '").append(userId).append("'");
        sqlBuffer.append(" and pipeline_id in (").append(pipelineIds).append(")");
        sqlBuffer.append(" GROUP BY pipeline_id");
        sqlBuffer.append(" ORDER BY MAX(create_time) DESC");
        sqlBuffer.append(" LIMIT ").append(number);

        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlBuffer.toString());
        if (queryForList.isEmpty()){
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : queryForList) {
            String id = (String)stringObjectMap.get("pipeline_id");
            list.add(id);
        }
        return list;
    }

    public List<PipelineOpenEntity> findPipelineOpenList(PipelineOpenQuery query){
        QueryCondition builders = QueryBuilders.createQuery(PipelineOpenEntity.class)
                .eq("userId",query.getUserId())
                .eq("pipelineId",query.getPipelineId())
                .in("pipelineId",query.getPipelineIds())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(builders, PipelineOpenEntity.class);
    }


    public Pagination<PipelineOpenEntity> findPipelineOpenPage(PipelineOpenQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineOpenEntity.class)
                .eq("userId",query.getUserId())
                .eq("pipelineId",query.getPipelineId())
                .in("pipelineId",query.getPipelineIds())
                .orders(query.getOrderParams())
                .pagination(query.getPageParam())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findPage(queryCondition, PipelineOpenEntity.class);
    }


    /**
     * 查询所有次数
     * @return 次数集合
     */
    public List<PipelineOpenEntity> findAllOpen(){
        return jpaTemplate.findAll(PipelineOpenEntity.class);
    }

    public List<PipelineOpenEntity> findAllOpenList(List<String> idList){
        return jpaTemplate.findList(PipelineOpenEntity.class,idList);
    }



}
