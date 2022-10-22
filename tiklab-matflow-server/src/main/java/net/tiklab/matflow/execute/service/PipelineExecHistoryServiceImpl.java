package net.tiklab.matflow.execute.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.execute.dao.PipelineExecHistoryDao;
import net.tiklab.matflow.execute.entity.PipelineExecHistoryEntity;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineHistoryQuery;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    JoinTemplate joinTemplate;

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
        //List<PipelineExecHistory> allHistory = findAllHistory();
        List<PipelineExecHistoryEntity> list = pipelineExecHistoryDao.findAllHistory(pipelineId);
        if (list == null){
            return null;
        }
        List<PipelineExecHistory> allHistory = BeanMapper.mapList(list, PipelineExecHistory.class);
        for (PipelineExecHistory pipelineExecHistory : allHistory) {
            pipelineExecHistory.setExecTime(PipelineUntil.formatDateTime(pipelineExecHistory.getRunTime()));
        }
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime,Comparator.reverseOrder()));
        return allHistory;
    }

    //查询用户所有历史
    @Override
    public List<PipelineExecHistory> findAllUserHistory(String lastTime, String nowTime, StringBuilder s) {
        List<PipelineExecHistoryEntity> allUserHistory = pipelineExecHistoryDao.findAllUserHistory(lastTime,nowTime,s);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(allUserHistory, PipelineExecHistory.class);
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories;
    }


    //查询最近一次执行历史
    @Override
    public PipelineExecHistory findLatelyHistory(String pipelineId){
        List<PipelineExecHistoryEntity> latelySuccess = pipelineExecHistoryDao.findLatelyHistory(pipelineId);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecHistory.class);
        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    //查询最近一次成功记录
    @Override
    public PipelineExecHistory findLatelySuccess(String pipelineId){
        List<PipelineExecHistoryEntity> latelySuccess = pipelineExecHistoryDao.findLatelySuccess(pipelineId);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(latelySuccess, PipelineExecHistory.class);

        if (pipelineExecHistories.size() == 0){
            return null;
        }
        joinTemplate.joinQuery(pipelineExecHistories);
        return pipelineExecHistories.get(0);
    }

    @Override
    public List<PipelineExecHistory> findHistoryList(List<String> idList) {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findHistoryList(idList);
        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }

    //获取最后一次的运行日志
    @Override
    public PipelineExecLog getRunLog(String historyId){
        List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
        if (allLog == null || allLog.size() == 0){
           return null;
        }
        allLog.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
        PipelineExecLog pipelineExecLog = allLog.get(allLog.size() - 1);
        for (PipelineExecLog execLog : allLog) {
            pipelineExecLog.setRunTime(pipelineExecLog.getRunTime()+execLog.getRunTime());
        }
        return allLog.get(allLog.size()-1);
    }


    @Override
    public Pagination<PipelineExecHistory> findPageHistory(PipelineHistoryQuery pipelineHistoryQuery){
        if (pipelineHistoryQuery.getPipelineId() == null){
            return null;
        }
        Pagination<PipelineExecHistoryEntity> pagination = pipelineExecHistoryDao.findPageHistory(pipelineHistoryQuery);
        List<PipelineExecHistory> pipelineExecHistories = BeanMapper.mapList(pagination.getDataList(), PipelineExecHistory.class);
        if (pipelineExecHistories == null){
            return null;
        }
        pipelineExecHistories.removeIf(pipelineExecHistory -> pipelineExecHistory.getFindState() == 0);
        joinTemplate.joinQuery(pipelineExecHistories);
        return PaginationBuilder.build(pagination,pipelineExecHistories);
    }

}
