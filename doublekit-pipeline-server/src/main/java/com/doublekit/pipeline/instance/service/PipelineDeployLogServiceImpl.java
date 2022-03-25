package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineDeployLogDao;
import com.doublekit.pipeline.instance.entity.PipelineDeployLogEntity;
import com.doublekit.pipeline.instance.model.PipelineCodeLog;
import com.doublekit.pipeline.instance.model.PipelineDeployLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineDeployLogServiceImpl implements PipelineDeployLogService {
    

    @Autowired
    PipelineDeployLogDao pipelineDeployLogDao;

    //创建
    @Override
    public String createDeployLog(PipelineDeployLog pipelineDeployLog) {
        return pipelineDeployLogDao.createDeployLog(BeanMapper.map(pipelineDeployLog, PipelineDeployLogEntity.class));
    }

    //删除
    @Override
    public void deleteDeployLog(String deployLogId) {
        pipelineDeployLogDao.deleteDeployLog(deployLogId);
    }

    //修改
    @Override
    public void updateDeployLog(PipelineDeployLog pipelineDeployLog) {
        pipelineDeployLogDao.updateDeployLog(BeanMapper.map(pipelineDeployLog, PipelineDeployLogEntity.class));
    }

    //查询单个
    @Override
    public PipelineDeployLog findOneDeployLog(String deployLogId) {
        return BeanMapper.map(pipelineDeployLogDao.findOneDeployLog(deployLogId),PipelineDeployLog.class);
    }

    //查询所有
    @Override
    public List<PipelineDeployLog> findAllDeployLog() {
        return BeanMapper.mapList(pipelineDeployLogDao.findAllDeployLog(),PipelineDeployLog.class);
    }

    @Override
    public List<PipelineDeployLog> findAllDeployLogList(List<String> idList) {
        return BeanMapper.mapList(pipelineDeployLogDao.findAllDeployLogList(idList), PipelineDeployLog.class);
    }
}
