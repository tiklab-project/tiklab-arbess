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
        if (allLog == null){
            return;
        }
        for (PipelineExecLog pipelineExecLog : allLog) {
            if (!pipelineExecLog.getHistoryId().equals(historyId)){
                continue;
            }
            deleteLog(pipelineExecLog.getPipelineLogId());
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
        if (allLog == null){
           return null;
        }
        for (PipelineExecLog pipelineExecLog : allLog) {
            if (!pipelineExecLog.getHistoryId().equals(historyId)){
                continue;
            }
            pipelineExecLog.setExecTime(formatDateTime(pipelineExecLog.getRunTime()));
            pipelineExecLogList.add(pipelineExecLog);
        }
        if (pipelineExecLogList.size() == 0){
           return null;
        }
        pipelineExecLogList.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
        return pipelineExecLogList;
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


    public static String formatDateTime(long time) {
        String DateTimes ;
        long days = time / ( 60 * 60 * 24);
        long hours = (time % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (time % ( 60 * 60)) /60;
        long seconds = time % 60;
        if(days>0){
            DateTimes= days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(hours>0){
            DateTimes=hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(minutes>0){
            DateTimes=minutes + "分钟" + seconds + "秒";
        }else{
            DateTimes=seconds + "秒";
        }
        return DateTimes;
    }

}
