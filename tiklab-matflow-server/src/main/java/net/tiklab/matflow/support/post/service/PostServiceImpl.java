package net.tiklab.matflow.support.post.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.support.post.dao.PostDao;
import net.tiklab.matflow.support.post.entity.PostEntity;
import net.tiklab.matflow.support.post.model.Post;
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
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    PostTaskService postTaskServer;

    /**
     * 创建后置配置及任务
     * @param post message信息
     * @return 配置id
     */
    @Override
    public String createPostTask(Post post) {
        List<Post> allPost = findAllPost(post.getTaskId());
        post.setTaskSort(1);
        int taskType = post.getTaskType();
        if (allPost != null){
            post.setTaskSort(allPost.size()+1);
        }
        PostEntity postEntity = BeanMapper.map(post, PostEntity.class);
        String name = postTaskServer.findConfigName(taskType);
        postEntity.setName(name);
        postEntity.setCreateTime(PipelineUntil.date(1));
        String postId = postDao.createPost(postEntity);
        post.setConfigId(postId);
        postTaskServer.updateConfig(post);
        return postId;
    }

    /**
     * 查询配置
     * @param taskId 流水线id
     * @return 配置
     */
    public List<Object> findAllPostTask(String taskId){
        List<Post> allPost = findAllPost(taskId);
        if (allPost == null){
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (Post post : allPost) {
            Object config = postTaskServer.findOneConfig(post);
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
        Post onePost = findOnePost(postId);
        postTaskServer.deleteConfig(onePost);
        postDao.deletePost(postId);
    }

    /**
     * 根据流水线id查询后置配置
     * @param taskId 流水线id
     * @return 配置
     */
    @Override
    public List<Post> findAllPost(String taskId) {
        List<Post> allPost = findAllPost();
        if (allPost == null){
            return Collections.emptyList();
        }
        List<Post> list = new ArrayList<>();
        for (Post post : allPost) {
            String postTaskId = post.getTaskId();
            if (!postTaskId.equals(taskId)){
                continue;
            }
            list.add(post);
        }
        list.sort(Comparator.comparing(Post::getCreateTime));
        return list;
    }

    //更新
    @Override
    public void updatePostTask(Post post) {
        postTaskServer.updateConfig(post);
    }

    //查询单个
    @Override
    public Post findOnePost(String postId) {
        PostEntity postEntity = postDao.findOnePost(postId);
        return BeanMapper.map(postEntity, Post.class);

    }

    //查询所有
    @Override
    public List<Post> findAllPost() {
        return BeanMapper.mapList(postDao.findAllPost(), Post.class);
    }

    @Override
    public List<Post> findAllPostList(List<String> idList) {
        return BeanMapper.mapList(postDao.findAllPostList(idList), Post.class);
    }



}
