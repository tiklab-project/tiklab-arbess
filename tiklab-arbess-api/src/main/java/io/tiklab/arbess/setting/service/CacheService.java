package io.tiklab.arbess.setting.service;

import io.tiklab.arbess.setting.model.Cache;
import java.util.List;

/**
 * 缓存服务接口
 */
public interface CacheService {

    /**
     * 创建缓存
     * @param cache 缓存对象
     * @return 缓存ID
     */
    String createCathe(Cache cache);

    /**
     * 更新缓存
     * @param cache 缓存对象
     */
    void updateCathe(Cache cache);

    /**
     * 删除缓存
     * @param cacheId 缓存ID
     */
    void deleteCathe(String cacheId);

    /**
     * 查询缓存
     * @param cacheId 缓存ID
     * @return 缓存对象
     */
    Cache findCathe(String cacheId);

    /**
     * 查询所有缓存
     * @return 缓存列表
     */
    List<Cache> findAllCathe();
    
    
}
