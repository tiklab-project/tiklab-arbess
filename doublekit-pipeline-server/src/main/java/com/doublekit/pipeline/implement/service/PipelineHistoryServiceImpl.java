package com.doublekit.pipeline.implement.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.implement.dao.PipelineHistoryDao;
import com.doublekit.pipeline.implement.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.implement.model.PipelineHistory;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineHistoryServiceImpl implements PipelineHistoryService{

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineHistoryDao pipelineHistoryDao;


    //创建
    @Override
    public String createPipelineHistory(PipelineHistory pipelineHistory) {

        PipelineHistoryEntity pipelineHistoryEntity = BeanMapper.map(pipelineHistory, PipelineHistoryEntity.class);

        return pipelineHistoryDao.createPipelineHistory(pipelineHistoryEntity);
    }

    //删除
    @Override
    public void deletePipelineHistory(String id) {

        pipelineHistoryDao.deletePipelineHistory(id);

    }

    //修改
    @Override
    public void updatePipelineHistory(PipelineHistory pipelineHistory) {

        PipelineHistoryEntity pipelineHistoryEntity = BeanMapper.map(pipelineHistory, PipelineHistoryEntity.class);

        pipelineHistoryDao.updatePipelineHistory(pipelineHistoryEntity);

    }

    //查询单个
    @Override
    public PipelineHistory selectPipelineHistory(String id) {

        PipelineHistoryEntity pipelineHistoryEntity = pipelineHistoryDao.selectPipelineHistory(id);

        PipelineHistory pipelineHistory = BeanMapper.map(pipelineHistoryEntity, PipelineHistory.class);

        joinTemplate.joinQuery(pipelineHistory);

        return pipelineHistory;
    }

    //查询所有
    @Override
    public List<PipelineHistory> selectAllPipelineHistory() {

        List<PipelineHistoryEntity> pipelineHistoryEntityList = pipelineHistoryDao.selectAllPipelineHistory();

        List<PipelineHistory> pipelineHistoryList = BeanMapper.mapList(pipelineHistoryEntityList, PipelineHistory.class);

        joinTemplate.joinQuery(pipelineHistoryList);

        return pipelineHistoryList;
    }

    @Override
    public List<PipelineHistory> selectPipelineHistoryList(List<String> idList) {

        List<PipelineHistoryEntity> pipelineHistoryEntityList = pipelineHistoryDao.selectPipelineHistoryList(idList);

        return BeanMapper.mapList(pipelineHistoryEntityList, PipelineHistory.class);
    }
}
