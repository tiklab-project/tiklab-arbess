package io.thoughtware.arbess.support.postprocess.dao;

import io.thoughtware.arbess.support.postprocess.entity.PostprocessInstanceEntity;
import io.thoughtware.arbess.support.postprocess.model.PostprocessInstance;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后置任务数据访问
 */

@Repository
public class PostprocessInstanceDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createPostInstance(PostprocessInstance instance){
        PostprocessInstanceEntity instanceEntity = BeanMapper.map(instance, PostprocessInstanceEntity.class);
        return jpaTemplate.save(instanceEntity,String.class);
    }

    public void deletePostInstance(String postInstanceId){
        jpaTemplate.delete(PostprocessInstanceEntity.class,postInstanceId);
    }

    public void updatePostInstance(PostprocessInstance instance){
        PostprocessInstanceEntity instanceEntity = BeanMapper.map(instance, PostprocessInstanceEntity.class);
        jpaTemplate.update(instanceEntity);
    }

    public PostprocessInstance findOnePostInstance(String postInstanceId){
        PostprocessInstanceEntity instanceEntity = jpaTemplate.findOne(PostprocessInstanceEntity.class, postInstanceId);
        return BeanMapper.map(instanceEntity,PostprocessInstance.class);
    }

    public List<PostprocessInstance> findAllPostInstance(){
        List<PostprocessInstance> instanceList = jpaTemplate.findAll(PostprocessInstance.class);
        return BeanMapper.mapList(instanceList,PostprocessInstance.class);
    }


    /**
     * 获取流水线的后置任务
     * @param instanceId 流水线实例id
     * @return 流水线的后置任务
     */
    public List<PostprocessInstanceEntity> findPipelinePostInstance(String instanceId){
        QueryCondition queryCondition = QueryBuilders.createQuery(PostprocessInstanceEntity.class)
                .eq("instanceId", instanceId)
                .get();
        return jpaTemplate.findList(queryCondition,PostprocessInstanceEntity.class);
    }


    /**
     * 获取流水线的后置任务
     * @param taskInstanceId 任务实例id
     * @return 流水线的后置任务
     */
    public List<PostprocessInstanceEntity> findTaskPostInstance(String taskInstanceId){
        QueryCondition queryCondition = QueryBuilders.createQuery(PostprocessInstanceEntity.class)
                .eq("taskInstanceId", taskInstanceId)
                .get();
        return jpaTemplate.findList(queryCondition,PostprocessInstanceEntity.class);
    }




}



































