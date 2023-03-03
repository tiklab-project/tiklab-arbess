package net.tiklab.matflow.stages.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.stages.entity.StageInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 阶段运行实例数据访问
 */
@Repository
public class StageInstanceDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建阶段运行实例
     * @param stageInstanceEntity 阶段运行实例实体
     * @return 阶段运行实例id
     */
    public String createStagesInstance(StageInstanceEntity stageInstanceEntity){
        return jpaTemplate.save(stageInstanceEntity,String.class);
    }

    /**
     * 删除阶段运行实例
     * @param stagesId 阶段运行实例id
     */
    public void deleteStagesInstance(String stagesId){
        jpaTemplate.delete(StageInstanceEntity.class,stagesId);
    }

    /**
     * 更新阶段运行实例
     * @param stageInstanceEntity 更新信息
     */
    public void updateStagesInstance(StageInstanceEntity stageInstanceEntity){
        jpaTemplate.update(stageInstanceEntity);
    }

    /**
     * 查询单个阶段运行实例
     * @param StagesId 阶段运行实例id
     * @return 阶段运行实例实体
     */
    public StageInstanceEntity findOneStagesInstance(String StagesId){
        return jpaTemplate.findOne(StageInstanceEntity.class,StagesId);
    }

    /**
     * 查询所有阶段运行实例
     * @return 阶段运行实例集合
     */
    public List<StageInstanceEntity> findAllStagesInstance(){
        return jpaTemplate.findAll(StageInstanceEntity.class);
    }


    public List<StageInstanceEntity> findAllStagesListInstance(List<String> idList){
        return jpaTemplate.findList(StageInstanceEntity.class,idList);
    }
    
    
}
