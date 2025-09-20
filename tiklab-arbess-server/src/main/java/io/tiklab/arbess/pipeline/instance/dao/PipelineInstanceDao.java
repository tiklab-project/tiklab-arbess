package io.tiklab.arbess.pipeline.instance.dao;


import io.tiklab.arbess.support.util.util.DataBaseVersionUtil;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.pipeline.instance.entity.PipelineInstanceEntity;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
                .eq("userId", pipelineInstanceQuery.getExecUserId())
                .in("runStatus", pipelineInstanceQuery.getStatusList())
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
                .eq("userId", pipelineInstanceQuery.getExecUserId())
                .in("pipelineId",pipelineInstanceQuery.getIds())
                .in("runStatus", pipelineInstanceQuery.getStatusList())
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
        String sql = "select * from pip_pipeline_instance ";
        sql = sql.concat(" where 1 = 1 ");

        String[] idList = pipelineInstanceQuery.getIds();
        if (!Objects.isNull(idList) && idList.length > 0){
            String ids = Arrays.stream(idList).map(id -> "'" + id + "'").collect(Collectors.joining(","));
            sql = sql.concat(" and pipeline_id in ( "+ids+" ) ");
        }

        if (pipelineInstanceQuery.getType() != 0){
            sql = sql.concat(" and run_way = " + pipelineInstanceQuery.getType());
        }

        if (PipelineUtil.isNoNull(pipelineInstanceQuery.getState())){
            sql = sql.concat(" and run_status = '" + pipelineInstanceQuery.getState()+ "'");
        }

        if (PipelineUtil.isNoNull(pipelineInstanceQuery.getPipelineId())){
            sql = sql.concat(" and pipeline_id = '" + pipelineInstanceQuery.getPipelineId()+ "'");
        }

        if (PipelineUtil.isNoNull(pipelineInstanceQuery.getExecUserId())){
            sql = sql.concat(" and user_id = '" + pipelineInstanceQuery.getExecUserId()+ "'");
        }

        if (!Objects.isNull(pipelineInstanceQuery.getNumber())){
            String number = pipelineInstanceQuery.getNumber();
            sql = sql.concat(" and ( find_number like '%" + number+ "%' " +
                    "or pipeline_id in (select id from pip_pipeline where name like '%"+number+"%'))");
        }

        //正在运行的流水线排在第一
        // sql = sql.concat(" order by create_time desc , case 'run_status' when 'run' then 1 end");
        sql = sql.concat(" order by create_time desc");

        return jpaTemplate.getJdbcTemplate().findPage(sql, new Object[]{}, pipelineInstanceQuery.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineInstanceEntity.class));
    }


    /**
     * 最近运行的流水线进行时间排序，并分组
     * @return
     */
    public List<PipelineInstanceEntity> findUserPipelineInstance(String userId,Integer limit){

        // 子查询： 根据流水线id分组，userId筛选获取用户运行的流水线，
        // 然后将子查询的结果作为临时表与主查询中的 pip_pipeline_instance 表进行联接
        String sql = "select p1.*" +
                " from pip_pipeline_instance p1" +
                " join (" +
                "    select pipeline_id, MAX(create_time) AS max_create_time" +
                "    from pip_pipeline_instance" +
                "    where user_id = '" + userId + "'" +
                "    group by pipeline_id" +
                " ) p2 on p1.pipeline_id = p2.pipeline_id and p1.create_time = p2.max_create_time" +
                " order by p1.create_time DESC" +
                " limit " + limit ;
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<PipelineInstanceEntity> instanceEntityList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
        if (instanceEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return instanceEntityList;
    }


    /**
     * 获取最近一次构建信息不包括正在运行的
     * @param pipelineId 流水线id
     * @return 构建信息
     */
    public List<PipelineInstanceEntity> findLatelyInstance(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";

        if (DataBaseVersionUtil.isMysql()){
            sql = sql.concat(" where pipeline_id = '"+pipelineId+"' " +
                    " and run_status != 'run'"+
                    " order by create_time desc" +
                    " LIMIT 1 OFFSET 0");
        }else {
            sql = sql.concat(" where pipeline_id = '"+pipelineId+"' " +
                    " and run_status != 'run'"+
                    " order by create_time desc" +
                    " limit 1 OFFSET 0");
        }
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }


    /**
     * 获取最近构建信息不包括正在运行的
     * @param pipelineId 流水线id
     * @param queryTime 时间 [开始时间,结束时间]
     * @return 构建信息
     */
    public List<PipelineInstanceEntity> findInstanceByTime(String pipelineId,String[] queryTime){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        if (StringUtils.isEmpty(pipelineId)){
            sql = sql.concat(" where run_status != 'run'  and create_time between ? and ? " );
            JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
            return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class),queryTime[0],queryTime[1]);
        }else {
            sql = sql.concat(" where pipeline_id = ? and run_status != 'run'  and create_time between ? and ? " );
            JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
            return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class),pipelineId,queryTime[0],queryTime[1]);
        }
    }

    /**
     * 获取最近构建信息不包括正在运行的
     * @param queryTime 时间 [开始时间,结束时间]
     * @return 构建信息
     */
    public List<PipelineInstanceEntity> findInstanceByTime(String[] queryTime,String[] pipelineIdList){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance";
        sql = sql.concat(" where run_status != 'run'  and create_time between ? and ? " );
        String join = String.join("','", pipelineIdList);

        sql = sql.concat(" and pipeline_id in ('" +join+"')");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class),queryTime[0],queryTime[1]);
    }

}






















