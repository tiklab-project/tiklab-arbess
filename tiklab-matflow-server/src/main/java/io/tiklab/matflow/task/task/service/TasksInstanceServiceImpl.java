package io.tiklab.matflow.task.task.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;
import io.tiklab.matflow.support.postprocess.service.PostprocessInstanceService;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.dao.TaskInstanceDao;
import io.tiklab.matflow.task.task.entity.TaskInstanceEntity;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.model.TaskInstanceQuery;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务执行实例服务接口
 */

@Service
@Exporter
public class TasksInstanceServiceImpl implements TasksInstanceService {

    @Autowired
    TaskInstanceDao taskInstanceDao;

    @Autowired
    PostprocessInstanceService postInstanceService;

    //任务运行时间
    private final Map<String,Integer> runTime = new HashMap<>();

    @Override
    public String createTaskInstance(TaskInstance taskInstance) {
        TaskInstanceEntity instanceEntity = BeanMapper.map(taskInstance, TaskInstanceEntity.class);
        return taskInstanceDao.createInstance(instanceEntity);
    }

    @Override
    public void deleteAllInstanceInstance(String instanceId) {
        List<TaskInstance> allInstanceInstance = findAllInstanceInstance(instanceId);
        if (allInstanceInstance.isEmpty()){
            return;
        }
        for (TaskInstance instance : allInstanceInstance) {
            String taskInstanceId = instance.getId();
            deleteTaskInstance(taskInstanceId);
        }
    }

