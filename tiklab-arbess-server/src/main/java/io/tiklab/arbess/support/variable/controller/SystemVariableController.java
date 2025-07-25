package io.tiklab.arbess.support.variable.controller;

import io.tiklab.arbess.support.variable.model.SystemVariable;
import io.tiklab.arbess.support.variable.model.SystemVariableQuery;
import io.tiklab.arbess.support.variable.service.SystemVariableService;
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
@RequestMapping("/system/variable")
public class SystemVariableController {

    @Autowired
    SystemVariableService systemVariableServer;

    /**
     * @pi.name:创建流水线变量
     * @pi.url:/pipelineSystemVariable/createSystemVariable
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=systemVariable
     */
    @RequestMapping(path="/createSystemVariable",method = RequestMethod.POST)
    public Result<String> createSystemVariable(@RequestBody @NotNull @Valid SystemVariable systemVariable){
        String systemVariableId = systemVariableServer.createSystemVariable(systemVariable);
        return Result.ok(systemVariableId);
    }

    /**
     * @pi.name:删除流水线变量
     * @pi.url:/pipelineSystemVariable/deleteSystemVariable
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=varId;dataType=string;value=varId;
     */
    @RequestMapping(path="/deleteSystemVariable",method = RequestMethod.POST)
    public Result<Void> deleteSystemVariable( @NotNull  String id){
         systemVariableServer.deleteSystemVariable(id);
        return Result.ok();
    }

    /**
     * @pi.name:更新流水线变量
     * @pi.url:/pipelineSystemVariable/createSystemVariable
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=systemVariable
     */
    @RequestMapping(path="/updateSystemVariable",method = RequestMethod.POST)
    public Result<Void> updateSystemVariable(@RequestBody @NotNull @Valid SystemVariable systemVariable){
        systemVariableServer.updateSystemVariable(systemVariable);
        return Result.ok();
    }

    @RequestMapping(path="/findSystemVariableList",method = RequestMethod.POST)
    public Result<List<SystemVariable>> findSystemVariableList( @RequestBody @Valid @NotNull SystemVariableQuery systemVariableQuery){
        List<SystemVariable> allSystemVariable = systemVariableServer.findSystemVariableList(systemVariableQuery);
        return Result.ok(allSystemVariable);
    }

    @RequestMapping(path="/findSystemVariablePage",method = RequestMethod.POST)
    public Result<Pagination<SystemVariable>> findSystemVariablePage( @RequestBody @Valid @NotNull SystemVariableQuery systemVariableQuery){
        Pagination<SystemVariable> allSystemVariable = systemVariableServer.findSystemVariablePage(systemVariableQuery);
        return Result.ok(allSystemVariable);
    }


}































































