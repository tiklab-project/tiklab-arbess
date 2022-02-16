package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineQuery;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PipelineServiceImpl
 */
@Service
@Exporter
public class PipelineServiceImpl implements PipelineService{

    @Autowired
    PipelineDao pipelineDao;

    @Override
    public String createPipeline(Pipeline pipeline) {
        //把模型转化成对应实体
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);

        return pipelineDao.createPipeline(pipelineEntity);
    }

    @Override
    public void deletePipeline(String id) {
        pipelineDao.deletePipeline(id);
    }

    @Override
    public String updatePipeline(Pipeline pipeline) {
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);

        pipelineDao.updatePipeline(pipelineEntity);

        return pipelineEntity.getPipelineName();
    }

    // public Pipeline selectAllPipeline(String id) {
    //     PipelineEntity pipelineEntity = pipelineDao.selectAllPipeline();
    //     return BeanMapper.map(pipelineEntity, Pipeline.class);
    // }

    @Override
    public List<Pipeline> selectAllPipeline() {
        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipeline();

        return BeanMapper.mapList(pipelineEntityList,Pipeline.class);
    }

    @Override
    public Pipeline selectPipeline(String id) {
        return null;
    }

    @Override
    public List<Pipeline> selectAllPipelineList(List<String> idList) {
        return null;
    }

    @Override
    public List<Pipeline> selectName(PipelineQuery pipelineQuery) {
        return null;
    }

    @Override
    public List<PipelineStatus> selectAll() {
        return null;
    }

}
