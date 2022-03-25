package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineStructureLogDao;
import com.doublekit.pipeline.instance.entity.PipelineStructureLogEntity;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineStructureLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineStructureLogServiceImpl  implements PipelineStructureLogService{

    @Autowired
    PipelineStructureLogDao pipelineStructureLogDao;

    @Autowired
    PipelineDeployLogService pipelineDeployLogService;


    //创建
    @Override
    public String createStructureLog(PipelineStructureLog pipelineStructureLog) {
        return pipelineStructureLogDao.createStructureLog(BeanMapper.map(pipelineDeployLogService, PipelineStructureLogEntity.class));
    }

    //删除
    @Override
    public void deleteStructureLog(String structureLogId) {
        pipelineStructureLogDao.deleteStructureLog(structureLogId);
    }

    //删除
    @Override
    public void deleteStructureLog(PipelineExecLog pipelineExecLog) {
        deleteStructureLog(pipelineExecLog.getStructureLog().getLogStructureId());
        pipelineDeployLogService.deleteDeployLog(pipelineExecLog.getDeployLog().getLogDeployId());
    }

    //修改
    @Override
    public void updateStructureLog(PipelineStructureLog pipelineStructureLog) {
        pipelineStructureLogDao.updateStructureLog(BeanMapper.map(pipelineStructureLog,PipelineStructureLogEntity.class));
    }

    //查询单个
    @Override
    public PipelineStructureLog findOneStructureLog(String structureLogId) {
        return BeanMapper.map(pipelineStructureLogDao.findOneStructureLog(structureLogId),PipelineStructureLog.class);
    }

    //查询所有
    @Override
    public List<PipelineStructureLog> findAllStructureLog() {
        return BeanMapper.mapList(pipelineStructureLogDao.findAllStructureLog(),PipelineStructureLog.class);
    }

    @Override
    public List<PipelineStructureLog> findAllStructureLogList(List<String> idList) {
        return BeanMapper.mapList(pipelineStructureLogDao.findAllStructureLogList(idList), PipelineStructureLog.class);
    }
}
