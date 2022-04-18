package com.doublekit.pipeline.example.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.service.PipelineConfigureServiceImpl;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.service.PipelineDeployService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deploy")
@Api(name = "PipelineDeployController",desc = "部署管理")
public class PipelineDeployController {

    @Autowired
    PipelineDeployService pipelineDeployService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigureServiceImpl.class);

    @RequestMapping(path="/createDeploy",method = RequestMethod.POST)
    @ApiMethod(name = "createConfigure",desc = "创建部署配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> createConfigure( String pipelineId,int taskType){
        String configureId = pipelineDeployService.createConfigure( pipelineId,taskType);
        return Result.ok(configureId);
    }
    
    @RequestMapping(path="/deleteDeploy",method = RequestMethod.POST)
    @ApiMethod(name = "deleteDeploy",desc = "创建源码配置")
    @ApiParam(name = "deployId",desc = "deployId",required = true)
    public Result<String> deleteDeploy(String deployId){
        pipelineDeployService.deleteDeploy(deployId);
        return Result.ok();
    }

}