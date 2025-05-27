package io.tiklab.arbess.setting.other.dao;

import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.setting.other.entity.ResourcesEntity;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.arbess.setting.other.model.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ResourcesDao {


    @Autowired
    JpaTemplate jpaTemplate;


    public String createResources(Resources resources){
        ResourcesEntity resourcesEntity = BeanMapper.map(resources, ResourcesEntity.class);
        return jpaTemplate.save(resourcesEntity, String.class);
    }


    public void updateResources(Resources resources){
        ResourcesEntity resourcesEntity = BeanMapper.map(resources, ResourcesEntity.class);
        jpaTemplate.update(resourcesEntity);
    }


    public void deleteResources(String resourcesId){
        jpaTemplate.delete(ResourcesEntity.class,resourcesId);
    }

    public Resources findOneResources(String resourcesId){
        ResourcesEntity resourcesEntity = jpaTemplate.findOne(ResourcesEntity.class, resourcesId);
        return BeanMapper.map(resourcesEntity, Resources.class);
    }


    public Resources findResources(String beginTime,String endTime){
        QueryBuilders<ResourcesEntity> queryBuilders = QueryBuilders.createQuery(ResourcesEntity.class)
                .eq("beginTime",beginTime)
                .eq("endTime",endTime);
        QueryCondition queryCondition = queryBuilders.get();
        List<ResourcesEntity> list = jpaTemplate.findList(queryCondition, ResourcesEntity.class);
        if (list == null || list.isEmpty()){
            return null;
        }
        return BeanMapper.map(list.get(0),Resources.class);
    }

    public List<Resources> findAllResources(){
        List<ResourcesEntity> resourcesEntityList = jpaTemplate.findAll(ResourcesEntity.class);
        if (resourcesEntityList == null || resourcesEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(resourcesEntityList, Resources.class);
    }

}
