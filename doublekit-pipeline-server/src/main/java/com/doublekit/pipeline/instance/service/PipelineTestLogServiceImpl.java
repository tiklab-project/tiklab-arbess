package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineTestLogDao;
import com.doublekit.pipeline.instance.entity.PipelineTestLogEntity;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineTestLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineTestLogServiceImpl implements PipelineTestLogService {

    @Autowired
    PipelineTestLogDao pipelineTestLogDao;

    @Autowired
    PipelineStructureLogService pipelineStructureLogService;

    //创建
    @Override
    public String createTestLog(PipelineTestLog pipelineTestLog) {
        return pipelineTestLogDao.createTestLog(BeanMapper.map(pipelineTestLog, PipelineTestLogEntity.class));
    }

    //删除
    @Override
    public void deleteTestLog(String testLogId) {
        pipelineTestLogDao.deleteTestLog(testLogId);
    }

    //删除
    @Override
    public void deleteTestLog(PipelineExecLog pipelineExecLog) {
        pipelineStructureLogService.deleteStructureLog(pipelineExecLog);
        deleteTestLog(pipelineExecLog.getTestLog().getLogTestId());
    }

    //修改
    @Override
    public void updateTestLog(PipelineTestLog pipelineTestLog) {
        pipelineTestLogDao.updateTestLog(BeanMapper.map(pipelineTestLog,PipelineTestLogEntity.class));
    }

    //查询单个
    @Override
    public PipelineTestLog findOneTestLog(String testLogId) {
        return BeanMapper.map(pipelineTestLogDao.findOneTestLog(testLogId),PipelineTestLog.class);
    }

    //查询所有
    @Override
    public List<PipelineTestLog> findAllTestLog() {
        return BeanMapper.mapList(pipelineTestLogDao.findAllTestLog(),PipelineTestLog.class);
    }

    @Override
    public List<PipelineTestLog> findAllTestLogList(List<String> idList) {
        return BeanMapper.mapList(pipelineTestLogDao.findAllTestLogList(idList), PipelineTestLog.class);
    }
}
