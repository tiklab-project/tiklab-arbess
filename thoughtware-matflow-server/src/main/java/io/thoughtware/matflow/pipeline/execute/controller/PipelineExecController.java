package io.thoughtware.matflow.pipeline.execute.controller;


import io.thoughtware.matflow.pipeline.execute.model.PipelineRunMsg;
import io.thoughtware.matflow.pipeline.execute.service.PipelineExecService;
import io.thoughtware.core.Result;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=runMsg;
     */
    @RequestMapping(path="/start",method = RequestMethod.POST)
    public Result<PipelineInstance> start(@RequestBody @Valid @NotNull PipelineRunMsg runMsg){
        runMsg.setRunWay(1);
        PipelineInstance start = pipelineExecService.start(runMsg);
        return Result.ok(start);
    }

    /**
     * @pi.name:停止流水线执行
     * @pi.path:/exec/stop
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=流水线id;
     */
    @RequestMapping(path="/stop",method = RequestMethod.POST)
    public Result<Void> stop(@NotNull String pipelineId) {
        pipelineExecService.stop(pipelineId);
        return Result.ok();
    }



}




























