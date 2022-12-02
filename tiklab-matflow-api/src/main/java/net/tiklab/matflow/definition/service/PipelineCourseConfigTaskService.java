package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineCourseConfig;

import java.util.List;

/**
 * 流水线流程设计任务配置
 */
public interface PipelineCourseConfigTaskService {




    /**
     * 创建任务
     * @param config 配置信息
     * @return 任务id
     */
    String createConfig(PipelineCourseConfig config);

    /**
     * 删除任务
     * @param config 配置
     */
    void deleteConfig(PipelineCourseConfig config);

    /**
     * 更新任务
     * @param config 配置
     */
    void updateConfig(PipelineCourseConfig config);

    /**
     * 查询任务
     * @param config 配置
     * @return 任务信息
     */
    Object findConfig(PipelineCourseConfig config);



    /**
     * 效验配置必填字段
     * @param config 配置
     */
    void configValid(PipelineCourseConfig config,List<String> list);




}
