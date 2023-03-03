package net.tiklab.matflow.task.task.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.task.task.dao.TaskInstanceDao;
import net.tiklab.matflow.task.task.entity.TaskInstanceEntity;
import net.tiklab.matflow.task.task.model.TaskInstance;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * 任务日志服务接口
 */
@Service
@Exporter
public class TaskInstanceServiceImpl implements TaskInstanceService {

    @Autowired
    private TaskInstanceDao taskInstanceDao;

    @Override
    public String createLog(TaskInstance taskInstance) {
        TaskInstanceEntity logEntity = BeanMapper.map(taskInstance, TaskInstanceEntity.class);
        return taskInstanceDao.createLog(logEntity);
    }

    @Override
    public void updateLog(TaskInstance taskInstance) {
        taskInstanceDao.updateLog(BeanMapper.map(taskInstance, TaskInstanceEntity.class));
    }


    @Override
    public TaskInstance findOneLog(String logId){
        TaskInstanceEntity execLogEntity = taskInstanceDao.findOne(logId);
        return BeanMapper.map(execLogEntity, TaskInstance.class);
    }

    @Override
    public void deleteInstanceLog(String instance) {
        taskInstanceDao.deleteAllLog(instance);
    }

    /**
     * 查询历史对应的日志
     * @param instance 历史id
     * @return 日志集合
     */
    @Override
    public List<TaskInstance> findAllLog(String instance){
        List<TaskInstanceEntity> pipelineExecLogList = taskInstanceDao.findAllLog(instance);
        List<TaskInstance> list = BeanMapper.mapList(pipelineExecLogList, TaskInstance.class);
        if (list == null){
           return null;
        }
        list.sort(Comparator.comparing(TaskInstance::getTaskSort));
        return list;
    }

    /**
     * 根据阶段查询对应日志
     * @param stagesId 阶段id
     * @return 日志
     */
    public List<TaskInstance> findAllStagesLog(String instance, String stagesId){
        List<TaskInstanceEntity> pipelineExecLogList = taskInstanceDao.findAllStagesLog(instance,stagesId);
        return BeanMapper.mapList(pipelineExecLogList, TaskInstance.class);
    }

    //查询所有
    @Override
    public List<TaskInstance> findAllLog() {
        List<TaskInstanceEntity> allLog = taskInstanceDao.findAllLog();
        return BeanMapper.mapList(allLog, TaskInstance.class);
    }

    @Override
    public List<TaskInstance> findAllLogList(List<String> idList) {
        List<TaskInstanceEntity> pipelineLogList = taskInstanceDao.findAllLogList(idList);
        return BeanMapper.mapList(pipelineLogList, TaskInstance.class);
    }


}
