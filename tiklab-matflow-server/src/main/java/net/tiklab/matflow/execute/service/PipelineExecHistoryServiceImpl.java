package net.tiklab.matflow.execute.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineStages;
import net.tiklab.matflow.definition.service.PipelineStagesServer;
import net.tiklab.matflow.execute.dao.PipelineExecHistoryDao;
import net.tiklab.matflow.execute.entity.PipelineExecHistoryEntity;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineHistoryQuery;
import net.tiklab.matflow.execute.model.PipelineStagesLog;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
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
    PipelineStagesServer stagesServer;

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
    public void deleteAllHistory(String pipelineId) {
        List<PipelineExecHistory> allHistory = findAllHistory();
        if (allHistory == null){
            return;
        }
        for (PipelineExecHistory history : allHistory) {
            Pipeline pipeline = history.getPipeline();
            if (pipeline == null){
                deleteHistory(history.getHistoryId());
            }
            if (history.getPipeline().getPipelineId().equals(pipelineId)){
                deleteHistory(history.getHistoryId());
            }
        }
    }

    /**
     * 删除单个历史以及历史对应日志
     * @param historyId 历史id
     */
    @Override
    public void deleteHistory(String historyId) {
        pipelineExecHistoryDao.deleteHistory(historyId);
        pipelineExecLogService.deleteHistoryLog(historyId);
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
        PipelineExecHistory history = BeanMapper.map(pipelineExecHistoryEntity, PipelineExecHistory.class);
        joinTemplate.joinQuery(history);
        return history;
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
        List<PipelineExecHistoryEntity> list = pipelineExecHistoryDao.findAllHistory(pipelineId);
        if (list == null){
            return null;
        }
        List<PipelineExecHistory> allHistory = BeanMapper.mapList(list, PipelineExecHistory.class);
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime,Comparator.reverseOrder()));
        return allHistory;
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


    /**
     * 查询历史及日志详情
     * @param historyId 历史id
     * @return 日志集合
     */
    public List<Object> findAllLog(String historyId){
        PipelineExecHistory history = findOneHistory(historyId);

        Pipeline pipeline = history.getPipeline();
        String pipelineId = pipeline.getPipelineId();

        int pipelineType = pipeline.getPipelineType();
        List<Object> list = new ArrayList<>();

        //多任务
        if (pipelineType == 1){
            List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
            list.addAll(allLog);
        }

        //多阶段
        if (pipelineType == 2){
            List<PipelineStagesLog> logs = new ArrayList<>();
            List<PipelineStages> allStagesConfig = stagesServer.findAllStagesConfig(pipelineId);

            for (PipelineStages pipelineStages : allStagesConfig) {
                String stagesId = pipelineStages.getStagesId();
                PipelineStagesLog pipelineStagesLog = new PipelineStagesLog();
                List<PipelineExecLog> allStagesLog = pipelineExecLogService.findAllStagesLog(historyId,stagesId);
                if (allStagesLog == null || allStagesLog.size() == 0){
                    continue;
                }
                allStagesLog.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
                pipelineStagesLog.setStages(pipelineStages.getTaskStage());
                pipelineStagesLog.setLogList(allStagesLog);
                pipelineStagesLog.setTaskSort(pipelineStages.getTaskSort());
                logs.add(pipelineStagesLog);
            }

            //添加消息阶段
            PipelineStagesLog pipelineStages = new PipelineStagesLog();
            List<PipelineExecLog> allLog = pipelineExecLogService.findAllLog(historyId);
            allLog.removeIf(pipelineExecLog -> PipelineUntil.isNoNull(pipelineExecLog.getStagesId()));
            pipelineStages.setStages(allStagesConfig.size()+1);
            pipelineStages.setLogList(allLog);

            list.addAll(logs);
            list.add(pipelineStages);
        }

        return list;
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
