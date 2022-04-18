package com.doublekit.pipeline.example.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.service.PipelineConfigureServiceImpl;
import com.doublekit.pipeline.example.service.PipelineTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(name = "PipelineTestController",desc = "测试管理")
public class PipelineTestController {

    @Autowired
    PipelineTestService pipelineTestService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigureServiceImpl.class);

    @RequestMapping(path="/createTest",method = RequestMethod.POST)
    @ApiMethod(name = "createConfigure",desc = "创建测试配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> createConfigure( String pipelineId,int taskType){
        String configureId = pipelineTestService.createConfigure(pipelineId,taskType);
        return Result.ok(configureId);
    }

    @RequestMapping(path="/deleteTest",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTest",desc = "创建源码配置")
    @ApiParam(name = "testId",desc = "testId",required = true)
    public Result<String> deleteTest(String testId){
        pipelineTestService.deleteTest(testId);
        return Result.ok();
    }


}
