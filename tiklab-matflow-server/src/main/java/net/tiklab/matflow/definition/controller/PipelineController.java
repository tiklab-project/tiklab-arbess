package net.tiklab.matflow.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineMassage;
import net.tiklab.matflow.definition.service.PipelineCommonServer;
import net.tiklab.matflow.definition.service.PipelineService;
import net.tiklab.matflow.execute.model.PipelineExecState;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.user.user.model.DmUser;
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
    PipelineCommonServer pipelineCommonServer;

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
    public Result<List<PipelineMassage>> findLikePipeline(@NotNull String pipelineName, String userId){

        List<PipelineMassage> pipelineQueryList = pipelineService.findLikePipeline(pipelineName,userId);

        return Result.ok(pipelineQueryList);
    }

    //查询用户所有流水线
    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserPipeline",desc = "查询用户所有流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineMassage>> findUserPipeline(@NotNull String userId){

        List<PipelineMassage> userPipeline = pipelineService.findUserPipeline(userId);

        return Result.ok(userPipeline);
    }

    //查询用户收藏的流水线
    @RequestMapping(path="/findUserFollowPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserFollowPipeline",desc = "查询用户收藏的流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<PipelineMassage>> findUserFollowPipeline(@NotNull String userId){

        List<PipelineMassage> userPipeline = pipelineService.findUserFollowPipeline(userId);

        return Result.ok(userPipeline);
    }


    @RequestMapping(path="/findPipelineUser",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineUser",desc = "查询此拥有流水线的用户")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<DmUser>> findPipelineUser(@NotNull String pipelineId){

        List<DmUser> dmUser = pipelineCommonServer.findPipelineUser(pipelineId);

        return Result.ok(dmUser);
    }

    @RequestMapping(path="/pipelineCensus",method = RequestMethod.POST)
    @ApiMethod(name = "pipelineCensus",desc = "查询流水线最近状态")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineExecState> pipelineCensus(@NotNull String pipelineId){

        PipelineExecState buildStatus = pipelineService.pipelineCensus(pipelineId);

        return Result.ok(buildStatus);
    }

    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOpen",desc = "查询流水线最近状态")
    public Result< List<PipelineOpen>> findAllOpen(){

        List<PipelineOpen> openList = pipelineService.findAllOpen();

        return Result.ok(openList);
    }

}


















