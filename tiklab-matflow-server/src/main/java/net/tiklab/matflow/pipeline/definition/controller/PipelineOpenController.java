package net.tiklab.matflow.pipeline.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.pipeline.definition.model.PipelineOpen;
import net.tiklab.matflow.pipeline.definition.service.PipelineOpenService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/open")
@Api(name = "PipelineController",desc = "流水线")
public class PipelineOpenController {

    @Autowired
    PipelineOpenService openService;

    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOpen",desc = "查询流水线最近状态")
    @ApiParam(name = "number",desc = "数量",required = true)
    public Result<List<PipelineOpen>> findAllOpen(int number){

        List<PipelineOpen> openList = openService.findUserAllOpen(number);

        return Result.ok(openList);
    }



}
