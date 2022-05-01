package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.instance.dao.PipelineExecLogDao;
import com.doublekit.pipeline.instance.entity.PipelineExecLogEntity;
import com.doublekit.pipeline.instance.model.*;
import com.doublekit.rpc.annotation.Exporter;
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
        List<PipelineExecLog> allLog = findAllLog();
        if (allLog != null){
            for (PipelineExecLog pipelineExecLog : allLog) {
                if (pipelineExecLog.getHistoryId().equals(historyId)){
                    deleteLog(pipelineExecLog.getPipelineLogId());
                }
            }
        }
    }

    //更新
    @Override
    public void updateLog(PipelineExecLog pipelineExecLog) {
        pipelineExecLogDao.updateLog(BeanMapper.map(pipelineExecLog, PipelineExecLogEntity.class));
    }

    @Override
    public List<PipelineExecLog> findAllLog(String historyId){
        List<PipelineExecLog> allLog = findAllLog();
        List<PipelineExecLog> pipelineExecLogList = new ArrayList<>();
        if (allLog != null){
            for (PipelineExecLog pipelineExecLog : allLog) {
                if (pipelineExecLog.getHistoryId().equals(historyId)){
                    pipelineExecLogList.add(pipelineExecLog);

                }
            }
            if (pipelineExecLogList.size() != 0){
                pipelineExecLogList.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
            }
            return pipelineExecLogList;
        }
    return null;
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
