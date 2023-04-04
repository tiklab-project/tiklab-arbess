package io.tiklab.matflow.task.task.controller;


import io.tiklab.core.Result;
import io.tiklab.matflow.task.task.dao.TaskInstanceDao;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/taskInstance")
@Api(name = "TaskInstanceController",desc = "任务实例")
public class TaskInstanceController {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    TaskInstanceDao taskInstanceDao;

    @RequestMapping(path="/findTaskInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findTaskInstance",desc = "查询日志")
    @ApiParam(name = "instanceId",desc = "流水线实例id",required = true)
    public Result< List<TaskInstance>> findTaskInstance(@NotNull String instanceId){

        List<TaskInstance> allLog = tasksInstanceService.findAllInstanceInstance(instanceId);

        return Result.ok(allLog);
    }

}




















