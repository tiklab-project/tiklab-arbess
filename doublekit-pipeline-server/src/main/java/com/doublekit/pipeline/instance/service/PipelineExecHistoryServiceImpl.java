package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.service.PipelineConfigureServiceImpl;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.instance.dao.PipelineExecHistoryDao;
import com.doublekit.pipeline.instance.entity.PipelineExecHistoryEntity;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.rpc.annotation.Exporter;
import com.sun.xml.bind.v2.model.core.ID;
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
    PipelineService pipelineService;

    @Autowired
    PipelineExecHistoryDao pipelineExecHistoryDao;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecHistoryServiceImpl.class);

    //创建
    @Override
    public String createHistory(PipelineExecHistory pipelineExecHistory) {
        return pipelineExecHistoryDao.createHistory(BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class));
    }

    //删除
    @Override
    public void deleteHistory(int historyId) {
        PipelineExecHistory oneHistory = findOneHistory(historyId);
        if (oneHistory.getLogId() != null){
            pipelineExecLogService.deleteLog(oneHistory.getLogId());
        }
        pipelineExecHistoryDao.deleteHistory(historyId);
    }

    //删除
    @Override
    public void deleteHistory(String pipelineId) {
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        String pipelineName = pipeline.getPipelineName();
        List<PipelineExecHistory> allHistory = findAllHistory();
        if (allHistory != null){
            for (PipelineExecHistory pipelineExecHistory : allHistory) {
                if (pipelineName.equals(pipelineExecHistory.getPipelineName())){
                    deleteHistory(pipelineExecHistory.getLogId());
                }
            }
        }
    }

    //修改
    @Override
    public void updateHistory(PipelineExecHistory pipelineExecHistory) {
        pipelineExecHistoryDao.updateHistory(BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class));
    }

    //查询单个
    @Override
    public PipelineExecHistory findOneHistory(int historyId) {
        PipelineExecHistoryEntity pipelineExecHistoryEntity = pipelineExecHistoryDao.findOneHistory(historyId);
        // joinTemplate.joinQuery(pipelineExecHistory);
        return BeanMapper.map(pipelineExecHistoryEntity, PipelineExecHistory.class);
    }

    //查询所有
    @Override
    public List<PipelineExecHistory> findAllHistory() {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findAllHistory();
        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }

    //根据流水线id查询所有
    @Override
    public List<PipelineExecHistory> findAllHistory(String pipelineId) {
        List<PipelineExecHistory> allHistory = findAllHistory();
        List<PipelineExecHistory> historyList = new ArrayList<>();
        if (allHistory != null){
            for (PipelineExecHistory pipelineExecHistory : allHistory) {
                if (pipelineExecHistory.getPipelineId().equals(pipelineId)){
                    historyList.add(pipelineExecHistory);
                }
            }
        }
        return historyList;
    }

    //查询最近一次历史
    @Override
    public PipelineExecHistory findLatelyHistory(String pipelineId){
        List<PipelineExecHistory> allHistory = findAllHistory(pipelineId);
        // allHistory.sort(Comparator.comparing(PipelineExecHistory::getHistoryCreateTime)); 升序排列
        //根据时间降序排列
        if (allHistory.size() == 0){
            return null;
        }
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getHistoryCreateTime,Comparator.reverseOrder()));
        return allHistory.get(0);
    }

    //查询最近一次成功
    @Override
    public PipelineExecHistory findLatelySuccess(String pipelineId){
        List<PipelineExecHistory> allHistory = findAllHistory(pipelineId);
        if (allHistory != null){
            allHistory.sort(Comparator.comparing(PipelineExecHistory::getHistoryCreateTime,Comparator.reverseOrder()));
            for (PipelineExecHistory pipelineExecHistory : allHistory) {
                if (pipelineExecHistory.getHistoryStatus() ==30){
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
}