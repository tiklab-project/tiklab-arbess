package io.thoughtware.matflow.setting.service;


import io.thoughtware.beans.BeanMapper;
import io.thoughtware.matflow.setting.dao.CacheDao;
import io.thoughtware.matflow.setting.entity.CacheEntity;
import io.thoughtware.matflow.setting.model.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CacheServiceImpl implements CacheService {
    
    
    @Autowired
    CacheDao cacheDao;
    

    @Override
    public String createCathe(Cache cache){
        CacheEntity cacheEntity = BeanMapper.map(cache, CacheEntity.class);
        return cacheDao.createCathe(cacheEntity);
    }

    @Override
    public void updateCathe(Cache cache){

        String id = cache.getId();
        Cache cathe = findCathe(id);
        if (cache.getArtifactCache() == 0){
            cache.setArtifactCache(cathe.getArtifactCache());
        }
        if (cache.getLogCache() == 0){
            cache.setLogCache(cathe.getLogCache());
        }
        CacheEntity cacheEntity = BeanMapper.map(cache, CacheEntity.class);
        cacheDao.updateCathe(cacheEntity);
    }

    @Override
    public void deleteCathe(String cacheId){
        cacheDao.deleteCathe(cacheId);
    }

    @Override
    public Cache findCathe(String cacheId){
        CacheEntity cacheEntity = cacheDao.findCathe(cacheId);
        return BeanMapper.map(cacheEntity, Cache.class);
    }

    @Override
    public List<Cache> findAllCathe(){
        List<CacheEntity> cacheEntityList = cacheDao.findAllCathe();
        if (cacheEntityList == null){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(cacheEntityList, Cache.class);
    }
    
    
    
}
