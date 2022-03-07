package com.doublekit.pipeline.definition.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.service.PipelineHistoryService;
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
    PipelineHistoryService pipelineHistoryService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    //创建
    @Override
    public String createPipeline(Pipeline pipeline) {
        //把模型转化成对应实体
        PipelineEntity pipelineEntity = BeanMapper.map(pipeline, PipelineEntity.class);

        List<Pipeline> pipelineList = selectAllPipeline();

        //判断是否存在相同名称
        for (Pipeline pipeline1 : pipelineList) {

            if (pipeline1.getPipelineName().equals(pipeline.getPipelineName())){

                return "0";
            }
        }

        return pipelineDao.createPipeline(pipelineEntity);
    }

    //删除
    @Override
    public void deletePipeline(String pipelineId) {

         pipelineDao.deletePipeline(pipelineId);

        if (pipelineId != null){

            //删除对应的流水线历史
            pipelineHistoryService.deleteAllPipelineHistory(pipelineId);

            //删除对应的流水线配置
            pipelineConfigureService.deletePipelineConfigure(pipelineId);

        }
    }

    //更新
    @Override
    public String updatePipeline(Pipeline pipeline) {

        String pipelineName = pipeline.getPipelineName();

        List<Pipeline> pipelineList = selectAllPipeline();

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
    public Pipeline selectPipeline(String id) {

        PipelineEntity pipelineEntity = pipelineDao.selectPipeline(id);

        // joinTemplate.joinQuery(pipeline);

        return BeanMapper.map(pipelineEntity, Pipeline.class);
    }

    //查询所有
    @Override
    public List<Pipeline> selectAllPipeline() {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipeline();

       // joinTemplate.joinQuery(pipelineList);

        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    @Override
    public List<Pipeline> selectAllPipelineList(List<String> idList) {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipelineList(idList);

        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    @Override
    public List<Pipeline> selectName(String pipelineName) {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectName(pipelineName);

        return BeanMapper.mapList(pipelineEntityList, Pipeline.class);
    }

    //查询所有流水线状态
    @Override
    public List<PipelineStatus> selectAll() {

        List<PipelineEntity> pipelineEntityList = pipelineDao.selectAllPipeline();

        List<PipelineStatus> pipelineAllList =new ArrayList<>();

        //把数据添加到pipelineAllList对象中

        for (PipelineEntity pipelineEntity : pipelineEntityList) {

            PipelineStatus pipelineStatus = new PipelineStatus();

            //获取id
            pipelineStatus.setPipelineId(pipelineEntity.getPipelineId());

            //获取名称
            pipelineStatus.setPipelineName(pipelineEntity.getPipelineName());

            //获取最近一次的构建
            PipelineHistory pipelineHistory = pipelineHistoryService.selectLastPipelineHistory(pipelineEntity.getPipelineId());

            if (pipelineHistory != null){
                //获取上次构建时间
                pipelineStatus.setLastStructureTime(pipelineHistory.getHistoryCreateTime());

            }
            //获取同一id下的所有历史记录
            List<PipelineHistory> pipelineHistoryList = pipelineHistoryService.selectAllPipelineIdList(pipelineEntity.getPipelineId());

            if (pipelineHistoryList != null){

                for (int i = pipelineHistoryList.size() - 1; i >= 0; i--) {

                    if (pipelineHistoryList.get(i).getPipelineLog().getLogRunStatus() == 30){
                        //获取上次成功时间
                        pipelineStatus.setLastSuccessTime(pipelineHistoryList.get(i).getHistoryCreateTime());
                    }
                    //获取状态
                    pipelineStatus.setStructureStatus(pipelineHistoryList.get(i).getPipelineLog().getLogRunStatus());
                }
            }
            pipelineAllList.add(pipelineStatus);
        }

        return pipelineAllList;

    }
}
