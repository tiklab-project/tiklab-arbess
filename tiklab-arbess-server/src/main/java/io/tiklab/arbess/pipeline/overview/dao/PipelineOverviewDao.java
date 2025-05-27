package io.tiklab.arbess.pipeline.overview.dao;

import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.user.util.util.DatabaseVersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineOverviewDao {


    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 获取流水线指定状态的次数
     * @param pipelineId 流水线id
     * @param runState 状态
     * @return 次数
     */
    public Integer findPipelineRunStateNumber(String pipelineId,String runState){
        String sql = "select count(*) from pip_pipeline_instance  ";
        sql = sql.concat(" where pipeline_id = '"+pipelineId+"' ");
        sql = sql.concat(" and run_status =  '" + runState + "' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();

        List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
        if (list.isEmpty()){
            return 0;
        }
        return list.get(0);
    }


    public Integer findPipelineRunTime(String pipelineId){
        String sql ;
        if (DatabaseVersionUtil.isMysql()){
            sql = "select SUM(run_time) AS total from pip_pipeline_instance  ";
        }else {
            sql = "select SUM (run_time) AS total from pip_pipeline_instance  ";
        }
        sql = sql.concat(" where pipeline_id = '" + pipelineId + "' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
        if (list.isEmpty()){
            return 0;
        }
        return list.get(0);
    }







}
