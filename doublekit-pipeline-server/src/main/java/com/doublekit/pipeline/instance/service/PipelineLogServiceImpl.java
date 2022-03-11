package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineLogDao;
import com.doublekit.pipeline.instance.entity.PipelineLogEntity;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.services.PlatformService;

import java.util.List;

@Service
@Exporter
public class PipelineLogServiceImpl implements PipelineLogService {

    @Autowired
    PipelineStructureService pipelineStructureService;

    @Autowired
    PipelineHistoryService pipelineHistoryService;

    @Autowired
    PipelineLogDao pipelineLogDao;

    @Override
    public String createPipelineLog(PipelineLog pipelineLog) {

        PipelineLogEntity pipelineLogEntity = BeanMapper.map(pipelineLog, PipelineLogEntity.class);

        return pipelineLogDao.createPipelineLog(pipelineLogEntity);
    }

    @Override
    public void deletePipelineLog(String id) {
        pipelineLogDao.deletePipelineLog(id);
    }

    @Override
    public void updatePipelineLog(PipelineLog pipelineLog) {

        PipelineLogEntity pipelineLogEntity = BeanMapper.map(pipelineLog, PipelineLogEntity.class);

        pipelineLogDao.updatePipelineLog(pipelineLogEntity);
    }

    @Override
    public PipelineLog selectPipelineLog(String id) {
        PipelineLogEntity pipelineLogEntity = pipelineLogDao.selectPipelineLog(id);

        return BeanMapper.map(pipelineLogEntity, PipelineLog.class);
    }

    @Override
    public List<PipelineLog> selectAllPipelineLog() {

        List<PipelineLogEntity> pipelineLogEntityList = pipelineLogDao.selectAllPipelineLog();

        return BeanMapper.mapList(pipelineLogEntityList, PipelineLog.class);
    }

    @Override
    public List<PipelineLog> selectAllPipelineLogList(List<String> idList) {

        List<PipelineLogEntity> pipelineLogList = pipelineLogDao.selectAllPipelineLogList(idList);

        return BeanMapper.mapList(pipelineLogList, PipelineLog.class);
    }

    //创建历史表
    public String createHistory(String logId){
        PipelineHistory pipelineHistory = new PipelineHistory();
        pipelineHistory.getPipelineLog().setLogId(logId);
        return pipelineHistoryService.createPipelineHistory(pipelineHistory);
    }

    @Override
    public void pipelineHistoryThree(String pipelineId ,String logId )  {

        List<PipelineHistory> pipelineHistories = pipelineHistoryService.selectAllPipelineIdList(pipelineId);
        int i = 1 ;
        if (pipelineHistories != null){
            i =  pipelineHistories.size() + 1  ;
        }

        int number =  i;
        PipelineHistory pipelineHistory = pipelineStructureService.pipelineHistoryTwo(pipelineId);
        PipelineLog pipelineLog = selectPipelineLog(logId);
        pipelineHistory.setPipelineLog(pipelineLog);
        pipelineHistory.setHistoryNumber(number);

         pipelineHistoryService.foundPipelineHistory(pipelineHistory);
    }

}
