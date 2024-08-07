package io.thoughtware.matflow.task.deploy.service;


import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.matflow.task.deploy.model.TaskDeploy;

import java.util.List;

/**
 * 部署服务接口
 */
@JoinProvider(model = TaskDeploy.class)
public interface TaskDeployService {

    /**
     * 创建
     * @param taskDeploy deploy信息
     * @return deployId
     */
    String createDeploy(TaskDeploy taskDeploy) ;

    /**
     * 删除
     * @param deployId deployId
     */
    void deleteDeploy(String deployId) ;

    /**
     * 测试是否有效
     * @param taskType 任务类型
     * @param object object
     * @return 是否有效
     */
    Boolean deployValid(String taskType,Object object);


    /**
     * 根据配置id查询任务
     * @param authId 配置id
     * @return 任务
     */
    TaskDeploy findDeployByAuth(String authId);

    /**
     * 更新
     * @param taskDeploy 更新信息
     */
    void updateDeploy(TaskDeploy taskDeploy);

    /**
     * 查询单个信息
     * @param deployId pipelineDeployId
     * @return deploy信息
     */
    @FindOne
    TaskDeploy findOneDeploy(String deployId) ;

    /**
     * 查询所有信息
     * @return deploy信息集合
     */
    @FindAll
    List<TaskDeploy> findAllDeploy() ;

    @FindList
    List<TaskDeploy> findAllDeployList(List<String> idList);

}