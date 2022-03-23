package com.doublekit.pipeline.definition.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineConfigureServiceImpl;
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

    //添加
    @RequestMapping(path="/createPipelineConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineConfigure",desc = "创建流水线配置")
    @ApiParam(name = "pipelineConfigure",desc = "配置信息",required = true)
    public Result<String> createPipelineConfigure(@RequestBody @NotNull @Valid PipelineConfigure pipelineConfigure){

        String configureId = pipelineConfigureService.createConfigure(pipelineConfigure);

        return Result.ok(configureId);
    }

    //删除
    @RequestMapping(path="/deletePipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineConfig",desc = "删除流水线配置")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result deletePipelineConfig(@NotNull String id){

        pipelineConfigureService.deleteConfig(id);

        return Result.ok();
    }

    //根据流水线id查询配置信息
    @RequestMapping(path="/selectPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineConfig",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineConfigure> selectPipelineConfig(@NotNull String pipelineId) {

        PipelineConfigure selectPipelineConfig = pipelineConfigureService.findTimeId(pipelineId);

        return Result.ok(selectPipelineConfig);
    }

    //更新信息
    @RequestMapping(path="/updatePipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineConfig",desc = "更新流水线配置")
    @ApiParam(name = "pipelineConfigure",desc = "配置信息",required = true)
    public Result<String> updatePipelineConfig(@RequestBody @NotNull @Valid PipelineConfigure pipelineConfigure){

        String configureId = pipelineConfigureService.updateConfigure(pipelineConfigure);

        return Result.ok(configureId);
    }

    //查询所有
    @RequestMapping(path="/selectAllPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "selectAllPipelineConfig",desc = "查询所有流水线配置")
    public Result<PipelineConfigure> selectAllPipelineConfig(){

        List<PipelineConfigure> pipelineConfigureList = pipelineConfigureService.findAllConfigure();

        return Result.ok(pipelineConfigureList);
    }




}
