package net.tiklab.matflow.pipeline.definition.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.dao.PipelineFollowDao;
import net.tiklab.matflow.pipeline.definition.entity.PipelineFollowEntity;
import net.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineFollow;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏信息实现
 */

@Service
@Exporter
public class PipelineFollowServiceImpl implements PipelineFollowService {

    @Autowired
    PipelineFollowDao pipelineFollowDao;

    @Override
    public void updateFollow(PipelineFollow pipelineFollow) {
        Pipeline pipeline = pipelineFollow.getPipeline();
        String pipelineId = pipeline.getId();
        if (!PipelineUtil.isNoNull(pipelineId)){
           throw new ApplicationException(50001,"流水线id不能为空。");
        }
        String userId = pipelineFollow.getUserId();
        List<PipelineFollow> list = pipelineFollowDao.updateFollow(userId, pipelineId);
        if (list.size() == 0){
            String follow = pipelineFollowDao.createFollow(BeanMapper.map(pipelineFollow, PipelineFollowEntity.class));
            if (follow == null){
                throw new ApplicationException(50001,"收藏失败");
            }
        }else {
            deleteFollow(list.get(0).getId());
        }
    }

    @Override
    public void deleteFollow(String followId) {
        pipelineFollowDao.deleteFollow(followId);
    }

    @Override
    public  List<Pipeline> findAllFollow(String userId, StringBuilder s){
        List<PipelineEntity> pipelineFollow = pipelineFollowDao.findPipelineFollow(userId,s);
        List<Pipeline> list = BeanMapper.mapList(pipelineFollow, Pipeline.class);
        list.forEach(pipeline -> pipeline.setCollect(1));
        return list;
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
