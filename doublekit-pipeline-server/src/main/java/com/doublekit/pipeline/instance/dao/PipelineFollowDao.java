package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineFollowEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineFollowDao {


    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建次数
     * @param pipelineFollowEntity 次数
     * @return 次数id
     */
    public String createFollow(PipelineFollowEntity pipelineFollowEntity){
        return jpaTemplate.save(pipelineFollowEntity, String.class);
    }

    /**
     * 删除次数
     * @param followId 次数id
     */
    public void deleteFollow(String followId){
        jpaTemplate.delete(PipelineFollowEntity.class, followId);
    }

    /**
     * 更新次数
     * @param pipelineFollowEntity 更新信息
     */
    public void updateFollow(PipelineFollowEntity pipelineFollowEntity){
        jpaTemplate.update(pipelineFollowEntity);
    }

    /**
     * 查询单个次数信息
     * @param followId 次数id
     * @return 次数信息
     */

    public PipelineFollowEntity findOneFollow(String followId){
        return jpaTemplate.findOne(PipelineFollowEntity.class,followId);
    }

    /**
     * 查询所有次数
     * @return 次数集合
     */
    public List<PipelineFollowEntity> findAllFollow(){
        return jpaTemplate.findAll(PipelineFollowEntity.class);
    }


    public List<PipelineFollowEntity> findAllFollowList(List<String> idList){
        return jpaTemplate.findList(PipelineFollowEntity.class,idList);
    }
















}
