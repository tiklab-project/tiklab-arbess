package net.tiklab.matflow.execute.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.execute.dao.MatFlowExecHistoryDao;
import net.tiklab.matflow.execute.entity.MatFlowExecHistoryEntity;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.model.MatFlowHistoryQuery;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class MatFlowExecHistoryServiceImpl implements MatFlowExecHistoryService {

    @Autowired
    MatFlowExecHistoryDao matFlowExecHistoryDao;

    @Autowired
    MatFlowExecLogService matFlowExecLogService;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowExecHistoryServiceImpl.class);

    //创建
    @Override
    public String createHistory(MatFlowExecHistory matFlowExecHistory) {

        return matFlowExecHistoryDao.createHistory(BeanMapper.map(matFlowExecHistory, MatFlowExecHistoryEntity.class));
    }

    //删除
    @Override
    public void deleteHistory(String historyId) {
        MatFlowExecHistory oneHistory = findOneHistory(historyId);
        if (oneHistory == null){
            return;
        }
        matFlowExecLogService.deleteHistoryLog(oneHistory.getHistoryId());
        matFlowExecHistoryDao.deleteHistory(historyId);
    }

    @Override
    public String createLog(MatFlowExecLog matFlowExecLog){
       return matFlowExecLogService.createLog(matFlowExecLog);
   }

    //修改
    @Override
    public void updateHistory(MatFlowExecHistory matFlowExecHistory) {
        matFlowExecHistoryDao.updateHistory(BeanMapper.map(matFlowExecHistory, MatFlowExecHistoryEntity.class));
    }

    //查询单个
    @Override
    public MatFlowExecHistory findOneHistory(String historyId) {
        MatFlowExecHistoryEntity matFlowExecHistoryEntity = matFlowExecHistoryDao.findOneHistory(historyId);
        return BeanMapper.map(matFlowExecHistoryEntity, MatFlowExecHistory.class);
    }

    //查询所有
    @Override
    public List<MatFlowExecHistory> findAllHistory() {
        List<MatFlowExecHistoryEntity> matFlowExecHistoryEntityList = matFlowExecHistoryDao.findAllHistory();
        return BeanMapper.mapList(matFlowExecHistoryEntityList, MatFlowExecHistory.class);
    }

    //根据流水线id查询所有历史
    @Override
    public List<MatFlowExecHistory> findAllHistory(String matFlowId) {
        //List<MatFlowExecHistory> allHistory = findAllHistory();
        List<MatFlowExecHistoryEntity> list = matFlowExecHistoryDao.findAllHistory(matFlowId);
        if (list == null){
            return null;
        }
        List<MatFlowExecHistory> allHistory = BeanMapper.mapList(list, MatFlowExecHistory.class);
        for (MatFlowExecHistory matFlowExecHistory : allHistory) {
            matFlowExecHistory.setExecTime(formatDateTime(matFlowExecHistory.getRunTime()));
        }
        allHistory.sort(Comparator.comparing(MatFlowExecHistory::getCreateTime,Comparator.reverseOrder()));
        return allHistory;
    }

    //查询用户所有历史
    @Override
    public List<MatFlowExecHistory> findAllUserHistory(String lastTime, String nowTime, StringBuilder s) {
        List<MatFlowExecHistoryEntity> allUserHistory = matFlowExecHistoryDao.findAllUserHistory(lastTime,nowTime,s);
        List<MatFlowExecHistory> matFlowExecHistories = BeanMapper.mapList(allUserHistory, MatFlowExecHistory.class);
        joinTemplate.joinQuery(matFlowExecHistories);
        return matFlowExecHistories;
    }


    //查询最近一次执行历史
    @Override
    public MatFlowExecHistory findLatelyHistory(String matFlowId){
        List<MatFlowExecHistoryEntity> latelySuccess = matFlowExecHistoryDao.findLatelyHistory(matFlowId);
        List<MatFlowExecHistory> matFlowExecHistories = BeanMapper.mapList(latelySuccess, MatFlowExecHistory.class);
        if (matFlowExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(matFlowExecHistories);
        return matFlowExecHistories.get(0);
    }

    //查询最近一次成功记录
    @Override
    public MatFlowExecHistory findLatelySuccess(String matFlowId){
        List<MatFlowExecHistoryEntity> latelySuccess = matFlowExecHistoryDao.findLatelySuccess(matFlowId);
        List<MatFlowExecHistory> matFlowExecHistories = BeanMapper.mapList(latelySuccess, MatFlowExecHistory.class);

        if (matFlowExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(matFlowExecHistories);
        return matFlowExecHistories.get(0);
    }

    @Override
    public List<MatFlowExecHistory> findHistoryList(List<String> idList) {
        List<MatFlowExecHistoryEntity> matFlowExecHistoryEntityList = matFlowExecHistoryDao.findHistoryList(idList);
        return BeanMapper.mapList(matFlowExecHistoryEntityList, MatFlowExecHistory.class);
    }

    //获取最后一次的运行日志
    @Override
    public MatFlowExecLog getRunLog(String historyId){
        List<MatFlowExecLog> allLog = matFlowExecLogService.findAllLog(historyId);
        if (allLog == null){
           return null;
        }
        allLog.sort(Comparator.comparing(MatFlowExecLog::getTaskSort));
        MatFlowExecLog matFlowExecLog = allLog.get(allLog.size() - 1);
        for (MatFlowExecLog execLog : allLog) {
            matFlowExecLog.setRunTime(matFlowExecLog.getRunTime()+execLog.getRunTime());
        }
        return allLog.get(allLog.size()-1);
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

    @Override
    public Pagination<MatFlowExecHistory> findPageHistory(MatFlowHistoryQuery matFlowHistoryQuery){
        if (matFlowHistoryQuery.getMatflowId() == null){
            return null;
        }
        Pagination<MatFlowExecHistoryEntity> pagination = matFlowExecHistoryDao.findPageHistory(matFlowHistoryQuery);
        List<MatFlowExecHistory> matFlowExecHistories = BeanMapper.mapList(pagination.getDataList(), MatFlowExecHistory.class);
        if (matFlowExecHistories == null){
            return null;
        }
        matFlowExecHistories.removeIf(matFlowExecHistory -> matFlowExecHistory.getFindState() == 0);
        joinTemplate.joinQuery(matFlowExecHistories);
        return PaginationBuilder.build(pagination,matFlowExecHistories);
    }

}
