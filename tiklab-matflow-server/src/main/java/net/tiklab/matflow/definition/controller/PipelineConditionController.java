package net.tiklab.matflow.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.PipelineCondition;
import net.tiklab.matflow.definition.service.PipelineConditionServer;
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
@RequestMapping("/pipelineCond")
@Api(name = "PipelineConditionController",desc = "流水线配置")
public class PipelineConditionController {

    @Autowired
    PipelineConditionServer conditionServer;

    @RequestMapping(path="/createCond",method = RequestMethod.POST)
    @ApiMethod(name = "createCond",desc = "查询流水线配置")
    @ApiParam(name = "condition",desc = "条件信息",required = true)
    public Result<String> createCond(@RequestBody @NotNull @Valid PipelineCondition condition){
        String condId = conditionServer.createCond(condition);
        return Result.ok(condId);
    }

    @RequestMapping(path="/updateCond",method = RequestMethod.POST)
    @ApiMethod(name = "updateCond",desc = "查询流水线配置")
    @ApiParam(name = "condition",desc = "条件信息",required = true)
    public Result<Void> updateCond(@RequestBody @NotNull @Valid PipelineCondition condition){
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
        List<PipelineCondition> condId = conditionServer.findAllTaskCond(taskId);
        return Result.ok(condId);
    }



}

















































