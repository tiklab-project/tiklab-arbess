package net.tiklab.matflow.definition.service;

import java.util.List;

/**
 * 流水线流程设计任务配置
 */
public interface PipelineCourseConfigTaskService {




    /**
     * 创建任务
     * @param configId 配置信息
     * @return 任务id
     */
    String createTaskConfig(String configId,int taskType);

    /**
     * 删除任务
     * @param configId 配置
     */
    void deleteTaskConfig(String configId,int taskType);

    /**
     * 更新任务
     * @param configId 配置
     */
    void updateTaskConfig(String configId,int taskType,Object o);

    /**
     * 查询任务
     * @param configId 配置
     * @return 任务信息
     */
    Object findOneTaskConfig(String configId,int taskType,int taskSort);



    /**
     * 效验配置必填字段
     * @param configId 配置
     */
    void validTaskConfig(String configId,int taskType,List<String> list);




}
