package com.doublekit.pipeline.instance.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.dao.PipelineFollowDao;
import com.doublekit.pipeline.instance.entity.PipelineFollowEntity;
import com.doublekit.pipeline.instance.model.PipelineFollow;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineFollowServiceImpl implements  PipelineFollowService{

    @Autowired
    PipelineFollowDao pipelineFollowDao;

    @Override
    public String updateFollow(PipelineFollow pipelineFollow) {
        List<PipelineFollow> allFollow = findAllFollow();
        if (allFollow!= null){
            for (PipelineFollow follow : allFollow) {
                if (pipelineFollow.getUserId().equals(follow.getUserId())
                        && pipelineFollow.getPipeline().getPipelineId().equals(follow.getPipeline().getPipelineId()) ){
                     deleteFollow(follow.getId());
                    return null;
                }
            }
        }
        return pipelineFollowDao.createFollow(BeanMapper.map(pipelineFollow, PipelineFollowEntity.class));
    }

    @Override
    public void deleteFollow(String followId) {
        pipelineFollowDao.deleteFollow(followId);
    }

    @Override
    public List<PipelineStatus> findAllFollow(String userId,List<PipelineStatus> allStatus){
        List<PipelineStatus> list = new ArrayList<>();
        List<PipelineFollow> allFollow = findAll(userId);
        if (allFollow != null){
            if (allStatus !=null){
                for (PipelineStatus status : allStatus) {
                    for (PipelineFollow pipelineFollow : allFollow) {
                        if (pipelineFollow.getPipeline().getPipelineId().equals(status.getPipelineId())){
                            status.setPipelineCollect(1);
                            list.add(status);
                        }
                    }
                }
                return list;
            }
        }
        return null;
    }

    //获取用户所有收藏信息
    public List<PipelineFollow> findAll(String userId){
        List<PipelineFollow> list = new ArrayList<>();
        if (findAllFollow()!=null){
            for (PipelineFollow pipelineFollow : findAllFollow()) {
                if (pipelineFollow.getUserId().equals(userId)){
                    list.add(pipelineFollow);
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public List<PipelineStatus> findUserPipeline(String userId, List<PipelineStatus> allStatus){
        List<PipelineFollow> allFollow = findAll(userId);
        if (allStatus!=null){
            for (PipelineStatus status : allStatus) {
                if (allFollow != null){
                    for (PipelineFollow pipelineFollow : allFollow) {
                        if (pipelineFollow.getPipeline().getPipelineId().equals(status.getPipelineId())){
                            status.setPipelineCollect(1);
                        }
                    }
                }
            }
            return allStatus;
        }
        return null;
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
