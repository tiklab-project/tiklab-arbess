package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.dao.PipelineExecHistoryDao;
import com.doublekit.pipeline.instance.entity.PipelineExecHistoryEntity;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineHistoryQuery;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineExecHistoryServiceImpl implements PipelineExecHistoryService {

    @Autowired
    PipelineExecHistoryDao pipelineExecHistoryDao;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineService pipelineService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecHistoryServiceImpl.class);

    //创建
    @Override
    public String createHistory(PipelineExecHistory pipelineExecHistory) {
        return pipelineExecHistoryDao.createHistory(BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class));
    }

    //删除
    @Override
    public void deleteHistory(String historyId) {
        PipelineExecHistory oneHistory = findOneHistory(historyId);
        if (oneHistory == null){
            return;
        }
        pipelineExecLogService.deleteHistoryLog(oneHistory.getHistoryId());
        pipelineExecHistoryDao.deleteHistory(historyId);
    }

    @Override
    public String createLog(PipelineExecLog pipelineExecLog){
       return pipelineExecLogService.createLog(pipelineExecLog);
   }

    //修改
    @Override
    public void updateHistory(PipelineExecHistory pipelineExecHistory) {
        pipelineExecHistoryDao.updateHistory(BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class));
    }

    //查询单个
    @Override
    public PipelineExecHistory findOneHistory(String historyId) {
        PipelineExecHistoryEntity pipelineExecHistoryEntity = pipelineExecHistoryDao.findOneHistory(historyId);
        return BeanMapper.map(pipelineExecHistoryEntity, PipelineExecHistory.class);
    }

    //查询所有
    @Override
    public List<PipelineExecHistory> findAllHistory() {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findAllHistory();
        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }

    //根据流水线id查询所有历史
    @Override
    public List<PipelineExecHistory> findAllHistory(String pipelineId) {
        List<PipelineExecHistory> allHistory = findAllHistory();
        List<PipelineExecHistory> historyList = new ArrayList<>();
        if (allHistory != null){
            for (PipelineExecHistory pipelineExecHistory : allHistory) {
                if (pipelineExecHistory.getPipeline() ==null){
                    continue;
                }
                if (pipelineExecHistory.getPipeline().getPipelineId().equals(pipelineId) && pipelineExecHistory.getFindState() == 1){
                    pipelineExecHistory.setExecTime(formatDateTime(pipelineExecHistory.getRunTime()));
                    historyList.add(pipelineExecHistory);
                }
            }
        }
        historyList.sort(Comparator.comparing(PipelineExecHistory::getCreateTime,Comparator.reverseOrder()));
        return historyList;
    }

    //查询最近一次执行历史
    @Override
    public PipelineExecHistory findLatelyHistory(String pipelineId){
        List<PipelineExecHistory> allHistory = findAllHistory(pipelineId);
        //升序排列
        // allHistory.sort(Comparator.comparing(PipelineExecHistory::getHistoryCreateTime));
        //根据时间降序排列
        if (allHistory.size() == 0){
            return null;
        }
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime,Comparator.reverseOrder()));
        return allHistory.get(0);
    }

    //查询最近一次成功记录
    @Override
    public PipelineExecHistory findLatelySuccess(String pipelineId){
        List<PipelineExecHistory> allHistory = findAllHistory(pipelineId);
        if (allHistory != null){
            allHistory.sort(Comparator.comparing(PipelineExecHistory::getRunTime,Comparator.reverseOrder()));
            for (PipelineExecHistory pipelineExecHistory : allHistory) {
                if (pipelineExecHistory.getRunStatus() ==30){
                    return pipelineExecHistory;
                }
            }
        }
        return  null;
    }

    @Override
    public List<PipelineExecHistory> findHistoryList(List<String> idList) {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findHistoryList(idList);
        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }

    //根据条件筛选历史信息
    @Override
    public List<PipelineExecHistory> findLikeHistory(PipelineHistoryQuery pipelineHistoryQuery){
         Pipeline pipeline = pipelineService.findPipeline(pipelineHistoryQuery.getPipelineId());
        if (pipeline == null){
            return null;
        }
        List<PipelineExecHistory> allHistory = findAllHistory(pipelineHistoryQuery.getPipelineId());
        if (allHistory != null){
            allHistory.removeIf(pipelineExecHistory ->
                    pipelineHistoryQuery.getState() != pipelineExecHistory.getRunStatus() && pipelineHistoryQuery.getState() != 0);
            allHistory.removeIf(pipelineExecHistory ->
                    pipelineHistoryQuery.getName() != null && !pipelineHistoryQuery.getName().equals(pipeline.getUser().getName()));
            allHistory.removeIf(pipelineExecHistory ->
                    pipelineHistoryQuery.getType() != pipelineExecHistory.getRunWay() && pipelineHistoryQuery.getType() != 0);
            return allHistory;
        }
        return null;
    }

    //获取最后一次的运行日志
    @Override
    public PipelineExecLog getRunLog(String historyId){
        List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
        if (allLog != null){
            allLog.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
            PipelineExecLog pipelineExecLog = allLog.get(allLog.size() - 1);
            for (PipelineExecLog execLog : allLog) {
                pipelineExecLog.setRunTime(pipelineExecLog.getRunTime()+execLog.getRunTime());
            }
            return allLog.get(allLog.size()-1);
        }
        return null;
    }

    //时间转换成时分秒
    @Override
    public String formatDateTime(long time) {
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
