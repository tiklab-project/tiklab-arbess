package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.instance.dao.PipelineExecHistoryDao;
import com.doublekit.pipeline.instance.entity.PipelineExecHistoryEntity;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineExecHistoryServiceImpl implements PipelineExecHistoryService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineExecHistoryDao pipelineExecHistoryDao;



    //创建
    @Override
    public String createHistory(PipelineExecHistory pipelineExecHistory) {
        return pipelineExecHistoryDao.createHistory(BeanMapper.map(pipelineExecHistory, PipelineExecHistoryEntity.class));
    }

    //删除
    @Override
    public void deleteHistory(String historyId) {
        pipelineExecHistoryDao.deleteHistory(historyId);
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
        PipelineExecHistory pipelineExecHistory = BeanMapper.map(pipelineExecHistoryEntity, PipelineExecHistory.class);
        joinTemplate.joinQuery(pipelineExecHistory);

        return pipelineExecHistory;
    }

    //查询所有
    @Override
    public List<PipelineExecHistory> findAllHistory() {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findAllHistory();
        List<PipelineExecHistory> pipelineExecHistoryList = BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
        joinTemplate.joinQuery(pipelineExecHistoryList);

        return pipelineExecHistoryList;
    }

    @Override
    public List<PipelineExecHistory> findHistoryList(List<String> idList) {
        List<PipelineExecHistoryEntity> pipelineExecHistoryEntityList = pipelineExecHistoryDao.findHistoryList(idList);
        return BeanMapper.mapList(pipelineExecHistoryEntityList, PipelineExecHistory.class);
    }
}
