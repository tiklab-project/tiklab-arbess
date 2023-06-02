package io.tiklab.matflow.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.setting.model.Resources;
import io.tiklab.matflow.setting.service.ResourcesService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
@Api(name = "ConditionController",desc = "流水线配置")
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesService;

    @RequestMapping(path="/findAllResources",method = RequestMethod.POST)
    @ApiMethod(name = "findAllResources",desc = "查询资源信息")
    // @ApiParam(name = "condition",desc = "条件信息",required = true)
    public Result<List<Resources>> findAllResources(){
        List<Resources> resourcesList = resourcesService.findAllResources();
        return Result.ok(resourcesList);
    }


    @RequestMapping(path="/findResourcesList",method = RequestMethod.POST)
    @ApiMethod(name = "findResourcesList",desc = "查询资源信息")
    // @ApiParam(name = "condition",desc = "条件信息",required = true)
    public Result<Resources> findResourcesList(){
        Resources resources = resourcesService.findResourcesList();
        return Result.ok(resources);
    }



}
