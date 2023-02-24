package net.tiklab.matflow.support.variable.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.support.variable.model.PipelineVariable;
import net.tiklab.matflow.support.variable.service.PipelineVariableService;
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
@RequestMapping("/pipelineVariable")
@Api(name = "PipelineVariableController",desc = "流水线变量")
public class PipelineVariableController  {

    @Autowired
    PipelineVariableService variableServer;

    @RequestMapping(path="/createVariable",method = RequestMethod.POST)
    @ApiMethod(name = "createVariable",desc = "创建变量")
    @ApiParam(name = "variable",desc = "variable",required = true)
    public Result<String> createVariable(@RequestBody @NotNull @Valid PipelineVariable variable){
        String variableId = variableServer.createVariable(variable);
        return Result.ok(variableId);
    }


    @RequestMapping(path="/deleteVariable",method = RequestMethod.POST)
    @ApiMethod(name = "deleteVariable",desc = "删除变量")
    @ApiParam(name = "varId",desc = "varId",required = true)
    public Result<Void> deleteVariable( @NotNull  String varId){
         variableServer.deleteVariable(varId);
        return Result.ok();
    }


    @RequestMapping(path="/updateVariable",method = RequestMethod.POST)
    @ApiMethod(name = "updateVariable",desc = "更新变量")
    @ApiParam(name = "variable",desc = "variable",required = true)
    public Result<Void> updateVariable(@RequestBody @NotNull @Valid PipelineVariable variable){
        variableServer.updateVariable(variable);
        return Result.ok();
    }

    @RequestMapping(path="/findAllVariable",method = RequestMethod.POST)
    @ApiMethod(name = "findAllVariable",desc = "查询变量")
    @ApiParam(name = "taskId",desc = "taskId",required = true)
    public Result< List<PipelineVariable>> findAllVariable(@NotNull String taskId){
        List<PipelineVariable> allVariable = variableServer.findAllVariable(taskId);
        return Result.ok(allVariable);
    }



}































































