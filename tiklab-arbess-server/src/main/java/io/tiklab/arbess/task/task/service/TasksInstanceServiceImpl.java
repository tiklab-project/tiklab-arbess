package io.tiklab.arbess.task.task.service;


import io.tiklab.arbess.support.postprocess.model.PostprocessInstance;
import io.tiklab.arbess.support.postprocess.service.PostprocessInstanceService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.deploy.model.TaskDeployInstance;
import io.tiklab.arbess.task.deploy.model.TaskDeployInstanceQuery;
import io.tiklab.arbess.task.deploy.service.TaskDeployInstanceService;
import io.tiklab.arbess.task.task.dao.TaskInstanceDao;
import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.model.TaskInstanceQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.task.task.entity.TaskInstanceEntity;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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

    @Autowired
    TaskDeployInstanceService taskDeployInstanceService;

    public static Map<String, TaskInstance> taskInstanceMap = new HashMap<>();

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

        // 没有正在运行的任务时查询所有日志
        TaskInstance taskInstance1 = allInstance.get(allInstance.size() - 1);
        if (!taskInstance1.getRunState().equals(PipelineFinal.RUN_RUN)){
            readLogLength = 0;
        }

        List<TaskInstance> list = new ArrayList<>();
        for (TaskInstance instance : allInstance) {
            String taskInstanceId = instance.getId();

            TaskInstance taskInstance = taskInstanceMap.get(taskInstanceId);
            if (!Objects.isNull(taskInstance)) {
                int time = taskInstance.getRunTime();
                if (time == 0){
                    time = 1;
                }
                instance.setRunTime(time);
                if (!StringUtils.isEmpty(instance.getRunLog())){
                    instance.setRunLog(instance.getRunLog() + taskInstance.getRunLog());
                }else {
                    instance.setRunLog(taskInstance.getRunLog());
                }
                instance.setRunState(PipelineFinal.RUN_RUN);
            }
            String logAddress = instance.getLogAddress();
            String readFile = PipelineFileUtil.readFile(logAddress, readLogLength);
            String time = PipelineUtil.formatDateTime(instance.getRunTime());
            instance.setRunTimeDate(time);
            instance.setRunLog(readFile);
            list.add(instance);
        }
        TaskInstance taskInstance = findPostPipelineRunMessage(instanceId);
        if (!Objects.isNull(taskInstance)){
            list.add(list.size(),taskInstance);
        }
        return list;
    }

    @Override
    public TaskInstance findPostPipelineRunMessage(String instanceId){
        List<PostprocessInstance> postInstanceList = postInstanceService.findPipelinePostInstance(instanceId);;

        if (Objects.isNull(postInstanceList) || postInstanceList.isEmpty()){
            return null;
        }

        int runTime = 0;
        TaskInstance taskInstances4 = new TaskInstance();
        StringBuilder runLog = new StringBuilder();
        for (PostprocessInstance postprocessInstance : postInstanceList) {
            String postInstanceId = postprocessInstance.getId();
            List<TaskInstanceEntity> postInstance = taskInstanceDao.findPostInstance(postInstanceId);
            if (Objects.isNull(postInstance) || Objects.equals(postInstance.size(),0)){
                return null;
            }
            //任务是否在运行中
            String taskInstanceId = postInstance.get(0).getId();
            TaskInstance taskInstance = taskInstanceMap.get(taskInstanceId);

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

    @Override
    public List<TaskInstance> findStagePostRunMessage(String id){
        List<PostprocessInstance> taskPostInstance = postInstanceService.findPipelinePostInstance(id);
        List<TaskInstance>  list = new ArrayList<>();
        for (PostprocessInstance postprocessInstance : taskPostInstance) {
            String postInstanceId = postprocessInstance.getId();
            List<TaskInstanceEntity> postInstanceEntityList = taskInstanceDao.findPostInstance(postInstanceId);
            TaskInstance instance = BeanMapper.map(postInstanceEntityList.get(0), TaskInstance.class);
            String taskInstanceId = instance.getId();
            TaskInstance taskInstance = taskInstanceMap.get(taskInstanceId);
            if (Objects.isNull(taskInstance)){
                String readFile = PipelineFileUtil.readFile(instance.getLogAddress(), 0);
                instance.setRunLog(readFile);
            }else {
                instance.setRunTime(taskInstance.getRunTime());
                if (!StringUtils.isEmpty(instance.getRunLog())){
                    instance.setRunLog(instance.getRunLog() + taskInstance.getRunLog());
                }else {
                    instance.setRunLog(taskInstance.getRunLog());
                }
            }
            String time = PipelineUtil.formatDateTime(instance.getRunTime());
            instance.setRunTimeDate(time);
            list.add(instance);
        }
        list.sort(Comparator.comparing(TaskInstance::getTaskSort));
        return list;
    }

    @Override
    public List<TaskInstance> findAllStageInstance(String stageId) {
        List<TaskInstanceEntity> pipelineInstance = taskInstanceDao.findStageInstance(stageId);
        List<TaskInstance> allInstance = BeanMapper.mapList(pipelineInstance, TaskInstance.class);
        if (Objects.isNull(allInstance) || allInstance.isEmpty()){
            return Collections.emptyList();
        }

        int allTaskRunTime = 0;
        for (TaskInstance instance : allInstance) {
            String taskInstanceId = instance.getId();

            TaskDeployInstanceQuery deployInstanceQuery = new TaskDeployInstanceQuery();
            deployInstanceQuery.setTaskInstanceId(taskInstanceId);
            List<TaskDeployInstance> allDeployInstanceList = taskDeployInstanceService.findAllDeployInstanceList(deployInstanceQuery);
            List<TaskDeployInstance> list = allDeployInstanceList.stream().peek(deployInstance -> {
                String runTime = deployInstance.getRunTime();
                if (StringUtils.isEmpty(runTime)) {
                    runTime = "0";
                }
                String s = PipelineUtil.formatDateTime(Long.parseLong(runTime));
                deployInstance.setRunTime(s);
            }).toList();
            instance.setDeployInstanceList(list);

            TaskInstance taskInstance = taskInstanceMap.get(taskInstanceId);
            if (Objects.isNull(taskInstance)){
                String logAddress = instance.getLogAddress();
                String readFile = PipelineFileUtil.readFile(logAddress, 2000);
                instance.setRunLog(readFile);
            }else {
                int time = taskInstance.getRunTime();
                if (time == 0){
                    time = 1;
                }
                if (StringUtils.isEmpty(taskInstance.getRunLog()) ){
                    String logAddress = instance.getLogAddress();
                    String readFile = PipelineFileUtil.readFile(logAddress, readLogLength);
                    instance.setRunLog(readFile);
                }else {
                    instance.setRunLog(taskInstance.getRunLog());
                }
                instance.setRunTime(time);
                instance.setRunState(taskInstance.getRunState());
            }
            String time = PipelineUtil.formatDateTime(instance.getRunTime());
            instance.setRunTimeDate(time);
            allTaskRunTime = allTaskRunTime + instance.getRunTime();
        }
        return allInstance;
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
                    writeExecLog(taskId,  s1);
                }
                writeExecLog(taskId,  s);

            }

            //读取err执行信息
            inputStreamReader = PipelineUtil.encode(errInputStream, enCode);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((s = bufferedReader.readLine()) != null) {
                String s1 = validStatus(s, error);
                if (!Objects.isNull(s1)){
                    state = false ;
                    writeExecLog(taskId,  s1);
                }
                writeExecLog(taskId,  s);
            }

            // 关闭
            inputStreamReader.close();
            bufferedReader.close();

        } catch (Exception e){
            writeExecLog(taskId,  e.getMessage());
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
    public String validStatus(String s,Map<String,String> errors){

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

    @Override
    public void writeAllExecLog(String taskId, String execLog){
        if(!PipelineUtil.isNoNull(execLog)){
            return;
        }
        TasksExecServiceImpl tasksExecService = new TasksExecServiceImpl();
        String taskInstanceId = tasksExecService.findTaskInstanceId(taskId);
        TaskInstance taskInstance1 = tasksExecService.findTaskInstance(taskInstanceId);
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
}
