package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.dal.jpa.criterial.condition.QueryCondition;
import com.doublekit.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.controller.PipelineController;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PipelineServiceImpl
 */

@Service
@Exporter
public class PipelineServiceImpl implements PipelineService{

    @Autowired
    PipelineDao pipelineDao;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);

    //创建
    @Override
    public Map<String, String> createPipeline(Pipeline pipeline) {

        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        List<Pipeline> pipelineList = findAllPipeline();
        //判断是否存在相同名称
        for (Pipeline pipeline1 : pipelineList) {
            if (pipeline1.getPipelineName().equals(pipeline.getPipelineName())){
                return null;
            }
        }
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        pipeline.setPipelineId(pipelineId);
        pipelineConfigure.setPipeline(pipeline);
        Map<String, String> map = pipelineConfigureService.createConfigure(pipelineConfigure);
        map.put("pipelineId",pipelineId);
        return map;
    }

    //删除
    @Override
    public void deletePipeline(String pipelineId) {
        if (pipelineId != null){
            pipelineDao.deletePipeline(pipelineId);
            //删除对应的流水线配置
            pipelineConfigureService.deletePipelineIdConfigure(pipelineId);
            //删除对应的历史
            pipelineExecHistoryService.deleteHistory(pipelineId);
        }
    }

    //更新
    @Override
    public String updatePipeline(Pipeline pipeline) {
        String pipelineName = pipeline.getPipelineName();
        List<Pipeline> pipelineList = findAllPipeline();
        for (Pipeline pipeline1 : pipelineList) {
        //判断是否有此用户
            if (pipelineName.equals(pipeline1.getPipelineName())){
                return "0";
            }
        }
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        //更新用户信息
        pipelineDao.updatePipeline(pipelineEntity);
        return pipelineEntity.getPipelineName();
    }

    //查询
    @Override
    public Pipeline findPipeline(String id) {
        return BeanMapper.map(pipelineDao.findPipeline(id), Pipeline.class);
    }

    //查询所有
    @Override
    public List<Pipeline> findAllPipeline() {
        return BeanMapper.mapList(pipelineDao.findAllPipeline(), Pipeline.class);
    }

    @Override
    public List<Pipeline> findAllPipelineList(List<String> idList) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findAllPipelineList(idList);
        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    //模糊查询
    @Override
    public List<Pipeline> findLike(String pipelineName) {
        List<PipelineEntity> pipelineEntityList = pipelineDao.findName(pipelineName);
        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    //查询所有流水线状态
    @Override
    public List<PipelineStatus> findAllStatus() {
        List<PipelineStatus> pipelineStatusList= new ArrayList<>();
        List<Pipeline> allPipeline = findAllPipeline();
        for (Pipeline pipeline : allPipeline) {
            PipelineStatus pipelineStatus = new PipelineStatus();
            PipelineExecHistory latelyHistory = pipelineExecHistoryService.findLatelyHistory(pipeline.getPipelineId());
            PipelineExecHistory latelySuccess = pipelineExecHistoryService.findLatelySuccess(pipeline.getPipelineId());
            pipelineStatus.setPipelineId(pipeline.getPipelineId());
            pipelineStatus.setPipelineName(pipeline.getPipelineName());
            if (latelyHistory != null){
                pipelineStatus.setLastStructureTime(latelyHistory.getHistoryCreateTime());
                pipelineStatus.setStructureStatus(latelyHistory.getHistoryStatus());
            }
            if (latelySuccess != null){
                pipelineStatus.setLastSuccessTime(latelySuccess.getHistoryCreateTime());
            }
            pipelineStatusList.add(pipelineStatus);
        }
        return pipelineStatusList;
    }



}
