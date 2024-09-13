package io.thoughtware.arbess.setting.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.arbess.setting.entity.CacheEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CacheDao {


    @Autowired
    JpaTemplate jpaTemplate;


    public String createCathe(CacheEntity cacheEntity){
        return jpaTemplate.save(cacheEntity, String.class);
    }

    public void updateCathe(CacheEntity cacheEntity){
        jpaTemplate.update(cacheEntity);
    }

    public void deleteCathe(String cacheId){
        jpaTemplate.delete( CacheEntity.class,cacheId);
    }

    public CacheEntity findCathe(String cacheId){
        return jpaTemplate.findOne(CacheEntity.class, cacheId);
    }


    public List<CacheEntity> findAllCathe(){
        return jpaTemplate.findAll(CacheEntity.class);
    }



}
