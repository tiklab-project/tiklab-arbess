package io.tiklab.matflow.task.task.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.artifact.service.TaskArtifactExecService;
import io.tiklab.matflow.task.build.service.TaskBuildExecService;
import io.tiklab.matflow.task.code.service.TaskCodeExecService;
import io.tiklab.matflow.task.codescan.service.TaskCodeScanExecService;
import io.tiklab.matflow.task.deploy.service.TaskDeployExecService;
import io.tiklab.matflow.task.pullArtifact.service.TaskPullArtifactExecService;
import io.tiklab.matflow.task.task.model.TaskExecMessage;
import io.tiklab.matflow.task.message.service.TaskMessageExecService;
import io.tiklab.matflow.task.script.service.TaskScriptExecService;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.model.TaskInstanceQuery;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.test.service.TaskTestExecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

@Service
public class TasksExecServiceImpl implements TasksExecService {

    @Autowired
    TaskCodeExecService code ;

    @Autowired
    TaskBuildExecService build ;

    @Autowired
    TaskTestExecService test;

    @Autowired
    TaskDeployExecService deploy ;

    @Autowired
    TaskCodeScanExecService codeScan;

    @Autowired
    TaskArtifactExecService product;

    @Autowired
    TaskMessageExecService message;

    @Autowired
    TaskScriptExecService scripts;

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    TasksService tasksService;

