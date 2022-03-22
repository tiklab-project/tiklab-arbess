package com.doublekit.pipeline.definition.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.model.Configure;
import com.doublekit.pipeline.definition.service.ConfigureService;
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
@Api(name = "ConfigureController",desc = "流水线配置")
public class ConfigureController {

    @Autowired
    ConfigureService configureService;

    //添加
    @RequestMapping(path="/createPipelineConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineConfigure",desc = "创建流水线配置")
    @ApiParam(name = "configure",desc = "配置信息",required = true)
    public Result<String> createPipelineConfigure(@RequestBody @NotNull @Valid Configure configure){

        String configureId = configureService.createConfigure(configure);

        return Result.ok(configureId);
    }

    //删除
    @RequestMapping(path="/deletePipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineConfig",desc = "删除流水线配置")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result deletePipelineConfig(@NotNull String id){

        configureService.deleteConfig(id);

        return Result.ok();
    }

    //根据流水线id查询配置信息
    @RequestMapping(path="/selectPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineConfig",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Configure> selectPipelineConfig(@NotNull String pipelineId) {

        Configure selectPipelineConfig = configureService.findTimeId(pipelineId);

        return Result.ok(selectPipelineConfig);
    }

    //更新信息
    @RequestMapping(path="/updatePipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineConfig",desc = "更新流水线配置")
    @ApiParam(name = "configure",desc = "配置信息",required = true)
    public Result<String> updatePipelineConfig(@RequestBody @NotNull @Valid Configure configure){

        String configureId = configureService.updateConfigure(configure);

        return Result.ok(configureId);
    }

    //查询所有
    @RequestMapping(path="/selectAllPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "selectAllPipelineConfig",desc = "查询所有流水线配置")
    public Result<Configure> selectAllPipelineConfig(){

        List<Configure> configureList = configureService.findAllConfigure();

        return Result.ok(configureList);
    }




}
