package io.tiklab.arbess.task.deploy.service;


import io.tiklab.arbess.setting.host.model.AuthHost;
import io.tiklab.arbess.setting.hostgroup.model.AuthHostGroup;
import io.tiklab.arbess.setting.hostgroup.service.AuthHostGroupService;
import io.tiklab.arbess.setting.host.service.AuthHostService;
import io.tiklab.arbess.setting.k8s.model.Kubectl;
import io.tiklab.arbess.setting.k8s.service.KubectlService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.deploy.model.TaskDeploy;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.task.deploy.dao.TaskDeployDao;
import io.tiklab.arbess.task.deploy.entity.TaskDeployEntity;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

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
            }else {
                AuthHostGroup hostGroup = groupService.findOneHostGroup(authId);
                taskDeploy.setHostType("hostGroup");
                taskDeploy.setAuth(hostGroup);
            }
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
























