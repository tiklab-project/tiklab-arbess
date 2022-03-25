package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.example.model.PipelineTest;
import com.doublekit.pipeline.instance.dao.PipelineCodeLogDao;
import com.doublekit.pipeline.instance.entity.PipelineCodeLogEntity;
import com.doublekit.pipeline.instance.model.PipelineCodeLog;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Exporter
public class PipelineCodeLogServiceImpl implements  PipelineCodeLogService{

    @Autowired
    PipelineCodeLogDao pipelineCodeLogDao;

    @Autowired
    PipelineTestLogService pipelineTestLogService;

    //创建
    @Override
    public String createCodeLog(PipelineCodeLog PipelineCodeLog) {
        return pipelineCodeLogDao.createCodeLog(BeanMapper.map(PipelineCodeLog, PipelineCodeLogEntity.class));
    }

    //删除
    @Override
    public void deleteCodeLog(String codeLogId) {
        pipelineCodeLogDao.deleteCodeLog(codeLogId);
    }

    //删除
    @Override
    public void deleteCodeLog(PipelineExecLog pipelineExecLog) {
        pipelineTestLogService.deleteTestLog(pipelineExecLog);
        deleteCodeLog(pipelineExecLog.getCodeLog().getLogCodeId());
    }

    //修改
    @Override
    public void updateCodeLog(PipelineCodeLog PipelineCodeLog) {

    }

    //查询单个
    @Override
    public PipelineCodeLog findOneCodeLog(String codeLogId) {
        return null;
    }

    //查询所有
    @Override
    public List<PipelineCodeLog> findAllCodeLog() {
        return null;
    }

    @Override
    public List<PipelineCodeLog> findAllCodeLogList(List<String> idList) {
        return BeanMapper.mapList(pipelineCodeLogDao.findAllCodeLogList(idList), PipelineCodeLog.class);
    }
}
