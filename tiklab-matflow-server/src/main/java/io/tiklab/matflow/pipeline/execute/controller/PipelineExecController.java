package io.tiklab.matflow.pipeline.execute.controller;


import io.tiklab.core.Result;
import io.tiklab.matflow.pipeline.execute.service.PipelineExecService;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线执行控制器
 */
@RestController
@RequestMapping("/exec")
public class PipelineExecController {

    @Autowired
    PipelineExecService pipelineExecService;

    /**
     * @pi.name:执行流水线
     * @pi.path:/exec/start
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/start",method = RequestMethod.POST)
    public Result<PipelineInstance> start(@NotNull String pipelineId ){
        PipelineInstance start = pipelineExecService.start(pipelineId,1);
        return Result.ok(start);
    }

    /**
     * @pi.name:停止流水线执行
     * @pi.path:/exec/stop
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/stop",method = RequestMethod.POST)
    public Result<Void> killInstance(@NotNull String pipelineId) {
        pipelineExecService.stop(pipelineId);
        return Result.ok();
    }



}




























