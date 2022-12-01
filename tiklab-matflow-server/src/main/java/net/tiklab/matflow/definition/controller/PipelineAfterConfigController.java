package net.tiklab.matflow.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.PipelineAfterConfig;
import net.tiklab.matflow.definition.service.PipelineAfterConfigServer;
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
@RequestMapping("/pipelineAfterConfig")
@Api(name = "PipelineAfterConfigController",desc = "流水线后置配置")
public class PipelineAfterConfigController {

    @Autowired
    PipelineAfterConfigServer afterConfigServer;

    //更新信息
    @RequestMapping(path="/createAfterConfig",method = RequestMethod.POST)
    @ApiMethod(name = "createAfterConfig",desc = "创建后置配置")
    @ApiParam(name = "pipelineConfig",desc = "pipelineConfig",required = true)
    public Result<String> createAfterConfig(@RequestBody @NotNull @Valid PipelineAfterConfig pipelineConfig){
        String afterConfig = afterConfigServer.createAfterConfig(pipelineConfig);
        return Result.ok(afterConfig);
    }


    @RequestMapping(path="/updateAfterConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateAfterConfig",desc = "更新后置配置")
    @ApiParam(name = "pipelineConfig",desc = "pipelineConfig",required = true)
    public Result<Void> updateAfterConfig(@RequestBody @NotNull @Valid PipelineAfterConfig pipelineConfig){
         afterConfigServer.updateAfterConfig(pipelineConfig);
        return Result.ok();
    }


    //根据流水线id查询配置
    @RequestMapping(path="/findAllAfterConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllConfig(@NotNull String pipelineId) {
        List<Object> list = afterConfigServer.findAllConfig(pipelineId);
        return Result.ok(list);
    }


    //删除配置
    @RequestMapping(path="/deleteAfterConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deleteAfterConfig",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "configId",desc = "流水线id",required = true)
    public Result<Void> deleteAfterConfig(@NotNull String configId) {
         afterConfigServer.deleteAfterConfig(configId);
        return Result.ok();
    }


    // @RequestMapping(path="/configValid",method = RequestMethod.POST)
    // @ApiMethod(name = "configValid",desc = "效验必填字段")
    // @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    // public Result<Map<String, String>> configValid(@NotNull String pipelineId) {
    //     Map<String, String> list = afterConfigServer.configValid(pipelineId);
    //     return Result.ok(list);
    // }


}
