package net.tiklab.matflow.support.trigger.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.support.trigger.model.Trigger;
import net.tiklab.matflow.support.trigger.service.TriggerService;
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
@RequestMapping("/pipelineTriggerConfig")
@Api(name = "TriggerConfigController",desc = "流水线配置")
public class TriggerConfigController {

    @Autowired
    TriggerService pipelineTriggerConfigService;

    //更新信息
    @RequestMapping(path="/updateConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<Void> updateConfigure(@RequestBody @NotNull @Valid Trigger config){
        pipelineTriggerConfigService.updateConfig(config);
        return Result.ok();
    }

    @RequestMapping(path="/deleteConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deleteConfig",desc = "删除配置")
    @ApiParam(name = "configId",desc = "配置id",required = true)
    public Result<Void> deleteTriggerConfig( @NotNull String configId){
        pipelineTriggerConfigService.deleteTriggerConfig(configId);
        return Result.ok();
    }

    @RequestMapping(path="/createConfig",method = RequestMethod.POST)
    @ApiMethod(name = "createConfig",desc = "创建配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<String> createConfig(@RequestBody @NotNull @Valid Trigger config) {
        String id = pipelineTriggerConfigService.createConfig(config);
        return Result.ok(id);
    }



    @RequestMapping(path="/findAllTriggerConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "流水线id查询配置顺序信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllPipelineConfig(@NotNull String pipelineId) {
        List<Object> list = pipelineTriggerConfigService.findAllConfig(pipelineId);
        return Result.ok(list);
    }

    @RequestMapping(path="/findOneTriggerConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "流水线id查询配置顺序信息")
    @ApiParam(name = "configId",desc = "配置id",required = true)
    public Result<Object> findOneTriggerConfig(@NotNull String configId) {
        Object list = pipelineTriggerConfigService.findOneConfig(configId);
        return Result.ok(list);
    }



}
