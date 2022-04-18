package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineStructureLogDao;
import com.doublekit.pipeline.instance.entity.PipelineStructureLogEntity;
import com.doublekit.pipeline.instance.model.PipelineDeployLog;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineStructureLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return pipelineStructureLogDao.createStructureLog(BeanMapper.map(pipelineStructureLog, PipelineStructureLogEntity.class));
    }

    //创建
    @Override
    public Map<String ,String> createStructureLog(){
       Map<String, String> map = new HashMap<>();
       PipelineStructureLog pipelineStructureLog = new PipelineStructureLog();
       String structureLogId = createStructureLog(pipelineStructureLog);
       map.put("structureLogId",structureLogId);
       PipelineDeployLog pipelineDeployLog = new PipelineDeployLog();
       String deployLogId = pipelineDeployLogService.createDeployLog(pipelineDeployLog);
       map.put("deployLogId",deployLogId);
       return map;
    }

    //删除
    @Override
    public void deleteStructureLog(String structureLogId) {
        pipelineStructureLogDao.deleteStructureLog(structureLogId);
    }

    //删除
    @Override
    public void deleteStructureLog(PipelineExecLog pipelineExecLog) {
        // PipelineStructureLog structureLog = pipelineExecLog.getStructureLog();
        // PipelineDeployLog deployLog = pipelineExecLog.getDeployLog();
        // if (structureLog != null){
        //     deleteStructureLog(structureLog.getLogStructureId());
        // }
        // if (deployLog != null){
        //     pipelineDeployLogService.deleteDeployLog(deployLog.getLogDeployId());
        // }
    }

    //修改
    @Override
    public void updateStructureLog(PipelineStructureLog pipelineStructureLog) {
        pipelineStructureLogDao.updateStructureLog(BeanMapper.map(pipelineStructureLog,PipelineStructureLogEntity.class));
    }

    //修改
    @Override
    public void updateStructureLog(PipelineExecLog pipelineExecLog) {
        // if (pipelineExecLog.getStructureLog() != null){
        //     updateStructureLog(pipelineExecLog.getStructureLog());
        // }
        // if (pipelineExecLog.getDeployLog() != null){
        //     pipelineDeployLogService.updateDeployLog(pipelineExecLog.getDeployLog());
        // }
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
