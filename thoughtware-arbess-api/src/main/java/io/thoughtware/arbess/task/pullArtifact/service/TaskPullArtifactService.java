package io.thoughtware.arbess.task.pullArtifact.service;

import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.arbess.task.pullArtifact.model.TaskPullArtifact;

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
     * 更新流水线推送制品
     * @param taskType taskType
     * @param object object
     * @return Boolean
     */
    Boolean pullArtifactValid(String taskType,Object object);

    /**
     * 根据配置id查询任务
     * @param taskId 配置id
     * @return 任务
     */
    TaskPullArtifact findPullArtifactByAuth(String taskId);


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
