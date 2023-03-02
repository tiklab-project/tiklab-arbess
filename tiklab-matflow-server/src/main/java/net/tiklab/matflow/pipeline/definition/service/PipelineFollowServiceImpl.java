package net.tiklab.matflow.pipeline.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.dao.PipelineFollowDao;
import net.tiklab.matflow.pipeline.definition.entity.PipelineFollowEntity;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineFollow;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String userId = pipelineFollow.getUserId();
        List<PipelineFollow> list =
                pipelineFollowDao.findUserFollowPipeline(userId, pipelineId);
        //用户为收藏该流水线
        if (list.size() == 0){
            PipelineFollowEntity followEntity = BeanMapper.map(pipelineFollow, PipelineFollowEntity.class);
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
    public void deleteFollow(String followId) {
        pipelineFollowDao.deleteFollow(followId);
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
