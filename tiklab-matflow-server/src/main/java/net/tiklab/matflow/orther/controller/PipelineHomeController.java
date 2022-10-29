package net.tiklab.matflow.orther.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.orther.model.PipelineFollow;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.service.PipelineOpenService;
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
@RequestMapping("/pipelineHome")
@Api(name = "PipelineHomeController",desc = "首页")
public class PipelineHomeController {

    @Autowired
    PipelineHomeService pipelineHomeService;

    @Autowired
    PipelineOpenService pipelineOpenService;



    @RequestMapping(path="/updateFollow",method = RequestMethod.POST)
    @ApiMethod(name = "updateFollow",desc = "更新收藏")
    @ApiParam(name = "pipelineFollow",desc = "收藏信息",required = true)
    public Result<String>  updateFollow( @RequestBody @Valid @NotNull PipelineFollow pipelineFollow){
        String s = pipelineHomeService.updateFollow(pipelineFollow);
        return Result.ok(s);
    }







}
