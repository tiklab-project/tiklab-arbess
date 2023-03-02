package net.tiklab.matflow.task.deploy.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.setting.service.AuthHostService;
import net.tiklab.matflow.task.deploy.dao.TaskDeployDao;
import net.tiklab.matflow.task.deploy.entity.TaskDeployEntity;
import net.tiklab.matflow.task.deploy.model.TaskDeploy;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class TaskDeployServiceImpl implements TaskDeployService {

    @Autowired
    TaskDeployDao taskDeployDao;

    @Autowired
    AuthHostService hostServer;

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
        if (taskDeploy.getAuthId() != null){
            taskDeploy.setAuth(hostServer.findOneAuthHost(taskDeploy.getAuthId()));
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
























