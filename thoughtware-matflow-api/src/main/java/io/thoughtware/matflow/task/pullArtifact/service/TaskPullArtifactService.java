package io.thoughtware.matflow.task.pullArtifact.service;

import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.matflow.task.pullArtifact.model.TaskPullArtifact;

import java.util.List;

public interface TaskPullArtifactService {

    /**
     * 创建流水线推送制品
     * @param taskPullArtifact 流水线推送制品
     * @return 流水线推送制品id
     */
    String createPullArtifact(TaskPullArtifact taskPullArtifact);

    /**
     * 删除流水线推送制品
     * @param productId 流水线推送制品id
     */
    void deletePullArtifact(String productId);


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    void deletePullArtifactTask(String configId);

    /**
     * 根据配置id查询任务
     * @param taskId 配置id
     * @return 任务
     */
    TaskPullArtifact findPullArtifact(String taskId,String taskType);


    /**
     * 更新推送制品信息
     * @param taskPullArtifact 信息
     */
    void updatePullArtifact(TaskPullArtifact taskPullArtifact);

    /**
     * 查询推送制品信息
     * @param productId id
     * @return 信息
     */
    @FindOne
    TaskPullArtifact findOnePullArtifact(String productId);

    /**
     * 查询所有流水线推送制品
     * @return 流水线推送制品列表
     */
    @FindAll
    List<TaskPullArtifact> findAllPullArtifact();


    @FindList
    List<TaskPullArtifact> findAllPullArtifactList(List<String> idList);
    
    
}
