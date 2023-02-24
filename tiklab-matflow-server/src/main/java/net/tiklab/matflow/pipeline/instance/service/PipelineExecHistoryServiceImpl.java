package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineStages;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesService;
import net.tiklab.matflow.pipeline.instance.dao.PipelineExecHistoryDao;
import net.tiklab.matflow.pipeline.instance.entity.PipelineExecHistoryEntity;
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
public class PipelineExecHistoryServiceImpl implements PipelineExecHistoryService {

    @Autowired
    PipelineExecHistoryDao pipelineExecHistoryDao;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineStagesService stagesServer;

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
        PipelineExecHistory history = findOneHistory(historyId);
        String id = history.getPipeline().getId();
        pipelineExecHistoryDao.deleteHistory(historyId);
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

    //查询用户所有流水线历史
    @Override
    public Pagination<PipelineExecHistory> findUserAllHistory(PipelineAllHistoryQuery pipelineHistoryQuery){
        Pagination<PipelineExecHistoryEntity> pagination = pipelineExecHistoryDao.findAllPageHistory(pipelineHistoryQuery);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineExecHistory.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);

    }

    //查询用户所有正在运行的流水线
    @Override
    public Pagination<PipelineExecHistory> findUserRunPageHistory(PipelineAllHistoryQuery pipelineHistoryQuery){
        Pagination<PipelineExecHistoryEntity> pagination = pipelineExecHistoryDao.findUserRunPageHistory(pipelineHistoryQuery);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineExecHistory.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
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

    /**
     * 查询流水线最后一次的运行历史
     * @param pipelineId 流水线id
     * @return 运行历史
     */
    @Override
    public PipelineExecHistory findLastHistory(String pipelineId){

        List<PipelineExecHistoryEntity> latelySuccess = pipelineExecHistoryDao.findLastHistory(pipelineId);

        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecHistory.class);
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
    public PipelineExecHistory findRunHistory(String pipelineId){
        List<PipelineExecHistoryEntity> latelySuccess = pipelineExecHistoryDao.findRunHistory(pipelineId);
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
