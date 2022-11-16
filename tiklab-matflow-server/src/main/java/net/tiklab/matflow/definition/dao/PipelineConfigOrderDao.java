package net.tiklab.matflow.definition.dao;


import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineConfigOrderEntity;
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
public class PipelineConfigOrderDao {

    @Autowired
    JpaTemplate jpaTemplate;


    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigOrderDao.class);

    /**
     * 添加流水线配置信息
     * @param pipelineConfigureEntity 配置信息
     * @return 配置信息id
     */
    public String createConfigure(PipelineConfigOrderEntity pipelineConfigureEntity){
        return jpaTemplate.save(pipelineConfigureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deleteConfigure(String id){
        jpaTemplate.delete(PipelineConfigOrderEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param pipelineConfigureEntity 配置信息
     */
    public void updateConfigure(PipelineConfigOrderEntity pipelineConfigureEntity){
        jpaTemplate.update(pipelineConfigureEntity);
    }

    /**
     * 查询配置信息
     * @param configureId 查询id
     * @return 配置信息
     */
    public PipelineConfigOrderEntity findOneConfigure(String configureId){
        return jpaTemplate.findOne(PipelineConfigOrderEntity.class, configureId);
    }

    /**
     * 根据流水线id获取配置
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    public List<PipelineConfigOrderEntity> findAllConfigure(String pipelineId){
        String sql = "select pipeline_config_order.* from pipeline_config_order";
        sql = sql.concat(" where pipeline_config_order.pipeline_id = '"+ pipelineId+"' " );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineConfigOrderEntity.class));
    }


    /**
     * 查询所有配置信息
     * @return 配置信息集合
     */
    public List<PipelineConfigOrderEntity> findAllConfigure(){
        return jpaTemplate.findAll(PipelineConfigOrderEntity.class);
    }

    public List<PipelineConfigOrderEntity> findAllConfigureList(List<String> idList){
        return jpaTemplate.findList(PipelineConfigOrderEntity.class,idList);
    }
}
