package com.tiklab.matfiow.setting.view.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.tiklab.matfiow.setting.view.entity.PipelineViewEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineViewDao {

    private static final Logger logger = LoggerFactory.getLogger(PipelineViewDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建视图
     * @param pipelineViewEntity 视图
     * @return 视图id
     */
    public String createView(PipelineViewEntity pipelineViewEntity){
        return jpaTemplate.save(pipelineViewEntity, String.class);
    }

    /**
     * 删除视图
     * @param viewId 视图id
     */
    public void deleteView(String viewId){
        jpaTemplate.delete(PipelineViewEntity.class, viewId);
    }

    /**
     * 更新视图
     * @param pipelineViewEntity 更新信息
     */
    public void updateView(PipelineViewEntity pipelineViewEntity){
        jpaTemplate.update(pipelineViewEntity);
    }

    /**
     * 查询单个视图信息
     * @param viewId 视图id
     * @return 视图信息
     */
    public PipelineViewEntity findOneView(String viewId){
        return jpaTemplate.findOne(PipelineViewEntity.class,viewId);
    }

    /**
     * 查询所有视图
     * @return 视图集合
     */
    public List<PipelineViewEntity> findAllView(){
        return jpaTemplate.findAll(PipelineViewEntity.class);
    }
}
