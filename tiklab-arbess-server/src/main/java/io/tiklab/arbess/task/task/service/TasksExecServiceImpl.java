package io.tiklab.arbess.task.task.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.arbess.setting.hostgroup.model.HostGroup;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.deploy.model.TaskDeploy;
import io.tiklab.arbess.task.deploy.model.TaskDeployInstance;
import io.tiklab.arbess.task.deploy.service.TaskDeployInstanceService;
import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class TasksExecServiceImpl implements TasksExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    TaskDeployInstanceService taskDeployInstanceService;

    private static final Logger logger = LoggerFactory.getLogger(TasksExecServiceImpl.class);

    @Override
    public String createTaskExecInstance(Tasks task, String instanceId, int type, String logPath){
        TaskInstance instance = new TaskInstance();
        if (type == 1){
            instance.setInstanceId(instanceId);
        }
        if (type == 2) {
            instance.setStagesId(instanceId);
        }
        if (type == 3) {
            instance.setPostprocessId(instanceId);
        }
        instance.setRunState(PipelineFinal.RUN_WAIT);
        instance.setTaskName(task.getTaskName());
        instance.setTaskType(task.getTaskType());
        instance.setTaskSort(task.getTaskSort());
        instance.setTaskId(task.getTaskId());
        instance.setCreateTime(new Timestamp(System.currentTimeMillis()));

        String taskInstanceId = tasksInstanceService.createTaskInstance(instance);
        instance.setId(taskInstanceId);
        //日志文件地址
        String  fileAddress = logPath +"/"+ taskInstanceId + ".log";
        instance.setLogAddress(fileAddress);
        PipelineFileUtil.createFile(fileAddress);
        tasksInstanceService.updateTaskInstance(instance);
        return taskInstanceId;
    }

    @Override
    public String createTaskExecInstance(Tasks task, String instanceId, int type, String logPath,Boolean isFirst){
        TaskInstance instance = new TaskInstance();
        if (type == 1){
            instance.setInstanceId(instanceId);
        }
        if (type == 2) {
            instance.setStagesId(instanceId);
        }
        if (type == 3) {
            instance.setPostprocessId(instanceId);
        }
        if (isFirst){
            instance.setRunState(PipelineFinal.RUN_RUN);
        }else {
            instance.setRunState(PipelineFinal.RUN_WAIT);
        }
        instance.setTaskName(task.getTaskName());
        instance.setTaskType(task.getTaskType());
        instance.setTaskSort(task.getTaskSort());
        instance.setTaskId(task.getTaskId());
        instance.setCreateTime(new Timestamp(System.currentTimeMillis()));

        String taskInstanceId = tasksInstanceService.createTaskInstance(instance);
        instance.setId(taskInstanceId);
        //日志文件地址
        String  fileAddress = logPath +"/"+ taskInstanceId + ".log";
        instance.setLogAddress(fileAddress);
        PipelineFileUtil.createFile(fileAddress);
        tasksInstanceService.updateTaskInstance(instance);
        return taskInstanceId;
    }

    @Override
    public void createDeployInstance(Tasks task,String taskInstanceId){
        if (task.getTaskType().equals(PipelineFinal.TASK_DEPLOY_LINUX)){
            String object = JSON.toJSONString(task.getTask());
            TaskDeploy taskDeploy = JSON.parseObject(object, TaskDeploy.class);
            if ( taskDeploy.getAuthType() == 2){
                return;
            }
            String hostType = taskDeploy.getHostType();
            if (!"hostGroup".equals(hostType)){
                return;
            }
            List<HostGroup> hostGroupList = taskDeploy.getHostGroupList();
            if (Objects.isNull(hostGroupList)){
                return;
            }
            for (int i = 0; i < hostGroupList.size(); i++) {
                HostGroup hostGroup = hostGroupList.get(i);
                String format = String.format("第%s批次", i + 1);
                TaskDeployInstance taskDeployInstance = new TaskDeployInstance(hostGroup.getTaskInstanceId(),taskInstanceId);
                taskDeployInstance.setName(format);
                taskDeployInstance.setSort(i+1);
                taskDeployInstanceService.createDeployInstance(taskDeployInstance);
            }
        }
    }

}

































