package net.tiklab.matflow.execute.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.execute.dao.PipelineExecLogDao;
import net.tiklab.matflow.execute.entity.PipelineExecLogEntity;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class PipelineExecLogServiceImpl implements PipelineExecLogService {

    @Autowired
    PipelineExecLogDao pipelineExecLogDao;

    //创建
    @Override
    public String createLog(PipelineExecLog pipelineExecLog) {
        return pipelineExecLogDao.createLog(BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class));
    }

    //删除
    public void deleteLog(String logId) {
        pipelineExecLogDao.deleteLog(logId);
    }

    @Override
    public void deleteHistoryLog(String historyId) {
        pipelineExecLogDao.deleteAllLog(historyId);
    }

    //更新
    @Override
    public void updateLog(PipelineExecLog pipelineExecLog) {
        pipelineExecLogDao.updateLog(BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class));
    }

    @Override
    public List<PipelineExecLog> findAllLog(String historyId){
        List<PipelineExecLogEntity> pipelineExecLogList = pipelineExecLogDao.findAllLog(historyId);
        List<PipelineExecLog> list = BeanMapper.mapList(pipelineExecLogList, PipelineExecLog.class);
        if (list == null){
           return null;
        }
        for (PipelineExecLog pipelineExecLog : list) {
            pipelineExecLog.setExecTime(PipelineUntil.formatDateTime(pipelineExecLog.getRunTime()));
        }
        list.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
        return list;
    }
    //查询所有
    @Override
    public List<PipelineExecLog> findAllLog() {
        List<PipelineExecLogEntity> allLog = pipelineExecLogDao.findAllLog();
        return BeanMapper.mapList(allLog, PipelineExecLog.class);
    }


    @Override
    public List<PipelineExecLog> findAllLogList(List<String> idList) {
        List<PipelineExecLogEntity> pipelineLogList = pipelineExecLogDao.findAllLogList(idList);
        return BeanMapper.mapList(pipelineLogList, PipelineExecLog.class);
    }


}