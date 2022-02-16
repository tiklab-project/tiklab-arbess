package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineQuery;
import com.doublekit.pipeline.implement.dao.PipelineLogDao;
import com.doublekit.pipeline.implement.entity.PipelineLogEntity;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    PipelineLogDao pipelineLogDao;

    @Autowired
    JoinTemplate joinTemplate;

    //创建
    @Override
    public String createPipeline(Pipeline pipeline) {
        //把模型转化成对应实体
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);

        List<Pipeline> pipelineList = selectAllPipeline();

        for (Pipeline pipeline1 : pipelineList) {

            if (pipeline1.getPipelineName().equals(pipeline.getPipelineName())){

                return null;
            }
        }

        return pipelineDao.createPipeline(pipelineEntity);
    }

    //删除
    @Override
    public void deletePipeline(String id) {

        pipelineDao.deletePipeline(id);
    }

    //更新
    @Override
    public String updatePipeline(Pipeline pipeline) {

        String pipelineName = pipeline.getPipelineName();

        List<Pipeline> pipelineList = selectAllPipeline();

        for (Pipeline pipeline1 : pipelineList) {

            if (pipelineName.equals(pipeline1.getPipelineName())){

                return null;

            }
        }
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);

        pipelineDao.updatePipeline(pipelineEntity);

        return pipelineEntity.getPipelineName();
    }

    //查询
    @Override
    public Pipeline selectPipeline(String id) {

        PipelineEntity pipelineEntity = pipelineDao.selectPipeline(id);

        Pipeline pipeline = BeanMapper.map(pipelineEntity, Pipeline.class);

        joinTemplate.joinQuery(pipeline);

        return pipeline;
    }

    //查询所有
    @Override
    public List<Pipeline> selectAllPipeline() {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipeline();

        List<Pipeline> pipelineList = BeanMapper.mapList(pipelineEntityList, Pipeline.class);

        joinTemplate.joinQuery(pipelineList);

        return pipelineList;
    }

    @Override
    public List<Pipeline> selectAllPipelineList(List<String> idList) {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipelineList(idList);

        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    @Override
    public List<Pipeline> selectName(PipelineQuery pipelineQuery) {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectName(pipelineQuery);

        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    //查询所有流水线状态
    @Override
    public List<PipelineStatus> selectAll() {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipeline();

        List<PipelineStatus> pipelineAllList =new ArrayList<>();

        //把数据添加到pipelineListQuery对象中

        for (PipelineEntity pipelineEntity : pipelineEntityList) {

            PipelineStatus pipelineStatus = new PipelineStatus();

            //设置id
            pipelineStatus.setPipelineId(pipelineEntity.getPipelineId());

            //设置名称
            pipelineStatus.setPipelineName(pipelineEntity.getPipelineName());

            //设置上次构建时间
            PipelineLogEntity pipelineLogEntity = pipelineLogDao.selectLastLog(pipelineEntity.getPipelineId());

            if (pipelineLogEntity!= null){

                pipelineStatus.setLastStructureTime(pipelineLogEntity.getLogCreateTime());

            }

            //设置上次成功时间
            PipelineLogEntity logEntity = pipelineLogDao.selectLastSuccess(pipelineEntity.getPipelineId());
            if (logEntity != null){
                pipelineStatus.setLastStructureTime(logEntity.getLogCreateTime());

            }

            //设置状态
            PipelineLogEntity entity = pipelineLogDao.selectLastLog(pipelineEntity.getPipelineId());

            if (entity!= null){

                pipelineStatus.setStructureStatus(entity.getLogRunStatus());
            }


            pipelineAllList.add(pipelineStatus);

        }

        return pipelineAllList;

    }
}
