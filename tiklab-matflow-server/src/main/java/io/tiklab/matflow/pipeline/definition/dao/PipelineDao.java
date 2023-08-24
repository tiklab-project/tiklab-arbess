package io.tiklab.matflow.pipeline.definition.dao;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import io.tiklab.matflow.pipeline.instance.entity.PipelineInstanceEntity;
import io.tiklab.matflow.support.util.PipelineUtil;
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
        if (!Objects.isNull(query.getPipelineName())){
            sql = sql.concat(" and name like '%" + query.getPipelineName() +"%'");
        }

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, query.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineEntity.class));
    }

    public List<PipelineEntity> findPipelineList(PipelineQuery query){
        QueryCondition builders = QueryBuilders.createQuery(PipelineEntity.class)
                .eq("userId",query.getUserId())
                .eq("type",query.getPipelineType())
                .eq("state",query.getPipelineState())
                .eq("power",query.getPipelinePower())
                .like("name",query.getPipelineName())
                .in("id",query.getIdString())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(builders, PipelineEntity.class);
    }

    public Pagination<PipelineEntity> findPipelinePage(PipelineQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class)
                .eq("userId",query.getUserId())
                .eq("type",query.getPipelineType())
                .eq("state",query.getPipelineState())
                .eq("power",query.getPipelinePower())
                .like("name",query.getPipelineName())
                .in("id",query.getIdString())
                .pagination(query.getPageParam())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findPage(queryCondition, PipelineEntity.class);
    }
















}
