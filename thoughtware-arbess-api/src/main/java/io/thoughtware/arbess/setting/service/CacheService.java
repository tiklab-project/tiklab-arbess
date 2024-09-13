package io.thoughtware.arbess.setting.service;

import io.thoughtware.arbess.setting.model.Cache;
import java.util.List;

public interface CacheService {


    String createCathe(Cache cache);

    void updateCathe(Cache cache);

    void deleteCathe(String cacheId);

    Cache findCathe(String cacheId);


    List<Cache> findAllCathe();
    
    
}
