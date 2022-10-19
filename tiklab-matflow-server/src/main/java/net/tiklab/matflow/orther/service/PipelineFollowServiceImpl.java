package net.tiklab.matflow.orther.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.entity.PipelineEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.orther.dao.PipelineFollowDao;
import net.tiklab.matflow.orther.entity.PipelineFollowEntity;
import net.tiklab.matflow.orther.model.PipelineFollow;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

/**
 * 收藏信息实现
 */

@Service
@Exporter
public class PipelineFollowServiceImpl implements PipelineFollowService {

    @Autowired
    PipelineFollowDao pipelineFollowDao;

    @Autowired
    PipelineActivityService pipelineActivityService;

    @Override
    public String updateFollow(PipelineFollow pipelineFollow) {
        Pipeline pipeline = pipelineFollow.getPipeline();
        String pipelineId = pipeline.getPipelineId();
        String userId = pipelineFollow.getUserId();
        List<PipelineFollow> list = pipelineFollowDao.updateFollow(userId, pipelineId);
        if (list.size() == 0){
            String follow = pipelineFollowDao.createFollow(BeanMapper.map(pipelineFollow, PipelineFollowEntity.class));
            //pipelineActivityService.log("create", "pipelineOther", "收藏了流水线"+pipeline.getPipelineName());
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
    public  List<Pipeline> findAllFollow(String userId, StringBuilder s){
        List<PipelineEntity> pipelineFollow = pipelineFollowDao.findPipelineFollow(userId,s);
        List<Pipeline> list = BeanMapper.mapList(pipelineFollow, Pipeline.class);
        list.forEach(pipeline -> pipeline.setPipelineCollect(1));
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
