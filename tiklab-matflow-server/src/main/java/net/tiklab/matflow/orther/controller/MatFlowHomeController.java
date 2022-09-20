package net.tiklab.matflow.orther.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.MatFlowStatus;
import net.tiklab.matflow.execute.model.MatFlowExecState;
import net.tiklab.matflow.orther.model.MatFlowActivity;
import net.tiklab.matflow.orther.model.MatFlowActivityQuery;
import net.tiklab.matflow.orther.model.MatFlowFollow;
import net.tiklab.matflow.orther.model.MatFlowOpen;
import net.tiklab.matflow.orther.service.MatFlowHomeService;
import net.tiklab.matflow.setting.model.Proof;
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


    //@RequestMapping(path="/findAllActivity",method = RequestMethod.POST)
    //@ApiMethod(name = "findAllActivity",desc = "获取用户动态")
    //@ApiParam(name = "userId",desc = "用户id",required = true)
    //public Result< List<MatFlowActivity>> findAllActivity(@NotNull String userId){
    //    List<MatFlowActivity> allActivity = matFlowHomeService.findAllActivity(userId);
    //    return Result.ok(allActivity);
    //}


    @RequestMapping(path="findUserActivity",method = RequestMethod.POST)
    @ApiMethod(name = "findUserActivity",desc = "获取用户动态")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<MatFlowActivityQuery> findUserActivity(@RequestBody @NotNull MatFlowActivityQuery matFlowActivityQuery){
        MatFlowActivityQuery allActivity = matFlowHomeService.findUserActivity(matFlowActivityQuery);
        return Result.ok(allActivity);
    }

    @RequestMapping(path="/findMatFlowProof",method = RequestMethod.POST)
    @ApiMethod(name = "findMatFlowProof",desc = "查询流水线凭证")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<Proof>> findMatFlowProof(@NotNull String userId, String matFlowId, int type){

        List<Proof> matFlowProof = matFlowHomeService.findMatFlowProof(userId,matFlowId,type);

        return Result.ok(matFlowProof);
    }










}
