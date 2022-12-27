package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelinePostDao;
import net.tiklab.matflow.definition.entity.PipelinePostEntity;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelinePost;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelinePostServerImpl implements PipelinePostServer {

    @Autowired
    private PipelinePostDao pipelinePostDao;

    @Autowired
    PipelinePostTaskServer postTaskServer;

    /**
     * 创建后置配置及任务
     * @param pipelinePost message信息
     * @return 配置id
     */
    @Override
    public String createPostTask(PipelinePost pipelinePost) {
        Pipeline pipeline = pipelinePost.getPipeline();
        List<PipelinePost> allPost = findAllPost(pipeline.getId());
        pipelinePost.setTaskSort(1);
        int taskType = pipelinePost.getTaskType();
        if (allPost != null){
            pipelinePost.setTaskSort(allPost.size()+1);
        }
        PipelinePostEntity pipelinePostEntity = BeanMapper.map(pipelinePost, PipelinePostEntity.class);
        String name = postTaskServer.findConfigName(taskType);
        pipelinePostEntity.setName(name);
        pipelinePostEntity.setCreateTime(PipelineUntil.date(1));
        String postId = pipelinePostDao.createPost(pipelinePostEntity);
        pipelinePost.setConfigId(postId);
        postTaskServer.updateConfig(pipelinePost);
        return postId;
    }

    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    public List<Object> findAllPostTask(String pipelineId){
        List<PipelinePost> allPost = findAllPost(pipelineId);
        if (allPost == null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (PipelinePost pipelinePost : allPost) {
            Object config = postTaskServer.findOneConfig(pipelinePost);
            list.add(config);
        }
        return list;
    }

    /**
     * 删除配置及任务
     * @param postId messageId
     */
    @Override
    public void deletePostTask(String postId) {
        PipelinePost onePost = findOnePost(postId);
        postTaskServer.deleteConfig(onePost);
        pipelinePostDao.deletePost(postId);
    }

    /**
     * 根据流水线id查询后置配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<PipelinePost> findAllPost(String pipelineId) {
        List<PipelinePost> allPost = findAllPost();
        if (allPost == null){
            return null;
        }
        List<PipelinePost> list = new ArrayList<>();
        for (PipelinePost pipelinePost : allPost) {
            Pipeline pipeline = pipelinePost.getPipeline();
            if (!pipeline.getId().equals(pipelineId)){
                continue;
            }
            list.add(pipelinePost);
        }
        return list;
    }

    //更新
    @Override
    public void updatePostTask(PipelinePost pipelinePost) {
        postTaskServer.updateConfig(pipelinePost);
    }

    //查询单个
    @Override
    public PipelinePost findOnePost(String postId) {
        PipelinePostEntity postEntity = pipelinePostDao.findOnePost(postId);
        return BeanMapper.map(postEntity, PipelinePost.class);

    }

    //查询所有
    @Override
    public List<PipelinePost> findAllPost() {
        return BeanMapper.mapList(pipelinePostDao.findAllPost(), PipelinePost.class);
    }

    @Override
    public List<PipelinePost> findAllPostList(List<String> idList) {
        return BeanMapper.mapList(pipelinePostDao.findAllPostList(idList), PipelinePost.class);
    }



}
