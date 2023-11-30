package io.tiklab.matflow.stages.controller;

import io.tiklab.matflow.stages.model.Stage;
import io.tiklab.matflow.stages.service.StageService;
import io.tiklab.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线多阶段控制器
 */
@RestController
@RequestMapping("/stage")
public class StageController {

    @Autowired
    StageService stageService;

    /**
     * @pi.name:创建流水线阶段及任务
     * @pi.path:/stage/createStage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=stage
     */
    @RequestMapping(path="/createStage",method = RequestMethod.POST)
    public Result<String> createStagesOrTask(@RequestBody @Valid @NotNull Stage stage){
        String taskId = stageService.createStagesOrTask(stage);
        return Result.ok(taskId);
    }

    /**
     * @pi.name:查询流水线阶段信息
     * @pi.path:/stage/finAllStage
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/finAllStage",method = RequestMethod.POST)
    public Result<List<Stage>> finAllPipelineTaskOrTask(@NotNull String pipelineId){
        List<Stage> tasks = stageService.findAllStagesOrTask(pipelineId);
        return Result.ok(tasks);
    }

    /**
     * @pi.name:更新流水线阶段任务信息
     * @pi.path:/stage/createStage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=stage
     */
    @RequestMapping(path="/updateStage",method = RequestMethod.POST)
    public Result<Void> updateStageTask(@RequestBody @Valid @NotNull Stage stage){
        stageService.updateStagesTask(stage);
        return Result.ok();
    }

    /**
     * @pi.name:更新流水线阶段名称
     * @pi.path:/stage/updateStageName
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=stage
     */
    @RequestMapping(path="/updateStageName",method = RequestMethod.POST)
    public Result<Void> updateTasksStage(@RequestBody @Valid @NotNull Stage stage){
        stageService.updateStageName(stage);
        return Result.ok();
    }

    /**
     * @pi.name:删除流水线阶段及任务
     * @pi.path:/stage/deleteStage
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=taskId;dataType=string;value=taskId;
     */
    @RequestMapping(path="/deleteStage",method = RequestMethod.POST)
    public Result<Void> deleteTasksOrStage(@NotNull String taskId){
        stageService.deleteStagesOrTask(taskId);
        return Result.ok();
    }

    /**
     * @pi.name:效验流水线各个阶段完整性
     * @pi.path:/stage/validStagesMustField
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/validStagesMustField",method = RequestMethod.POST)
    public Result<List<String>> validStagesMustField(@NotNull String pipelineId){
        List<String> list = stageService.validStagesMustField(pipelineId);
        return Result.ok(list);
    }



}




























