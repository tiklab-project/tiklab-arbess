package net.tiklab.matflow.stages.service;

import net.tiklab.matflow.stages.model.StageInstance;

import java.util.List;

/**
 * 阶段运行实例服务接口
 */
public interface StageInstanceServer {

    /**
     * 创建阶段运行实例
     * @param stageInstance 实例模型
     * @return 运行实例模型
     */
    String createStageInstance(StageInstance stageInstance);

    /**
     * 删除所有流水线实例下的阶段运行实例
     * @param instanceId 流水线实例id
     */
    void deleteAllMainStageInstance(String instanceId);

    /**
     * 删除阶段下的所有实例
     * @param stageId 阶段id
     */
    void deleteAllStageInstance(String stageId);

    /**
     * 更新阶段实例内容
     * @param stageInstance 实例模型
     */
    void updateStageInstance(StageInstance stageInstance);

    /**
     * 查询单个阶段运行实例
     * @param stageInstanceId 阶段运行实例id
     * @return 阶段运行实例模型
     */
    StageInstance findOneStageInstance(String stageInstanceId);

    /**
     * 查询流水线运行实例下的所有阶段运行实例
     * @param instanceId 流水线运行实例id
     * @return 阶段运行实例
     */
    List<StageInstance> findAllMainStageInstance(String instanceId);

    /**
     * 查询主阶段实例下的所有运行实例
     * @param mainStageId 主阶段id
     * @return 运行实例
     */
    List<StageInstance> findAllOtherStageInstance(String mainStageId);

    /**
     * 查询阶段运行实例
     * @param instanceId 流水线实例id
     * @return 阶段运行实例
     */
    List<StageInstance> findStageExecInstance(String instanceId);



}

































