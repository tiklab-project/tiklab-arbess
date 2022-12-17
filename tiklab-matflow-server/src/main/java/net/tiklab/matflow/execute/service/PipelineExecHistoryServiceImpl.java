package net.tiklab.matflow.execute.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineStages;
import net.tiklab.matflow.definition.service.PipelineStagesServer;
import net.tiklab.matflow.execute.dao.PipelineExecHistoryDao;
import net.tiklab.matflow.execute.entity.PipelineExecHistoryEntity;
import net.tiklab.matflow.execute.model.*;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class PipelineExecHistoryServiceImpl implements PipelineExecHistoryService {

    @Autowired
    PipelineExecHistoryDao pipelineExecHistoryDao;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineStagesServer stagesServer;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecHistoryServiceImpl.class);

    //创建
    @Override
    public String createHistory(PipelineExecHistory pipelineExecHistory) {
        return pipelineExecHistoryDao.createHistory(BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class));
    }

    //删除
    @Override
    public void deleteAllHistory(String pipelineId) {
        List<PipelineExecHistory> allHistory = findAllHistory();
        if (allHistory == null){
            return;
        }
        for (PipelineExecHistory history : allHistory) {
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
        pipelineExecHistoryDao.deleteHistory(historyId);
        pipelineExecLogService.deleteHistoryLog(historyId);
    }

    @Override
    public String createLog(PipelineExecLog pipelineExecLog){
       return pipelineExecLogService.createLog(pipelineExecLog);
   }

    //修改
    @Override
    public void updateHistory(PipelineExecHistory pipelineExecHistory) {
        pipelineExecHistoryDao.updateHistory(BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class));
    }

    //查询单个
    @Override
    public PipelineExecHistory findOneHistory(String historyId) {
        PipelineExecHistoryEntity pipelineExecHistoryEntity = pipelineExecHistoryDao.findOneHistory(historyId);
        PipelineExecHistory history = BeanMapper.map(pipelineExecHistoryEntity, PipelineExecHistory.class);
        joinTemplate.joinQuery(history);
        return history;
    }

    //查询所有
    @Override
    public List<PipelineExecHistory> findAllHistory() {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findAllHistory();
        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }

    //根据流水线id查询所有历史
    @Override
    public List<PipelineExecHistory> findAllHistory(String pipelineId) {
        List<PipelineExecHistoryEntity> list = pipelineExecHistoryDao.findAllHistory(pipelineId);
        if (list == null){
            return null;
        }
        List<PipelineExecHistory> allHistory = BeanMapper.mapList(list, PipelineExecHistory.class);
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime,Comparator.reverseOrder()));
        return allHistory;
    }

    //查询最近一次执行历史
    @Override
    public PipelineExecHistory findLatelyHistory(String pipelineId){
        List<PipelineExecHistoryEntity> latelySuccess = pipelineExecHistoryDao.findLatelyHistory(pipelineId);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecHistory.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    //查询最近一次成功记录
    @Override
    public PipelineExecHistory findLatelySuccess(String pipelineId){
        List<PipelineExecHistoryEntity> latelySuccess = pipelineExecHistoryDao.findLatelySuccess(pipelineId);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecHistory.class);

        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    @Override
    public List<PipelineExecHistory> findHistoryList(List<String> idList) {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findHistoryList(idList);
        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }

    /**
     * 最近一次的构建历史
     * @param pipelineId 流水线id
     * @return 历史
     */
    @Override
    public PipelineExecHistory findLastHistory(String pipelineId){
        List<PipelineExecHistory> allHistory = findAllHistory(pipelineId);
        if (allHistory == null){
            return null;
        }
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime).reversed());
        return allHistory.get(0);
    }

    /**
     * 查询日志详情
     * @param historyId 历史id
     * @return 日志集合
     */
    @Override
    public PipelineRunLog findAll(String historyId){
        PipelineExecHistory history = findOneHistory(historyId);

        if (history == null){
            return null;
        }

        PipelineRunLog execRunLog = new PipelineRunLog();

        Pipeline pipeline = history.getPipeline();
        String pipelineId = pipeline.getId();
        int pipelineType = pipeline.getType();

        List<PipelineRunLog> runLogList = new ArrayList<>();

        if (pipelineType == 1){
            List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
            for (PipelineExecLog execLog : allLog) {
                PipelineRunLog runLog = new PipelineRunLog();
                runLog.setRunLog(execLog.getRunLog());
                runLog.setTime(execLog.getRunTime());
                runLog.setName("1");
                runLog.setState(execLog.getRunState());
                runLog.setId(execLog.getLogId());
                runLogList.add(runLog);
            }
        }

        if (pipelineType == 2){
            //多阶段
            List<PipelineStages> stagesMainStage = stagesServer.findAllStagesMainStage(pipelineId);
            for (PipelineStages stages : stagesMainStage) {
                PipelineRunLog runLog = new PipelineRunLog();
                runLog.setName(stages.getName());

                String stagesId = stages.getStagesId();
                //并行阶段
                List<PipelineRunLog> logList= new ArrayList<>();
                List<PipelineStages> allMainStage = stagesServer.findAllMainStage(stagesId);
                for (PipelineStages pipelineStages : allMainStage) {
                    PipelineRunLog pipelineRunLog = new PipelineRunLog();
                    pipelineRunLog.setName(pipelineStages.getName());
                    String stagesStagesId = pipelineStages.getStagesId();
                    //并行阶段任务
                    List<PipelineExecLog> allStagesLog = pipelineExecLogService.findAllStagesLog(historyId, stagesStagesId);
                    List<PipelineRunLog> logs = new ArrayList<>();
                    for (PipelineExecLog log : allStagesLog) {
                        PipelineRunLog runLogs = new PipelineRunLog();
                        runLogs.setName("任务Task");
                        runLogs.setRunLog(log.getRunLog());
                        runLogs.setTime(log.getRunTime());
                        runLogs.setState(log.getRunState());
                        runLogs.setId(log.getLogId());
                        logs.add(runLogs);
                    }
                    pipelineRunLog.setRunLogList(logs);
                    pipelineRunLog.setName(pipelineStages.getName());
                    pipelineRunLog.setId(pipelineStages.getStagesId());
                    Map<String, Object> timeState = findTimeState(logs);
                    pipelineRunLog.setState((Integer) timeState.get("state"));
                    pipelineRunLog.setTime((Integer) timeState.get("time"));
                    pipelineRunLog.setRunLog((String) timeState.get("runLog"));
                    logList.add(pipelineRunLog);
                }
                Map<String, Object> timeState = findTimeState(logList);
                runLog.setState((Integer) timeState.get("state"));
                runLog.setTime((Integer) timeState.get("time"));
                runLog.setRunLog((String) timeState.get("runLog"));
                runLog.setRunLogList(logList);
                runLog.setId(stagesId);
                runLog.setName(stages.getName());
                runLogList.add(runLog);
            }

            //添加消息阶段
            List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
            allLog.removeIf(pipelineExecLog -> PipelineUntil.isNoNull(pipelineExecLog.getStagesId()));
            if (allLog.size() == 0){
                execRunLog.setRunLogList(runLogList);
                return execRunLog;
            }
            PipelineRunLog runLog = new PipelineRunLog();
            List<PipelineRunLog> logs = new ArrayList<>();
            for (PipelineExecLog pipelineExecLog : allLog) {
                PipelineRunLog log = new PipelineRunLog();
                log.setId(pipelineExecLog.getLogId());
                log.setState(pipelineExecLog.getRunState());
                log.setTime(pipelineExecLog.getRunTime());
                log.setName("312");
                log.setRunLog(pipelineExecLog.getRunLog());
                logs.add(log);
            }
            Map<String, Object> timeState = findTimeState(logs);
            runLog.setId("后置任务");
            runLog.setName("23423");
            runLog.setRunLog((String) timeState.get("runLog"));
            runLog.setState((Integer) timeState.get("state"));
            runLog.setTime((Integer) timeState.get("time"));
            runLogList.add(runLog);
        }
        execRunLog.setRunLogList(runLogList);
        return execRunLog;
    }


    public Map<String,Object> findTimeState(List<PipelineRunLog> logs){
        int time = 0;
        int state = 10;
        StringBuilder runLog  = new StringBuilder();
        Map<String,Object> map = new HashMap<>();
        for (PipelineRunLog log : logs) {
            time = time + log.getTime();
            runLog.append(log.getRunLog());
            int logState = log.getState();
            if (logState == 1){
                state = logState;
            }
            if (logState == 20 && state != 1){
                state = logState;
            }
        }
        map.put("time",time);
        map.put("state",state);
        map.put("runLog", runLog.toString());
        return map;
    }


    @Override
    public Pagination<PipelineExecHistory> findPageHistory(PipelineHistoryQuery pipelineHistoryQuery){
        if (pipelineHistoryQuery.getPipelineId() == null){
            return null;
        }
        Pagination<PipelineExecHistoryEntity> pagination = pipelineExecHistoryDao.findPageHistory(pipelineHistoryQuery);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineExecHistory.class);
        if (pipelineExecHistories == null){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }

}
