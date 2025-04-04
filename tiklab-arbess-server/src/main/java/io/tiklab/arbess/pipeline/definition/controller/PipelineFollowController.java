package io.tiklab.arbess.pipeline.definition.controller;

import io.tiklab.arbess.pipeline.definition.model.PipelineFollow;
import io.tiklab.arbess.pipeline.definition.service.PipelineFollowService;
import io.tiklab.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @pi.protocol: http
 * @pi.groupName: 流水线收藏控制器
 */
@RestController
@RequestMapping("/follow")
public class PipelineFollowController {

    @Autowired
    PipelineFollowService followService;


    /**
     * @pi.name:更新流水线收藏
     * @pi.url:/follow/updateFollow
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=pipelineFollow
     */
    @RequestMapping(path="/updateFollow",method = RequestMethod.POST)
    public Result<Void> updateFollow(@RequestBody @Valid @NotNull PipelineFollow pipelineFollow){
        followService.updateFollow(pipelineFollow);
        return Result.ok();
    }


}































