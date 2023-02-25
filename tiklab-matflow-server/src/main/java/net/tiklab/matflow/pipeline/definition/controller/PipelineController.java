package net.tiklab.matflow.pipeline.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineMessageList;
import net.tiklab.matflow.pipeline.definition.service.PipelineRelationService;
import net.tiklab.matflow.pipeline.definition.service.PipelineService;
import net.tiklab.matflow.pipeline.instance.model.PipelineAllInstanceQuery;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.definition.model.PipelineOverview;
import net.tiklab.matflow.pipeline.instance.model.PipelineOpen;
import net.tiklab.matflow.pipeline.instance.model.PipelineFollow;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.user.user.model.User;
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

@RestController
@RequestMapping("/pipeline")
@Api(name = "PipelineController",desc = "流水线")
public class PipelineController {

    private static Logger logger = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineRelationService relationServer;

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

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);

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
    public Result<List<PipelineMessageList>> findLikePipeline(@NotNull String pipelineName, String userId){

        List<PipelineMessageList> pipelineQueryList = pipelineService.findLikePipeline(pipelineName,userId);

        return Result.ok(pipelineQueryList);
    }

    //查询用户所有流水线
    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserPipeline",desc = "查询用户所有流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineOverview>> findUserPipeline(@NotNull String userId){

        List<PipelineMessageList> userPipeline = pipelineService.findUserPipelineExecMessage(userId);

        return Result.ok(userPipeline);
    }

    //查询用户收藏的流水线
    @RequestMapping(path="/findUserFollowPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserFollowPipeline",desc = "查询用户收藏的流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineOverview>> findUserFollowPipeline(@NotNull String userId){

        List<PipelineMessageList> userPipeline = pipelineService.findUserFollowPipeline(userId);

        return Result.ok(userPipeline);
    }


    @RequestMapping(path="/findPipelineUser",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineUser",desc = "查询此拥有流水线的用户")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<User>> findPipelineUser(@NotNull String pipelineId){

        List<User>  dmUser = relationServer.findPipelineUser(pipelineId);

        return Result.ok(dmUser);
    }

    @RequestMapping(path="/pipelineCensus",method = RequestMethod.POST)
    @ApiMethod(name = "pipelineCensus",desc = "查询流水线最近状态")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineOverview> pipelineCensus(@NotNull String pipelineId){

        PipelineOverview buildStatus = relationServer.census(pipelineId);

        return Result.ok(buildStatus);
    }

    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOpen",desc = "查询流水线最近状态")
    @ApiParam(name = "number",desc = "数量",required = true)
    public Result< List<PipelineOpen>> findAllOpen(int number){

        List<PipelineOpen> openList = pipelineService.findAllOpen(number);

        return Result.ok(openList);
    }


    @RequestMapping(path="/updateFollow",method = RequestMethod.POST)
    @ApiMethod(name = "updateFollow",desc = "更新收藏")
    @ApiParam(name = "pipelineFollow",desc = "收藏信息",required = true)
    public Result<Void>  updateFollow( @RequestBody @Valid @NotNull PipelineFollow pipelineFollow){
         relationServer.updateFollow(pipelineFollow);
        return Result.ok();
    }

    @RequestMapping(path="/findUserAllHistory",method = RequestMethod.POST)
    @ApiMethod(name = "findUserAllHistory",desc = "分页")
    @ApiParam(name = "pipelineHistoryQuery",desc = "分页信息",required = true)
    public Result<Pagination<PipelineInstance>>  findUserAllHistory(@RequestBody @Valid @NotNull PipelineAllInstanceQuery pipelineHistoryQuery){
        Pagination<PipelineInstance> userAllHistory = pipelineService.findUserAllHistory(pipelineHistoryQuery);
        return Result.ok(userAllHistory);
    }

}


















