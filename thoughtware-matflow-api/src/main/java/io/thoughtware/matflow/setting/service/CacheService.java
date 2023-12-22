package io.thoughtware.matflow.setting.service;

import io.thoughtware.matflow.setting.model.Cache;
import java.util.List;

public interface CacheService {


    String createCathe(Cache cache);

    void updateCathe(Cache cache);

    void deleteCathe(String cacheId);

    Cache findCathe(String cacheId);


    List<Cache> findAllCathe();
    
    
}
