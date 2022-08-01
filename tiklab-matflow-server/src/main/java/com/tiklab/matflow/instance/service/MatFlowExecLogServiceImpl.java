package com.tiklab.matflow.instance.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.instance.dao.MatFlowExecLogDao;
import com.tiklab.matflow.instance.entity.MatFlowExecLogEntity;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class MatFlowExecLogServiceImpl implements MatFlowExecLogService {

    @Autowired
    MatFlowExecLogDao matFlowExecLogDao;

    //创建
    @Override
    public String createLog(MatFlowExecLog matFlowExecLog) {
        return matFlowExecLogDao.createLog(BeanMapper.map(matFlowExecLog, MatFlowExecLogEntity.class));
    }


    //删除
    public void deleteLog(String logId) {
        matFlowExecLogDao.deleteLog(logId);
    }

    @Override
    public void deleteHistoryLog(String historyId) {
        matFlowExecLogDao.deleteAllLog(historyId);
    }

    //更新
    @Override
    public void updateLog(MatFlowExecLog matFlowExecLog) {
        matFlowExecLogDao.updateLog(BeanMapper.map(matFlowExecLog, MatFlowExecLogEntity.class));
    }

    @Override
    public List<MatFlowExecLog> findAllLog(String historyId){
        List<MatFlowExecLogEntity> matFlowExecLogList = matFlowExecLogDao.findAllLog(historyId);
        List<MatFlowExecLog> list = BeanMapper.mapList(matFlowExecLogList, MatFlowExecLog.class);
        if (list == null){
           return null;
        }
        for (MatFlowExecLog matFlowExecLog : list) {
            matFlowExecLog.setExecTime(formatDateTime(matFlowExecLog.getRunTime()));
        }
        list.sort(Comparator.comparing(MatFlowExecLog::getTaskSort));
        return list;
    }
    //查询所有
    @Override
    public List<MatFlowExecLog> findAllLog() {
        List<MatFlowExecLogEntity> allLog = matFlowExecLogDao.findAllLog();
        return BeanMapper.mapList(allLog, MatFlowExecLog.class);
    }


    @Override
    public List<MatFlowExecLog> findAllLogList(List<String> idList) {
        List<MatFlowExecLogEntity> matFlowLogList = matFlowExecLogDao.findAllLogList(idList);
        return BeanMapper.mapList(matFlowLogList, MatFlowExecLog.class);
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
