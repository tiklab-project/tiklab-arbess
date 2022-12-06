package net.tiklab.matflow.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.PipelineStages;
import net.tiklab.matflow.definition.service.PipelineStagesServer;
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
@RequestMapping("/pipelineStagesConfig")
@Api(name = "PipelineStagesController",desc = "流水线配置")
public class PipelineStagesController {

    @Autowired
    PipelineStagesServer pipelineStagesService;

    //更新信息
    @RequestMapping(path="/updateConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<Void> updateConfigure(@RequestBody @NotNull @Valid PipelineStages config){
        // pipelineStagesService.updateConfig(config);
        return Result.ok();
    }

    @RequestMapping(path="/deleteConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deleteConfig",desc = "删除配置")
    @ApiParam(name = "configId",desc = "配置id",required = true)
    public Result<Void> updateConfigure( @NotNull String configId){
        // pipelineStagesService.deleteConfig(configId);
        return Result.ok();
    }

    @RequestMapping(path="/createConfig",method = RequestMethod.POST)
    @ApiMethod(name = "createConfig",desc = "创建配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<String> createConfig(@RequestBody @NotNull @Valid PipelineStages config) {
        String id = pipelineStagesService.createStagesConfig(config);
        return Result.ok(id);
    }

    //根据流水线id查询配置
    @RequestMapping(path="/findAllConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "查询配置任务信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<PipelineStages>> findAllConfig(@NotNull String pipelineId) {
        List<PipelineStages> list = pipelineStagesService.findAllStagesConfig(pipelineId);
        return Result.ok(list);
    }



}
