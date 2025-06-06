package io.tiklab.arbess.task.deploy.service;

import io.tiklab.arbess.task.deploy.dao.TaskDeployInstanceDao;
import io.tiklab.arbess.task.deploy.entity.TaskDeployInstanceEntity;
import io.tiklab.arbess.task.deploy.model.TaskDeployInstance;
import io.tiklab.arbess.task.deploy.model.TaskDeployInstanceQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
            return new ArrayList<>();
        }
        List<TaskDeployInstance> taskDeployInstances = BeanMapper.mapList(deployInstanceEntities, TaskDeployInstance.class);
        taskDeployInstances.sort(Comparator.comparing(TaskDeployInstance::getSort));
        return taskDeployInstances;
    }

    @Override
    public List<TaskDeployInstance> findAllDeployInstanceList() {
        List<TaskDeployInstanceEntity> deployInstanceEntities = deployInstanceDao.findAllTaskDeployInstance();
        if (Objects.isNull(deployInstanceEntities) || deployInstanceEntities.isEmpty()){
            return new ArrayList<>();
        }
        List<TaskDeployInstance> taskDeployInstances = BeanMapper.mapList(deployInstanceEntities, TaskDeployInstance.class);
        taskDeployInstances.sort(Comparator.comparing(TaskDeployInstance::getSort));
        return taskDeployInstances;
    }
}
