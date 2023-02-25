package net.tiklab.matflow.support.postprocess.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.support.postprocess.dao.PostprocessDao;
import net.tiklab.matflow.support.postprocess.entity.PostprocessEntity;
import net.tiklab.matflow.support.postprocess.model.Postprocess;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PostprocessServiceImpl implements PostprocessService {

    @Autowired
    private PostprocessDao postprocessDao;

    @Autowired
    PostprocessTaskService postTaskServer;

    /**
     * 创建后置配置及任务
     * @param postprocess message信息
     * @return 配置id
     */
    @Override
    public String createPostTask(Postprocess postprocess) {
        List<Postprocess> allPostprocess = findAllPost(postprocess.getTaskId());
        postprocess.setTaskSort(1);
        int taskType = postprocess.getTaskType();
        if (allPostprocess != null){
            postprocess.setTaskSort(allPostprocess.size()+1);
        }
        PostprocessEntity postprocessEntity = BeanMapper.map(postprocess, PostprocessEntity.class);
        String name = postTaskServer.findConfigName(taskType);
        postprocessEntity.setName(name);
        postprocessEntity.setCreateTime(PipelineUntil.date(1));
        String postId = postprocessDao.createPost(postprocessEntity);
        postprocess.setConfigId(postId);
        postTaskServer.updateConfig(postprocess);
        return postId;
    }

    /**
     * 查询配置
     * @param taskId 流水线id
     * @return 配置
     */
    public List<Object> findAllPostTask(String taskId){
        List<Postprocess> allPostprocess = findAllPost(taskId);
        if (allPostprocess == null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (Postprocess postprocess : allPostprocess) {
            Object config = postTaskServer.findOneConfig(postprocess);
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
        Postprocess onePostprocess = findOnePost(postId);
        postTaskServer.deleteConfig(onePostprocess);
        postprocessDao.deletePost(postId);
    }

    /**
     * 根据流水线id查询后置配置
     * @param taskId 流水线id
     * @return 配置
     */
    @Override
    public List<Postprocess> findAllPost(String taskId) {
        List<Postprocess> allPostprocess = findAllPost();
        if (allPostprocess == null){
            return Collections.emptyList();
        }
        List<Postprocess> list = new ArrayList<>();
        for (Postprocess postprocess : allPostprocess) {
            String postTaskId = postprocess.getTaskId();
            if (!postTaskId.equals(taskId)){
                continue;
            }
            list.add(postprocess);
        }
        list.sort(Comparator.comparing(Postprocess::getCreateTime));
        return list;
    }

    //更新
    @Override
    public void updatePostTask(Postprocess postprocess) {
        postTaskServer.updateConfig(postprocess);
    }

    //查询单个
    @Override
    public Postprocess findOnePost(String postId) {
        PostprocessEntity postprocessEntity = postprocessDao.findOnePost(postId);
        return BeanMapper.map(postprocessEntity, Postprocess.class);

    }

    //查询所有
    @Override
    public List<Postprocess> findAllPost() {
        return BeanMapper.mapList(postprocessDao.findAllPost(), Postprocess.class);
    }

    @Override
    public List<Postprocess> findAllPostList(List<String> idList) {
        return BeanMapper.mapList(postprocessDao.findAllPostList(idList), Postprocess.class);
    }



}
