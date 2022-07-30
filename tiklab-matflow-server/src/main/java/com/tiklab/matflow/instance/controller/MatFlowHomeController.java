package com.tiklab.matflow.instance.controller;


import com.tiklab.core.Result;
import com.tiklab.matflow.definition.model.MatFlowStatus;
import com.tiklab.matflow.instance.model.MatFlowActionQuery;
import com.tiklab.matflow.instance.model.MatFlowExecState;
import com.tiklab.matflow.instance.model.MatFlowOpen;
import com.tiklab.matflow.instance.service.MatFlowHomeService;
import com.tiklab.matflow.instance.model.MatFlowFollow;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/matFlowHome")
@Api(name = "MatFlowHomeController",desc = "首页")
public class MatFlowHomeController {

    @Autowired
    MatFlowHomeService matFlowHomeService;

    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOpen",desc = "最近打开的流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<MatFlowOpen>>  findAllOpen(@NotNull String userId){
        List<MatFlowOpen> allOpen = matFlowHomeService.findAllOpen(userId);
        return Result.ok(allOpen);
    }

    @RequestMapping(path="/findAllFollow",method = RequestMethod.POST)
    @ApiMethod(name = "findAllFollow",desc = "查询所有收藏")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<MatFlowOpen>>  findAllFollow(@NotNull String userId){
        List<MatFlowStatus> allFollow = matFlowHomeService.findAllFollow(userId);
        return Result.ok(allFollow);
    }

    @RequestMapping(path="/updateFollow",method = RequestMethod.POST)
    @ApiMethod(name = "updateFollow",desc = "更新收藏")
    @ApiParam(name = "matFlowFollow",desc = "收藏信息",required = true)
    public Result<String>  updateFollow( @RequestBody @Valid @NotNull MatFlowFollow matFlowFollow){
        String s = matFlowHomeService.updateFollow(matFlowFollow);
        return Result.ok(s);
    }

    @RequestMapping(path="/findUserMatFlow",method = RequestMethod.POST)
    @ApiMethod(name = "findUserMatFlow",desc = "最近打开的流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<MatFlowStatus>>  findUserMatFlow(@NotNull String userId){
        List<MatFlowStatus> allOpen = matFlowHomeService.findUserMatFlow(userId);
        return Result.ok(allOpen);
    }


    @RequestMapping(path="/runState",method = RequestMethod.POST)
    @ApiMethod(name = "runState",desc = "获取用户7天内的构建状态")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<MatFlowStatus>> runState(@NotNull String userId){
        List<MatFlowExecState> list = matFlowHomeService.runState(userId);
        return Result.ok(list);
    }


    //@RequestMapping(path="/findAllAction",method = RequestMethod.POST)
    //@ApiMethod(name = "findAllAction",desc = "获取用户动态")
    //@ApiParam(name = "userId",desc = "用户id",required = true)
    //public Result< List<MatFlowAction>> findAllAction(@NotNull String userId){
    //    List<MatFlowAction> allAction = matFlowHomeService.findAllAction(userId);
    //    return Result.ok(allAction);
    //}


    @RequestMapping(path="findUserAction",method = RequestMethod.POST)
    @ApiMethod(name = "findUserAction",desc = "获取用户动态")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<MatFlowActionQuery> findUserAction(@RequestBody @NotNull MatFlowActionQuery matFlowActionQuery){
        MatFlowActionQuery allAction = matFlowHomeService.findUserAction(matFlowActionQuery);
        return Result.ok(allAction);
    }












}
