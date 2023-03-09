package io.tiklab.matflow.stages.controller;

import io.tiklab.matflow.stages.model.Stage;
import io.tiklab.matflow.stages.service.StageService;
import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/stage")
@Api(name = "StageController",desc = "流水线多阶段配置")
public class StageController {

    @Autowired
    StageService stageService;

    @RequestMapping(path="/createStage",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> finAllStageTaskOrTask(@RequestBody @Valid @NotNull Stage stage){
        String taskId = stageService.createStagesOrTask(stage);
        return Result.ok(taskId);
    }

    @RequestMapping(path="/finAllStage",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Stage>> finAllPipelineTaskOrTask(@NotNull String pipelineId){
        List<Stage> tasks = stageService.findAllStagesOrTask(pipelineId);
        return Result.ok(tasks);
    }


    @RequestMapping(path="/updateStage",method = RequestMethod.POST)
    @ApiMethod(name = "updateTasksTask",desc = "查询流水线配置")
    @ApiParam(name = "taskId",desc = "任务id",required = true)
    public Result<Void> updateStageTask(@RequestBody @Valid @NotNull Stage stage){
        stageService.updateStagesTask(stage);
        return Result.ok();
    }

    @RequestMapping(path="/updateStageName",method = RequestMethod.POST)
    @ApiMethod(name = "updateTaskName",desc = "查询流水线配置")
    @ApiParam(name = "taskId",desc = "任务id",required = true)
    public Result<Void> updateTasksStage(@RequestBody @Valid @NotNull Stage stage){
        stageService.updateStageName(stage);
        return Result.ok();
    }


    @RequestMapping(path="/deleteStage",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTasksOrTask",desc = "查询流水线配置")
    @ApiParam(name = "taskId",desc = "任务id",required = true)
    public Result<Void> deleteTasksOrStage(@NotNull String taskId){
        stageService.deleteStagesOrTask(taskId);
        return Result.ok();
    }


    @RequestMapping(path="/validStagesMustField",method = RequestMethod.POST)
    @ApiMethod(name = "validStagesMustField",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<String>> validStagesMustField(@NotNull String pipelineId){
        List<String> list = stageService.validStagesMustField(pipelineId);
        return Result.ok(list);
    }



}




























