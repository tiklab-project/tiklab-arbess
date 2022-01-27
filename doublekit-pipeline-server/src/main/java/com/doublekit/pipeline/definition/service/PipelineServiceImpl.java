package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
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

    @Autowired
    JoinTemplate joinTemplate;

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
    public void updatePipeline(Pipeline pipeline) {
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);

        pipelineDao.updatePipeline(pipelineEntity);
    }

    @Override
    public Pipeline selectPipeline(String id) {

        PipelineEntity pipelineEntity = pipelineDao.selectPipeline(id);

        Pipeline pipeline = BeanMapper.map(pipelineEntity, Pipeline.class);

        joinTemplate.joinQuery(pipeline);

        return pipeline;
    }


    @Override
    public List<Pipeline> selectAllPipeline() {
        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipeline();

        return BeanMapper.mapList(pipelineEntityList,Pipeline.class);
    }
}
