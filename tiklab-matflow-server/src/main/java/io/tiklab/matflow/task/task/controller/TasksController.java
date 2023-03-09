package io.tiklab.matflow.task.task.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksService;
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
@RequestMapping("/tasks")
@Api(name = "TasksController",desc = "流水线多任务配置")
public class TasksController {

    @Autowired
    TasksService tasksService;

    @RequestMapping(path="/createTask",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> finAllPipelineTaskOrTask(@RequestBody @Valid @NotNull Tasks tasks){
        String taskId = tasksService.createTasksOrTask(tasks);
        return Result.ok(taskId);
    }

    @RequestMapping(path="/finAllTask",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Tasks>> finAllPipelineTaskOrTask(@NotNull String pipelineId){
        List<Tasks> tasks = tasksService.finAllPipelineTaskOrTask(pipelineId);
        return Result.ok(tasks);
    }


    @RequestMapping(path="/updateTask",method = RequestMethod.POST)
    @ApiMethod(name = "updateTasksTask",desc = "查询流水线配置")
    @ApiParam(name = "taskId",desc = "任务id",required = true)
    public Result<Void> updateTasksTask(@RequestBody @Valid @NotNull Tasks tasks){
        tasksService.updateTasksTask(tasks);
        return Result.ok();
    }

    @RequestMapping(path="/updateTaskName",method = RequestMethod.POST)
    @ApiMethod(name = "updateTaskName",desc = "查询流水线配置")
    @ApiParam(name = "taskId",desc = "任务id",required = true)
    public Result<Void> updateTasksTaskName(@RequestBody @Valid @NotNull Tasks tasks){
        tasksService.updateTaskName(tasks);
        return Result.ok();
    }


    @RequestMapping(path="/deleteTask",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTasksOrTask",desc = "查询流水线配置")
    @ApiParam(name = "taskId",desc = "任务id",required = true)
    public Result<Void> deleteTasksOrTask(@NotNull String taskId){
        tasksService.deleteTasksOrTask(taskId);
        return Result.ok();
    }


    @RequestMapping(path="/validTaskMustField",method = RequestMethod.POST)
    @ApiMethod(name = "validTaskMustField",desc = "查询流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<String>> validTaskMustField(@NotNull String pipelineId){
        List<String> list = tasksService.validTasksMustField(pipelineId, 1);
        return Result.ok(list);
    }


}

















































