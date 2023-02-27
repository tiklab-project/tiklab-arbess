package net.tiklab.matflow.pipeline.definition.dao;


import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.pipeline.definition.entity.StagesTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流水线顺序配置
 */

@Repository
public class StagesTaskDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param stagesTaskEntity 后置配置
     * @return StagesTaskId
     */
    public String createStagesTask(StagesTaskEntity stagesTaskEntity){
        return jpaTemplate.save(stagesTaskEntity,String.class);
    }

    /**
     * 删除StagesTask
     * @param stagesTaskId stagesTaskId
     */
    public void deleteStagesTask(String stagesTaskId){
        jpaTemplate.delete(StagesTaskEntity.class,stagesTaskId);
    }

    /**
     * 更新StagesTask
     * @param stagesTaskEntity 更新信息
     */
    public void updateStagesTask(StagesTaskEntity stagesTaskEntity){
        jpaTemplate.update(stagesTaskEntity);
    }

    /**
     * 查询单个后置配置
     * @param StagesTaskId StagesTaskId
     * @return 后置配置
     */
    public StagesTaskEntity findOneStagesTask(String StagesTaskId){
        return jpaTemplate.findOne(StagesTaskEntity.class,StagesTaskId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<StagesTaskEntity> findAllStagesTask(){
        return jpaTemplate.findAll(StagesTaskEntity.class);
    }


    public List<StagesTaskEntity> findAllStagesTaskList(List<String> idList){
        return jpaTemplate.findList(StagesTaskEntity.class,idList);
    }
}
