package net.tiklab.matflow.definition.dao;


import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineCourseConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PipelineConfigureDao
 */

@Repository
public class PipelineCourseConfigDao {

    @Autowired
    JpaTemplate jpaTemplate;


    private static final Logger logger = LoggerFactory.getLogger(PipelineCourseConfigDao.class);

    /**
     * 添加流水线配置信息
     * @param pipelineConfigureEntity 配置信息
     * @return 配置信息id
     */
    public String createConfigure(PipelineCourseConfigEntity pipelineConfigureEntity){
        return jpaTemplate.save(pipelineConfigureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deleteConfigure(String id){
        jpaTemplate.delete(PipelineCourseConfigEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param pipelineConfigureEntity 配置信息
     */
    public void updateConfigure(PipelineCourseConfigEntity pipelineConfigureEntity){
        jpaTemplate.update(pipelineConfigureEntity);
    }

    /**
     * 查询配置信息
     * @param configureId 查询id
     * @return 配置信息
     */
    public PipelineCourseConfigEntity findOneConfigure(String configureId){
        return jpaTemplate.findOne(PipelineCourseConfigEntity.class, configureId);
    }

    /**
     * 根据流水线id获取配置
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    public List<PipelineCourseConfigEntity> findAllConfigure(String pipelineId){
        String sql = "select pipeline_course_config.* from pipeline_course_config";
        sql = sql.concat(" where pipeline_course_config.pipeline_id = '"+ pipelineId+"' " );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineCourseConfigEntity.class));
    }


    /**
     * 查询所有配置信息
     * @return 配置信息集合
     */
    public List<PipelineCourseConfigEntity> findAllConfigure(){
        return jpaTemplate.findAll(PipelineCourseConfigEntity.class);
    }

    public List<PipelineCourseConfigEntity> findAllConfigureList(List<String> idList){
        return jpaTemplate.findList(PipelineCourseConfigEntity.class,idList);
    }
}
