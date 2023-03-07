package io.tiklab.matflow.support.condition.controller;

import io.tiklab.matflow.support.condition.model.Condition;
import io.tiklab.matflow.support.condition.service.ConditionService;
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
@RequestMapping("/pipelineCond")
@Api(name = "ConditionController",desc = "流水线配置")
public class ConditionController {

    @Autowired
    ConditionService conditionServer;

    @RequestMapping(path="/createCond",method = RequestMethod.POST)
    @ApiMethod(name = "createCond",desc = "查询流水线配置")
    @ApiParam(name = "condition",desc = "条件信息",required = true)
    public Result<String> createCond(@RequestBody @NotNull @Valid Condition condition){
        String condId = conditionServer.createCond(condition);
        return Result.ok(condId);
    }

    @RequestMapping(path="/updateCond",method = RequestMethod.POST)
    @ApiMethod(name = "updateCond",desc = "查询流水线配置")
    @ApiParam(name = "condition",desc = "条件信息",required = true)
    public Result<Void> updateCond(@RequestBody @NotNull @Valid Condition condition){
         conditionServer.updateCond(condition);
        return Result.ok();
    }

    @RequestMapping(path="/deleteCond",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCond",desc = "查询流水线配置")
    @ApiParam(name = "condId",desc = "条件id",required = true)
    public Result<Void> deleteCond(@NotNull String condId){
        conditionServer.deleteCond(condId);
        return Result.ok();
    }

    @RequestMapping(path="/findAllTaskCond",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTaskCond",desc = "查询流水线配置")
    @ApiParam(name = "taskId",desc = "任务id",required = true)
    public Result<String> findAllTaskCond(@NotNull String  taskId){
        List<Condition> condId = conditionServer.findAllTaskCond(taskId);
        return Result.ok(condId);
    }



}

















































