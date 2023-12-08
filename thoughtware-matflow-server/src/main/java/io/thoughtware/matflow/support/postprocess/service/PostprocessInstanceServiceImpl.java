package io.thoughtware.matflow.support.postprocess.service;

import io.thoughtware.matflow.support.postprocess.entity.PostprocessInstanceEntity;
import io.thoughtware.matflow.support.postprocess.model.PostprocessInstance;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.matflow.support.postprocess.dao.PostprocessInstanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        if (allPostInstance == null || allPostInstance.size() == 0){
            return Collections.emptyList();
        }
        return allPostInstance;
    }


    //运行时间
    private final static Map<String,Integer> postInstanceRunTime = new HashMap<>();

    ExecutorService threadPool = Executors.newCachedThreadPool();

    public void postInstanceRunTime(String postInstanceId){
        postInstanceRunTime.put(postInstanceId,0);
        threadPool.submit(() -> {
            while (true){
                Thread.currentThread().setName(postInstanceId);
                try {
                    int integer = postInstanceRunTime.get(postInstanceId);
                    Thread.sleep(1000);
                    integer = integer +1;
                    postInstanceRunTime.put(postInstanceId,integer);
                }catch (RuntimeException e){
                    throw new RuntimeException();
                }

            }
        });
    }

    public Integer findPostInstanceRunTime(String postInstanceId){
        Integer integer = postInstanceRunTime.get(postInstanceId);
        if (Objects.isNull(integer)){
            return 0;
        }
        return integer;
    }

    public void removePostInstanceRunTime(String postInstanceId){
        postInstanceRunTime.remove(postInstanceId);
    }
































}
