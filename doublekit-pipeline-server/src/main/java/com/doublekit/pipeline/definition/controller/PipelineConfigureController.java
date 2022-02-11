package com.doublekit.pipeline.definition.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
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
@Api(name = "pipelineConfigureController",desc = "流水线配置")
public class PipelineConfigureController {

    private static Logger logger = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    //添加
    @RequestMapping(path="/createPipelineConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineConfigure",desc = "创建流水线配置")
    @ApiParam(name = "PipelineConfigure",desc = "pipelineConfigure",required = true)
    public Result<String> createPipelineConfigure(@RequestBody @NotNull @Valid PipelineConfigure pipelineConfigure){

        String pipelineConfigureId = pipelineConfigureService.createPipelineConfigure(pipelineConfigure);

        return Result.ok(pipelineConfigureId);
    }

    //删除
    @RequestMapping(path="/deletePipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineConfig",desc = "删除流水线配置")
    @ApiParam(name = "PipelineConfigure",desc = "pipelineConfigure",required = true)
    public Result deletePipelineConfig(@NotNull String id){

        pipelineConfigureService.deletePipelineConfigure(id);

        return Result.ok();
    }

    //更新前信息
    @RequestMapping(path="/updateListPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineConfig",desc = "更新流水线配置")
    @ApiParam(name = "PipelineConfigure",desc = "pipelineConfigure",required = true)
    public Result<PipelineConfigure> updateListPipelineConfig() {

        PipelineConfigure updateListPipelineConfig = pipelineConfigureService.updateListPipelineConfig();

        return Result.ok(updateListPipelineConfig);
    }

    //更新后信息
    @RequestMapping(path="/updatePipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineConfig",desc = "更新流水线配置")
    @ApiParam(name = "PipelineConfigure",desc = "pipelineConfigure",required = true)
    public Result<Void> updatePipelineConfig(@RequestBody @NotNull @Valid PipelineConfigure pipelineConfigure){

        pipelineConfigureService.updatePipelineConfigure(pipelineConfigure);

        return Result.ok();
    }

    //查询
    @RequestMapping(path="/selectPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineConfig",desc = "查询流水线配置")
    @ApiParam(name = "PipelineConfigure",desc = "pipelineConfigure",required = true)
    public Result<PipelineConfigure> selectPipelineConfig(String id){

        PipelineConfigure pipelineConfigure = pipelineConfigureService.selectPipelineConfigure(id);

        return Result.ok(pipelineConfigure);
    }

    //查询所有
        @RequestMapping(path="/selectAllPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "selectAllPipelineConfig",desc = "查询所有流水线配置")
    @ApiParam(name = "PipelineConfigure",desc = "pipelineConfigure",required = true)
    public Result<PipelineConfigure> selectAllPipelineConfig(){

        List<PipelineConfigure> pipelineConfigureList = pipelineConfigureService.selectAllPipelineConfigure();

        return Result.ok(pipelineConfigureList);
    }



}
