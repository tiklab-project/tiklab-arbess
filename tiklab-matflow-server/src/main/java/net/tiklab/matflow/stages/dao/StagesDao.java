package net.tiklab.matflow.stages.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.stages.entity.StagesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StagesDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建阶段配置
     * @param stagesEntity 阶段配置实体
     * @return 阶段id
     */
    public String createStages(StagesEntity stagesEntity){
        return jpaTemplate.save(stagesEntity,String.class);
    }

    /**
     * 删除阶段配置
     * @param StagesId 阶段id
     */
    public void deleteStages(String StagesId){
        jpaTemplate.delete(StagesEntity.class,StagesId);
    }

    /**
     * 更新阶段配置
     * @param stagesEntity 更新信息
     */
    public void updateStages(StagesEntity stagesEntity){
        jpaTemplate.update(stagesEntity);
    }

    /**
     * 查询单个阶段配置
     * @param StagesId 阶段id
     * @return 阶段配置实体
     */
    public StagesEntity findOneStages(String StagesId){
        return jpaTemplate.findOne(StagesEntity.class,StagesId);
    }

    /**
     * 查询所有阶段配置
     * @return 阶段配置集合
     */
    public List<StagesEntity> findAllStages(){
        return jpaTemplate.findAll(StagesEntity.class);
    }


    public List<StagesEntity> findAllStagesList(List<String> idList){
        return jpaTemplate.findList(StagesEntity.class,idList);
    }
    
    
}
