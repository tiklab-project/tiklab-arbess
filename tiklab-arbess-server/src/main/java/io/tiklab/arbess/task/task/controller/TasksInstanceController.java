package io.tiklab.arbess.task.task.controller;

import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.service.TasksInstanceService;
import io.tiklab.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线多任务实例控制器
 */
@RestController
@RequestMapping("/taskInstance")
public class TasksInstanceController {

    @Autowired
    TasksInstanceService tasksInstanceService;

    /**
     * @pi.name:查询流水线多任务实例
     * @pi.url:/taskInstance/findTaskInstance
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=instanceId;dataType=string;value=实例id;
     */
    @RequestMapping(path="/findTaskInstance",method = RequestMethod.POST)
    public Result<List<TaskInstance>> findTaskInstance(@NotNull String instanceId){

        List<TaskInstance> allLog = tasksInstanceService.findAllInstanceInstance(instanceId);

        return Result.ok(allLog);
    }

    /**
     * @pi.name:查询流水线任务实例日志
     * @pi.url:/taskInstance/findAllInstanceLogs
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=instanceId;dataType=string;value=实例id;
     */
    @RequestMapping(path="/findAllInstanceLogs",method = RequestMethod.POST)
    public Result<List<String>> findAllInstanceLogs(@NotNull String instanceId){

        List<String> allLog = tasksInstanceService.findAllInstanceLogs(instanceId);

        return Result.ok(allLog);
    }

}





















