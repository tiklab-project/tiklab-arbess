package io.tiklab.arbess.pipeline.definition.dao;

import io.tiklab.arbess.pipeline.definition.entity.PipelineFollowEntity;
import io.tiklab.arbess.pipeline.definition.model.PipelineFollow;
import io.tiklab.core.exception.SystemException;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderTypeEnum;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.dal.jpa.criterial.condition.OrQueryCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.OrQueryBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.pipeline.definition.entity.PipelineEntity;
import io.tiklab.arbess.pipeline.definition.model.PipelineQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流水线数据访问
 */

@Repository
public class PipelineDao {

    private static final Logger log = LoggerFactory.getLogger(PipelineDao.class);


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

    // 关联查询，查询出收藏的
    public Pagination<PipelineEntity> findPipelinePageByFollow(PipelineQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class,"pip")
                .leftJoin(PipelineFollowEntity.class, "pif", "pip.id=pif.pipelineId")
                .eq("pip.userId", query.getCreateUserId())
                .eq("pif.userId", query.getUserId())
                .eq("pip.envId", query.getEnvId())
                .eq("pip.groupId", query.getGroupId())
                .eq("pip.state", query.getPipelineState())
                .eq("pip.power", query.getPipelinePower())
                .in("pip.id", query.getIdString())
                .like("pip.name", query.getPipelineName(), false)
                .orders(query.getOrderParams())
                .pagination(query.getPageParam())
                .get();

        return jpaTemplate.findPage(queryCondition,PipelineEntity.class);
    }

    public List<PipelineEntity> findPipelineList(PipelineQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class)
                .eq("userId", query.getCreateUserId())
                .eq("envId", query.getEnvId())
                .eq("groupId", query.getGroupId())
                .eq("state", query.getPipelineState())
                .eq("power", query.getPipelinePower())
                .in("id", query.getIdString())
                .like("name", query.getPipelineName(), false)
                .orders(query.getOrderParams())
                .get();
       return jpaTemplate.findList(queryCondition, PipelineEntity.class);

    }

    public Pagination<PipelineEntity> findPipelinePage(PipelineQuery query){

        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class)
                .eq("userId", query.getCreateUserId())
                .eq("envId", query.getEnvId())
                .eq("groupId", query.getGroupId())
                .eq("state", query.getPipelineState())
                .eq("power", query.getPipelinePower())
                .in("id", query.getIdString())
                .like("name", query.getPipelineName(), false)
                .orders(query.getOrderParams())
                .pagination(query.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,PipelineEntity.class);
    }


    public <T> String orderBy (List<Order> orders,Class<T> clazz, String... alias) {
        StringBuilder sqlDesc = new StringBuilder();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            String key = order.getName();
            OrderTypeEnum orderType = order.getOrderType();

            String columnName = null;
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String name = field.getName();
                if (name.equals(key)){
                    Column column = field.getAnnotation(Column.class);
                    columnName = column.name();
                }
            }
            if (StringUtils.isEmpty(columnName)){
                throw new SystemException("字段"+key+"实体字段不存在！");
            }

            if (alias.length == 0){
                if ( i + 1 == orders.size()) {
                    sqlDesc.append(columnName).append(" ").append(orderType);
                } else {
                    sqlDesc.append(columnName).append(" ").append(orderType).append(" ").append(',');
                }
            }else {
                if ( i + 1 == orders.size()) {
                    sqlDesc.append(alias[0]).append(".").append(columnName).append(" ").append(orderType);
                } else {
                    sqlDesc.append(alias[0]).append(".").append(columnName).append(" ").append(orderType).append(" ").append(',');
                }
            }
        }
        return sqlDesc.toString();
    }

}

