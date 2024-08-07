package io.thoughtware.matflow.task.deploy.service;


import io.thoughtware.matflow.setting.model.AuthHost;
import io.thoughtware.matflow.setting.model.AuthHostGroup;
import io.thoughtware.matflow.setting.service.AuthHostGroupService;
import io.thoughtware.matflow.setting.service.AuthHostService;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.task.deploy.model.TaskDeploy;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.matflow.task.deploy.dao.TaskDeployDao;
import io.thoughtware.matflow.task.deploy.entity.TaskDeployEntity;
import io.thoughtware.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.thoughtware.matflow.support.util.util.PipelineFinal.TASK_DEPLOY_DOCKER;
import static io.thoughtware.matflow.support.util.util.PipelineFinal.TASK_DEPLOY_LINUX;

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


    @Override
    public String createDeploy(TaskDeploy taskDeploy) {
        return taskDeployDao.createDeploy(BeanMapper.map(taskDeploy, TaskDeployEntity.class));
    }

    @Override
    public TaskDeploy findDeployByAuth(String authId){
        return findOneDeploy(authId);
    }

    @Override
    public void deleteDeploy(String deployId) {
        taskDeployDao.deleteDeploy(deployId);
    }

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

    @Override
    public List<TaskDeploy> findAllDeploy() {
        return BeanMapper.mapList(taskDeployDao.findAllDeploy(), TaskDeploy.class);
    }

    @Override
    public List<TaskDeploy> findAllDeployList(List<String> idList) {
        return BeanMapper.mapList(taskDeployDao.findAllCodeList(idList), TaskDeploy.class);
    }

    @Override
    public Boolean deployValid(String taskType,Object object){
        TaskDeploy deploy =(TaskDeploy) object;;

        if ( taskType.equals(TASK_DEPLOY_LINUX)){
            if (deploy.getAuthType() == 1){
                if (!PipelineUtil.isNoNull(deploy.getLocalAddress())){
                    return false;
                }
                return PipelineUtil.isNoNull(deploy.getDeployAddress());
            }
        }
        if ( taskType.equals(TASK_DEPLOY_DOCKER)){
            if (!PipelineUtil.isNoNull(deploy.getDockerImage())){
                return false;
            }
            return PipelineUtil.isNoNull(deploy.getDeployAddress());
        }
        return true;
    }

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
























