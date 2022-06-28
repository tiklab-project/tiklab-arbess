package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jdbc.JdbcTemplate;
import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.instance.entity.PipelineOpenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<PipelineOpenEntity> findAllOpen(String userId){
        String sql = "select pipeline_open.* from pipeline_open ";
        sql = sql.concat(" where (pipeline_open.user_id COLLATE utf8mb4_general_ci )  = ('222222' COLLATE utf8mb4_general_ci ) " +
                " and (pipeline_open.pipeline_id COLLATE utf8mb4_general_ci )  " +
                " in " +
                " ( select p.pipeline_id  from orc_dm_user d,pipeline p" +
                " where (d.domain_id  COLLATE utf8mb4_general_ci ) = (p.pipeline_id COLLATE utf8mb4_general_ci ) " +
                " and  (d.user_id COLLATE utf8mb4_general_ci ) =  ('"+userId+"' COLLATE utf8mb4_general_ci ))");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineOpenEntity.class));
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
