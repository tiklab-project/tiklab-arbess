package com.tiklab.matflow.instance.service;

import com.doublekit.beans.BeanMapper;
import com.tiklab.matflow.definition.model.Pipeline;
import com.tiklab.matflow.instance.dao.PipelineFollowDao;
import com.tiklab.matflow.instance.entity.PipelineFollowEntity;
import com.tiklab.matflow.instance.model.PipelineFollow;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 收藏信息实现
 */

@Service
@Exporter
public class PipelineFollowServiceImpl implements  PipelineFollowService{

    @Autowired
    PipelineFollowDao pipelineFollowDao;

    @Override
    public String updateFollow(PipelineFollow pipelineFollow) {
        String pipelineId = pipelineFollow.getPipeline().getPipelineId();
        String userId = pipelineFollow.getUserId();
        List<PipelineFollow> list = pipelineFollowDao.updateFollow(userId, pipelineId);
        if (list.size() == 0){
            String follow = pipelineFollowDao.createFollow(BeanMapper.map(pipelineFollow, PipelineFollowEntity.class));
            if (follow == null){
                return null;
            }
        }else {
            deleteFollow(list.get(0).getId());
        }
        return "1";
    }

    @Override
    public void deleteFollow(String followId) {
        pipelineFollowDao.deleteFollow(followId);
    }

    @Override
    public  List<Pipeline> findAllFollow(String userId,StringBuilder s){
        List<Pipeline> pipelineFollow = pipelineFollowDao.findPipelineFollow(userId,s);
        pipelineFollow.forEach(pipeline -> pipeline.setPipelineCollect(1));
        return pipelineFollow;
    }

    @Override
    public List<Pipeline> findUserPipeline(String userId,StringBuilder s){

        List<Pipeline> pipelineFollow = pipelineFollowDao.findPipelineFollow(userId,s);
        List<Pipeline> pipelineNotFollow = pipelineFollowDao.findPipelineNotFollow(userId,s);

        List<Pipeline> pipelineList = new ArrayList<>();

        if (pipelineFollow != null){
            pipelineFollow.forEach(pipeline -> pipeline.setPipelineCollect(1));
            pipelineList.addAll(pipelineFollow);
        }

        if (pipelineNotFollow != null){
            pipelineList.addAll(pipelineNotFollow);
        }
        pipelineList.sort(Comparator.comparing(Pipeline::getPipelineName));
        return pipelineList;
    }

    @Override
    public PipelineFollow findOneFollow(String followId) {
        return BeanMapper.map(pipelineFollowDao.findOneFollow(followId), PipelineFollow.class);
    }

    @Override
    public List<PipelineFollow> findAllFollow() {
        return BeanMapper.mapList(pipelineFollowDao.findAllFollow(), PipelineFollow.class);
    }

    @Override
    public List<PipelineFollow> findAllFollowList(List<String> idList) {
        List<PipelineFollowEntity> followList = pipelineFollowDao.findAllFollowList(idList);
        return BeanMapper.mapList(followList, PipelineFollow.class);
    }
}
