package com.tiklab.matflow.execute.dao;


import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.execute.entity.MatFlowDeployEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowDeployDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param matFlowDeployEntity deploy信息
     * @return deployId
     */
    public String createDeploy(MatFlowDeployEntity matFlowDeployEntity){
        return jpaTemplate.save(matFlowDeployEntity,String.class);
    }

    /**
     * 删除deploy
     * @param deployId deployId
     */
    public void deleteDeploy(String deployId){
        jpaTemplate.delete(MatFlowDeployEntity.class,deployId);
    }

    /**
     * 更新deploy
     * @param matFlowDeployEntity 更新信息
     */
    public void updateDeploy(MatFlowDeployEntity matFlowDeployEntity){
        jpaTemplate.update(matFlowDeployEntity);
    }

    /**
     * 查询单个deploy信息
     * @param deployId deployId
     * @return deploy信息
     */
    public MatFlowDeployEntity findOneDeploy(String deployId){
        return jpaTemplate.findOne(MatFlowDeployEntity.class,deployId);
    }

    /**
     * 查询所有deploy信息
     * @return deploy信息集合
     */
    public List<MatFlowDeployEntity> findAllDeploy(){
        return jpaTemplate.findAll(MatFlowDeployEntity.class);
    }


    public List<MatFlowDeployEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(MatFlowDeployEntity.class,idList);
    }

}
