package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.Configure;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.History;
import com.doublekit.pipeline.instance.service.HistoryService;
import com.doublekit.rpc.annotation.Exporter;
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
    HistoryService historyService;

    @Autowired
    ConfigureService configureService;

    //创建
    @Override
    public Map<String, String> createPipeline(Pipeline pipeline) {

        Map<String, String> map = new HashMap<>();
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);
        Configure configure = new Configure();
        List<Pipeline> pipelineList = findAllPipeline();
        //判断是否存在相同名称
        for (Pipeline pipeline1 : pipelineList) {
            if (pipeline1.getPipelineName().equals(pipeline.getPipelineName())){
                return null;
            }
        }
        String pipelineId = pipelineDao.createPipeline(pipelineEntity);
        pipeline.setPipelineId(pipelineId);
        configure.setPipeline(pipeline);
        String pipelineConfigureId = configureService.createConfigure(configure);
        map.put("pipelineId",pipelineId);
        map.put("pipelineConfigureId",pipelineConfigureId);

        return map;
    }

    //删除
    @Override
    public void deletePipeline(String pipelineId) {
        if (pipelineId != null){
            pipelineDao.deletePipeline(pipelineId);
            //删除对应的流水线历史
            historyService.deleteAllHistory(pipelineId);
            //删除对应的流水线配置
            configureService.deleteConfig(pipelineId);
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
            History history = historyService.findLastPipelineHistory(pipelineEntity.getPipelineId());
            if (history != null){
                pipelineStatus.setLastStructureTime(history.getHistoryCreateTime());
            }

            //获取同一id下的所有历史记录
            List<History> historyList = historyService.findAllPipelineIdList(pipelineEntity.getPipelineId());
            if (historyList != null){
                for (int i = historyList.size() - 1; i >= 0; i--) {
                    if (historyList.get(i).getPipelineLog().getLogRunStatus() == 30){
                        //获取上次成功时间
                        pipelineStatus.setLastSuccessTime(historyList.get(i).getHistoryCreateTime());
                    }
                    pipelineStatus.setStructureStatus(historyList.get(i).getPipelineLog().getLogRunStatus());
                }
            }
            pipelineAllList.add(pipelineStatus);
        }

        return pipelineAllList;

    }
}
