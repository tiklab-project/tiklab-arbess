package com.tiklab.matfiow.definition.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.tiklab.matfiow.definition.model.PipelineConfigure;
import com.tiklab.matfiow.definition.model.PipelineExecConfigure;
import com.tiklab.matfiow.definition.service.PipelineConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    @ApiMethod(name = "findAll",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAll(@NotNull String pipelineId) {
        List<Object> list = pipelineConfigureService.findAll(pipelineId);
        return Result.ok(list);
    }

    //更新信息
    @RequestMapping(path="/updateConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineExecConfigure",desc = "pipelineExecConfigure",required = true)
    public Result<Void> updateConfigure(@RequestBody @NotNull @Valid PipelineExecConfigure pipelineExecConfigure){
        pipelineConfigureService.updateTask(pipelineExecConfigure);
        return Result.ok();
    }

    //根据流水线id查询配置
    @RequestMapping(path="/findAll",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfigure",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllConfigure(@NotNull String pipelineId) {
        List<PipelineConfigure> list = pipelineConfigureService.findAllConfigure(pipelineId);
        return Result.ok(list);
    }


}
