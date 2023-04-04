package io.tiklab.matflow.task.task.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.artifact.service.TaskArtifactExecService;
import io.tiklab.matflow.task.build.service.TaskBuildExecService;
import io.tiklab.matflow.task.code.service.TaskCodeExecService;
import io.tiklab.matflow.task.codescan.service.TaskCodeScanExecService;
import io.tiklab.matflow.task.deploy.service.TaskDeployExecService;
import io.tiklab.matflow.task.message.service.TaskMessageExecService;
import io.tiklab.matflow.task.script.service.TaskScriptExecService;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.test.service.TaskTestExecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

@Service
public class TasksExecServiceImpl implements TasksExecService {

    @Autowired
    private TaskCodeExecService code ;

    @Autowired
    private TaskBuildExecService build ;

    @Autowired
    private TaskTestExecService test;

    @Autowired
    private TaskDeployExecService deploy ;

    @Autowired
    private TaskCodeScanExecService codeScan;

    @Autowired
    private TaskArtifactExecService product;

    @Autowired
    private TaskMessageExecService message;

    @Autowired
    private TaskScriptExecService scripts;

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private TasksService tasksService;

    private static final Logger logger = LoggerFactory.getLogger(TasksExecServiceImpl.class);

    //任务id与任务实例id关系
    public static Map<String , String> taskIdOrTaskInstanceId = new HashMap<>();

    //任务实例id与任务实例关系
    public static  Map<String, TaskInstance> taskOrTaskInstance = new HashMap<>();

    @Override
    public void createTaskExecInstance(Tasks task,String instanceId,int type, String logPath){
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
        instance.setRunState(RUN_WAIT);
        instance.setTaskName(task.getTaskName());
        instance.setTaskType(task.getTaskType());
        instance.setTaskSort(task.getTaskSort());
        String taskInstanceId = tasksInstanceService.createTaskInstance(instance);
        instance.setId(taskInstanceId);
        //日志文件地址
        String  fileAddress = logPath +"/"+ taskInstanceId + ".log";
        instance.setLogAddress(fileAddress);
        PipelineUtil.createFile(fileAddress);
        tasksInstanceService.updateTaskInstance(instance);
        taskOrTaskInstance.put(taskInstanceId,instance);
        taskIdOrTaskInstanceId.put(task.getTaskId(),taskInstanceId);
    }

    @Override
    public boolean execTask(String pipelineId , int taskType,String taskId)  throws ApplicationException {
        Tasks tasks = tasksService.findOneTasksOrTask(taskId);
        logger.info("执行任务："+tasks.getTaskName());
        String taskInstanceId = taskIdOrTaskInstanceId.get(taskId);
        //计算时间
        tasksInstanceService.taskRuntime(taskInstanceId);
        //更改日志为运行运行中
        TaskInstance instance = taskOrTaskInstance.get(taskInstanceId);
        instance.setRunState(RUN_RUN);
        taskOrTaskInstance.put(taskInstanceId,instance);
        tasksInstanceService.updateTaskInstance(instance);

        boolean state = true;

        //分发执行不同任务
        switch (taskType / 10) {
            case 0 -> state = code.clone(pipelineId, tasks, taskType);
            case 1 -> state = test.test(pipelineId, tasks, taskType);
            case 2 -> state = build.build(pipelineId, tasks, taskType);
            case 3 -> state = deploy.deploy(pipelineId, tasks, taskType);
            case 4 -> state = codeScan.codeScan(pipelineId, tasks, taskType);
            case 5 -> state = product.product(pipelineId, tasks, taskType);
            case 7 -> state = scripts.scripts(pipelineId, tasks, taskType);
        }

        //更新阶段状态
        taskExecEnd(taskId,state);

        return state;
    }

    @Override
    public boolean execSendMessageTask(Pipeline pipeline,Tasks task , boolean execStatus,boolean isPipeline){
       return message.message(pipeline, task , execStatus, isPipeline);
    }

    /**
     * 任务运行完成，更新数据
     * @param taskId 任务id
     * @param state 任务状态
     */
    private void taskExecEnd(String taskId,boolean state){
        String taskInstanceId = taskIdOrTaskInstanceId.get(taskId);
        TaskInstance instance = taskOrTaskInstance.get(taskInstanceId);
        if (state){
            instance.setRunState(RUN_SUCCESS);
        }else {
            instance.setRunState(RUN_ERROR);
        }
        String logAddress = instance.getLogAddress();
        String runLog = instance.getRunLog();
        PipelineUtil.logWriteFile(runLog,logAddress);

        Integer integer = tasksInstanceService.findTaskRuntime(taskInstanceId);
        instance.setRunTime(integer);
        //更新数据库数据,移除内存中的实例数据
        tasksInstanceService.updateTaskInstance(instance);
        stopThread(taskInstanceId);
        stopThread(taskId);
        tasksInstanceService.removeTaskRuntime(taskInstanceId);
        taskIdOrTaskInstanceId.remove(taskId);
        taskOrTaskInstance.remove(taskInstanceId);
    }

    public void stopTask(String taskId){
        String taskInstanceId = taskIdOrTaskInstanceId.get(taskId);
        TaskInstance taskInstance = taskOrTaskInstance.get(taskInstanceId);
        //更新任务实例状态
        if (taskInstance == null){
            stopThread(taskInstanceId);
            stopThread(taskId);
            return;
        }
        Integer integer = tasksInstanceService.findTaskRuntime(taskInstanceId);
        taskInstance.setRunTime(integer);
        taskInstance.setRunState(RUN_HALT);

        tasksInstanceService.updateTaskInstance(taskInstance);
        //移除内存
        tasksInstanceService.removeTaskRuntime(taskInstanceId);
        stopThread(taskInstanceId);
        stopThread(taskId);
        taskIdOrTaskInstanceId.remove(taskId);
        taskOrTaskInstance.remove(taskInstanceId);
    }

    public void stopThread(String threadName){
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        if (Objects.equals(lstThreads.length,0)){
            return;
        }
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (!PipelineUtil.isNoNull(nm) ||!nm.equals(threadName)) {
                continue;
            }
            lstThreads[i].stop();
        }
    }

    public  TaskInstance findTaskInstance(String taskInstanceId){
        return taskOrTaskInstance.get(taskInstanceId);
    }

    public  String findTaskInstanceId(String taskId){
        return taskIdOrTaskInstanceId.get(taskId);
    }

    public void setTaskOrTaskInstance(String taskInstanceId ,TaskInstance taskInstance ){
        taskOrTaskInstance.put(taskInstanceId,taskInstance);
    }


}

































