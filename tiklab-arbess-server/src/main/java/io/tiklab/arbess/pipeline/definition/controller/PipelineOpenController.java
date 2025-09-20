package io.tiklab.arbess.pipeline.definition.controller;

import io.tiklab.arbess.pipeline.definition.model.PipelineOpen;
import io.tiklab.arbess.pipeline.definition.model.PipelineOpenQuery;
import io.tiklab.arbess.pipeline.definition.service.PipelineOpenService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线最近打开控制器
 */
@RestController
@RequestMapping("/open")
public class PipelineOpenController {

    @Autowired
    PipelineOpenService openService;


    @RequestMapping(path="/findOpenPage",method = RequestMethod.POST)
    public Result<Pagination<PipelineOpen>> findOpenPage(@RequestBody @Valid @NotNull PipelineOpenQuery query){

        Pagination<PipelineOpen> openPage = openService.findOpenPage(query);

        return Result.ok(openPage);
    }

    /**
     * @pi.name:更新最近打开
     * @pi.url:/open/updateOpen
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=流水线id;
     */
    @RequestMapping(path="/updateOpen",method = RequestMethod.POST)
    public Result<Void> updatePipelineOpen(@NotNull String pipelineId){

       openService.updatePipelineOpen(pipelineId);

        return Result.ok();
    }

}




























