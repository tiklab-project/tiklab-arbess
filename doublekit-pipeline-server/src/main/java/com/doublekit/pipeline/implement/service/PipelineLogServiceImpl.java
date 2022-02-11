package com.doublekit.pipeline.implement.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.implement.dao.PipelineLogDao;
import com.doublekit.pipeline.implement.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.implement.entity.PipelineLogEntity;
import com.doublekit.pipeline.implement.model.PipelineHistory;
import com.doublekit.pipeline.implement.model.PipelineLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineLogServiceImpl implements PipelineLogService {

    @Autowired
    JoinTemplate joinTemplate;

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
}
