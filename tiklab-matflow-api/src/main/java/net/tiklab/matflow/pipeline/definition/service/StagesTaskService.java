package net.tiklab.matflow.pipeline.definition.service;

import net.tiklab.matflow.pipeline.definition.model.PipelineConfig;
import net.tiklab.matflow.pipeline.definition.model.StagesTask;

import java.util.List;

public interface StagesTaskService {

    /**
     * 创建配置及任务
     * @param config 配置
     * @return stagesTaskId
     */
    String createStagesTasksTask (PipelineConfig config) ;

    /**
     * 删除配置及任务
     * @param configId Id
     */
    boolean deleteStagesTasksTask (String configId) ;

    /**
     * 删除所有配置及任务
     * @param stagesId 阶段id
     */
    void deleteAllStagesTasksTask(String stagesId);

    /**
     * 更新配置及任务
     * @param config 配置
     */
    void updateStagesTasksTask (PipelineConfig config);

    /**
     * 查询流水线配置
     * @param stagesId 阶段id
     * @return 配置
     */
    List<StagesTask> findAllStagesTasks (String stagesId) ;

    /**
     * 查询流水线所有配置及任务
     * @param stagesId 阶段id
     * @return 任务
     */
    List<Object> findAllStagesTasksTask (String stagesId) ;


    /**
     * 获取配置详情
     * @param configId 配置id
     * @return 详情
     */
    Object findOneStagesTasksTask(String configId);

    /**
     * 查询单个配置
     * @param stagesTaskId 配置id
     * @return 配置
     */
    StagesTask findOneStagesTask(String stagesTaskId);


    /**
     * 效验配置必填字段
     * @param stagesId 流水线id
     */
    void validAllConfig(String stagesId,List<String> list);



    //删除
    void deleteStagesTask(String stagesTaskId);


}















