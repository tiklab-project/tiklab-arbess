package io.tiklab.arbess.support.variable.controller;

import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.model.VariableQuery;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
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
@RequestMapping("/pipeline/variable")
public class VariableController {

    @Autowired
    VariableService variableServer;

    /**
     * @pi.name:创建流水线变量
     * @pi.url:/pipelineVariable/createVariable
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
     * @pi.name:删除流水线变量
     * @pi.url:/pipelineVariable/deleteVariable
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
     * @pi.name:更新流水线变量
     * @pi.url:/pipelineVariable/createVariable
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=variable
     */
    @RequestMapping(path="/updateVariable",method = RequestMethod.POST)
    public Result<Void> updateVariable(@RequestBody @NotNull @Valid Variable variable){
        variableServer.updateVariable(variable);
        return Result.ok();
    }

    @RequestMapping(path="/findVariableList",method = RequestMethod.POST)
    public Result<List<Variable>> findVariableList( @RequestBody @Valid @NotNull VariableQuery variableQuery){
        List<Variable> allVariable = variableServer.findVariableList(variableQuery);
        return Result.ok(allVariable);
    }

    @RequestMapping(path="/findVariablePage",method = RequestMethod.POST)
    public Result<Pagination<Variable>> findVariablePage( @RequestBody @Valid @NotNull VariableQuery variableQuery){
        Pagination<Variable> allVariable = variableServer.findVariablePage(variableQuery);
        return Result.ok(allVariable);
    }


}































































