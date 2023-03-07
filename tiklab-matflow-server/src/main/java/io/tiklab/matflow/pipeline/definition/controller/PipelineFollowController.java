package io.tiklab.matflow.pipeline.definition.controller;

import io.tiklab.matflow.pipeline.definition.model.PipelineFollow;
import io.tiklab.matflow.pipeline.definition.service.PipelineFollowService;
import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 流水线收藏控制器
 */

@RestController
@RequestMapping("/follow")
@Api(name = "PipelineFollowController",desc = "流水线")
public class PipelineFollowController {

    @Autowired
    private PipelineFollowService followService;

    @RequestMapping(path="/updateFollow",method = RequestMethod.POST)
    @ApiMethod(name = "updateFollow",desc = "更新收藏")
    @ApiParam(name = "pipelineFollow",desc = "收藏信息",required = true)
    public Result<Void> updateFollow(@RequestBody @Valid @NotNull PipelineFollow pipelineFollow){
        followService.updateFollow(pipelineFollow);
        return Result.ok();
    }


}































