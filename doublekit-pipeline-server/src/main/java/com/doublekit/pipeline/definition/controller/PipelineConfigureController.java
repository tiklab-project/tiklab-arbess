package com.doublekit.pipeline.definition.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineConfigure")
@Api(name = "PipelineConfigureController",desc = "流水线配置")
public class PipelineConfigureController {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigureController.class);


    //根据流水线id查询配置信息
    @RequestMapping(path="/findAllConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfigure",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllConfigure(@NotNull String pipelineId) {
        List<Object> list = pipelineConfigureService.findAll(pipelineId);
        return Result.ok(list);
    }

    //更新信息
    @RequestMapping(path="/updateConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> updateConfigure( @NotNull String pipelineId, @NotNull String params){
        pipelineConfigureService.updateTask(pipelineId,params);
        return Result.ok();
    }

}