    @Override
    public List<String> findAllInstanceLogs(String instanceId){
        List<TaskInstance> allInstanceInstance = findAllInstanceInstance(instanceId);
        if (allInstanceInstance.isEmpty()){
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();
        for (TaskInstance taskInstance : allInstanceInstance) {
            String runLog = taskInstance.getLogAddress();
            String readFile = PipelineFileUtil.readFile(runLog, 0);
            if (Objects.isNull(readFile)){
                continue;
            }
            list.add(readFile + "\n");
        }
        return list;
    }

    @Override
    public void deleteAllStageInstance(String stageId) {
        List<TaskInstance> allStageInstance = findAllStageInstance(stageId);
        if (allStageInstance.isEmpty()){
            return;
        }
        for (TaskInstance instance : allStageInstance) {
            String instanceId = instance.getId();
            deleteTaskInstance(instanceId);
        }
    }

    @Override
    public TaskInstance findOneTaskInstance(String taskInstanceId) {
        TaskInstanceEntity taskInstanceEntity = taskInstanceDao.findOne(taskInstanceId);
        return BeanMapper.map(taskInstanceEntity, TaskInstance.class);
    }

    @Override
    public void updateTaskInstance(TaskInstance taskInstance) {
        TaskInstanceEntity instance = BeanMapper.map(taskInstance, TaskInstanceEntity.class);
        taskInstanceDao.updateInstance(instance);
    }

    private static Integer readLogLength= 600;

    @Override
    public List<TaskInstance> findAllInstanceInstance(String instanceId) {
        List<TaskInstanceEntity> pipelineInstance = taskInstanceDao.findPipelineInstance(instanceId);
        List<TaskInstance> allInstance = BeanMapper.mapList(pipelineInstance, TaskInstance.class);
        if (Objects.isNull(allInstance) || allInstance.isEmpty()){
            return Collections.emptyList();
        }
        allInstance.sort(Comparator.comparing(TaskInstance::getTaskSort));

        // 没有正在运行的任务是查询所有日志
        TaskInstance taskInstance1 = allInstance.get(allInstance.size() - 1);
        String runState = taskInstance1.getRunState();
        if (!runState.equals(PipelineFinal.RUN_RUN)){
            readLogLength = 0;
        }

        List<TaskInstance> list = new ArrayList<>();
        for (TaskInstance instance : allInstance) {
            String taskInstanceId = instance.getId();
            //任务是否在运行中
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance5 = tasksExecService.findTaskInstance(taskInstanceId);
            String time;
            if (!Objects.isNull(taskInstance5)){
                Integer integer = findTaskRuntime(taskInstanceId);
                if (!Objects.isNull(integer)){
                    taskInstance5.setRunTime(integer);
                }
                time = PipelineUtil.formatDateTime(integer);
                taskInstance5.setRunTimeDate(time);
                list.add(taskInstance5);
            }else {
                TaskInstance taskInstances = findPostPipelineRunMessage(taskInstanceId,false);
                String logAddress = instance.getLogAddress();
                String readFile = PipelineFileUtil.readFile(logAddress, readLogLength);
                int date = instance.getRunTime();
                if (!Objects.isNull(taskInstances)){
                    date =  date + taskInstances.getRunTime();
                    readFile = readFile +"\n"+ taskInstances.getRunLog();
                }
                time = PipelineUtil.formatDateTime(date);
                instance.setRunTimeDate(time);
                instance.setRunLog(readFile);
                list.add(instance);
            }
        }
        TaskInstance taskInstance = findPostPipelineRunMessage(instanceId,true);
        if (!Objects.isNull(taskInstance)){
            list.add(list.size(),taskInstance);
        }
        return list;
    }

    @Override
    public TaskInstance findPostPipelineRunMessage(String id,Boolean b){
        List<PostprocessInstance> taskPostInstance;
        if (b){
            taskPostInstance = postInstanceService.findPipelinePostInstance(id);
        }else {
            taskPostInstance = postInstanceService.findTaskPostInstance(id);
        }

        if (Objects.isNull(taskPostInstance) || Objects.equals(taskPostInstance.size(),0)){
            return null;
        }

        int runTime = 0;
        TaskInstance taskInstances4 = new TaskInstance();
        StringBuilder runLog = new StringBuilder();
        for (PostprocessInstance postprocessInstance : taskPostInstance) {
            String postInstanceId = postprocessInstance.getId();
            List<TaskInstanceEntity> postInstance = taskInstanceDao.findPostInstance(postInstanceId);
            if (Objects.isNull(postInstance) || Objects.equals(postInstance.size(),0)){
                return null;
            }
            String taskInstanceId = postInstance.get(0).getId();
            //任务是否在运行中
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance = tasksExecService.findTaskInstance(taskInstanceId);
            if (Objects.isNull(taskInstance)){
                 taskInstance = BeanMapper.map(postInstance.get(0), TaskInstance.class);
            }
            String readFile = PipelineFileUtil.readFile(taskInstance.getLogAddress(), readLogLength);
            runTime = runTime + taskInstance.getRunTime();
            runLog.append(readFile).append("\n");
            taskInstances4.setRunState(taskInstance.getRunState());
            taskInstances4.setPostprocessId(postInstanceId);
            taskInstances4.setLogAddress(postprocessInstance.getPostAddress());
        }
        taskInstances4.setRunLog(runLog.toString());
        String runState = taskInstances4.getRunState();
        if (Objects.equals(runTime,0) &&
                !Objects.isNull(runState) &&
                !Objects.equals(runState,PipelineFinal.RUN_RUN) &&
                !Objects.equals(runState,PipelineFinal.RUN_HALT)){
            runTime = 1;
        }
        String time = PipelineUtil.formatDateTime(runTime);
        taskInstances4.setRunTimeDate(time);
        taskInstances4.setRunTime(runTime);
        taskInstances4.setTaskType("post");
        taskInstances4.setTaskName("后置处理");
        taskInstances4.setId("post");
        return taskInstances4;
    }


    public static Map<String,String> stageIdOrStagePostRunLog = new HashMap<>();

    public static Map<String,Integer> stageIdOrStagePostRunTime = new HashMap<>();

    public static Map<String,String> stageIdOrStagePostRunState = new HashMap<>();


    @Override
    public List<TaskInstance> findStagePostRunMessage(String id){
        List<PostprocessInstance> taskPostInstance = postInstanceService.findPipelinePostInstance(id);
        int runTime = 0;
        String state = PipelineFinal.RUN_HALT;
        StringBuilder runLog = new StringBuilder();
        List<TaskInstance>  list = new ArrayList<>();
        for (PostprocessInstance postprocessInstance : taskPostInstance) {
            String postInstanceId = postprocessInstance.getId();
            List<TaskInstanceEntity> postInstance = taskInstanceDao.findPostInstance(postInstanceId);
            String taskInstanceId = postInstance.get(0).getId();
            //任务是否在运行中
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance = tasksExecService.findTaskInstance(taskInstanceId);
            if (Objects.isNull(taskInstance)){
                 taskInstance = BeanMapper.map(postInstance.get(0), TaskInstance.class);
            }
            String time = PipelineUtil.formatDateTime(taskInstance.getRunTime());
            taskInstance.setRunTimeDate(time);
            String readFile = PipelineFileUtil.readFile(taskInstance.getLogAddress(), readLogLength);
            runTime = runTime + taskInstance.getRunTime();
            taskInstance.setRunLog(readFile);
            list.add(taskInstance);
            runLog.append(readFile).append("\n");
            state = taskInstance.getRunState();
        }
        stageIdOrStagePostRunLog.put(id,runLog.toString());
        stageIdOrStagePostRunTime.put(id,runTime);
        stageIdOrStagePostRunState.put(id,state);
        return list;
    }

    @Override
    public Map<String ,Object> findPostRunMessage(String id){
        Map<String,Object> map = new HashMap<>();
        String log = stageIdOrStagePostRunLog.get(id);
        Integer time = stageIdOrStagePostRunTime.get(id);
        String state = stageIdOrStagePostRunState.get(id);
        map.put("log",log);
        map.put("time",time);
        map.put("state",state);
        return map;
    }

    @Override
    public void removePostRunMessage(String id){
        stageIdOrStagePostRunLog.remove(id);
        stageIdOrStagePostRunTime.remove(id);
        stageIdOrStagePostRunState.remove(id);
    }

    public static Map<String,String> stageIdOrStageRunLog = new HashMap<>();

    public static Map<String,Integer> stageIdOrStageRunTime = new HashMap<>();

    public static Map<String,String> stageIdOrStageRunState = new HashMap<>();

    @Override
    public List<TaskInstance> findAllStageInstance(String stageId) {
        List<TaskInstanceEntity> pipelineInstance = taskInstanceDao.findStageInstance(stageId);
        List<TaskInstance> allInstance = BeanMapper.mapList(pipelineInstance, TaskInstance.class);
        if (Objects.isNull(allInstance) || allInstance.size() == 0){
            return Collections.emptyList();
        }
        StringBuilder stageRunLog = new StringBuilder();
        int stageRunTime = 0;
        String runState = PipelineFinal.RUN_SUCCESS;
        List<TaskInstance> list = new ArrayList<>();
        for (TaskInstance instance : allInstance) {
            String taskInstanceId = instance.getId();
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance3 = tasksExecService.findTaskInstance(taskInstanceId);
            String time;
            if (Objects.isNull(taskInstance3)){
                TaskInstance taskInstances = findPostPipelineRunMessage(taskInstanceId,false);
                String logAddress = instance.getLogAddress();
                String readFile = PipelineFileUtil.readFile(logAddress, readLogLength);
                int date = instance.getRunTime();
                if (!Objects.isNull(taskInstances)){
                    readFile = readFile +"\n"+ taskInstances.getRunLog();
                    date =  date + taskInstances.getRunTime();
                }
                instance.setRunLog(readFile);
                time = PipelineUtil.formatDateTime(date);
                instance.setRunTimeDate(time);
                stageRunLog.append(readFile);
                stageRunTime = stageRunTime + instance.getRunTime();
                list.add(instance);
            }else {
                Integer integer = findTaskRuntime(taskInstanceId);
                if (!Objects.isNull(integer)){
                    taskInstance3.setRunTime(integer);
                }
                time = PipelineUtil.formatDateTime(integer);
                taskInstance3.setRunTimeDate(time);
                stageRunLog.append(taskInstance3.getRunLog());
                stageRunTime = stageRunTime + integer;
                list.add(taskInstance3);
            }
        }

        for (TaskInstance instance : list) {
            String state = instance.getRunState();
            if (state.equals(PipelineFinal.RUN_ERROR)){
                runState = PipelineFinal.RUN_ERROR;
                break;
            }
            if (state.equals(PipelineFinal.RUN_WAIT)){
                runState = PipelineFinal.RUN_WAIT;
                break;
            }
            if (state.equals(PipelineFinal.RUN_RUN)){
                runState = PipelineFinal.RUN_RUN;
                break;
            }

        }
        stageIdOrStageRunTime.put(stageId,stageRunTime);
        stageIdOrStageRunLog.put(stageId, String.valueOf(stageRunLog));
        stageIdOrStageRunState.put(stageId,runState);
        return list;
    }


    @Override
    public String findStageRunState(String stageId){
        return stageIdOrStageRunState.get(stageId);
    }

    @Override
    public void removeStageRunState(String stageId){
        stageIdOrStageRunState.remove(stageId);
    }

    @Override
    public String findStageRunLog(String stageId){
        return stageIdOrStageRunLog.get(stageId);
    }

    @Override
    public void removeStageRunLog(String stageId){
         stageIdOrStageRunLog.remove(stageId);
    }

    @Override
    public Integer findStageRunTime(String stageId){
        return stageIdOrStageRunTime.get(stageId);
    }

    @Override
    public void removeStageRunTime(String stageId){
        stageIdOrStageRunTime.remove(stageId);
    }

    @Override
    public boolean readCommandExecResult(Process process , String enCode, Map<String,String> error,String taskId) {
        boolean state = true;
        //指定编码
        if (!PipelineUtil.isNoNull(enCode)){
            int systemType = PipelineUtil.findSystemType();
            if (systemType == 1){
                enCode = PipelineFinal.GBK;
            }else {
                enCode = PipelineFinal.UTF_8;
            }
        }

        //转换流
        InputStream inputStream = process.getInputStream();
        InputStream errInputStream = process.getErrorStream();

        InputStreamReader inputStreamReader ;
        BufferedReader bufferedReader ;
        if (inputStream == null){
            inputStreamReader = PipelineUtil.encode(errInputStream, enCode);
        }else {
            inputStreamReader = PipelineUtil.encode(inputStream, enCode);
        }

        String s;
        bufferedReader = new BufferedReader(inputStreamReader);

        try {

            //读取执行信息
            while ((s = bufferedReader.readLine()) != null) {
                String s1 = validStatus(s, error);
                if (!Objects.isNull(s1)){
                    state = false ;
                    writeExecLog(taskId, PipelineUtil.date(4) + s1);
                }
                writeExecLog(taskId, PipelineUtil.date(4) + s);

            }

            //读取err执行信息
            inputStreamReader = PipelineUtil.encode(errInputStream, enCode);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((s = bufferedReader.readLine()) != null) {
                String s1 = validStatus(s, error);
                if (!Objects.isNull(s1)){
                    state = false ;
                    writeExecLog(taskId, PipelineUtil.date(4) + s1);
                }
                writeExecLog(taskId, PipelineUtil.date(4) + s);
            }

            // 关闭
            inputStreamReader.close();
            bufferedReader.close();

        } catch (Exception e){
            writeExecLog(taskId, PipelineUtil.date(4) + e.getMessage());
            state = false;
        }
        process.destroy();
        return state;
    }

    /**
     * 效验日志状态
     * @param s 日志
     * @param errors 错误状态
     * @return null 正确  other：错误
     */
    private String validStatus(String s,Map<String,String> errors){

        for (Map.Entry<String, String> errorString : errors.entrySet()) {
            String key = errorString.getKey();
            if (!s.contains(key)){
                continue;
            }
            return errorString.getValue();
        }

        return null;
    }

    @Override
    public void writeExecLog(String taskId, String execLog){
        if(!PipelineUtil.isNoNull(execLog)){
            return;
        }
        TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
        String taskInstanceId = tasksExecService.findTaskInstanceId(taskId);
        TaskInstance taskInstance2 = tasksExecService.findTaskInstance(taskInstanceId);
        if (Objects.isNull(taskInstance2)){
            return;
        }
        Integer integer = findTaskRuntime(taskInstanceId);
        if (!Objects.isNull(integer)){
            taskInstance2.setRunTime(integer);
        }
        String execInstance = taskInstance2.getRunLog();

        if (!PipelineUtil.isNoNull(execInstance)){
            taskInstance2.setRunLog(execLog);
        }else {
            taskInstance2.setRunLog(execInstance +"\n"+ execLog);
        }

        //长度过长写入文件中
        String runInstance = taskInstance2.getRunLog();
        if (runInstance.length() > 9000){
            String logAddress = taskInstance2.getLogAddress();
            PipelineFileUtil.logWriteFile(runInstance,logAddress);
            taskInstance2.setRunLog(null);
        }
        tasksExecService.putTaskOrTaskInstance(taskInstanceId, taskInstance2);
    }


    public void writeAllExecLog(String taskId, String execLog){
        if(!PipelineUtil.isNoNull(execLog)){
            return;
        }
        TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
        String taskInstanceId = tasksExecService.findTaskInstanceId(taskId);
        TaskInstance taskInstance1 = tasksExecService.findTaskInstance(taskInstanceId);
        Integer integer = findTaskRuntime(taskInstanceId);
        if (!Objects.isNull(integer)){
            taskInstance1.setRunTime(integer);
        }
        String execInstance = taskInstance1.getRunLog();

        if (!PipelineUtil.isNoNull(execInstance)){
            taskInstance1.setRunLog(execLog);
        }else {
            taskInstance1.setRunLog(execInstance +"\n"+ execLog);
        }

        //长度过长写入文件中
        String runInstance = taskInstance1.getRunLog();
        String logAddress = taskInstance1.getLogAddress();
        PipelineFileUtil.logWriteFile(runInstance,logAddress);
    }

    /**
     * 删除任务执行实例
     * @param taskInstanceId 任务实例id
     */
    private void deleteTaskInstance(String taskInstanceId){
        taskInstanceDao.deleteInstance(taskInstanceId);
    }

    @Override
    public List<TaskInstance> findAllTaskInstance() {
        List<TaskInstanceEntity> allInstance = taskInstanceDao.findAllInstance();
        return BeanMapper.mapList(allInstance, TaskInstance.class);
    }

    @Override
    public List<TaskInstance> findAllInstanceList(List<String> idList) {
        List<TaskInstanceEntity> pipelineInstanceList = taskInstanceDao.findAllInstanceList(idList);
        return BeanMapper.mapList(pipelineInstanceList, TaskInstance.class);
    }

    @Override
    public List<TaskInstance> findTaskInstanceList(TaskInstanceQuery query){
        List<TaskInstanceEntity> pipelineInstanceList = taskInstanceDao.findTaskInstanceList(query);
        if (pipelineInstanceList == null || pipelineInstanceList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(pipelineInstanceList,TaskInstance.class);
    }


    //时间线程池
    private final ExecutorService timeThreadPool = Executors.newCachedThreadPool();

    @Override
    public void taskRuntime(String taskInstanceId){
        runTime.put(taskInstanceId,0);
        timeThreadPool.submit(() -> {
            while (true){
                Thread.currentThread().setName(taskInstanceId);
                int integer = findTaskRuntime(taskInstanceId);
                try {
                    Thread.sleep(1000);
                }catch (RuntimeException e){
                    throw new RuntimeException();
                }
                integer = integer +1;
                runTime.put(taskInstanceId,integer);
            }
        });
    }

    @Override
    public Integer findTaskRuntime(String taskInstanceId){
        Integer integer = runTime.get(taskInstanceId);
        if (Objects.isNull(integer)){
            return 0;
        }
        return integer;
    }


    @Override
    public void removeTaskRuntime(String taskInstanceId){
        runTime.remove(taskInstanceId);
        stopThread(taskInstanceId);
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



}
