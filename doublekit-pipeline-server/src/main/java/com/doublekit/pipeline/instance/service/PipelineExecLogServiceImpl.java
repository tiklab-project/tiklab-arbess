package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
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
    JoinTemplate joinTemplate;

    @Autowired
    PipelineExecLogDao pipelineExecLogDao;

    @Autowired
    PipelineCodeLogService pipelineCodeLogService;

    //创建
    @Override
    public String createLog(PipelineExecLog pipelineExecLog) {
        PipelineExecLogEntity pipelineExecLogEntity = BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class);
        return pipelineExecLogDao.createLog(pipelineExecLogEntity);
    }

    //删除
    @Override
    public void deleteLog(String pipelineLogId) {
        PipelineExecLog oneLog = findOneLog(pipelineLogId);
        pipelineCodeLogService.deleteCodeLog(oneLog);
        pipelineExecLogDao.deleteLog(pipelineLogId);
    }

    //更新
    @Override
    public void updateLog(PipelineExecLog pipelineExecLog) {
        PipelineExecLogEntity pipelineExecLogEntity = BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class);
        pipelineExecLogDao.updateLog(pipelineExecLogEntity);
    }

    //查询单个
    @Override
    public PipelineExecLog findOneLog(String pipelineLogId) {
        PipelineExecLogEntity pipelineExecLogEntity = pipelineExecLogDao.findOne(pipelineLogId);
        PipelineExecLog pipelineExecLog = BeanMapper.map(pipelineExecLogEntity, PipelineExecLog.class);
        joinTemplate.joinQuery(pipelineExecLog);
        return pipelineExecLog;
    }

    //查询所有
    @Override
    public List<PipelineExecLog> findAllLog() {
        List<PipelineExecLogEntity> pipelineExecLogEntityList = pipelineExecLogDao.findAllLog();
        List<PipelineExecLog> pipelineExecLogs = BeanMapper.mapList(pipelineExecLogEntityList, PipelineExecLog.class);
        joinTemplate.joinQuery(pipelineExecLogEntityList);
        return pipelineExecLogs;
    }

    @Override
    public List<PipelineExecLog> findAllLogList(List<String> idList) {
        List<PipelineExecLogEntity> pipelineLogList = pipelineExecLogDao.findAllLogList(idList);
        return BeanMapper.mapList(pipelineLogList, PipelineExecLog.class);
    }

}
