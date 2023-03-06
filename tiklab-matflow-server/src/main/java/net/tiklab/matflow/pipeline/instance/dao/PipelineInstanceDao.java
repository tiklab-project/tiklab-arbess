package net.tiklab.matflow.pipeline.instance.dao;


import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.instance.entity.PipelineInstanceEntity;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import net.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        QueryBuilders builders = QueryBuilders.createQuery(PipelineInstanceEntity.class);
            if (PipelineUtil.isNoNull(pipelineInstanceQuery.getPipelineId())){
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
        QueryCondition pipelineExecInstanceList =  builders.pagination(pipelineInstanceQuery.getPageParam())
                .orders(pipelineInstanceQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(pipelineExecInstanceList, PipelineInstanceEntity.class);
    }

    /**
     * 查询用户所有
     * @param pipelineInstanceQuery 条件
     * @return 历史
     */
    public Pagination<PipelineInstanceEntity> findAllPageInstance(PipelineInstanceQuery pipelineInstanceQuery){
        String pipelineId = pipelineInstanceQuery.getPipelineId();
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance ";
        sql = sql.concat(" where ( pip_pipeline_instance.pipeline_id = ");

        if (PipelineUtil.isNoNull(pipelineId)){
            sql = sql.concat("'" + pipelineId + "'");
        }else {
            List<Pipeline> pipelineList = pipelineInstanceQuery.getPipelineList();
            sql = sql.concat("'" +pipelineList.get(0).getId()+ "'");
            for (int i = 1; i < pipelineList.size(); i++) {
                sql = sql.concat(" or pip_pipeline_instance.pipeline_id = '" + pipelineList.get(i).getId()+ "'");
            }
        }
        sql = sql.concat(")");
        if (pipelineInstanceQuery.getType() != 0){
            sql = sql.concat(" and pip_pipeline_instance.run_way = " + pipelineInstanceQuery.getType());
        }

        if (pipelineInstanceQuery.getState() != 0){
            sql = sql.concat(" and pip_pipeline_instance.run_status = " + pipelineInstanceQuery.getState());
        }

        sql = sql.concat(" order by pip_pipeline_instance.create_time desc , case 'pip_pipeline_instance.run_status' when 30 then 1 end");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null,
                pipelineInstanceQuery.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineInstanceEntity.class));
    }

    /**
     * 查询用户所有正在运行的流水线
     * @param pipelineInstanceQuery 条件
     * @return 历史
     */
    public Pagination<PipelineInstanceEntity> findUserRunPageInstance(PipelineInstanceQuery pipelineInstanceQuery){

        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance ";
        sql = sql.concat(" where ( pip_pipeline_instance.pipeline_id = ");

        List<Pipeline> pipelineList = pipelineInstanceQuery.getPipelineList();
        sql = sql.concat("'" +pipelineList.get(0).getId()+ "'");
        for (int i = 1; i < pipelineList.size(); i++) {
            sql = sql.concat(" or pip_pipeline_instance.pipeline_id = '" + pipelineList.get(i).getId()+ "')");
        }
        sql = sql.concat(" order by pip_pipeline_instance.create_time desc");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null,
                pipelineInstanceQuery.getPageParam(),
                new BeanPropertyRowMapper<>(PipelineInstanceEntity.class));
    }


    /**
     * 查询一定时间内的用户所有流水线历史
     * @return 流水线历史列表
     */
    public List<PipelineInstanceEntity> findAllUserInstance(String lastTime, String nowTime, StringBuilder s) {
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance ";
        sql = sql.concat(" where pip_pipeline_instance.pipeline_id "
                + " in("+ s +" )"
                + " and pip_pipeline_instance.create_time > '"+ lastTime +"'"
                + " and pip_pipeline_instance.create_time < '"+ nowTime + "'"
                + " order by pip_pipeline_instance.create_time desc");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }


    /**
     * 根据流水线id查询所有历史
     * @param pipelineId 流水线id
     * @return 历史集合
     */
    public List<PipelineInstanceEntity> findAllInstance(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        sql = sql.concat(" where pip_pipeline_instance.pipeline_id   = '"+pipelineId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }


    /**
     * 查询用户命令所有流水线历史
     * @param list 所有流水线
     * @return 历史
     */
    public List<PipelineInstanceEntity> findUserAllInstance(List<Pipeline> list){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";

        String pipelineId = list.get(0).getId();
        sql = sql.concat(" where pip_pipeline_instance.pipeline_id   = '"+ pipelineId +"' ");

        for (int i = 1; i < list.size(); i++) {
            String id = list.get(i).getId();
            sql = sql.concat(" or pip_pipeline_instance.pipeline_id   = '"+ id +"' ");
        }
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));

    }




    /**
     * 获取最近成功的历史信息
     * @param pipelineId 流水线id
     * @return 成功列表
     */
    public List<PipelineInstanceEntity> findLatelySuccess(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        sql = sql.concat(" where pip_pipeline_instance.pipeline_id   = '"+pipelineId+"' " +
                " and pip_pipeline_instance.run_status != '30'  " +
                " order by pip_pipeline_instance.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }

    /**
     * 获取最近一次构建信息
     * @param pipelineId 流水线id
     * @return 构建信息
     */
    public List<PipelineInstanceEntity> findLatelyInstance(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        sql = sql.concat(" where pip_pipeline_instance.pipeline_id = '"+pipelineId+"' " +
                " and pip_pipeline_instance.run_status != 'run'"+
                " order by pip_pipeline_instance.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }

    /**
     * 获取流水线最近一次的执行信息
     * @param pipelineId 流水线id
     * @return 执行信息
     */
    public List<PipelineInstanceEntity> findLastInstance(String pipelineId){
        String sql = "select * from pip_pipeline_instance  ";
        sql = sql.concat(" where pip_pipeline_instance.pipeline_id = '"+pipelineId+"' " +
                " and pip_pipeline_instance.run_status != 30 "+
                " order by pip_pipeline_instance.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }

    public List<PipelineInstanceEntity> findRunInstance(String pipelineId){
        String sql = "select pip_pipeline_instance.* from pip_pipeline_instance  ";
        sql = sql.concat(" where pip_pipeline_instance.pipeline_id = '"+pipelineId+"' " +
                " and pip_pipeline_instance.find_state = '0' "+
                " order by pip_pipeline_instance.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineInstanceEntity.class));
    }

}






















