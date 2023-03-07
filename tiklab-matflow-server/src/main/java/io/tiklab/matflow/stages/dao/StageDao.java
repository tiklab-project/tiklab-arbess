package io.tiklab.matflow.stages.dao;

import io.tiklab.matflow.stages.entity.StageEntity;
import io.tiklab.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StageDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建阶段配置
     * @param stageEntity 阶段配置实体
     * @return 阶段id
     */
    public String createStages(StageEntity stageEntity){
        return jpaTemplate.save(stageEntity,String.class);
    }

    /**
     * 删除阶段配置
     * @param StagesId 阶段id
     */
    public void deleteStages(String StagesId){
        jpaTemplate.delete(StageEntity.class,StagesId);
    }

    /**
     * 更新阶段配置
     * @param stageEntity 更新信息
     */
    public void updateStages(StageEntity stageEntity){
        jpaTemplate.update(stageEntity);
    }

    /**
     * 查询单个阶段配置
     * @param StagesId 阶段id
     * @return 阶段配置实体
     */
    public StageEntity findOneStages(String StagesId){
        return jpaTemplate.findOne(StageEntity.class,StagesId);
    }

    /**
     * 查询所有阶段配置
     * @return 阶段配置集合
     */
    public List<StageEntity> findAllStages(){
        return jpaTemplate.findAll(StageEntity.class);
    }


    public List<StageEntity> findAllStagesList(List<String> idList){
        return jpaTemplate.findList(StageEntity.class,idList);
    }
    
    
}
