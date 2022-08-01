package com.tiklab.matflow.definition.service;

import java.util.List;

public interface MatFlowTaskService {

    /**
     * 更新配置信息
     * @param type 更新类型
     */
    void updateTaskConfig(Object o,String taskId,Integer type);

    /**
     * 创建配置信息
     * @param type 创建类型
     */
    String createTaskConfig(Object o,Integer type);

    /**
     * 删除配置信息
     * @param type 删除类型
     */
    void deleteTaskConfig(String taskId,Integer type);

    /**
     * 查询配置信息
     * @param type 查询类型
     */
    void findOneTask(List<Object> list,String taskId, Integer type);

}
