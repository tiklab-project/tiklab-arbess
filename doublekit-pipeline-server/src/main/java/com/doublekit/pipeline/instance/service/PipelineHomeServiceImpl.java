package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineFollow;
import com.doublekit.pipeline.instance.model.PipelineOpen;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineHomeServiceImpl implements  PipelineHomeService{

    @Autowired
    PipelineOpenService pipelineOpenService;

    @Autowired
    PipelineFollowService pipelineFollowService;

    //查询用户最近打开的流水线
    @Override
    public List<PipelineOpen> findAllOpen(String userId){
        List<PipelineOpen> allOpen = pipelineOpenService.findAllOpen(userId);
        allOpen.sort(Comparator.comparing(PipelineOpen::getNumber,Comparator.reverseOrder()));
        return allOpen;
    }

    //获取收藏列表
    @Override
    public List<PipelineStatus> findAllFollow(String userId){
        List<PipelineStatus> allFollow = pipelineFollowService.findAllFollow(userId);
        if (allFollow != null){
            for (PipelineStatus pipelineStatus : allFollow) {
                pipelineStatus.setPipelineCollect(1);
            }
        }
        return allFollow;
    }

    //更新收藏信息
    @Override
    public void updateFollow(PipelineFollow pipelineFollow){
        pipelineFollowService.updateFollow(pipelineFollow);
    }

    //获取用户流水线
    @Override
    public List<PipelineStatus> findUserPipeline(String userId){
        return pipelineFollowService.findUserPipeline(userId);
    }


}
