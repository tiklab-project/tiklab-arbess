package net.tiklab.matflow.pipeline.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.pipeline.definition.model.PipelineConfig;
import net.tiklab.matflow.pipeline.definition.service.PipelineConfigService;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesService;
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
@RequestMapping("/pipelineConfig")
@Api(name = "PipelineConfigController",desc = "流水线配置")
public class PipelineConfigController {


    @Autowired
    PipelineConfigService configServer;

    @Autowired
    PipelineStagesService stagesServer;

    @RequestMapping(path="/findAllConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result< List<Object>> updateConfigure(@NotNull String pipelineId){
        List<Object> allConfig = configServer.findAllConfig(pipelineId);
        return Result.ok(allConfig);
    }

    @RequestMapping(path="/findAllTaskConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTaskConfig",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result< List<Object>> findAllTaskConfig(@NotNull String pipelineId){
        List<Object> allConfig = configServer.findAllTaskConfig(pipelineId);
        return Result.ok(allConfig);
    }

    @RequestMapping(path="/createTaskConfig",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTaskConfig",desc = "创建流水线配置")
    @ApiParam(name = "config",desc = "配置",required = true)
    public Result<String> createTaskConfig(@RequestBody @Valid @NotNull PipelineConfig config){
       String configId = configServer.createTaskConfig(config);
        return Result.ok(configId);
    }

    @RequestMapping(path="/updateTaskConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateTaskConfig",desc = "更新流水线配置")
    @ApiParam(name = "config",desc = "配置",required = true)
    public Result<Void> updateTaskConfig(@RequestBody @Valid @NotNull PipelineConfig config){
        configServer.updateTaskConfig(config);
        return Result.ok();
    }

    @RequestMapping(path="/deleteTaskConfig",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTaskConfig",desc = "更新流水线配置")
    @ApiParam(name = "config",desc = "配置",required = true)
    public Result<Void> deleteTaskConfig(@RequestBody @Valid @NotNull PipelineConfig config){
        configServer.deleteTaskConfig(config);
        return Result.ok();
    }


    @RequestMapping(path="/validAllConfig",method = RequestMethod.POST)
    @ApiMethod(name = "validAllConfig",desc = "更新流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<String>> validAllConfig(@NotNull String pipelineId){
        List<String> list = configServer.validAllConfig(pipelineId);
        return Result.ok(list);
    }


    @RequestMapping(path="/updateStageName",method = RequestMethod.POST)
    @ApiMethod(name = "updateStageName",desc = "更新流水线配置")
    @ApiParam(name = "stagesId",desc = "阶段id",required = true)
    public Result<Void> updateStageName(@NotNull String stagesId,String stagesName){
         stagesServer.updateStageName(stagesId,stagesName);
        return Result.ok();
    }

}

























