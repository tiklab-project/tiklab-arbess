package io.tiklab.matflow.support.variable.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.support.variable.model.Variable;
import io.tiklab.matflow.support.variable.service.VariableService;
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
 * @pi.groupName: 流水线变量控制器
 */
@RestController
@RequestMapping("/pipelineVariable")
public class VariableController {

    @Autowired
    VariableService variableServer;

    /**
     * @pi.name:创建变量
     * @pi.path:/pipelineVariable/createVariable
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=variable
     */
    @RequestMapping(path="/createVariable",method = RequestMethod.POST)
    public Result<String> createVariable(@RequestBody @NotNull @Valid Variable variable){
        String variableId = variableServer.createVariable(variable);
        return Result.ok(variableId);
    }

    /**
     * @pi.name:删除变量
     * @pi.path:/pipelineVariable/deleteVariable
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=varId;dataType=string;value=varId;
     */
    @RequestMapping(path="/deleteVariable",method = RequestMethod.POST)
    public Result<Void> deleteVariable( @NotNull  String varId){
         variableServer.deleteVariable(varId);
        return Result.ok();
    }

    /**
     * @pi.name:更新变量
     * @pi.path:/pipelineVariable/createVariable
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=variable
     */
    @RequestMapping(path="/updateVariable",method = RequestMethod.POST)
    public Result<Void> updateVariable(@RequestBody @NotNull @Valid Variable variable){
        variableServer.updateVariable(variable);
        return Result.ok();
    }

    /**
     * @pi.name:查询任务变量
     * @pi.path:/pipelineVariable/findAllVariable
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=taskId;dataType=string;value=taskId;
     */
    @RequestMapping(path="/findAllVariable",method = RequestMethod.POST)
    // @ApiMethod(name = "findAllVariable",desc = "查询变量")
    // @ApiParam(name = "taskId",desc = "taskId",required = true)
    public Result< List<Variable>> findAllVariable(@NotNull String taskId){
        List<Variable> allVariable = variableServer.findAllVariable(taskId);
        return Result.ok(allVariable);
    }



}































































