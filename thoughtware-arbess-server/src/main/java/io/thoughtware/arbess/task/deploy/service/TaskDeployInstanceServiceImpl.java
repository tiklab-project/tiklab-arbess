package io.thoughtware.arbess.task.deploy.service;

import io.thoughtware.arbess.task.deploy.dao.TaskDeployInstanceDao;
import io.thoughtware.arbess.task.deploy.entity.TaskDeployInstanceEntity;
import io.thoughtware.arbess.task.deploy.model.TaskDeployInstance;
import io.thoughtware.arbess.task.deploy.model.TaskDeployInstanceQuery;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class TaskDeployInstanceServiceImpl implements TaskDeployInstanceService {


    @Autowired
    TaskDeployInstanceDao deployInstanceDao;


    @Override
    public String createDeployInstance(TaskDeployInstance deployInstance) {
        TaskDeployInstanceEntity deployInstanceEntity = BeanMapper.map(deployInstance, TaskDeployInstanceEntity.class);
        return deployInstanceDao.createTaskDeployInstance(deployInstanceEntity);
    }

    @Override
    public void updateDeployInstance(TaskDeployInstance deployInstance) {
        TaskDeployInstanceEntity deployInstanceEntity = BeanMapper.map(deployInstance, TaskDeployInstanceEntity.class);
        deployInstanceDao.updateTaskDeployInstance(deployInstanceEntity);
    }

    @Override
    public void deleteDeployInstance(String id) {
        deployInstanceDao.deleteTaskDeployInstance(id);
    }

    @Override
    public TaskDeployInstance findDeployInstance(String id) {
        TaskDeployInstanceEntity deployInstanceEntity = deployInstanceDao.findTaskDeployInstance(id);
        return BeanMapper.map(deployInstanceEntity, TaskDeployInstance.class);
    }

    @Override
    public List<TaskDeployInstance> findAllDeployInstanceList(TaskDeployInstanceQuery deployInstanceQuery) {
        List<TaskDeployInstanceEntity> deployInstanceEntities = deployInstanceDao.findTaskDeployInstanceList(deployInstanceQuery);
        if (Objects.isNull(deployInstanceEntities) || deployInstanceEntities.isEmpty()){
            return Collections.emptyList();
        }
        List<TaskDeployInstance> taskDeployInstances = BeanMapper.mapList(deployInstanceEntities, TaskDeployInstance.class);
        taskDeployInstances.sort(Comparator.comparing(TaskDeployInstance::getSort));
        return taskDeployInstances;
    }

    @Override
    public List<TaskDeployInstance> findAllDeployInstanceList() {
        List<TaskDeployInstanceEntity> deployInstanceEntities = deployInstanceDao.findAllTaskDeployInstance();
        if (Objects.isNull(deployInstanceEntities) || deployInstanceEntities.isEmpty()){
            return Collections.emptyList();
        }
        List<TaskDeployInstance> taskDeployInstances = BeanMapper.mapList(deployInstanceEntities, TaskDeployInstance.class);
        taskDeployInstances.sort(Comparator.comparing(TaskDeployInstance::getSort));
        return taskDeployInstances;
    }
}
