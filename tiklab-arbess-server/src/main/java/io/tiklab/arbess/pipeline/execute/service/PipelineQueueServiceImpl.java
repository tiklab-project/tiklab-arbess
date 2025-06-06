package io.tiklab.arbess.pipeline.execute.service;

import io.tiklab.arbess.pipeline.execute.dao.PipelineQueueDao;
import io.tiklab.arbess.pipeline.execute.entity.PipelineQueueEntity;
import io.tiklab.arbess.pipeline.execute.model.PipelineQueue;
import io.tiklab.arbess.pipeline.execute.model.PipelineQueueQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PipelineQueueServiceImpl implements PipelineQueueService {

    @Autowired
    PipelineQueueDao pipelineQueueDao;

    @Override
    public String createPipelineQueue(PipelineQueue pipelineQueue) {
        PipelineQueueEntity pipelineQueueEntity = BeanMapper.map(pipelineQueue, PipelineQueueEntity.class);
        if (StringUtils.isEmpty(pipelineQueueEntity.getCreateTime())){
            pipelineQueueEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        return pipelineQueueDao.createPipelineQueue(pipelineQueueEntity);
    }

    @Override
    public void updatePipelineQueue(PipelineQueue pipelineQueue) {
        PipelineQueueEntity pipelineQueueEntity = BeanMapper.map(pipelineQueue, PipelineQueueEntity.class);
        pipelineQueueDao.updatePipelineQueue(pipelineQueueEntity);
    }

    @Override
    public void deletePipelineQueue(String id) {
        pipelineQueueDao.deletePipelineQueue(id);
    }

    @Override
    public PipelineQueue findPipelineQueue(String id) {
        PipelineQueueEntity pipelineQueueEntity = pipelineQueueDao.findPipelineQueue(id);
        return BeanMapper.map(pipelineQueueEntity, PipelineQueue.class);
    }


    @Override
    public PipelineQueue findExecPipelineQueue(PipelineQueueQuery pipelineQueueQuery) {

        List<PipelineQueue> pipelineQueueList = findPipelineQueueList(pipelineQueueQuery);

        if (pipelineQueueList.isEmpty()){
            return null;
        }
        return pipelineQueueList.get(0);
    }

    @Override
    public List<PipelineQueue> findPipelineQueueList(PipelineQueueQuery pipelineQueueQuery) {
        List<PipelineQueueEntity> pipelineQueueEntityList = pipelineQueueDao.findPipelineQueueList(pipelineQueueQuery);
        if (Objects.isNull(pipelineQueueEntityList) || pipelineQueueEntityList.isEmpty()){
            return new ArrayList<>();
        }
        List<PipelineQueue> pipelineQueueList = BeanMapper.mapList(pipelineQueueEntityList, PipelineQueue.class);
        return pipelineQueueList;
    }

    @Override
    public List<PipelineQueue> findAllPipelineQueue() {
        List<PipelineQueueEntity> pipelineQueueEntityList = pipelineQueueDao.findPipelineQueueList();
        if (Objects.isNull(pipelineQueueEntityList) || pipelineQueueEntityList.isEmpty()){
            return new ArrayList<>();
        }
        List<PipelineQueue> pipelineQueueList = BeanMapper.mapList(pipelineQueueEntityList, PipelineQueue.class);
        return pipelineQueueList;
    }

    @Override
    public Pagination<PipelineQueue> findPipelineQueuePage(PipelineQueueQuery pipelineQueueQuery) {
        Pagination<PipelineQueueEntity> pipelineQueueEntityPagination = pipelineQueueDao.findPipelineQueuePage(pipelineQueueQuery);
        List<PipelineQueueEntity> dataList = pipelineQueueEntityPagination.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(pipelineQueueEntityPagination,new ArrayList<>());
        }
        List<PipelineQueue> pipelineQueueList = BeanMapper.mapList(dataList, PipelineQueue.class);
        return PaginationBuilder.build(pipelineQueueEntityPagination,pipelineQueueList);
    }
}
