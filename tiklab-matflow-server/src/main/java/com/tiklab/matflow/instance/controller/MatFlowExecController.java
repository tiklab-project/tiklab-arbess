package com.tiklab.matflow.instance.controller;


import com.tiklab.core.Result;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.service.MatFlowExecService;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/matFlowExec")
@Api(name = "MatFlowExecController",desc = "流水线执行")
public class MatFlowExecController {

    @Autowired
    MatFlowExecService matFlowExecService;

    //开始构建
    @RequestMapping(path="/start",method = RequestMethod.POST)
    @ApiMethod(name = "start",desc = "执行")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<Integer> start(@NotNull String matFlowId ,String userId) throws Exception {
        int start = matFlowExecService.start(matFlowId,userId);
        return Result.ok(start);
    }


    @RequestMapping(path="/findState",method = RequestMethod.POST)
    @ApiMethod(name = "findState",desc = "执行")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<MatFlowExecHistory> findState(String matFlowId)  {
        MatFlowExecHistory instanceState = matFlowExecService.findInstanceState(matFlowId);
        return Result.ok(instanceState);
    }

    @RequestMapping(path="/findExecState",method = RequestMethod.POST)
    @ApiMethod(name = "findExecState",desc = "判断是否执行")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<Integer> findExecState(@NotNull String matFlowId) {
        int state = matFlowExecService.findState(matFlowId);
        return Result.ok(state);
    }

    @RequestMapping(path="/killInstance",method = RequestMethod.POST)
    @ApiMethod(name = "killInstance",desc = "判断是否执行")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<Void> killInstance(@NotNull String matFlowId,String userId) {
        matFlowExecService.killInstance(matFlowId,userId);
        return Result.ok();
    }



}
