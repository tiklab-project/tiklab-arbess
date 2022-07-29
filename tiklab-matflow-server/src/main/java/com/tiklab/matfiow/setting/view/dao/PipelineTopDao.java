package com.tiklab.matfiow.setting.view.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.tiklab.matfiow.setting.view.entity.PipelineTopEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineTopDao {

    private static final Logger logger = LoggerFactory.getLogger(PipelineTopDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建视图
     * @param pipelineTopEntity 视图
     * @return 视图id
     */
    public String createTop(PipelineTopEntity pipelineTopEntity){
        return jpaTemplate.save(pipelineTopEntity, String.class);
    }

    /**
     * 删除视图
     * @param topId 视图id
     */
    public void deleteTop(String topId){
        jpaTemplate.delete(PipelineTopEntity.class, topId);
    }

    /**
     * 更新视图
     * @param pipelineTopEntity 更新信息
     */
    public void updateTop(PipelineTopEntity pipelineTopEntity){
        jpaTemplate.update(pipelineTopEntity);
    }

    /**
     * 查询单个视图信息
     * @param topId 视图id
     * @return 视图信息
     */
    public PipelineTopEntity findOneTop(String topId){
        return jpaTemplate.findOne(PipelineTopEntity.class,topId);
    }

    /**
     * 查询所有视图
     * @return 视图集合
     */
    public List<PipelineTopEntity> findAllTop(){
        return jpaTemplate.findAll(PipelineTopEntity.class);
    }
    
    
    
    
}
