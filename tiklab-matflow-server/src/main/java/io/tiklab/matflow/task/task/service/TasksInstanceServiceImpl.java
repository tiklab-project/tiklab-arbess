package io.tiklab.matflow.task.task.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;
import io.tiklab.matflow.support.postprocess.service.PostprocessInstanceService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.dao.TaskInstanceDao;
import io.tiklab.matflow.task.task.entity.TaskInstanceEntity;
import io.tiklab.matflow.task.task.model.TaskInstance;
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
    private TaskInstanceDao taskInstanceDao;

    @Autowired
    private PostprocessInstanceService postInstanceService;

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

    @Override
    public List<TaskInstance> findAllInstanceInstance(String instanceId) {
        List<TaskInstanceEntity> pipelineInstance = taskInstanceDao.findPipelineInstance(instanceId);
        List<TaskInstance> allInstance = BeanMapper.mapList(pipelineInstance, TaskInstance.class);
        if (Objects.isNull(allInstance) || allInstance.size() == 0){
            return Collections.emptyList();
        }
        List<TaskInstance> list = new ArrayList<>();
        for (TaskInstance instance : allInstance) {
            String taskInstanceId = instance.getId();
            //任务是否在运行中
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance = tasksExecService.findTaskInstance(taskInstanceId);
            String time;
            if (!Objects.isNull(taskInstance)){
                Integer integer = findTaskRuntime(taskInstanceId);
                taskInstance.setRunTime(integer);
                time = PipelineUtil.formatDateTime(integer);
                taskInstance.setRunTimeDate(time);
                list.add(taskInstance);
            }else {
                TaskInstance taskInstances = findPostPipelineRunMessage(taskInstanceId,false);
                String logAddress = instance.getLogAddress();
                String readFile = PipelineUtil.readFile(logAddress, 100);
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
        list.sort(Comparator.comparing(TaskInstance::getTaskSort));
        TaskInstance taskInstance = findPostPipelineRunMessage(instanceId,true);
        if (!Objects.isNull(taskInstance)){
            list.add(list.size(),taskInstance);
        }
        return list;
    }


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
        TaskInstance taskInstances = new TaskInstance();
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
            String readFile = PipelineUtil.readFile(taskInstance.getLogAddress(), 100);
            runTime = runTime + taskInstance.getRunTime();
            runLog.append(readFile).append("\n");
            taskInstances.setRunState(taskInstance.getRunState());
        }
        taskInstances.setRunLog(runLog.toString());
        String runState = taskInstances.getRunState();
        if (Objects.equals(runTime,0) &&
                !Objects.isNull(runState) &&
                !Objects.equals(runState,PipelineFinal.RUN_RUN) &&
                !Objects.equals(runState,PipelineFinal.RUN_HALT)){
            runTime = 1;
        }
        String time = PipelineUtil.formatDateTime(runTime);
        taskInstances.setRunTimeDate(time);
        taskInstances.setRunTime(runTime);
        taskInstances.setTaskName("后置处理");
        return taskInstances;
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
            String readFile = PipelineUtil.readFile(taskInstance.getLogAddress(), 100);
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

    @Override
    public List<TaskInstance> findAllStageInstance(String stageId) {
        List<TaskInstanceEntity> pipelineInstance = taskInstanceDao.findStageInstance(stageId);
        List<TaskInstance> allInstance = BeanMapper.mapList(pipelineInstance, TaskInstance.class);
        if (Objects.isNull(allInstance) || allInstance.size() == 0){
            return Collections.emptyList();
        }
        StringBuilder stageRunLog = new StringBuilder();
        int stageRunTime = 0;
        List<TaskInstance> list = new ArrayList<>();
        for (TaskInstance instance : allInstance) {
            String taskInstanceId = instance.getId();
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance = tasksExecService.findTaskInstance(taskInstanceId);
            String time;
            if (Objects.isNull(taskInstance)){
                TaskInstance taskInstances = findPostPipelineRunMessage(taskInstanceId,false);
                String logAddress = instance.getLogAddress();
                String readFile = PipelineUtil.readFile(logAddress, 100);
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
                taskInstance.setRunTime(integer);
                time = PipelineUtil.formatDateTime(integer);
                taskInstance.setRunTimeDate(time);
                stageRunLog.append(taskInstance.getRunLog());
                stageRunTime = stageRunTime + integer;
                list.add(taskInstance);
            }
        }
        stageIdOrStageRunTime.put(stageId,stageRunTime);
        stageIdOrStageRunLog.put(stageId, String.valueOf(stageRunLog));
        return list;
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
    public boolean readCommandExecResult(Process process , String enCode, String[] error,String taskId) {
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
                if (validStatus(s,error)){
                    state = false ;
                }
                writeExecLog(taskId, PipelineUtil.date(4) + s);
            }

            //读取err执行信息
            inputStreamReader = PipelineUtil.encode(errInputStream, enCode);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((s = bufferedReader.readLine()) != null) {
                if (validStatus(s,error)){state = false ;}
                writeExecLog(taskId, PipelineUtil.date(4) + s);
            }

            // 关闭
            inputStreamReader.close();
            bufferedReader.close();

        }catch (Exception e){
            state = false;
        }
        process.destroy();
        return state;
    }

    /**
     * 效验日志状态
     * @param s 日志
     * @param error 错误状态
     * @return true 正确 false：错误
     */
    private boolean validStatus(String s,String[] error){
        if (error == null || error.length == 0){
            return false;
        }
        for (String s1 : error) {
            if (!s.contains(s1)){
                continue;
            }
            return true;
        }
        return false;
    }

    @Override
    public void writeExecLog(String taskId, String execLog){
        if(!PipelineUtil.isNoNull(execLog)){
            return;
        }
        TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
        String taskInstanceId = tasksExecService.findTaskInstanceId(taskId);
        TaskInstance taskInstance = tasksExecService.findTaskInstance(taskInstanceId);
        Integer integer = findTaskRuntime(taskInstanceId);
        taskInstance.setRunTime(integer);
        String execInstance = taskInstance.getRunLog();

        if (!PipelineUtil.isNoNull(execInstance)){
            taskInstance.setRunLog(execLog);
        }else {
            taskInstance.setRunLog(execInstance +"\n"+ execLog);
        }

        //长度过长写入文件中
        String runInstance = taskInstance.getRunLog();
        if (runInstance.length() > 9000){
            String logAddress = taskInstance.getLogAddress();
            PipelineUtil.logWriteFile(runInstance,logAddress);
            taskInstance.setRunLog(null);
        }
        tasksExecService.setTaskOrTaskInstance(taskInstanceId, taskInstance);
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

    //时间线程池
    private final ExecutorService timeThreadPool = Executors.newCachedThreadPool();

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

    public Integer findTaskRuntime(String taskInstanceId){
        Integer integer = runTime.get(taskInstanceId);
        if (Objects.isNull(integer)){
            return 0;
        }
        return integer;
    }

    public void removeTaskRuntime(String taskInstanceId){
        runTime.remove(taskInstanceId);
    }



}
