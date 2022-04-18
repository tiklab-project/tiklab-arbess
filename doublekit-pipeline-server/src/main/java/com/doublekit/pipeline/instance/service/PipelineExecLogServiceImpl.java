package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.instance.dao.PipelineExecLogDao;
import com.doublekit.pipeline.instance.entity.PipelineExecLogEntity;
import com.doublekit.pipeline.instance.model.*;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineExecLogServiceImpl implements PipelineExecLogService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineExecLogDao pipelineExecLogDao;

    @Autowired
    PipelineCodeLogService pipelineCodeLogService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    //创建
    @Override
    public String createLog(PipelineExecLog pipelineExecLog) {
        PipelineExecLogEntity pipelineExecLogEntity = BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class);
        return pipelineExecLogDao.createLog(pipelineExecLogEntity);
    }

    //创建
    @Override
    public PipelineExecLog createLog(){
        // PipelineExecLog pipelineExecLog = new PipelineExecLog();
        // Map<String, String> map = pipelineCodeLogService.createCodeLog();
        // PipelineCodeLog pipelineCodeLog = new PipelineCodeLog();
        // pipelineCodeLog.setLogCodeId(map.get("codeLogId"));
        // pipelineExecLog.setCodeLog(pipelineCodeLog);
        // PipelineTestLog pipelineTestLog = new PipelineTestLog();
        // pipelineTestLog.setLogTestId(map.get("testLogId"));
        // pipelineExecLog.setTestLog(pipelineTestLog);
        // PipelineStructureLog pipelineStructureLog = new PipelineStructureLog();
        // pipelineStructureLog.setLogStructureId(map.get("structureLogId"));
        // pipelineExecLog.setStructureLog(pipelineStructureLog);
        // PipelineDeployLog pipelineDeployLog = new PipelineDeployLog();
        // pipelineDeployLog.setLogDeployId(map.get("deployLogId"));
        // pipelineExecLog.setDeployLog(pipelineDeployLog);
        // String logId = createLog(pipelineExecLog);
        // pipelineExecLog.setLogId(logId);
        // return pipelineExecLog;
        return null;
    }


    //删除
    @Override
    public void deleteLog(String logId) {
        PipelineExecLog oneLog = findOneLog(logId);
        if (oneLog != null){
            pipelineCodeLogService.deleteCodeLog(oneLog);
        }
        pipelineExecLogDao.deleteLog(logId);
    }

    //更新
    @Override
    public void updateLog(PipelineExecLog pipelineExecLog) {
        PipelineExecLogEntity pipelineExecLogEntity = BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class);
        pipelineCodeLogService.updateCodeLog(pipelineExecLog);
        pipelineExecLogDao.updateLog(pipelineExecLogEntity);
    }

    //查询单个
    @Override
    public PipelineExecLog findOneLog(String logId) {
        PipelineExecLogEntity oneLog = pipelineExecLogDao.findOne(logId);
        PipelineExecLog pipelineExecLog = BeanMapper.map(oneLog, PipelineExecLog.class);
        joinTemplate.joinQuery(pipelineExecLog);
        return pipelineExecLog;
    }

    //查询所有
    @Override
    public List<PipelineExecLog> findAllLog() {
        List<PipelineExecLogEntity> allLog = pipelineExecLogDao.findAllLog();
        List<PipelineExecLog> pipelineExecLogList = BeanMapper.mapList(allLog, PipelineExecLog.class);
        joinTemplate.joinQuery(pipelineExecLogList);
        return pipelineExecLogList;
    }

    //创建历史表
    @Override
    public void createHistory(PipelineExecHistory pipelineExecHistory){
        pipelineExecHistoryService.createHistory(pipelineExecHistory);
    }

    @Override
    public List<PipelineExecLog> findAllLogList(List<String> idList) {
        List<PipelineExecLogEntity> pipelineLogList = pipelineExecLogDao.findAllLogList(idList);
        return BeanMapper.mapList(pipelineLogList, PipelineExecLog.class);
    }

}
