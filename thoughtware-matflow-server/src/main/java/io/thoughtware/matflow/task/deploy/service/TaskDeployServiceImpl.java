package io.thoughtware.matflow.task.deploy.service;


import io.thoughtware.matflow.setting.model.AuthHost;
import io.thoughtware.matflow.setting.model.AuthHostGroup;
import io.thoughtware.matflow.setting.service.AuthHostGroupService;
import io.thoughtware.matflow.setting.service.AuthHostService;
import io.thoughtware.matflow.task.deploy.model.TaskDeploy;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.matflow.task.deploy.dao.TaskDeployDao;
import io.thoughtware.matflow.task.deploy.entity.TaskDeployEntity;
import io.thoughtware.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zcamy
 */
@Service
@Exporter
public class TaskDeployServiceImpl implements TaskDeployService {

    @Autowired
    TaskDeployDao taskDeployDao;

    @Autowired
    AuthHostService hostServer;

    @Autowired
    AuthHostGroupService groupService;

    //创建
    @Override
    public String createDeploy(TaskDeploy taskDeploy) {
        return taskDeployDao.createDeploy(BeanMapper.map(taskDeploy, TaskDeployEntity.class));
    }


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteDeployConfig(String configId){
        TaskDeploy oneDeployConfig = findOneDeployConfig(configId);
        deleteDeploy(oneDeployConfig.getTaskId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskDeploy findOneDeployConfig(String configId){
        List<TaskDeploy> allDeploy = findAllDeploy();
        if (allDeploy == null){
            return null;
        }
        for (TaskDeploy taskDeploy : allDeploy) {
            if (taskDeploy.getTaskId().equals(configId)){
                return findOneDeploy(taskDeploy.getTaskId());
            }
        }
        return null;
    }
    

    //删除
    @Override
    public void deleteDeploy(String deployId) {
        taskDeployDao.deleteDeploy(deployId);
    }

    //查询单个
    @Override
    public TaskDeploy findOneDeploy(String deployId) {
        TaskDeployEntity oneDeploy = taskDeployDao.findOneDeploy(deployId);
        TaskDeploy taskDeploy = BeanMapper.map(oneDeploy, TaskDeploy.class);
        String authId = taskDeploy.getAuthId();
        if (authId != null){
            AuthHost authHost = hostServer.findOneAuthHost(authId);
            if (!Objects.isNull(authHost)){
                taskDeploy.setHostType("host");
                taskDeploy.setAuth(authHost);
                return taskDeploy;
            }
            AuthHostGroup hostGroup = groupService.findOneHostGroup(authId);
            taskDeploy.setHostType("hostGroup");
            taskDeploy.setAuth(hostGroup);
        }
        return taskDeploy;
    }

    //查询所有
    @Override
    public List<TaskDeploy> findAllDeploy() {
        return BeanMapper.mapList(taskDeployDao.findAllDeploy(), TaskDeploy.class);
    }

    @Override
    public List<TaskDeploy> findAllDeployList(List<String> idList) {
        return BeanMapper.mapList(taskDeployDao.findAllCodeList(idList), TaskDeploy.class);
    }


    //修改
    @Override
    public void updateDeploy(TaskDeploy taskDeploy) {
        int authType = taskDeploy.getAuthType();
        String deployId = taskDeploy.getTaskId();

        if (authType == 0){
            TaskDeploy oneDeploy = findOneDeploy(deployId);
            taskDeploy.setAuthType(oneDeploy.getAuthType());
            taskDeploy.setStartOrder(taskDeploy.getStartOrder());
            taskDeployDao.updateDeploy(BeanMapper.map(taskDeploy, TaskDeployEntity.class));
            return;
        }

        if (authType == 2){
            taskDeploy.setAuthId("");
            taskDeploy.setAuthId("");
            taskDeploy.setLocalAddress("");
            taskDeploy.setDeployAddress("");
            taskDeploy.setDeployOrder("");
            taskDeploy.setStartAddress("");
        }
        taskDeploy.setStartOrder("");

        taskDeployDao.updateDeploy(BeanMapper.map(taskDeploy, TaskDeployEntity.class));
    }


}
























