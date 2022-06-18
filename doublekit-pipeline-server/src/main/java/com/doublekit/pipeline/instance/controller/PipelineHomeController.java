package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineAction;
import com.doublekit.pipeline.instance.model.PipelineExecState;
import com.doublekit.pipeline.instance.model.PipelineFollow;
import com.doublekit.pipeline.instance.model.PipelineOpen;
import com.doublekit.pipeline.instance.service.PipelineHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineHome")
@Api(name = "PipelineHomeController",desc = "首页")
public class PipelineHomeController {

    @Autowired
    PipelineHomeService pipelineHomeService;

    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOpen",desc = "最近打开的流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineOpen>>  findAllOpen(@NotNull String userId){
        List<PipelineOpen> allOpen = pipelineHomeService.findAllOpen(userId);
        return Result.ok(allOpen);
    }

    @RequestMapping(path="/findAllFollow",method = RequestMethod.POST)
    @ApiMethod(name = "findAllFollow",desc = "查询所有收藏")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineOpen>>  findAllFollow(@NotNull String userId){
        List<PipelineStatus> allFollow = pipelineHomeService.findAllFollow(userId);
        return Result.ok(allFollow);
    }

    @RequestMapping(path="/updateFollow",method = RequestMethod.POST)
    @ApiMethod(name = "updateFollow",desc = "更新收藏")
    @ApiParam(name = "pipelineFollow",desc = "收藏信息",required = true)
    public Result<Void>  updateFollow( @RequestBody @Valid @NotNull PipelineFollow pipelineFollow){
         pipelineHomeService.updateFollow(pipelineFollow);
        return Result.ok();
    }

    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserPipeline",desc = "最近打开的流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineStatus>>  findUserPipeline(@NotNull String userId){
        List<PipelineStatus> allOpen = pipelineHomeService.findUserPipeline(userId);
        return Result.ok(allOpen);
    }


    @RequestMapping(path="/runState",method = RequestMethod.POST)
    @ApiMethod(name = "runState",desc = "获取用户7天内的构建状态")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineStatus>> runState(@NotNull String userId){
        List<PipelineExecState> list = pipelineHomeService.runState(userId);
        return Result.ok(list);
    }


    @RequestMapping(path="/findAllAction",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAction",desc = "获取用户动态")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result< List<PipelineAction>> findAllAction(@NotNull String userId){
        List<PipelineAction> allAction = pipelineHomeService.findAllAction(userId);
        return Result.ok(allAction);
    }















}
