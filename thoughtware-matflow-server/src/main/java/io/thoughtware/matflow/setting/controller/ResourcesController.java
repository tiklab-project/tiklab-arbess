package io.thoughtware.matflow.setting.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.setting.model.Resources;
import io.thoughtware.matflow.setting.model.ResourcesDetails;
import io.thoughtware.matflow.setting.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线资源配置控制器
 */
@RestController
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    ResourcesService resourcesService;

    /**
     * @pi.name:查询所有资源配置信息
     * @pi.path:/resources/findAllResources
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllResources",method = RequestMethod.POST)
    public Result<List<Resources>> findAllResources(){
        List<Resources> resourcesList = resourcesService.findAllResources();
        return Result.ok(resourcesList);
    }

    /**
     * @pi.name:查询可用资源配置信息
     * @pi.path:/resources/findResourcesList
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findResourcesList",method = RequestMethod.POST)
    public Result<Resources> findResourcesList(){
        Resources resources = resourcesService.findResourcesList();
        return Result.ok(resources);
    }


    @RequestMapping(path="/findResourcesDetails",method = RequestMethod.POST)
    public Result<ResourcesDetails> findResourcesList(@NotNull String type){
        ResourcesDetails resources = resourcesService.findResourcesDetails(type);
        return Result.ok(resources);
    }


}
