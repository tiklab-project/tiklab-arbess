package io.tiklab.arbess.pipeline.definition.dao;

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
    public Pagination<PipelineEntity> findPipelineListQuery(PipelineQuery query){
        String sql = "select pip_pipeline.* from pip_pipeline ";
        sql = sql.concat(" where id in ( ");
        String ids = "";
        String[] idList = query.getIdString();

        for (int i = 0; i < idList.length; i++) {
            if (i == idList.length-1){
                ids = ids.concat("'" + idList[i]+ "'");
            }else {
                ids = ids.concat("'" + idList[i]+ "',");
            }
        }

        sql = sql.concat(ids+") and ");

        Integer follow = query.getPipelineFollow();
        String followSql=  "select pip_other_follow.pipeline_id from pip_other_follow where user_id = '"+query.getUserId()+"'";
        if (follow == 1){
            sql = sql.concat(" id in  (" +followSql +")");
        }else {
            sql = sql.concat(" id not in (" +followSql +")");
        }

        String pipelineName = query.getPipelineName();
        if (!Objects.isNull(pipelineName)){
            sql = sql.concat(" and name ILIKE '%").concat(pipelineName).concat("%'");
        }

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, query.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineEntity.class));
    }


    public List<PipelineEntity> findPipelineList(PipelineQuery query){
        PipelineCondition condition = findQueryCondition(query);
        return jpaTemplate.getJdbcTemplate().query(condition.getSql(), condition.getObjects(),
                new BeanPropertyRowMapper<>(PipelineEntity.class));
    }

    public Pagination<PipelineEntity> findPipelinePage(PipelineQuery query){
        PipelineCondition condition = findQueryCondition(query);
                return jpaTemplate.getJdbcTemplate().findPage(condition.getSql(), condition.getObjects(),
                        query.getPageParam(),new BeanPropertyRowMapper<>(PipelineEntity.class));
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


    PipelineCondition findQueryCondition(PipelineQuery query) {

        List<Object> params = new ArrayList<>();
        String sql = "select pip_pipeline.* from pip_pipeline where 1=1";

        if (!StringUtils.isBlank(query.getCreateUserId())){
            sql = sql.concat(" and user_id = ?");
            params.add(query.getCreateUserId());
        }

        if (!StringUtils.isBlank(query.getPipelineName())){
            sql = sql.concat(" and name ILIKE '%"+query.getPipelineName()+"%'");
            // params.add(query.getPipelineName());
        }

        if (!Objects.isNull(query.getPipelineState()) && query.getPipelineState()!=0){
            sql = sql.concat(" and state = ?");
            params.add(query.getPipelineState());
        }

        if (!StringUtils.isBlank(query.getEnvId())){
            sql = sql.concat(" and env_id = ?");
            params.add(query.getEnvId());
        }

        if (!StringUtils.isBlank(query.getGroupId())){
            sql = sql.concat(" and group_id = ?");
            params.add(query.getGroupId());
        }

        if (!Objects.isNull(query.getPipelinePower()) && query.getPipelinePower()!=0){
            sql = sql.concat(" and power = ?");
            params.add(query.getPipelinePower());
        }

        if (!Objects.isNull(query.getIdString()) && query.getIdString().length !=0 ){
            String ids = "";
            String[] idList = query.getIdString();
            for (int i = 0; i < idList.length; i++) {
                if (i == idList.length-1){
                    ids = ids.concat("'" + idList[i]+ "'");
                }else {
                    ids = ids.concat("'" + idList[i]+ "',");
                }
            }
            sql = sql.concat(" and id in ("+ids+") ");

            // String ids = Arrays.stream(query.getIdString())
            //         .map(id -> "'" + id + "'")
            //         .collect(Collectors.joining(","));
            // sql = sql.concat(" and id in (" + ids + ") ");

        }

        if (query.getOrderParams() != null && !query.getOrderParams().isEmpty()) {
            sql += " order by " + orderBy(query.getOrderParams(), PipelineEntity.class);
        }
        return new PipelineCondition(sql,params.toArray());

    }

}


class PipelineCondition{

    private String sql;

    private Object[] objects;

    public PipelineCondition(String sql, Object[] objects) {
        this.sql = sql;
        this.objects = objects;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}