    @Autowired
    TaskPullArtifactExecService pullArtifact;

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
        PipelineFileUtil.createFile(fileAddress);
        tasksInstanceService.updateTaskInstance(instance);
        putTaskOrTaskInstance(taskInstanceId,instance);
        taskIdOrTaskInstanceId.put(task.getTaskId(),taskInstanceId);
    }

    @Override
    public boolean execTask(String pipelineId, String taskType,String taskId){
        Tasks tasks = tasksService.findOneTasksOrTask(taskId);

        logger.warn("执行任务：" + tasks.getTaskName());
        String taskInstanceId = findTaskInstanceId(taskId);
        //计算时间
        tasksInstanceService.taskRuntime(taskInstanceId);

        //更改日志为运行运行中
        TaskInstance instance = findTaskInstance(taskInstanceId);
        instance.setRunState(RUN_RUN);
        putTaskOrTaskInstance(taskInstanceId,instance);
        tasksInstanceService.updateTaskInstance(instance);

        boolean state = true;

        //分发执行不同任务
        switch (tasksService.findTaskType(taskType)) {
            case TASK_TYPE_CODE     -> state = code.clone(pipelineId, tasks, taskType);
            case TASK_TYPE_TEST     -> state = test.test(pipelineId, tasks, taskType);
            case TASK_TYPE_BUILD    -> state = build.build(pipelineId, tasks, taskType);
            case TASK_TYPE_DEPLOY   -> state = deploy.deploy(pipelineId, tasks, taskType);
            case TASK_TYPE_CODESCAN -> state = codeScan.codeScan(pipelineId, tasks, taskType);
            case TASK_TYPE_ARTIFACT -> state = product.product(pipelineId, tasks, taskType);
            case TASK_TYPE_SCRIPT   -> state = scripts.scripts(pipelineId, tasks, taskType);
            case TASK_TYPE_PULL   -> state = pullArtifact.pullArtifact(pipelineId, tasks, taskType);
        }

        //更新阶段状态
        taskExecEnd(taskId,state);

        return state;
    }

    @Override
    public boolean execSendMessageTask(TaskExecMessage taskExecMessage){

        String taskId = taskExecMessage.getTasks().getTaskId();
        String taskInstanceId = findTaskInstanceId(taskId);
        //计算时间
        tasksInstanceService.taskRuntime(taskInstanceId);

        String taskName = taskExecMessage.getTaskName();
        logger.info("执行任务：{}" , taskName);
        //更改日志为运行运行中
        TaskInstance instance = findTaskInstance(taskInstanceId);
        instance.setRunState(RUN_RUN);
        putTaskOrTaskInstance(taskInstanceId,instance);

        tasksInstanceService.updateTaskInstance(instance);

        boolean state = message.message(taskExecMessage);
        //更新任务状态
        taskExecEnd(taskId,state);
        return state;
    }

    /**
     * 任务运行完成，更新数据
     * @param taskId 任务id
     * @param state 任务状态
     */
    private void taskExecEnd(String taskId,boolean state){
        String taskInstanceId = findTaskInstanceId(taskId);
        TaskInstance instance = findTaskInstance(taskInstanceId);
        if (Objects.isNull(instance)){
            return;
        }
        if (state){
            instance.setRunState(RUN_SUCCESS);
        }else {
            instance.setRunState(RUN_ERROR);
        }
        String logAddress = instance.getLogAddress();
        String runLog = instance.getRunLog();
        PipelineFileUtil.logWriteFile(runLog,logAddress);

        Integer integer = tasksInstanceService.findTaskRuntime(taskInstanceId);
        if (!Objects.isNull(integer)){
            instance.setRunTime(integer);
        }
        //更新数据库数据,移除内存中的实例数据
        tasksInstanceService.updateTaskInstance(instance);

        tasksInstanceService.removeTaskRuntime(taskInstanceId);
        taskIdOrTaskInstanceId.remove(taskId);
        taskOrTaskInstance.remove(taskInstanceId);

        stopThread(taskId);
    }

    public void stopTask(String taskId){
        String taskInstanceId = findTaskInstanceId(taskId);
        TaskInstance taskInstance = findTaskInstance(taskInstanceId);

        //更新任务实例状态
        if (Objects.isNull(taskInstance)){
            stopThread(taskInstanceId);
            stopThread(taskId);
            return;
        }
        Integer integer = tasksInstanceService.findTaskRuntime(taskInstanceId);
        if (!Objects.isNull(integer)){
            taskInstance.setRunTime(integer);
        }
        taskInstance.setRunState(RUN_HALT);
        tasksInstanceService.updateTaskInstance(taskInstance);

        Tasks task = tasksService.findOneTasks(taskId);

        tasksInstanceService.writeAllExecLog(taskId, PipelineUtil.date(4)+"任务"+task.getTaskName()+"运行终止。");
        //移除内存

        tasksInstanceService.removeTaskRuntime(taskInstanceId);
        stopThread(taskId);
        taskIdOrTaskInstanceId.remove(taskId);
        taskOrTaskInstance.remove(taskInstanceId);


    }

    public void stop(String instanceId,String stageInstanceId,String postProcessId){
        TaskInstanceQuery taskInstanceQuery = new TaskInstanceQuery();
        taskInstanceQuery.setRunState(RUN_RUN);
        taskInstanceQuery.setInstanceId(instanceId);
        taskInstanceQuery.setStagesId(stageInstanceId);
        taskInstanceQuery.setPostprocessId(postProcessId);
        List<TaskInstance> taskInstanceList = tasksInstanceService.findTaskInstanceList(taskInstanceQuery);
        if (taskInstanceList.isEmpty()){
            return;
        }
        for (TaskInstance taskInstance6 : taskInstanceList) {
            String id = taskInstance6.getId();
            taskInstance6.setRunState(RUN_HALT);
            Integer integer = tasksInstanceService.findTaskRuntime(id);
            if (!Objects.isNull(integer)){
                taskInstance6.setRunTime(integer);
            }
            tasksInstanceService.updateTaskInstance(taskInstance6);
        }
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

    public void runError(List<Tasks> tasks){
        for (Tasks task : tasks) {
            String taskInstanceId = findTaskInstanceId(task.getTaskId());
            if (Objects.isNull(taskInstanceId)){
                continue;
            }
            TaskInstance taskInstance = findTaskInstance(taskInstanceId);
            if (!Objects.isNull(taskInstance)){
                taskInstance.setRunState(PipelineFinal.RUN_HALT);
                tasksInstanceService.updateTaskInstance(taskInstance);
            }
        }
    }

    public  TaskInstance findTaskInstance(String taskInstanceId){
        return taskOrTaskInstance.get(taskInstanceId);
    }

    public  String findTaskInstanceId(String taskId){
        return taskIdOrTaskInstanceId.get(taskId);
    }

    public void putTaskOrTaskInstance(String taskInstanceId ,TaskInstance taskInstance ){
        taskOrTaskInstance.put(taskInstanceId,taskInstance);
    }


}

































