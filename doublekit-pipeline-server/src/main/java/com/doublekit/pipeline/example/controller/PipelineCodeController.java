package com.doublekit.pipeline.example.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.service.PipelineConfigureServiceImpl;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
@Api(name = "PipelineCodeController",desc = "源码管理")
public class PipelineCodeController {

    @Autowired
    PipelineCodeService pipelineCodeService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigureServiceImpl.class);


    @RequestMapping(path="/createCode",method = RequestMethod.POST)
    @ApiMethod(name = "createConfigure",desc = "创建源码配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> createConfigure(String pipelineId ,int taskType){
        String configureId = pipelineCodeService.createConfigure(pipelineId,taskType);
        return Result.ok(configureId);
    }

    @RequestMapping(path="/deleteCode",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCode",desc = "创建源码配置")
    @ApiParam(name = "codeId",desc = "codeId",required = true)
    public Result<String> deleteCode(String codeId){
        pipelineCodeService.deleteCode(codeId);
        return Result.ok();
    }
}
