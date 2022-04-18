package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineCodeLogDao;
import com.doublekit.pipeline.instance.entity.PipelineCodeLogEntity;
import com.doublekit.pipeline.instance.model.PipelineCodeLog;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineCodeLogServiceImpl implements  PipelineCodeLogService{

    @Autowired
    PipelineCodeLogDao pipelineCodeLogDao;

    @Autowired
    PipelineTestLogService pipelineTestLogService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //创建
    @Override
    public String createCodeLog(PipelineCodeLog pipelineCodeLog) {
        return pipelineCodeLogDao.createCodeLog(BeanMapper.map(pipelineCodeLog, PipelineCodeLogEntity.class));
    }

    //创建
    @Override
    public Map<String, String> createCodeLog(){
        Map<String, String> map = pipelineTestLogService.createTestLog();
        PipelineCodeLog pipelineCodeLog = new PipelineCodeLog();
        String codeLogId = createCodeLog(pipelineCodeLog);
        map.put("codeLogId",codeLogId);
        return map;
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
        // PipelineCodeLog codeLog = pipelineExecLog.getCodeLog();
        // if (codeLog != null){
        //     deleteCodeLog(codeLog.getLogCodeId());
        // }
    }

    //修改
    @Override
    public void updateCodeLog(PipelineCodeLog pipelineCodeLog) {
        pipelineCodeLogDao.updateCodeLog(BeanMapper.map(pipelineCodeLog,PipelineCodeLogEntity.class));
    }

    //修改
    @Override
    public void updateCodeLog(PipelineExecLog pipelineExecLog){
        pipelineTestLogService.updateTestLog(pipelineExecLog);
        // updateCodeLog(pipelineExecLog.getCodeLog());
    }

    //查询单个
    @Override
    public PipelineCodeLog findOneCodeLog(String codeLogId) {
        return BeanMapper.map(pipelineCodeLogDao.findOneCodeLog(codeLogId),PipelineCodeLog.class);
    }

    //查询所有
    @Override
    public List<PipelineCodeLog> findAllCodeLog() {
        return BeanMapper.mapList(pipelineCodeLogDao.findAllCodeLog(),PipelineCodeLog.class);
    }

    @Override
    public List<PipelineCodeLog> findAllCodeLogList(List<String> idList) {
        return BeanMapper.mapList(pipelineCodeLogDao.findAllCodeLogList(idList), PipelineCodeLog.class);
    }
}
