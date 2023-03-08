package io.tiklab.matflow.support.postprocess.dao;

import io.tiklab.beans.BeanMapper;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.matflow.support.postprocess.entity.PostprocessInstanceEntity;
import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;
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


}



































