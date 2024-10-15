package io.tiklab.arbess.support.postprocess.service;

import io.tiklab.arbess.support.postprocess.entity.PostprocessInstanceEntity;
import io.tiklab.arbess.support.postprocess.model.PostprocessInstance;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.support.postprocess.dao.PostprocessInstanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 后置处理实例服务实现
 */

@Service
public class PostprocessInstanceServiceImpl implements PostprocessInstanceService {


    @Autowired
    private PostprocessInstanceDao postInstanceDao;

    @Override
    public String createPostInstance(PostprocessInstance instance) {
        return postInstanceDao.createPostInstance(instance);
    }

    @Override
    public PostprocessInstance findPostInstance(String postInstanceId) {
        PostprocessInstance postprocessInstance = postInstanceDao.findOnePostInstance(postInstanceId);
        return BeanMapper.map(postprocessInstance,PostprocessInstance.class);
    }

    /**
     * 删除执行实例
     * @param postInstanceId 实例id
     */
    public void deletePostInstance(String postInstanceId) {
        postInstanceDao.findOnePostInstance(postInstanceId);
    }

    @Override
    public void deletePipelinePostInstance(String instanceId) {
        List<PostprocessInstance> postInstanceList = findPipelinePostInstance(instanceId);
        if (postInstanceList.isEmpty()){
            return;
        }
        for (PostprocessInstance instance : postInstanceList) {
            deletePostInstance(instance.getId());
        }
    }

    @Override
    public void deleteTaskPostInstance(String taskInstanceId) {
        List<PostprocessInstance> postInstanceList = findPipelinePostInstance(taskInstanceId);
        if (postInstanceList.isEmpty()){
            return;
        }
        for (PostprocessInstance instance : postInstanceList) {
            deletePostInstance(instance.getId());
        }
    }

    @Override
    public void updatePostInstance(PostprocessInstance instance) {
        postInstanceDao.updatePostInstance(instance);
    }

    @Override
    public List<PostprocessInstance> findTaskPostInstance(String taskInstanceId) {
        List<PostprocessInstanceEntity> allPostInstanceEntity = postInstanceDao.findTaskPostInstance(taskInstanceId);
        if (allPostInstanceEntity.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(allPostInstanceEntity,PostprocessInstance.class);
    }

    @Override
    public List<PostprocessInstance> findPipelinePostInstance(String instanceId) {
        List<PostprocessInstanceEntity> allPostInstanceEntity = postInstanceDao.findPipelinePostInstance(instanceId);
        if (allPostInstanceEntity.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(allPostInstanceEntity,PostprocessInstance.class);
    }

    /**
     * 查询所有后置处理实例
     * @return 后置处理实例集合
     */
    private  List<PostprocessInstance> findAllPostInstance(){
        List<PostprocessInstance> allPostInstance = postInstanceDao.findAllPostInstance();
        if (allPostInstance == null || allPostInstance.isEmpty()){
            return Collections.emptyList();
        }
        return allPostInstance;
    }
































}
