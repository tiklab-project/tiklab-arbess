package com.doublekit.pipeline.definition.dao;


import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.entity.ConfigureEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PipelineConfigureDao
 */

@Repository
public class ConfigureDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 添加流水线配置信息
     * @param configureEntity 配置信息
     * @return 配置信息id
     */
    public String createConfigure(ConfigureEntity configureEntity){
        return jpaTemplate.save(configureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deleteConfigure(String id){
        jpaTemplate.delete(ConfigureEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param configureEntity 配置信息
     */
    public void updateConfigure(ConfigureEntity configureEntity){
        jpaTemplate.update(configureEntity);
    }

    /**
     * 查询配置信息
     * @param id 查询id
     * @return 配置信息
     */
    public ConfigureEntity findConfigure(String id){
        return jpaTemplate.findOne(ConfigureEntity.class, id);
    }

    /**
     * 查询所有配置信息
     * @return 配置信息集合
     */
    public List<ConfigureEntity> findAllConfigure(){
        return jpaTemplate.findAll(ConfigureEntity.class);
    }

    public List<ConfigureEntity> findAllConfigureList(List<String> idList){

        return jpaTemplate.findList(ConfigureEntity.class,idList);
    }
}
