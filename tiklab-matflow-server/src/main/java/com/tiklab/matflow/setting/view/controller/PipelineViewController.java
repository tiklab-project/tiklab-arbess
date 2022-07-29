package com.tiklab.matflow.setting.view.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.tiklab.matflow.setting.view.model.PipelineView;
import com.tiklab.matflow.setting.view.service.PipelineViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/view")
@Api(name = "PipelineViewController",desc = "视图内容")
public class PipelineViewController {

    @Autowired
    PipelineViewService pipelineViewService;

    @RequestMapping(path="/createView",method = RequestMethod.POST)
    @ApiMethod(name = "createView",desc = "添加视图")
    @ApiParam(name = "pipelineView",desc = "pipelineView",required = true)
    public Result<String> createView(@RequestBody @NotNull @Valid PipelineView pipelineView){

        String viewId = pipelineViewService.createView(pipelineView);

        return Result.ok(viewId);
    }


    @RequestMapping(path="/deleteView",method = RequestMethod.POST)
    @ApiMethod(name = "deleteView",desc = "删除视图")
    @ApiParam(name = "viewId",desc = "viewId",required = true)
    public Result<Void> deleteView(@NotNull String viewId){

        pipelineViewService.deleteView(viewId);

        return Result.ok();
    }

    @RequestMapping(path="/updateView",method = RequestMethod.POST)
    @ApiMethod(name = "updateView",desc = "更新视图")
    @ApiParam(name = "pipelineView",desc = "pipelineView",required = true)
    public Result<Void> updateView(@RequestBody @NotNull @Valid PipelineView pipelineView){

        pipelineViewService.updateView(pipelineView);

        return Result.ok();
    }

    @RequestMapping(path="/findOneView",method = RequestMethod.POST)
    @ApiMethod(name = "findOneView",desc = "查询视图")
    @ApiParam(name = "viewId",desc = "viewId",required = true)
    public Result<PipelineView> findOneView(@NotNull String viewId){

        PipelineView view = pipelineViewService.findOneView(viewId);

        return Result.ok(view);
    }

    @RequestMapping(path="/findAllView",method = RequestMethod.POST)
    @ApiMethod(name = "findAllView",desc = "查询所有视图")
    public Result<List<PipelineView>> findAllView(){

        List<PipelineView> allView = pipelineViewService.findAllView();

        return Result.ok(allView);
    }

}
