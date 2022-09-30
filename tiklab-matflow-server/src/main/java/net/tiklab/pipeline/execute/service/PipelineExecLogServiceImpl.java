package net.tiklab.pipeline.execute.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.pipeline.execute.dao.PipelineExecLogDao;
import net.tiklab.pipeline.execute.entity.PipelineExecLogEntity;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
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
            pipelineExecLog.setExecTime(formatDateTime(pipelineExecLog.getRunTime()));
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

    public  String formatDateTime(long time) {
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
