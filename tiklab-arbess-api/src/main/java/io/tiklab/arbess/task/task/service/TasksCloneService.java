package io.tiklab.arbess.task.task.service;


public interface TasksCloneService {

    /**
     * 克隆后置任务
     * @param id 流水线id
     * @param cloneId 克隆流水线id
     */
    void clonePostTasks(String id ,String cloneId);

    /**
     * 克隆流水线任务
     * @param id 流水线id
     * @param cloneId 克隆流水线id
     */
    void clonePipelineTasks(String id ,String cloneId);

    /**
     * 克隆阶段任务
     * @param id 阶段id
     * @param cloneId 克隆阶段id
     */
    void cloneStageTasks(String id ,String cloneId);



}
