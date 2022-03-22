package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineExecLogDao;
import com.doublekit.pipeline.instance.entity.PipelineExecLogEntity;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineExecLogServiceImpl implements PipelineExecLogService {

    @Autowired
    PipelineExecService pipelineExecService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineExecLogDao pipelineExecLogDao;

    @Override
    public String createLog(PipelineExecLog pipelineExecLog) {

        PipelineExecLogEntity pipelineExecLogEntity = BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class);

        return pipelineExecLogDao.createLog(pipelineExecLogEntity);
    }

    @Override
    public void deleteLog(String id) {
        pipelineExecLogDao.deleteLog(id);
    }

    @Override
    public void updateLog(PipelineExecLog pipelineExecLog) {

        PipelineExecLogEntity pipelineExecLogEntity = BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class);

        pipelineExecLogDao.updateLog(pipelineExecLogEntity);
    }

    @Override
    public PipelineExecLog findOneLog(String logId) {
        PipelineExecLogEntity pipelineExecLogEntity = pipelineExecLogDao.findOne(logId);

        return BeanMapper.map(pipelineExecLogEntity, PipelineExecLog.class);
    }

    @Override
    public List<PipelineExecLog> findAllLog() {

        List<PipelineExecLogEntity> pipelineExecLogEntityList = pipelineExecLogDao.findAllLog();

        return BeanMapper.mapList(pipelineExecLogEntityList, PipelineExecLog.class);
    }

    @Override
    public List<PipelineExecLog> findAllLogList(List<String> idList) {

        List<PipelineExecLogEntity> pipelineLogList = pipelineExecLogDao.findAllLogList(idList);

        return BeanMapper.mapList(pipelineLogList, PipelineExecLog.class);
    }

    //创建历史表
    public String createHistory(String logId){
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        pipelineExecHistory.getPipelineLog().setLogId(logId);
        return pipelineExecHistoryService.createHistory(pipelineExecHistory);
    }

    @Override
    public void addHistoryThree(String pipelineId ,String logId )  {

        List<PipelineExecHistory> pipelineHistories = pipelineExecHistoryService.findAllPipelineIdList(pipelineId);
        int i = 1 ;
        if (pipelineHistories != null){
            i =  pipelineHistories.size() + 1  ;
        }

        int number =  i;
        PipelineExecHistory pipelineExecHistory = pipelineExecService.addHistoryTwo(pipelineId);
        PipelineExecLog pipelineExecLog = findOneLog(logId);
        pipelineExecHistory.setPipelineLog(pipelineExecLog);
        pipelineExecHistory.setHistoryNumber(number);

         pipelineExecHistoryService.perfectHistory(pipelineExecHistory);
    }

}
