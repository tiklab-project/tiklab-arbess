package io.tiklab.matflow.stages.dao;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.stages.entity.StageInstanceEntity;
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


    /**
     * 获取实例下的主阶段实例
     * @param instanceId 流水线实例id
     * @return 主阶段实例
     */
    public List<StageInstanceEntity> findMainStageInstance(String instanceId){
        List<Order> orderList = OrderBuilders.instance().asc("stageSort").get();
        QueryCondition queryCondition = QueryBuilders.createQuery(StageInstanceEntity.class)
                .eq("instanceId", instanceId)
                .orders(orderList)
                .get();
        return jpaTemplate.findList(queryCondition,StageInstanceEntity.class);
    }


    /**
     * 获取实例下的从阶段实例
     * @param parentId 阶段实例id
     * @return 从阶段实例
     */
    public List<StageInstanceEntity> findOtherStageInstance(String parentId){
        List<Order> orderList = OrderBuilders.instance().asc("stageSort").get();
        QueryCondition queryCondition = QueryBuilders.createQuery(StageInstanceEntity.class)
                .eq("parentId", parentId)
                .orders(orderList)
                .get();
        return jpaTemplate.findList(queryCondition,StageInstanceEntity.class);
    }




    public List<StageInstanceEntity> findAllStagesListInstance(List<String> idList){
        return jpaTemplate.findList(StageInstanceEntity.class,idList);
    }
    
    
}
