package io.tiklab.arbess.pipeline.execute.controller;


import io.tiklab.arbess.pipeline.execute.model.PipelineKeepOn;
import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.execute.service.PipelineExecService;
import io.tiklab.core.Result;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
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
 * @pi.url:/exec
 */

@RestController
@RequestMapping("/exec")
public class PipelineExecController {

    @Autowired
    PipelineExecService pipelineExecService;

    /**
     * @pi.name:执行流水线
     * @pi.url:/start
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
     */
    @RequestMapping(path="/start",method = RequestMethod.POST)
    public Result<PipelineInstance> start(@RequestBody @Valid @NotNull PipelineRunMsg runMsg){
        runMsg.setRunWay(1);
        PipelineInstance start = pipelineExecService.start(runMsg);
        return Result.ok(start);
    }


    /**
     * @pi.name:执行流水线
     * @pi.url:/rollBackStart
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
     */
    @RequestMapping(path="/rollBackStart",method = RequestMethod.POST)
    public Result<PipelineInstance> rollBackStart(@RequestBody @Valid @NotNull PipelineRunMsg runMsg){
        runMsg.setRunWay(3);
        PipelineInstance start = pipelineExecService.rollBackStart(runMsg);
        return Result.ok(start);
    }

    /**
     * @pi.name:流水线继续执行
     * @pi.url:/keepOn
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: model=io.tiklab.arbess.pipeline.execute.model.PipelineKeepOn;
     */
    @RequestMapping(path="/keepOn",method = RequestMethod.POST)
    public Result<Void> keepOn(@RequestBody @Valid @NotNull PipelineKeepOn keepOn){
        pipelineExecService.keepOn(keepOn);
        return Result.ok();
    }

    /**
     * @pi.name:停止流水线执行
     * @pi.url:/stop
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;desc=流水线id
     */
    @RequestMapping(path="/stop",method = RequestMethod.POST)
    public Result<Void> stop(@NotNull String pipelineId) {
        pipelineExecService.stop(pipelineId);
        return Result.ok();
    }



    @RequestMapping(path="/clean",method = RequestMethod.POST)
    public Result<Void> clean(@NotNull String pipelineId) {
        pipelineExecService.clean(pipelineId);
        return Result.ok();
    }



}




























