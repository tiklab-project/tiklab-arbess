package com.tiklab.matfiow.setting.view.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.tiklab.matfiow.setting.view.model.PipelineTop;
import com.tiklab.matfiow.setting.view.service.PipelineTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/top")
@Api(name = "PipelineTopController",desc = "视图内容")
public class PipelineTopController {

    @Autowired
    PipelineTopService pipelineTopService;

    @RequestMapping(path="/createTop",method = RequestMethod.POST)
    @ApiMethod(name = "createTop",desc = "添加视图信息")
    @ApiParam(name = "pipelineTop",desc = "pipelineTop",required = true)
    public Result<String> createTop(@RequestBody @NotNull @Valid PipelineTop pipelineTop){

        String topId = pipelineTopService.createTop(pipelineTop);

        return Result.ok(topId);
    }


    @RequestMapping(path="/deleteTop",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTop",desc = "删除视图内容")
    @ApiParam(name = "topId",desc = "topId",required = true)
    public Result<Void> deleteTop(@NotNull String topId){

        pipelineTopService.deleteTop(topId);

        return Result.ok();
    }

    @RequestMapping(path="/updateTop",method = RequestMethod.POST)
    @ApiMethod(name = "updateTop",desc = "更新视图信息")
    @ApiParam(name = "pipelineTop",desc = "pipelineTop",required = true)
    public Result<Void> updateTop(@RequestBody @NotNull @Valid PipelineTop pipelineTop){

        pipelineTopService.updateTop(pipelineTop);

        return Result.ok();
    }

    @RequestMapping(path="/findOneTop",method = RequestMethod.POST)
    @ApiMethod(name = "findOneTop",desc = "查询视图内容")
    @ApiParam(name = "topId",desc = "topId",required = true)
    public Result<PipelineTop> findOneTop(@NotNull String topId){

        PipelineTop top = pipelineTopService.findOneTop(topId);

        return Result.ok(top);
    }

    @RequestMapping(path="/findAllTop",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTop",desc = "查询所有视图内容")
    public Result< List<PipelineTop>> findAllTop(){

        List<PipelineTop> allTop = pipelineTopService.findAllTop();

        return Result.ok(allTop);
    }


























}
