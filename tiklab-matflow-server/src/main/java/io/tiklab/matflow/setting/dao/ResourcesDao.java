package io.tiklab.matflow.setting.dao;

import io.tiklab.beans.BeanMapper;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.matflow.setting.model.Resources;
import io.tiklab.matflow.setting.entity.ResourcesEntity;
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

    public List<Resources> findAllResources(){
        List<ResourcesEntity> resourcesEntityList = jpaTemplate.findAll(ResourcesEntity.class);
        if (resourcesEntityList == null || resourcesEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(resourcesEntityList, Resources.class);
    }

}
