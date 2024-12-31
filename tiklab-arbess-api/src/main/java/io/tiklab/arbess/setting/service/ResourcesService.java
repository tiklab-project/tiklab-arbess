package io.tiklab.arbess.setting.service;

import io.tiklab.arbess.setting.model.Resources;
import io.tiklab.arbess.setting.model.ResourcesDetails;

import java.util.List;

/**
 * 资源服务接口
 */
public interface ResourcesService {


     /**
      * 更新资源使用情况
      */
     void instanceResources(int time);

     /**
      * 判断资源情况
      */
     void judgeResources();

     /**
      * 创建资源
      * @param resources 资源对象
      * @return 资源ID
      */
     String createResources(Resources resources);

     /**
      * 更新资源
      * @param resources 资源对象
      */
     void updateResources(Resources resources);

     /**
      * 删除资源
      * @param resourcesId 资源ID
      */
     void deleteResources(String resourcesId);

     /**
      * 查询资源
      * @param resourcesId 资源ID
      * @return 资源对象
      */
     Resources findOneResources(String resourcesId);

     /**
      * 查询所有资源
      * @return 资源列表
      */
     List<Resources> findAllResources();

     /**
      * 查询资源使用情况
      * @return 使用情况
      */
     Resources findResourcesList();

     /**
      * 查询资源详情
      * @param type 资源类型
      * @return 资源详情
      */
     ResourcesDetails findResourcesDetails(String type);



}
