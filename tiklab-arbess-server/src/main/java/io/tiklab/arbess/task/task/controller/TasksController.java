package io.tiklab.arbess.task.task.controller;

import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksService;
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
 * @pi.groupName: 流水线多任务控制器
 */

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    TasksService tasksService;

    /**
     * @pi.name:创建流水线任务
     * @pi.path:/tasks/createTask
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=tasks
     */
    @RequestMapping(path="/createTask",method = RequestMethod.POST)
    public Result<String> finAllPipelineTaskOrTask(@RequestBody @Valid @NotNull Tasks tasks){
        String taskId = tasksService.createTasksOrTask(tasks);
        return Result.ok(taskId);
    }


    /**
     * @pi.name:更新流水线任务
     * @pi.path:/tasks/updateTask
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=tasks
     */
    @RequestMapping(path="/updateTask",method = RequestMethod.POST)
    public Result<Void> updateTasksTask(@RequestBody @Valid @NotNull Tasks tasks){
        tasksService.updateTasksTask(tasks);
        return Result.ok();
    }

    /**
     * @pi.name:更新任务名称
     * @pi.path:/tasks/updateTaskName
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=tasks
     */
    @RequestMapping(path="/updateTaskName",method = RequestMethod.POST)
    public Result<Void> updateTasksTaskName(@RequestBody @Valid @NotNull Tasks tasks){
        tasksService.updateTaskName(tasks);
        return Result.ok();
    }


    /**
     * @pi.name:删除任务
     * @pi.path:/tasks/deleteTask
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=taskId;dataType=string;value=taskId;
     */
    @RequestMapping(path="/deleteTask",method = RequestMethod.POST)
    public Result<Void> deleteTasksOrTask(@NotNull String taskId){
        tasksService.deleteTasksOrTask(taskId);
        return Result.ok();
    }

    /**
     * @pi.name:查询任务及任务详情
     * @pi.path:/tasks/findOneTasksOrTask
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=taskId;dataType=string;value=taskId;
     */
    @RequestMapping(path="/findOneTasksOrTask",method = RequestMethod.POST)
    public Result<Tasks> findOneTasksOrTask(@NotNull String taskId){
        Tasks tasksOrTask = tasksService.findOneTasksOrTask(taskId);
        return Result.ok(tasksOrTask);
    }

    /**
     * @pi.name:效验流水线多任务完整性
     * @pi.path:/tasks/validTaskMustField
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/validTaskMustField",method = RequestMethod.POST)
    public Result<List<String>> validTaskMustField(@NotNull String pipelineId){
        List<String> list = tasksService.validTasksMustField(pipelineId, 1);
        return Result.ok(list);
    }


}

















































