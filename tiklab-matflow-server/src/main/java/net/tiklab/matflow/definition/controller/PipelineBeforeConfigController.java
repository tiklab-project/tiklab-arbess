package net.tiklab.matflow.definition.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.PipelineBeforeConfig;
import net.tiklab.matflow.definition.service.PipelineBeforeConfigServer;
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
@RequestMapping("/pipelineBeforeConfig")
@Api(name = "PipelineBeforeConfigController",desc = "流水线配置")
public class PipelineBeforeConfigController {

    @Autowired
    PipelineBeforeConfigServer pipelineBeforeConfigService;

    //更新信息
    @RequestMapping(path="/updateConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<Void> updateConfigure(@RequestBody @NotNull @Valid PipelineBeforeConfig config){
        pipelineBeforeConfigService.updateConfig(config);
        return Result.ok();
    }

    @RequestMapping(path="/deleteConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deleteConfig",desc = "删除配置")
    @ApiParam(name = "configId",desc = "配置id",required = true)
    public Result<Void> updateConfigure( @NotNull String configId){
        pipelineBeforeConfigService.deleteBeforeConfig(configId);
        return Result.ok();
    }

    @RequestMapping(path="/createConfig",method = RequestMethod.POST)
    @ApiMethod(name = "createConfig",desc = "创建配置")
    @ApiParam(name = "pipelineConfig",desc = "配置信息",required = true)
    public Result<String> createConfig(@RequestBody @NotNull @Valid PipelineBeforeConfig config) {
        String id = pipelineBeforeConfigService.createConfig(config);
        return Result.ok(id);
    }



    @RequestMapping(path="/findAllBeforeConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "流水线id查询配置顺序信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllPipelineConfig(@NotNull String pipelineId) {
        List<Object> list = pipelineBeforeConfigService.findAllConfig(pipelineId);
        return Result.ok(list);
    }

    @RequestMapping(path="/findOneBeforeConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "流水线id查询配置顺序信息")
    @ApiParam(name = "configId",desc = "配置id",required = true)
    public Result<Object> findOneBeforeConfig(@NotNull String configId) {
        Object list = pipelineBeforeConfigService.findOneConfig(configId);
        return Result.ok(list);
    }



}
