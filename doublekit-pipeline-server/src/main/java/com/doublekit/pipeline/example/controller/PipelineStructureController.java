package com.doublekit.pipeline.example.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.service.PipelineConfigureServiceImpl;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.service.PipelineStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/structure")
@Api(name = "PipelineStructureController",desc = "构建管理")
public class PipelineStructureController {

    @Autowired
    PipelineStructureService pipelineStructureService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigureServiceImpl.class);

    @RequestMapping(path="/createStructure",method = RequestMethod.POST)
    @ApiMethod(name = "createConfigure",desc = "创建构建配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> createConfigure( String pipelineId,int taskType){
        String configureId = pipelineStructureService.createConfigure( pipelineId,taskType);
        return Result.ok(configureId);
    }
    



}