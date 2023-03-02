package net.tiklab.matflow.task.task.service;

import java.util.List;

public interface TaskDispatchService {

    /**
     * 分发创建不同类型的任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    void createDifferentTask(String taskId, int taskType);

    /**
     * 分发删除不同任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    void deleteDifferentTask(String taskId,int taskType);

    /**
     * 分发更新不同任务
     * @param taskId 任务id
     * @param taskType 任务类型
     * @param o 更新内容
     */
    void updateDifferentTask(String taskId,int taskType,Object o);


    /**
     * 分发获取默认任务名称
     * @param taskType 任务类型
     * @return 任务默认名称
     */
    String initDifferentTaskName(int taskType);


    Object findOneDifferentTask(String taskId,int taskType,int taskSort,String name);

    /**
     * 效验不同任务配置必填字段
     * @param taskId 任务id
     * @param taskType 任务类型
     * @param list 必填字段
     */
    void validDifferentTaskMastField(String taskId, int taskType, List<String> list);

}
