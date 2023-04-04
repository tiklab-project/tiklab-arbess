package io.tiklab.matflow.task.task.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.dao.TaskInstanceDao;
import io.tiklab.matflow.task.task.entity.TaskInstanceEntity;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
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
        taskInstanceDao.updateInstance(BeanMapper.map(taskInstance, TaskInstanceEntity.class));
    }

    @Override
    public List<TaskInstance> findAllInstanceInstance(String instanceId) {
        List<TaskInstance> allInstance = findAllTaskInstance();
        if (allInstance == null || allInstance.size() == 0){
            return Collections.emptyList();
        }
        List<TaskInstance> list = new ArrayList<>();
        for (TaskInstance instance : allInstance) {
            String id = instance.getInstanceId();
            if (id == null || !id.equals(instanceId)){
                continue;
            }
            //任务是否在运行中
            String taskInstanceId = instance.getId();
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance = tasksExecService.findTaskInstance(taskInstanceId);
            if (taskInstance != null){
                Integer integer = findTaskRuntime(taskInstanceId);
                if (integer == null){
                    integer = 0;
                }
                taskInstance.setRunTime(integer);
                list.add(taskInstance);
            }else {
                String logAddress = instance.getLogAddress();
                String readFile = PipelineUtil.readFile(logAddress, 100);
                instance.setRunLog(readFile);
                list.add(instance);
            }
        }
        list.sort(Comparator.comparing(TaskInstance::getTaskSort));
        return list;
    }

    @Override
    public List<TaskInstance> findAllStageInstance(String stageId) {
        List<TaskInstance> allInstance = findAllTaskInstance();
        if (allInstance == null || allInstance.size() == 0){
            return Collections.emptyList();
        }
        List<TaskInstance> list = new ArrayList<>();
        for (TaskInstance instance : allInstance) {
            String stagesId = instance.getStagesId();
            if (stagesId == null || !stagesId.equals(stageId)){
                continue;
            }
            String taskInstanceId = instance.getId();
            TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
            TaskInstance taskInstance = tasksExecService.findTaskInstance(taskInstanceId);
            if (taskInstance == null){
                String logAddress = instance.getLogAddress();
                String readFile = PipelineUtil.readFile(logAddress, 100);
                instance.setRunLog(readFile);
                list.add(instance);
            }else {
                Integer integer = findTaskRuntime(taskInstanceId);
                if (integer == null){
                    integer = 0;
                }
                taskInstance.setRunTime(integer);
                list.add(taskInstance);
            }
        }
        list.sort(Comparator.comparing(TaskInstance::getTaskSort));
        return list;
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
                writeExecLog(taskId, PipelineUtil.date(4)+s);
            }
            //读取err执行信息
            inputStreamReader = PipelineUtil.encode(errInputStream, enCode);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((s = bufferedReader.readLine()) != null) {
                if (validStatus(s,error)){state = false ;}
                writeExecLog(taskId, PipelineUtil.date(4)+s);
            }

            inputStreamReader.close();
            bufferedReader.close();

        }catch (IOException e){
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
        // taskOrTaskInstance.put(taskInstanceId, taskInstance);
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
