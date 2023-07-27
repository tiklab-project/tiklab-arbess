package io.tiklab.matflow.pipeline.definition.controller;

import io.tiklab.matflow.pipeline.definition.model.PipelineOpen;
import io.tiklab.matflow.pipeline.definition.service.PipelineOpenService;
import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: PipelineOpen
 */
@RestController
@RequestMapping("/open")
public class PipelineOpenController {

    @Autowired
    PipelineOpenService openService;

    /**
     * @pi.name:findAllOpen
     * @pi.path:/open/findAllOpen
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=number;dataType=int;value=5;
     */
    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    public Result<List<PipelineOpen>> findAllOpen(@NotNull int number){

        List<PipelineOpen> openList = openService.findUserAllOpen(number);

        return Result.ok(openList);
    }

    /**
     * @pi.name:updateOpen
     * @pi.path:/open/updateOpen
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/updateOpen",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineOpen",desc = "查询流水线最近状态")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> updatePipelineOpen(@NotNull String pipelineId){

       openService.updatePipelineOpen(pipelineId);

        return Result.ok();
    }

}




























