package io.tiklab.arbess.pipeline.definition.service;

import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.arbess.pipeline.definition.dao.PipelineFollowDao;
import io.tiklab.arbess.pipeline.definition.entity.PipelineFollowEntity;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineFollow;
import io.tiklab.arbess.pipeline.definition.model.PipelineFollowQuery;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 收藏信息服务
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
        String userId = LoginContext.getLoginId();
        List<PipelineFollowEntity> list =
                pipelineFollowDao.findOneUserFollowPipeline(userId, pipelineId);
        //用户为收藏该流水线
        if (list.isEmpty()){
            PipelineFollowEntity followEntity = BeanMapper.map(pipelineFollow, PipelineFollowEntity.class);
            followEntity.setUserId(userId);
            String follow = pipelineFollowDao.createFollow(followEntity);
            if (!PipelineUtil.isNoNull(follow)){
                throw new ApplicationException(50001,"收藏失败");
            }
        //用户已收藏该流水线
        }else {
            deleteFollow(list.get(0).getId());
        }
    }

    @Override
    public List<PipelineFollow> findUserFollowPipeline(String userId){
        List<PipelineFollowEntity> list =
                pipelineFollowDao.findUserFollowPipeline(userId);
        return BeanMapper.mapList(list, PipelineFollow.class);
    }

    @Override
    public List<PipelineFollow> findFollowQueryList(PipelineFollowQuery followQuery){
        List<PipelineFollowEntity> list = pipelineFollowDao.findFollowQueryList(followQuery);
        if (list == null || list.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(list, PipelineFollow.class);
    }

    @Override
    public void deleteFollow(String followId) {
        pipelineFollowDao.deleteFollow(followId);
    }


    @Override
    public void deletePipelineFollow(String pipelineId){
        PipelineFollowQuery followQuery = new PipelineFollowQuery();
        followQuery.setPipelineId(pipelineId);
        List<PipelineFollowEntity> list = pipelineFollowDao.findFollowQueryList(followQuery);

        for (PipelineFollowEntity pipelineFollowEntity : list) {
            deleteFollow(pipelineFollowEntity.getId());
        }

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
