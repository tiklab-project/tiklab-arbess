package io.tiklab.arbess.setting.service;

import io.tiklab.arbess.setting.model.Resources;
import io.tiklab.arbess.setting.model.ResourcesDetails;

import java.util.List;

public interface ResourcesService {


     /**
      * 更新资源使用情况
      */
     void instanceResources(int time);

     /**
      * 判断资源情况
      */
     void judgeResources();


     String createResources(Resources resources);

     void updateResources(Resources resources);


     void deleteResources(String resourcesId);

     Resources findOneResources(String resourcesId);

     List<Resources> findAllResources();

     /**
      * 查询资源使用情况
      * @return 使用情况
      */
     Resources findResourcesList();


     ResourcesDetails findResourcesDetails(String type);



}
