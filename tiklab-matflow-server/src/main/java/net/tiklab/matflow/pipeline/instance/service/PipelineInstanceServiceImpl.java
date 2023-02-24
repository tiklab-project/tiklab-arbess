package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineStages;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesService;
import net.tiklab.matflow.pipeline.instance.dao.PipelineInstanceDao;
import net.tiklab.matflow.pipeline.instance.entity.PipelineInstanceEntity;
import net.tiklab.matflow.pipeline.instance.model.*;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
@Exporter
public class PipelineInstanceServiceImpl implements PipelineInstanceService {

    @Autowired
    PipelineInstanceDao pipelineInstanceDao;

    @Autowired
    TaskInstanceLogService taskInstanceLogService;

    @Autowired
    PipelineStagesService stagesServer;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineInstanceServiceImpl.class);

    //创建
    @Override
    public String createHistory(PipelineInstance pipelineInstance) {
        return pipelineInstanceDao.createHistory(BeanMapper.map(pipelineInstance, PipelineInstanceEntity.class));
    }

    //删除
    @Override
    public void deleteAllHistory(String pipelineId) {
        List<PipelineInstance> allHistory = findAllHistory();
        if (allHistory == null){
            return;
        }
        for (PipelineInstance history : allHistory) {
            Pipeline pipeline = history.getPipeline();
            if (pipeline == null){
                deleteHistory(history.getHistoryId());
            }
            if (history.getPipeline().getId().equals(pipelineId)){
                deleteHistory(history.getHistoryId());
            }
        }
    }

    /**
     * 删除单个历史以及历史对应日志
     * @param historyId 历史id
     */
    @Override
    public void deleteHistory(String historyId) {
        PipelineInstance history = findOneHistory(historyId);
        String id = history.getPipeline().getId();
        pipelineInstanceDao.deleteHistory(historyId);
        taskInstanceLogService.deleteHistoryLog(historyId);
        String fileAddress = PipelineUntil.findFileAddress(id,2);
        //删除对应日志
        PipelineUntil.deleteFile(new File(fileAddress+"/"+historyId+"/"));
    }

    @Override
    public String createLog(TaskInstanceLog taskInstanceLog){
       return taskInstanceLogService.createLog(taskInstanceLog);
   }

    //修改
    @Override
    public void updateHistory(PipelineInstance pipelineInstance) {
        pipelineInstanceDao.updateHistory(BeanMapper.map(pipelineInstance, PipelineInstanceEntity.class));
    }

    //查询单个
    @Override
    public PipelineInstance findOneHistory(String historyId) {
        PipelineInstanceEntity pipelineInstanceEntity = pipelineInstanceDao.findOneHistory(historyId);
        PipelineInstance history = BeanMapper.map(pipelineInstanceEntity, PipelineInstance.class);
        joinTemplate.joinQuery(history);
        return history;
    }

    //查询所有
    @Override
    public List<PipelineInstance> findAllHistory() {
        List<PipelineInstanceEntity> pipelineInstanceEntityList = pipelineInstanceDao.findAllHistory();
        return BeanMapper.mapList(pipelineInstanceEntityList, PipelineInstance.class);
    }

    //根据流水线id查询所有历史
    @Override
    public List<PipelineInstance> findAllHistory(String pipelineId) {
        List<PipelineInstanceEntity> list = pipelineInstanceDao.findAllHistory(pipelineId);
        if (list == null){
            return null;
        }
        List<PipelineInstance> allHistory = BeanMapper.mapList(list, PipelineInstance.class);
        allHistory.sort(Comparator.comparing(PipelineInstance::getCreateTime,Comparator.reverseOrder()));
        return allHistory;
    }

    //查询用户所有流水线历史
    @Override
    public Pagination<PipelineInstance> findUserAllHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findAllPageHistory(pipelineHistoryQuery);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);

    }

    //查询用户所有正在运行的流水线
    @Override
    public Pagination<PipelineInstance> findUserRunPageHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findUserRunPageHistory(pipelineHistoryQuery);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }



    //查询最近一次执行历史
    @Override
    public PipelineInstance findLatelyHistory(String pipelineId){
        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findLatelyHistory(pipelineId);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    /**
     * 查询流水线最后一次的运行历史
     * @param pipelineId 流水线id
     * @return 运行历史
     */
    @Override
    public PipelineInstance findLastHistory(String pipelineId){

        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findLastHistory(pipelineId);

        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    /**
     * 查询流水线正在运行历史
     * @param pipelineId 流水线id
     * @return 运行历史
     */
    @Override
    public PipelineInstance findRunHistory(String pipelineId){
        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findRunHistory(pipelineId);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }


    //查询最近一次成功记录
    @Override
    public PipelineInstance findLatelySuccess(String pipelineId){
        List<PipelineInstanceEntity> latelySuccess = pipelineInstanceDao.findLatelySuccess(pipelineId);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineInstance.class);

        if (pipelineExecHistories.size() == 0){
            return null;
        }

        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    @Override
    public List<PipelineInstance> findHistoryList(List<String> idList) {
        List<PipelineInstanceEntity> pipelineInstanceEntityList = pipelineInstanceDao.findHistoryList(idList);
        return BeanMapper.mapList(pipelineInstanceEntityList, PipelineInstance.class);
    }


    /**
     * 查询日志详情
     * @param historyId 历史id
     * @return 日志集合
     */
    @Override
    public TaskRunLog findAll(String historyId){
        PipelineInstance history = findOneHistory(historyId);

        if (history == null){
            return null;
        }

        TaskRunLog execRunLog = new TaskRunLog();
        execRunLog.setName(String.valueOf(history.getFindNumber()));
        Pipeline pipeline = history.getPipeline();
        String pipelineId = pipeline.getId();
        int pipelineType = pipeline.getType();

        List<TaskRunLog> runLogList = new ArrayList<>();

        if (pipelineType == 1){
            List<TaskInstanceLog> allLog = taskInstanceLogService.findAllLog(historyId);
            for (TaskInstanceLog execLog : allLog) {
                TaskRunLog runLog =  initLog(execLog);
                runLogList.add(runLog);
            }
            Map<String, Object> timeState = findTimeState(runLogList);
            execRunLog.setRunLog((String) timeState.get("runLog"));
        }

        if (pipelineType == 2){
            //多阶段
            List<PipelineStages> stagesMainStage = stagesServer.findAllStagesMainStage(pipelineId);
            for (PipelineStages stages : stagesMainStage) {

                String stagesId = stages.getStagesId();
                //并行阶段
                List<TaskRunLog> logList= new ArrayList<>();
                List<PipelineStages> allMainStage = stagesServer.findAllMainStage(stagesId);
                for (PipelineStages pipelineStages : allMainStage) {

                    String stagesStagesId = pipelineStages.getStagesId();
                    //并行阶段任务
                    List<TaskInstanceLog> allStagesLog = taskInstanceLogService.findAllStagesLog(historyId, stagesStagesId);
                    List<TaskRunLog> logs = new ArrayList<>();
                    for (TaskInstanceLog log : allStagesLog) {
                        TaskRunLog runLogs =  initLog(log);
                        logs.add(runLogs);
                    }
                    TaskRunLog taskRunLog = initRunLog(logs, pipelineStages);
                    logList.add(taskRunLog);
                }
                TaskRunLog runLog = initRunLog(logList, stages);
                runLogList.add(runLog);
            }

            //添加消息阶段
            List<TaskInstanceLog> allLog = taskInstanceLogService.findAllLog(historyId);
            allLog.removeIf(pipelineExecLog -> PipelineUntil.isNoNull(pipelineExecLog.getStagesId()));
            if (allLog.size() == 0){
                execRunLog.setRunLogList(runLogList);
                return execRunLog;
            }

            List<TaskRunLog> logs = new ArrayList<>();
            for (TaskInstanceLog taskInstanceLog : allLog) {
                TaskRunLog log =  initLog(taskInstanceLog);
                logs.add(log);
            }
            PipelineStages stages = new PipelineStages();
            stages.setName("后置任务");
            stages.setStagesId("后置任务");
            TaskRunLog runLog = initRunLog(logs, stages);
            runLogList.add(runLog);
        }
        execRunLog.setRunLogList(runLogList);
        return execRunLog;
    }

    public TaskRunLog initLog(TaskInstanceLog log){
        TaskRunLog runLog = new TaskRunLog();
        runLog.setId(log.getLogId());
        runLog.setState(log.getRunState());
        runLog.setType(log.getTaskType());
        runLog.setTime(log.getRunTime());
        runLog.setName(log.getTaskName());
        String logAddress = log.getLogAddress();
        runLog.setRunLog(PipelineUntil.readFile(logAddress,500));
        return runLog;
    }

    public TaskRunLog initRunLog(List<TaskRunLog> logs, PipelineStages pipelineStages){
        TaskRunLog taskRunLog = new TaskRunLog();
        taskRunLog.setName(pipelineStages.getName());
        taskRunLog.setRunLogList(logs);
        taskRunLog.setName(pipelineStages.getName());
        taskRunLog.setId(pipelineStages.getStagesId());
        Map<String, Object> timeState = findTimeState(logs);
        taskRunLog.setState((Integer) timeState.get("state"));
        taskRunLog.setTime((Integer) timeState.get("time"));
        taskRunLog.setRunLog((String) timeState.get("runLog"));
        return taskRunLog;
    }

    public Map<String,Object> findTimeState(List<TaskRunLog> logs){
        int time = 0;
        int state = 0;
        int runState = 0;
        StringBuilder runLog  = new StringBuilder();
        Map<String,Object> map = new HashMap<>();
        for (TaskRunLog log : logs) {
            time = time + log.getTime();
            runLog.append(log.getRunLog());
            state = state + log.getState();
            if (log.getState() == 1){
                runState = 1;
            }
            if (log.getState() == 20 && runState != 1){
                runState = 20;
            }
        }

        if (runState == 0 ){
            runState = state/logs.size();
        }

        map.put("time",time);
        map.put("state",runState);
        map.put("runLog", runLog.toString());
        return map;
    }


    @Override
    public Pagination<PipelineInstance> findPageHistory(PipelineInstanceQuery pipelineInstanceQuery){
        if (pipelineInstanceQuery.getPipelineId() == null){
            return null;
        }
        Pagination<PipelineInstanceEntity> pagination = pipelineInstanceDao.findPageHistory(pipelineInstanceQuery);
        List<PipelineInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineInstance.class);
        if (pipelineExecHistories == null){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }

}
