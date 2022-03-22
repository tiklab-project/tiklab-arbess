package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.LogDao;
import com.doublekit.pipeline.instance.entity.LogEntity;
import com.doublekit.pipeline.instance.model.History;
import com.doublekit.pipeline.instance.model.Log;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class LogServiceImpl implements LogService {

    @Autowired
    StructureService structureService;

    @Autowired
    HistoryService historyService;

    @Autowired
    LogDao logDao;

    @Override
    public String createLog(Log log) {

        LogEntity logEntity = BeanMapper.map(log, LogEntity.class);

        return logDao.createLog(logEntity);
    }

    @Override
    public void deleteLog(String id) {
        logDao.deleteLog(id);
    }

    @Override
    public void updateLog(Log log) {

        LogEntity logEntity = BeanMapper.map(log, LogEntity.class);

        logDao.updateLog(logEntity);
    }

    @Override
    public Log findOneLog(String logId) {
        LogEntity logEntity = logDao.findOne(logId);

        return BeanMapper.map(logEntity, Log.class);
    }

    @Override
    public List<Log> findAllLog() {

        List<LogEntity> logEntityList = logDao.findAllLog();

        return BeanMapper.mapList(logEntityList, Log.class);
    }

    @Override
    public List<Log> findAllLogList(List<String> idList) {

        List<LogEntity> pipelineLogList = logDao.findAllLogList(idList);

        return BeanMapper.mapList(pipelineLogList, Log.class);
    }

    //创建历史表
    public String createHistory(String logId){
        History history = new History();
        history.getPipelineLog().setLogId(logId);
        return historyService.createHistory(history);
    }

    @Override
    public void addHistoryThree(String pipelineId ,String logId )  {

        List<History> pipelineHistories = historyService.findAllPipelineIdList(pipelineId);
        int i = 1 ;
        if (pipelineHistories != null){
            i =  pipelineHistories.size() + 1  ;
        }

        int number =  i;
        History history = structureService.addHistoryTwo(pipelineId);
        Log log = findOneLog(logId);
        history.setPipelineLog(log);
        history.setHistoryNumber(number);

         historyService.perfectHistory(history);
    }

}
