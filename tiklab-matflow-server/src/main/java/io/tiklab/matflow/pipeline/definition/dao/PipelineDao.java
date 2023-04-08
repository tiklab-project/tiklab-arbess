package io.tiklab.matflow.pipeline.definition.dao;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 流水线数据访问
 */

@Repository
public class PipelineDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线
     * @param pipelineEntity 流水线实体
     * @return 流水线id
     */
    public String createPipeline(PipelineEntity pipelineEntity){
        return jpaTemplate.save(pipelineEntity, String.class);
    }

    /**
     * 删除流水线
     * @param id 流水线id
     */
    public void deletePipeline(String id){
        jpaTemplate.delete(PipelineEntity.class,id);
    }

    /**
     * 更新流水线
     * @param pipelineEntity 流水线实体
     */
    public void updatePipeline(PipelineEntity pipelineEntity){
        jpaTemplate.update(pipelineEntity);
    }

    /**
     * 查询单个流水线
     * @param id 流水线id
     * @return 流水线信息
     */
    public PipelineEntity findPipelineById(String id){
        return jpaTemplate.findOne(PipelineEntity.class,id);
    }

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    public List<PipelineEntity> findAllPipeline(){
        return jpaTemplate.findAll(PipelineEntity.class);
    }


    public List<PipelineEntity> findAllPipelineList(List<String> idList){
        return jpaTemplate.findList(PipelineEntity.class,idList);
    }

    /**
     * 获取用户拥有的流水线
     * @param strings 拼装的流水线id
     * @return 流水线实体集合
     */
    public List<PipelineEntity> findUserPipeline(String[] strings){
        List<Order> orderList = OrderBuilders.instance().desc("createTime").get();
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class)
                .in("id", strings)
                .orders(orderList)
                .get();
        return jpaTemplate.findList(queryCondition,PipelineEntity.class);
    }

    /**
     * 查询全局流水线
     * @return 流水线实体集合
     */
    public List<PipelineEntity> findAllPublicPipeline(){
        String sql = " select p.* from pip_pipeline p";
        sql = sql.concat(" where p.power = 1");
        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    /**
     * 获取用户收藏的流水线
     * @param userId 用户id
     * @param idString 拼装的流水线id
     * @return 流水线实体集合
     */
    public List<PipelineEntity> findPipelineFollow(String userId, StringBuilder idString){
        String sql = "select p.* from pip_pipeline p ";
        sql = sql.concat(" where p.id  "
                + " in ("+ idString +")"
                + " and p.id "
                + " in (select pip_other_follow.pipeline_id from pip_other_follow"
                + " where pip_other_follow.user_id  =  '" + userId + "'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    public Pagination<PipelineEntity> findUserPipelineQuery(String[] idString, PipelineQuery query){
        QueryBuilders builders = QueryBuilders.createQuery(PipelineEntity.class);

        if (!Objects.isNull(query.getUserId())){
            builders.eq("userId",query.getUserId());
        }

        if (!Objects.isNull(query.getPipelineName())){
            builders.like("name",query.getPipelineName());
        }

        if (!Objects.isNull(query.getPipelineType())){
            builders.eq("type",query.getPipelineType());
        }
        if (!Objects.isNull(query.getPipelineState())){
            builders.eq("state",query.getPipelineState());
        }

        if (!Objects.isNull(query.getPipelinePower())){
            builders.eq("power",query.getPipelinePower());
        }

        builders.in("id",idString);


        QueryCondition pipelineEntityList =  builders.pagination(query.getPageParam())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findPage(pipelineEntityList, PipelineEntity.class);
    }

    /**
     * 查询用户未收藏的流水线
     * @param userId 用户id
     * @param idString 拼装的流水线id
     * @return 流水线实体集合
     */
    public List<PipelineEntity> findPipelineNotFollow(String userId, StringBuilder idString){
        String sql = "select p.* from pip_pipeline p ";
        sql = sql.concat(" where p.id  "
                + " in ("+ idString +")"
                + " and p.id "
                + " not in (select pip_other_follow.pipeline_id from pip_other_follow"
                + " where pip_other_follow.user_id  =  '" + userId + "'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    /**
     * 根据名称模糊查询
     * @param pipelineName 查询条件
     * @return 流水线集合
     */
    public List<PipelineEntity> findPipelineByName(String pipelineName){
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class)
                .like("name", pipelineName).get();
        return jpaTemplate.findList(queryCondition, PipelineEntity.class);
    }
















}
