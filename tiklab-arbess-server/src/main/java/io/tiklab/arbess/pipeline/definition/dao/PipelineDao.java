package io.tiklab.arbess.pipeline.definition.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.OrQueryCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.OrQueryBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.pipeline.definition.entity.PipelineEntity;
import io.tiklab.arbess.pipeline.definition.model.PipelineQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
            sql = sql.concat(" and ( ");
            sql = sql.concat(" name like '%").concat(pipelineName).concat("%'").concat(" or ");
            sql = sql.concat(" name like '%").concat(toLowerCase(pipelineName)).concat("%'").concat(" or ");
            sql = sql.concat(" name like '%").concat(toUpperCase(pipelineName)).concat("%'");
            sql = sql.concat(" )");
        }

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, query.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineEntity.class));
    }


    // 第一个方法：将字符串中的所有字母都转换为小写
    public static String toLowerCase(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            // 如果是大写字母，转换为小写
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // 第二个方法：将字符串中的所有字母都转换为大写
    public static String toUpperCase(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            // 如果是小写字母，转换为大写
            if (Character.isLowerCase(c)) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }


    public List<PipelineEntity> findPipelineList(PipelineQuery query){
        String pipelineName = query.getPipelineName();
        QueryBuilders queryBuilders = QueryBuilders.createQuery(PipelineEntity.class)
                .eq("userId", query.getCreateUserId())
                .eq("type", query.getPipelineType())
                .eq("state", query.getPipelineState())
                .eq("envId",query.getEnvId())
                .eq("groupId",query.getGroupId())
                .eq("power", query.getPipelinePower());
                if (query.isEqName()){
                    queryBuilders.eq("name",pipelineName);
                }else {
                    if (!StringUtils.isEmpty(pipelineName)){
                        OrQueryCondition orQueryCondition = OrQueryBuilders.instance()
                                .like("name", pipelineName)
                                .like("name", toLowerCase(pipelineName))
                                .like("name", toUpperCase(pipelineName))
                                .get();
                        queryBuilders.or(orQueryCondition);
                    }
                }
        QueryCondition queryCondition = queryBuilders.in("id", query.getIdString())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition, PipelineEntity.class);
    }

    public Pagination<PipelineEntity> findPipelinePage(PipelineQuery query){
        QueryBuilders queryBuilders = QueryBuilders.createQuery(PipelineEntity.class)
                .eq("userId", query.getCreateUserId())
                .eq("type", query.getPipelineType())
                .eq("state", query.getPipelineState())
                .eq("power", query.getPipelinePower())
                .eq("envId", query.getEnvId())
                .eq("groupId", query.getGroupId())
                .in("id", query.getIdString())
                .pagination(query.getPageParam())
                .orders(query.getOrderParams());
        String pipelineName = query.getPipelineName();
        if (StringUtils.isNotEmpty(pipelineName)){
            OrQueryCondition orQueryCondition = OrQueryBuilders.instance()
                    .like("name", pipelineName)
                    .like("name", getLowerCase(pipelineName))
                    .like("name", toUpperCase(pipelineName))
                    .get();

            queryBuilders = queryBuilders.or(orQueryCondition);
        }

        // queryBuilders.like("name",query.getPipelineName());
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findPage(queryCondition, PipelineEntity.class);
    }

    private static String getLowerCase(String pipelineName) {
        return toLowerCase(pipelineName);
    }

    public List<PipelineEntity> findRecentlyPipeline(Object[] pipelineIds,Integer number){
        List<Object> list = new ArrayList<>();

        String ids = Arrays.stream(pipelineIds).map(id -> "'" + id + "'")
                .collect(Collectors.joining(","));

        String sqlBuffer = " SELECT *  FROM pip_pipeline WHERE id not in (?) LIMIT ?";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        list.add(ids);
        list.add(number);
        return jdbcTemplate.query(sqlBuffer,new BeanPropertyRowMapper<>(PipelineEntity.class),list.toArray() );
    }















}
