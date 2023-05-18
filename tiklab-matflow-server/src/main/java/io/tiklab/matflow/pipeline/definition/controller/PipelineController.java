package io.tiklab.matflow.pipeline.definition.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineExecMessage;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import io.tiklab.matflow.pipeline.definition.model.PipelineRecently;
import io.tiklab.matflow.pipeline.definition.service.PipelineService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.user.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线控制器
 */

@RestController
@RequestMapping("/pipeline")
@Api(name = "PipelineController",desc = "流水线")
public class PipelineController {

    private static Logger logger = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    PipelineService pipelineService;

    //创建
    @RequestMapping(path="/createPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "createPipeline",desc = "创建流水线")
    @ApiParam(name = "pipeline",desc = "pipeline",required = true)
    public Result<String> createPipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

        String pipelineId = pipelineService.createPipeline(pipeline);

        return Result.ok(pipelineId);
    }

    //查询所有
    @RequestMapping(path="/findAllPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipeline",desc = "查询所有流水线")
    public Result<List<Pipeline>> findAllPipeline(){

        List<Pipeline> selectAllPipeline = pipelineService.findAllPipeline();

        return Result.ok(selectAllPipeline);
    }

    //删除
    @RequestMapping(path="/deletePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipeline",desc = "删除流水线")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> deletePipeline(@NotNull String pipelineId){

        pipelineService.deletePipeline(pipelineId);

        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findOnePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipeline",desc = "查询单个流水线")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Pipeline> findOnePipeline(@NotNull String pipelineId){

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);

        return Result.ok(pipeline);
    }

    //更新
    @RequestMapping(path="/updatePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipeline",desc = "更新流水线")
    @ApiParam(name = "pipeline",desc = "pipeline",required = true)
    public Result<Void> updatePipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

         pipelineService.updatePipeline(pipeline);

        return Result.ok();
    }

    //模糊查询
    @RequestMapping(path="/findLikePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findLikePipeline",desc = "模糊查询")
    @ApiParam(name = "pipelineName",desc = "模糊查询条件",required = true)
    public Result<List<PipelineExecMessage>> findLikePipeline(@NotNull String pipelineName){

        List<PipelineExecMessage> pipelineQueryList = pipelineService.findPipelineByName(pipelineName);

        return Result.ok(pipelineQueryList);
    }

    @RequestMapping(path="/findUserPipelinePage",method = RequestMethod.POST)
    @ApiMethod(name = "findUserPipelinePage",desc = "查询用户所有流水线")
    @ApiParam(name = "query",desc = "查询条件",required = true)
    public Result< Pagination<PipelineExecMessage>> findUserPipelinePage(@RequestBody @NotNull @Valid PipelineQuery query){

        Pagination<PipelineExecMessage> userPipeline = pipelineService.findUserPipelinePage(query);

        return Result.ok(userPipeline);
    }

    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserPipeline",desc = "查询用户所有流水线")
    // @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result< List<Pipeline>> findAllUserPipeline(){
        List<Pipeline> userPipeline = pipelineService.findUserPipeline();

        return Result.ok(userPipeline);
    }

    //查询用户收藏的流水线
    @RequestMapping(path="/findUserFollowPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserFollowPipeline",desc = "查询用户收藏的流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<Pipeline>> findUserFollowPipeline(){

        List<Pipeline> userPipeline = pipelineService.findUserFollowPipeline();

        return Result.ok(userPipeline);
    }


    @RequestMapping(path="/findPipelineUser",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineUser",desc = "查询此拥有流水线的用户")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<User>> findPipelineUser(@NotNull String pipelineId){

        List<User>  dmUser = pipelineService.findPipelineUser(pipelineId);

        return Result.ok(dmUser);
    }

    @RequestMapping(path="/findPipelineRecently",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineRecently",desc = "查询用户最近构建的流水线")
    public Result<List<PipelineRecently>> findPipelineRecently(){

        List<PipelineRecently> userPipeline = pipelineService.findPipelineRecently();

        return Result.ok(userPipeline);
    }


}


















