package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.support.postprocess.dao.PostprocessInstanceDao;
import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 后置处理实例服务实现
 */

@Service
public class PostprocessInstanceServiceImpl implements PostprocessInstanceService{


    @Autowired
    private PostprocessInstanceDao postInstanceDao;

    @Override
    public String createPostInstance(PostprocessInstance instance) {
        return postInstanceDao.createPostInstance(instance);
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
        List<PostprocessInstance> allPostInstance = findAllPostInstance();
        if (allPostInstance.isEmpty()){
            return Collections.emptyList();
        }
        List<PostprocessInstance> list = new ArrayList<>();
        for (PostprocessInstance instance : allPostInstance) {
            String id = instance.getTaskInstanceId();
            if (!id.equals(taskInstanceId)){
                continue;
            }
            list.add(instance);
        }
        return list;
    }

    @Override
    public List<PostprocessInstance> findPipelinePostInstance(String instanceId) {
        List<PostprocessInstance> allPostInstance = findAllPostInstance();
        if (allPostInstance.isEmpty()){
            return Collections.emptyList();
        }
        List<PostprocessInstance> list = new ArrayList<>();
        for (PostprocessInstance instance : allPostInstance) {
            String id = instance.getInstanceId();
            if (!id.equals(instanceId)){
                continue;
            }
            list.add(instance);
        }
        return list;
    }


    /**
     * 查询所有后置处理实例
     * @return 后置处理实例集合
     */
    private  List<PostprocessInstance> findAllPostInstance(){
        List<PostprocessInstance> allPostInstance = postInstanceDao.findAllPostInstance();
        if (allPostInstance == null || allPostInstance.size() == 0){
            return Collections.emptyList();
        }
        return allPostInstance;
    }


}
