package io.thoughtware.matflow.task.task.service;

import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.task.task.model.TaskInstance;
import io.thoughtware.matflow.task.task.model.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TasksExecServiceImpl implements TasksExecService {


    @Autowired
    TasksInstanceService tasksInstanceService;

    private static final Logger logger = LoggerFactory.getLogger(TasksExecServiceImpl.class);

    //任务id与任务实例id关系
    public static Map<String , String> taskIdOrTaskInstanceId = new HashMap<>();

    //任务实例id与任务实例关系
    public static  Map<String, TaskInstance> taskOrTaskInstance = new HashMap<>();

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

        String taskInstanceId = tasksInstanceService.createTaskInstance(instance);
        instance.setId(taskInstanceId);
        //日志文件地址
        String  fileAddress = logPath +"/"+ taskInstanceId + ".log";
        instance.setLogAddress(fileAddress);
        PipelineFileUtil.createFile(fileAddress);
        tasksInstanceService.updateTaskInstance(instance);
        putTaskOrTaskInstance(taskInstanceId,instance);
        taskIdOrTaskInstanceId.put(task.getTaskId(),taskInstanceId);
        return taskInstanceId;
    }

    public TaskInstance findTaskInstance(String taskInstanceId){
        return taskOrTaskInstance.get(taskInstanceId);
    }

    public String findTaskInstanceId(String taskId){
        return taskIdOrTaskInstanceId.get(taskId);
    }

    public void putTaskOrTaskInstance(String taskInstanceId ,TaskInstance taskInstance ){
        taskOrTaskInstance.put(taskInstanceId,taskInstance);
    }


}

































