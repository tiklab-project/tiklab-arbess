package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineStages;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesService;
import net.tiklab.matflow.pipeline.instance.dao.PipelineExecInstanceDao;
import net.tiklab.matflow.pipeline.instance.entity.PipelineExecInstanceEntity;
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
public class PipelineExecInstanceServiceImpl implements PipelineExecInstanceService {

    @Autowired
    PipelineExecInstanceDao pipelineExecInstanceDao;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineStagesService stagesServer;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecInstanceServiceImpl.class);

    //创建
    @Override
    public String createHistory(PipelineExecInstance pipelineExecInstance) {
        return pipelineExecInstanceDao.createHistory(BeanMapper.map(pipelineExecInstance, PipelineExecInstanceEntity.class));
    }

    //删除
    @Override
    public void deleteAllHistory(String pipelineId) {
        List<PipelineExecInstance> allHistory = findAllHistory();
        if (allHistory == null){
            return;
        }
        for (PipelineExecInstance history : allHistory) {
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
        PipelineExecInstance history = findOneHistory(historyId);
        String id = history.getPipeline().getId();
        pipelineExecInstanceDao.deleteHistory(historyId);
        pipelineExecLogService.deleteHistoryLog(historyId);
        String fileAddress = PipelineUntil.findFileAddress(id,2);
        //删除对应日志
        PipelineUntil.deleteFile(new File(fileAddress+"/"+historyId+"/"));
    }

    @Override
    public String createLog(PipelineExecLog pipelineExecLog){
       return pipelineExecLogService.createLog(pipelineExecLog);
   }

    //修改
    @Override
    public void updateHistory(PipelineExecInstance pipelineExecInstance) {
        pipelineExecInstanceDao.updateHistory(BeanMapper.map(pipelineExecInstance, PipelineExecInstanceEntity.class));
    }

    //查询单个
    @Override
    public PipelineExecInstance findOneHistory(String historyId) {
        PipelineExecInstanceEntity pipelineExecInstanceEntity = pipelineExecInstanceDao.findOneHistory(historyId);
        PipelineExecInstance history = BeanMapper.map(pipelineExecInstanceEntity, PipelineExecInstance.class);
        joinTemplate.joinQuery(history);
        return history;
    }

    //查询所有
    @Override
    public List<PipelineExecInstance> findAllHistory() {
        List<PipelineExecInstanceEntity> pipelineExecInstanceEntityList = pipelineExecInstanceDao.findAllHistory();
        return BeanMapper.mapList(pipelineExecInstanceEntityList, PipelineExecInstance.class);
    }

    //根据流水线id查询所有历史
    @Override
    public List<PipelineExecInstance> findAllHistory(String pipelineId) {
        List<PipelineExecInstanceEntity> list = pipelineExecInstanceDao.findAllHistory(pipelineId);
        if (list == null){
            return null;
        }
        List<PipelineExecInstance> allHistory = BeanMapper.mapList(list, PipelineExecInstance.class);
        allHistory.sort(Comparator.comparing(PipelineExecInstance::getCreateTime,Comparator.reverseOrder()));
        return allHistory;
    }

    //查询用户所有流水线历史
    @Override
    public Pagination<PipelineExecInstance> findUserAllHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        Pagination<PipelineExecInstanceEntity> pagination = pipelineExecInstanceDao.findAllPageHistory(pipelineHistoryQuery);
        List<PipelineExecInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineExecInstance.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);

    }

    //查询用户所有正在运行的流水线
    @Override
    public Pagination<PipelineExecInstance> findUserRunPageHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        Pagination<PipelineExecInstanceEntity> pagination = pipelineExecInstanceDao.findUserRunPageHistory(pipelineHistoryQuery);
        List<PipelineExecInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineExecInstance.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }



    //查询最近一次执行历史
    @Override
    public PipelineExecInstance findLatelyHistory(String pipelineId){
        List<PipelineExecInstanceEntity> latelySuccess = pipelineExecInstanceDao.findLatelyHistory(pipelineId);
        List<PipelineExecInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecInstance.class);
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
    public PipelineExecInstance findLastHistory(String pipelineId){

        List<PipelineExecInstanceEntity> latelySuccess = pipelineExecInstanceDao.findLastHistory(pipelineId);

        List<PipelineExecInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecInstance.class);
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
    public PipelineExecInstance findRunHistory(String pipelineId){
        List<PipelineExecInstanceEntity> latelySuccess = pipelineExecInstanceDao.findRunHistory(pipelineId);
        List<PipelineExecInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecInstance.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }


    //查询最近一次成功记录
    @Override
    public PipelineExecInstance findLatelySuccess(String pipelineId){
        List<PipelineExecInstanceEntity> latelySuccess = pipelineExecInstanceDao.findLatelySuccess(pipelineId);
        List<PipelineExecInstance> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecInstance.class);

        if (pipelineExecHistories.size() == 0){
            return null;
        }

        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    @Override
    public List<PipelineExecInstance> findHistoryList(List<String> idList) {
        List<PipelineExecInstanceEntity> pipelineExecInstanceEntityList = pipelineExecInstanceDao.findHistoryList(idList);
        return BeanMapper.mapList(pipelineExecInstanceEntityList, PipelineExecInstance.class);
    }


    /**
     * 查询日志详情
     * @param historyId 历史id
     * @return 日志集合
     */
    @Override
    public PipelineRunLog findAll(String historyId){
        PipelineExecInstance history = findOneHistory(historyId);

        if (history == null){
            return null;
        }

        PipelineRunLog execRunLog = new PipelineRunLog();
        execRunLog.setName(String.valueOf(history.getFindNumber()));
        Pipeline pipeline = history.getPipeline();
        String pipelineId = pipeline.getId();
        int pipelineType = pipeline.getType();

        List<PipelineRunLog> runLogList = new ArrayList<>();

        if (pipelineType == 1){
            List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
            for (PipelineExecLog execLog : allLog) {
                PipelineRunLog runLog =  initLog(execLog);
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
                List<PipelineRunLog> logList= new ArrayList<>();
                List<PipelineStages> allMainStage = stagesServer.findAllMainStage(stagesId);
                for (PipelineStages pipelineStages : allMainStage) {

                    String stagesStagesId = pipelineStages.getStagesId();
                    //并行阶段任务
                    List<PipelineExecLog> allStagesLog = pipelineExecLogService.findAllStagesLog(historyId, stagesStagesId);
                    List<PipelineRunLog> logs = new ArrayList<>();
                    for (PipelineExecLog log : allStagesLog) {
                        PipelineRunLog runLogs =  initLog(log);
                        logs.add(runLogs);
                    }
                    PipelineRunLog pipelineRunLog = initRunLog(logs, pipelineStages);
                    logList.add(pipelineRunLog);
                }
                PipelineRunLog runLog = initRunLog(logList, stages);
                runLogList.add(runLog);
            }

            //添加消息阶段
            List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
            allLog.removeIf(pipelineExecLog -> PipelineUntil.isNoNull(pipelineExecLog.getStagesId()));
            if (allLog.size() == 0){
                execRunLog.setRunLogList(runLogList);
                return execRunLog;
            }

            List<PipelineRunLog> logs = new ArrayList<>();
            for (PipelineExecLog pipelineExecLog : allLog) {
                PipelineRunLog log =  initLog(pipelineExecLog);
                logs.add(log);
            }
            PipelineStages stages = new PipelineStages();
            stages.setName("后置任务");
            stages.setStagesId("后置任务");
            PipelineRunLog runLog = initRunLog(logs, stages);
            runLogList.add(runLog);
        }
        execRunLog.setRunLogList(runLogList);
        return execRunLog;
    }

    public PipelineRunLog initLog(PipelineExecLog log){
        PipelineRunLog runLog = new PipelineRunLog();
        runLog.setId(log.getLogId());
        runLog.setState(log.getRunState());
        runLog.setType(log.getTaskType());
        runLog.setTime(log.getRunTime());
        runLog.setName(log.getTaskName());
        String logAddress = log.getLogAddress();
        runLog.setRunLog(PipelineUntil.readFile(logAddress));
        return runLog;
    }

    public PipelineRunLog initRunLog(List<PipelineRunLog> logs,PipelineStages pipelineStages){
        PipelineRunLog pipelineRunLog = new PipelineRunLog();
        pipelineRunLog.setName(pipelineStages.getName());
        pipelineRunLog.setRunLogList(logs);
        pipelineRunLog.setName(pipelineStages.getName());
        pipelineRunLog.setId(pipelineStages.getStagesId());
        Map<String, Object> timeState = findTimeState(logs);
        pipelineRunLog.setState((Integer) timeState.get("state"));
        pipelineRunLog.setTime((Integer) timeState.get("time"));
        pipelineRunLog.setRunLog((String) timeState.get("runLog"));
        return pipelineRunLog;
    }

    public Map<String,Object> findTimeState(List<PipelineRunLog> logs){
        int time = 0;
        int state = 0;
        int runState = 0;
        StringBuilder runLog  = new StringBuilder();
        Map<String,Object> map = new HashMap<>();
        for (PipelineRunLog log : logs) {
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
    public Pagination<PipelineExecInstance> findPageHistory(PipelineInstanceQuery pipelineInstanceQuery){
        if (pipelineInstanceQuery.getPipelineId() == null){
            return null;
        }
        Pagination<PipelineExecInstanceEntity> pagination = pipelineExecInstanceDao.findPageHistory(pipelineInstanceQuery);
        List<PipelineExecInstance> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineExecInstance.class);
        if (pipelineExecHistories == null){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }

}
