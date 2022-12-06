package net.tiklab.matflow.definition.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.definition.service.PipelineCourseConfigService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineCourseConfig")
@Api(name = "PipelineCourseConfigController",desc = "流水线配置")
public class PipelineCourseConfigController {

    @Autowired
    PipelineCourseConfigService pipelineCourseConfigService;

    //更新信息
    @RequestMapping(path="/updateConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<Void> updateConfigure(@RequestBody @NotNull @Valid PipelineCourseConfig config){
        pipelineCourseConfigService.updateConfig(config);
        return Result.ok();
    }

    @RequestMapping(path="/deleteConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deleteConfig",desc = "删除配置")
    @ApiParam(name = "configId",desc = "配置id",required = true)
    public Result<Void> updateConfigure( @NotNull String configId){
        pipelineCourseConfigService.deleteConfig(configId);
        return Result.ok();
    }

    @RequestMapping(path="/createConfig",method = RequestMethod.POST)
    @ApiMethod(name = "createConfig",desc = "创建配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<String> createConfig(@RequestBody @NotNull @Valid PipelineCourseConfig config) {
        String id = pipelineCourseConfigService.createConfig(config);
        return Result.ok(id);
    }


    // @RequestMapping(path="/updateOrderConfig",method = RequestMethod.POST)
    // @ApiMethod(name = "updateOrderConfig",desc = "创建配置")
    // @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    // public Result<Void> updateOrderConfig(@RequestBody @NotNull @Valid PipelineCourseConfig config) {
    //      pipelineCourseConfigService.updateOrderConfig(config);
    //     return Result.ok();
    // }


    //根据流水线id查询配置
    @RequestMapping(path="/findAllConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "查询配置任务信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllConfig(@NotNull String pipelineId) {
        List<Object> list = pipelineCourseConfigService.findAllConfig(pipelineId);
        return Result.ok(list);
    }


    @RequestMapping(path="/findAllCourseConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "流水线id查询配置顺序信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<PipelineCourseConfig>> findAllPipelineConfig(@NotNull String pipelineId) {
        List<PipelineCourseConfig> list = pipelineCourseConfigService.findAllCourseConfig(pipelineId);
        return Result.ok(list);
    }

    @RequestMapping(path="/configValid",method = RequestMethod.POST)
    @ApiMethod(name = "configValid",desc = "效验必填字段")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<String>> configValid(@NotNull String pipelineId) {

        List<String> list = pipelineCourseConfigService.configValid(pipelineId);
        return Result.ok(list);
    }



}
