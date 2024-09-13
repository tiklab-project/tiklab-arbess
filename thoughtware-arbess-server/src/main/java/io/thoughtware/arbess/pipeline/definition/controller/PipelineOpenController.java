package io.thoughtware.arbess.pipeline.definition.controller;

import io.thoughtware.arbess.pipeline.definition.model.PipelineOpen;
import io.thoughtware.arbess.pipeline.definition.service.PipelineOpenService;
import io.thoughtware.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线最近打开控制器
 */
@RestController
@RequestMapping("/open")
public class PipelineOpenController {

    @Autowired
    PipelineOpenService openService;

    /**
     * @pi.name:查询最近打开
     * @pi.path:/open/findAllOpen
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=number;dataType=int;value=查询数据量;
     */
    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    public Result<List<PipelineOpen>> findAllOpen(@NotNull int number){

        List<PipelineOpen> openList = openService.findUserAllOpen(number);

        return Result.ok(openList);
    }

    /**
     * @pi.name:更新最近打开
     * @pi.path:/open/updateOpen
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




























