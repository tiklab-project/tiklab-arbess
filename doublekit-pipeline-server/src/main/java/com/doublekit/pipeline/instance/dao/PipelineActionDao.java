package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineActionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PipelineActionDao {

    @Autowired
    JpaTemplate jpaTemplate ;
    /**
     * 创建动态
     * @param pipelineActionEntity 次数
     * @return 次数id
     */
    public  String createAction(PipelineActionEntity pipelineActionEntity){

        return jpaTemplate.save(pipelineActionEntity, String.class);
    }

    /**
     * 删除次数
     * @param actionId 次数id
     */
    public  void deleteAction(String actionId){
        jpaTemplate.delete(PipelineActionEntity.class, actionId);
    }

    /**
     * 更新次数
     * @param pipelineActionEntity 更新信息
     */
    public  void updateAction(PipelineActionEntity pipelineActionEntity){
        jpaTemplate.update(pipelineActionEntity);
    }

    /**
     * 查询单个次数信息87
     * @param actionId 次数id
     * @return 次数信息
     */
    public  PipelineActionEntity findOneAction(String actionId){
        return jpaTemplate.findOne(PipelineActionEntity.class,actionId);
    }

    /**
     * 查询所有次数
     * @return 次数集合
     */
    public  List<PipelineActionEntity> findAllAction(){
        return jpaTemplate.findAll(PipelineActionEntity.class);
    }

    //public List<PipelineActionEntity> findAllActionList(List<String> idList){
    //    return jpaTemplate.findList(PipelineActionEntity.class,idList);
    //}


}
