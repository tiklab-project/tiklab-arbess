package io.tiklab.matflow.pipeline.definition.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import io.tiklab.matflow.pipeline.definition.model.PipelineRecently;
import io.tiklab.matflow.pipeline.definition.service.PipelineService;
import io.tiklab.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: pipeline
 */
@RestController
@RequestMapping("/pipeline")
public class PipelineController {

    @Autowired
    PipelineService pipelineService;

    /**
     * @pi.name:createPipeline
     * @pi.path:/pipeline/createPipeline
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=pipeline
     */
    @RequestMapping(path="/createPipeline",method = RequestMethod.POST)
    public Result<String> createPipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

        String pipelineId = pipelineService.createPipeline(pipeline);

        return Result.ok(pipelineId);
    }

    /**
     * @pi.name:findAllPipeline
     * @pi.path:/pipeline/findAllPipeline
     * @pi.method:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllPipeline",method = RequestMethod.POST)
    public Result<List<Pipeline>> findAllPipeline(){

        List<Pipeline> selectAllPipeline = pipelineService.findAllPipeline();

        return Result.ok(selectAllPipeline);
    }

    /**
     * @pi.name:deletePipeline
     * @pi.path:/pipeline/deletePipeline
     * @pi.method:post
     * @pi.request-type:formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/deletePipeline",method = RequestMethod.POST)
    public Result<Void> deletePipeline(@NotNull String pipelineId){

        pipelineService.deletePipeline(pipelineId);

        return Result.ok();
    }

    /**
     * @pi.name:findOnePipeline
     * @pi.path:/pipeline/findOnePipeline
     * @pi.method:post
     * @pi.request-type:formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findOnePipeline",method = RequestMethod.POST)
    public Result<Pipeline> findOnePipeline(@NotNull String pipelineId){

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);

        return Result.ok(pipeline);
    }

    /**
     * @pi.name:updatePipeline
     * @pi.path:/pipeline/updatePipeline
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=pipeline
     */
    @RequestMapping(path="/updatePipeline",method = RequestMethod.POST)
    public Result<Void> updatePipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

         pipelineService.updatePipeline(pipeline);

        return Result.ok();
    }


    /**
     * @pi.name:findUserPipelinePage
     * @pi.path:/pipeline/findUserPipelinePage
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=query
     */
    @RequestMapping(path="/findUserPipelinePage",method = RequestMethod.POST)
    public Result< Pagination<Pipeline>> findUserPipelinePage(@RequestBody @NotNull @Valid PipelineQuery query){

        Pagination<Pipeline> userPipeline = pipelineService.findUserPipelinePage(query);

        return Result.ok(userPipeline);
    }

    /**
     * @pi.name:findUserPipeline
     * @pi.path:/pipeline/findUserPipeline
     * @pi.method:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    public Result< List<Pipeline>> findAllUserPipeline(){
        List<Pipeline> userPipeline = pipelineService.findUserPipeline();

        return Result.ok(userPipeline);
    }

    /**
     * @pi.name:findPipelineUser
     * @pi.path:/pipeline/findPipelineUser
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findPipelineUser",method = RequestMethod.POST)
    public Result<List<User>> findPipelineUser(@NotNull String pipelineId){

        List<User>  dmUser = pipelineService.findPipelineUser(pipelineId);

        return Result.ok(dmUser);
    }

    /**
     * @pi.name:findPipelineRecently
     * @pi.path:/pipeline/findPipelineRecently
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=number;dataType=int;value=5;
     */
    @RequestMapping(path="/findPipelineRecently",method = RequestMethod.POST)
    public Result<List<PipelineRecently>> findPipelineRecently(Integer number){

        List<PipelineRecently> userPipeline = pipelineService.findPipelineRecently(number);

        return Result.ok(userPipeline);
    }


    /**
     * @pi.name:pipelineClone
     * @pi.path:/pipeline/pipelineClone
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/pipelineClone",method = RequestMethod.POST)
    public Result<Void> pipelineClone(@NotNull String pipelineId,@NotNull String pipelineName){

        pipelineService.pipelineClone(pipelineId,pipelineName);

        return Result.ok();
    }


    /**
     * @pi.name:findPipelineCloneName
     * @pi.path:/pipeline/findPipelineCloneName
     * @pi.method:post
     * @pi.request-type: formdata;
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findPipelineCloneName",method = RequestMethod.POST)
    public Result<String> pindPipelineCloneName(@NotNull String pipelineId){

        String name = pipelineService.findPipelineCloneName(pipelineId);

        return Result.ok(name);
    }

}


















