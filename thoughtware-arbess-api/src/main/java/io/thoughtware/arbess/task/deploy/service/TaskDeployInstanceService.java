package io.thoughtware.arbess.task.deploy.service;

import io.thoughtware.arbess.task.deploy.model.TaskDeployInstance;
import io.thoughtware.arbess.task.deploy.model.TaskDeployInstanceQuery;

import java.util.List;

public interface TaskDeployInstanceService {

    /**
     * 创建部署实例
     * @param deployInstance deployInstance
     * @return id
     */
    String createDeployInstance(TaskDeployInstance deployInstance);

    /**
     * 更新部署实例
     * @param deployInstance deployInstance
     */
    void updateDeployInstance(TaskDeployInstance deployInstance);

    /**
     * 删除部署实例
     * @param id id
     */
    void deleteDeployInstance(String id);


    /**
     * 查找部署实例
     * @param id id
     * @return deployInstance
     */
    TaskDeployInstance findDeployInstance(String id);

    /**
     * 查找部署实例
     * @param deployInstanceQuery deployInstanceQuery
     * @return deployInstance
     */
    List<TaskDeployInstance> findAllDeployInstanceList(TaskDeployInstanceQuery deployInstanceQuery);

    /**
     * 查找部署实例
     * @return deployInstance
     */
    List<TaskDeployInstance> findAllDeployInstanceList();

}
