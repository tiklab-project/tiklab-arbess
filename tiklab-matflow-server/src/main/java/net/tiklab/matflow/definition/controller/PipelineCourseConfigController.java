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
import java.util.Map;

@RestController
@RequestMapping("/pipelineCourseConfig")
@Api(name = "PipelineCourseConfigController",desc = "流水线配置")
public class PipelineCourseConfigController {

    @Autowired
    PipelineCourseConfigService pipelineCourseConfigService;

    //更新信息
    @RequestMapping(path="/updateConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineConfig",desc = "pipelineConfig",required = true)
    public Result<Void> updateConfigure(@RequestBody @NotNull @Valid PipelineCourseConfig pipelineConfig){
        pipelineCourseConfigService.updateConfig(pipelineConfig);
        return Result.ok();
    }

    //根据流水线id查询配置
    @RequestMapping(path="/findAllConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllConfig(@NotNull String pipelineId) {
        List<Object> list = pipelineCourseConfigService.findAllConfig(pipelineId);
        return Result.ok(list);
    }


    @RequestMapping(path="/findAllPipelineConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "流水线id查询配置顺序信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<PipelineCourseConfig>> findAllPipelineConfig(@NotNull String pipelineId) {
        List<PipelineCourseConfig> list = pipelineCourseConfigService.findAllPipelineConfig(pipelineId);
        return Result.ok(list);
    }

    @RequestMapping(path="/configValid",method = RequestMethod.POST)
    @ApiMethod(name = "configValid",desc = "效验必填字段")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result< Map<String, String>> configValid(@NotNull String pipelineId) {
        Map<String, String> list = pipelineCourseConfigService.configValid(pipelineId);
        return Result.ok(list);
    }



}
