package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.pipeline.instance.dao.TaskInstanceLogDao;
import net.tiklab.matflow.pipeline.instance.entity.TaskInstanceLogEntity;
import net.tiklab.matflow.pipeline.instance.model.TaskInstanceLog;
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
public class TaskInstanceLogServiceImpl implements TaskInstanceLogService {

    @Autowired
    private TaskInstanceLogDao taskInstanceLogDao;

    @Override
    public String createLog(TaskInstanceLog taskInstanceLog) {
        TaskInstanceLogEntity logEntity = BeanMapper.map(taskInstanceLog, TaskInstanceLogEntity.class);
        return taskInstanceLogDao.createLog(logEntity);
    }

    @Override
    public void updateLog(TaskInstanceLog taskInstanceLog) {
        taskInstanceLogDao.updateLog(BeanMapper.map(taskInstanceLog, TaskInstanceLogEntity.class));
    }


    @Override
    public TaskInstanceLog findOneLog(String logId){
        TaskInstanceLogEntity execLogEntity = taskInstanceLogDao.findOne(logId);
        return BeanMapper.map(execLogEntity, TaskInstanceLog.class);
    }

    @Override
    public void deleteInstanceLog(String instance) {
        taskInstanceLogDao.deleteAllLog(instance);
    }

    /**
     * 查询历史对应的日志
     * @param instance 历史id
     * @return 日志集合
     */
    @Override
    public List<TaskInstanceLog> findAllLog(String instance){
        List<TaskInstanceLogEntity> pipelineExecLogList = taskInstanceLogDao.findAllLog(instance);
        List<TaskInstanceLog> list = BeanMapper.mapList(pipelineExecLogList, TaskInstanceLog.class);
        if (list == null){
           return null;
        }
        list.sort(Comparator.comparing(TaskInstanceLog::getTaskSort));
        return list;
    }

    /**
     * 根据阶段查询对应日志
     * @param stagesId 阶段id
     * @return 日志
     */
    public List<TaskInstanceLog> findAllStagesLog(String instance, String stagesId){
        List<TaskInstanceLogEntity> pipelineExecLogList = taskInstanceLogDao.findAllStagesLog(instance,stagesId);
        return BeanMapper.mapList(pipelineExecLogList, TaskInstanceLog.class);
    }

    //查询所有
    @Override
    public List<TaskInstanceLog> findAllLog() {
        List<TaskInstanceLogEntity> allLog = taskInstanceLogDao.findAllLog();
        return BeanMapper.mapList(allLog, TaskInstanceLog.class);
    }

    @Override
    public List<TaskInstanceLog> findAllLogList(List<String> idList) {
        List<TaskInstanceLogEntity> pipelineLogList = taskInstanceLogDao.findAllLogList(idList);
        return BeanMapper.mapList(pipelineLogList, TaskInstanceLog.class);
    }


}
