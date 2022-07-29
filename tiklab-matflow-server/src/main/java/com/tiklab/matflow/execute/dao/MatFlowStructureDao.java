package com.tiklab.matflow.execute.dao;


import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.execute.entity.MatFlowStructureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowStructureDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param matFlowStructureEntity structure信息
     * @return structureId
     */
    public String createStructure(MatFlowStructureEntity matFlowStructureEntity){
        return jpaTemplate.save(matFlowStructureEntity,String.class);
    }

    /**
     * 删除
     * @param structureId structureId
     */
    public void deleteStructure(String structureId){
        jpaTemplate.delete(MatFlowStructureEntity.class,structureId);
    }

    /**
     * 更新structure
     * @param matFlowStructureEntity 更新信息
     */
    public void updateStructure(MatFlowStructureEntity matFlowStructureEntity){
        jpaTemplate.update(matFlowStructureEntity);
    }

    /**
     * 查询单个structure信息
     * @param structureId structureId
     * @return structure信息
     */
    public MatFlowStructureEntity findOneStructure(String structureId){
        return jpaTemplate.findOne(MatFlowStructureEntity.class,structureId);
    }

    /**
     * 查询所有structure信息
     * @return structure信息集合
     */
    public List<MatFlowStructureEntity> findAllStructure(){
        return jpaTemplate.findAll(MatFlowStructureEntity.class);
    }


    public List<MatFlowStructureEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(MatFlowStructureEntity.class,idList);
    }
}
