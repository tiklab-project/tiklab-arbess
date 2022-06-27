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
        List<PipelineFollow> allFollow = findAllFollow();
        if (allFollow == null){
            return null;
        }
        for (PipelineFollow follow : allFollow) {
            //判断是否存在收藏，存在删除改为取消
            if (pipelineFollow.getUserId().equals(follow.getUserId())
                    && pipelineFollow.getPipeline().getPipelineId().equals(follow.getPipeline().getPipelineId()) ){
                deleteFollow(follow.getId());
                return "1";
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
        if (allFollow == null){
            return null;
        }
        if (allStatus == null){
            return list;
        }
        for (PipelineStatus status : allStatus) {
            List<PipelineFollow> lists = allFollow.stream().filter(pipelineFollow -> status.getPipelineId().equals(pipelineFollow.getPipeline().getPipelineId())).toList();
            if (lists.size() == 0){
                continue;
            }
            status.setPipelineCollect(1);
            list.add(status);
            }
        return list;
    }

    //获取用户所有收藏信息
    public List<PipelineFollow> findAll(String userId){
        if (findAllFollow() == null){
            return null;
        }
        return findAllFollow().stream().filter(pipelineFollow -> userId.equals(pipelineFollow.getUserId())).toList();
    }

    @Override
    public List<PipelineStatus> findUserPipeline(String userId, List<PipelineStatus> allStatus){
        List<PipelineFollow> allFollow = findAll(userId);
        if (allStatus == null || allFollow == null){
            return null;
        }
        for (PipelineStatus status : allStatus) {
            List<PipelineFollow> list = allFollow.stream().filter(pipelineFollow -> status.getPipelineId().equals(pipelineFollow.getPipeline().getPipelineId())).toList();
            if (list.size() == 0){
                continue;
            }
            status.setPipelineCollect(1);
        }
        return allStatus;
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
