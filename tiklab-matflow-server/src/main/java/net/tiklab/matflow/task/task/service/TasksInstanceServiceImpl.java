package net.tiklab.matflow.task.task.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.support.util.PipelineFinal;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.dao.TaskInstanceDao;
import net.tiklab.matflow.task.task.entity.TaskInstanceEntity;
import net.tiklab.matflow.task.task.model.TaskInstance;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 任务执行实例服务接口
 */

@Service
@Exporter
public class TasksInstanceServiceImpl implements TasksInstanceService {

    @Autowired
    private TaskInstanceDao taskInstanceDao;

    //任务实例id与任务
    private final  Map<String , TaskInstance> taskOrTaskInstance = TasksExecServiceImpl.taskOrTaskInstance;

    private final  Map<String , String> taskIdOrTaskInstanceId = TasksExecServiceImpl.taskIdOrTaskInstanceId;

    //任务运行时间
    private final Map<String, Integer> runTime = TasksExecServiceImpl.runTime;

    @Override
    public String createTaskInstance(TaskInstance taskInstance) {
        TaskInstanceEntity instanceEntity = BeanMapper.map(taskInstance, TaskInstanceEntity.class);
        return taskInstanceDao.createInstance(instanceEntity);
    }

    @Override
    public void deleteAllInstanceInstance(String instanceId) {
        List<TaskInstance> allStageInstance = findAllStageInstance(instanceId);
        if (allStageInstance.isEmpty()){
            return;
        }
        for (TaskInstance instance : allStageInstance) {
            String taskInstanceId = instance.getInstanceId();
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
            String instanceId = instance.getInstanceId();
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
            if (!id.equals(instanceId)){
                continue;
            }
            //任务是否在运行中
            String taskInstanceId = instance.getInstanceId();
            TaskInstance taskInstance = taskOrTaskInstance.get(taskInstanceId);
            if (taskInstance != null){
                Integer integer = runTime.get(taskInstanceId);
                taskInstance.setRunTime(integer);
                list.add(taskInstance);
            }else {
                list.add(instance);
            }
        }

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
            if (!stagesId.equals(stageId)){
                continue;
            }
            String instanceId = instance.getInstanceId();
            TaskInstance taskInstance = taskOrTaskInstance.get(instanceId);
            if (taskInstance == null){
                list.add(instance);
            }else {
                Integer integer = runTime.get(instanceId);
                taskInstance.setRunTime(integer);
                list.add(taskInstance);
            }
        }
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
    public void writeExecLog(String taskId, String instance){
        if(!PipelineUtil.isNoNull(instance)){
            return;
        }
        String taskInstanceId = taskIdOrTaskInstanceId.get(taskId);
        TaskInstance taskInstance = taskOrTaskInstance.get(taskInstanceId);
        Integer integer = runTime.get(taskInstanceId);
        if (integer == null){
            integer = 0;
        }
        taskInstance.setRunTime(integer);

        String execInstance = taskInstance.getRunLog();

        if (!PipelineUtil.isNoNull(execInstance)){
            taskInstance.setRunLog(instance);
        }else {
            taskInstance.setRunLog(execInstance +"\n"+ instance);
        }

        //长度过长写入文件中
        if (taskInstance.getRunLog().length() > 25000){
            String runInstance = taskInstance.getRunLog();
            PipelineUtil.logWriteFile(execInstance,runInstance);
            taskInstance.setRunLog(null);
        }

        taskOrTaskInstance.put(taskInstanceId, taskInstance);
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




}
