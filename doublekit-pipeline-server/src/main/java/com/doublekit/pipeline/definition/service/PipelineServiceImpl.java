package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
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
    JoinTemplate joinTemplate;

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
            //删除对应的流水线历史
            pipelineExecHistoryService.deleteAllHistory(pipelineId);
            //删除对应的流水线配置
            pipelineConfigureService.deletePipelineIdConfigure(pipelineId);
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

        PipelineEntity pipelineEntity = pipelineDao.findPipeline(id);
        Pipeline pipeline = BeanMapper.map(pipelineEntity, Pipeline.class);
        joinTemplate.joinQuery(pipeline);
        return pipeline;
    }

    //查询所有
    @Override
    public List<Pipeline> findAllPipeline() {

        List<PipelineEntity> pipelineEntityList = pipelineDao.findAllPipeline();
        List<Pipeline> pipelineList = BeanMapper.mapList(pipelineEntityList, Pipeline.class);
        joinTemplate.joinQuery(pipelineList);
        return pipelineList;
    }

    @Override
    public List<Pipeline> findAllPipelineList(List<String> idList) {

        List<PipelineEntity> pipelineEntityList = pipelineDao.findAllPipelineList(idList);
        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    @Override
    public List<Pipeline> findName(String pipelineName) {

        List<PipelineEntity> pipelineEntityList = pipelineDao.findName(pipelineName);
        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    //查询所有流水线状态
    @Override
    public List<PipelineStatus> findAll() {

        List<PipelineEntity> pipelineEntityList = pipelineDao.findAllPipeline();
        List<PipelineStatus> pipelineAllList =new ArrayList<>();

        //把数据添加到pipelineAllList对象中
        for (PipelineEntity pipelineEntity : pipelineEntityList) {
            PipelineStatus pipelineStatus = new PipelineStatus();
            pipelineStatus.setPipelineId(pipelineEntity.getPipelineId());
            pipelineStatus.setPipelineName(pipelineEntity.getPipelineName());
            PipelineExecHistory pipelineExecHistory = pipelineExecHistoryService.findLastPipelineHistory(pipelineEntity.getPipelineId());
            if (pipelineExecHistory != null){
                pipelineStatus.setLastStructureTime(pipelineExecHistory.getHistoryCreateTime());
            }

            //获取同一id下的所有历史记录
            List<PipelineExecHistory> pipelineExecHistoryList = pipelineExecHistoryService.findAllPipelineIdList(pipelineEntity.getPipelineId());
            if (pipelineExecHistoryList != null){
                for (int i = pipelineExecHistoryList.size() - 1; i >= 0; i--) {
                    if (pipelineExecHistoryList.get(i).getPipelineExecLog().getLogRunStatus() == 30){
                        //获取上次成功时间
                        pipelineStatus.setLastSuccessTime(pipelineExecHistoryList.get(i).getHistoryCreateTime());
                    }
                    pipelineStatus.setStructureStatus(pipelineExecHistoryList.get(i).getPipelineExecLog().getLogRunStatus());
                }
            }
            pipelineAllList.add(pipelineStatus);
        }

        return pipelineAllList;

    }
}
