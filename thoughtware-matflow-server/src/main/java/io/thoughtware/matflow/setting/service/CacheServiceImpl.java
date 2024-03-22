package io.thoughtware.matflow.setting.service;


import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.version.service.PipelineVersionService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.matflow.setting.dao.CacheDao;
import io.thoughtware.matflow.setting.entity.CacheEntity;
import io.thoughtware.matflow.setting.model.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static io.thoughtware.matflow.support.util.util.PipelineFinal.DEFAULT_CLEAN_CACHE_DAY;

@Service
public class CacheServiceImpl implements CacheService {
    
    
    @Autowired
    CacheDao cacheDao;

    @Autowired
    PipelineVersionService versionService;
    

    @Override
    public String createCathe(Cache cache){
        CacheEntity cacheEntity = BeanMapper.map(cache, CacheEntity.class);
        return cacheDao.createCathe(cacheEntity);
    }

    @Override
    public void updateCathe(Cache cache){

        String id = cache.getId();
        Cache oneCathe = findCathe(id);
        if (cache.getArtifactCache() == 0){
            cache.setArtifactCache(oneCathe.getArtifactCache());
        }
        if (cache.getLogCache() == 0){
            cache.setLogCache(oneCathe.getLogCache());
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
        if (Objects.isNull(cacheEntityList)){
            return Collections.emptyList();
        }
        List<Cache> caches = BeanMapper.mapList(cacheEntityList, Cache.class);
        Cache cache = caches.get(0);
        if (versionService.isVip()){
            return caches;
        }else {
            cache.setArtifactCache(DEFAULT_CLEAN_CACHE_DAY);
            cache.setLogCache(DEFAULT_CLEAN_CACHE_DAY);
            updateCathe(cache);
        }

        List<Cache> cacheList = new ArrayList<>();
        cacheList.add(cache);
        return cacheList;
    }
    
    
    
}















