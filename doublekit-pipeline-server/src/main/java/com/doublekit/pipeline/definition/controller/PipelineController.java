package com.doublekit.pipeline.definition.controller;


import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.definition.service.PipelineService;
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

    //创建
    @RequestMapping(path="/createPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "createPipeline",desc = "创建流水线")
    @ApiParam(name = "pipeline",desc = "pipeline",required = true)
    public Result<String> createPipeline(@RequestBody @NotNull @Valid Pipeline pipeline,String userId){

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
    public Result deletePipeline(@NotNull String pipelineId){

        pipelineService.deletePipeline(pipelineId);

        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findOnePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipeline",desc = "查询单个流水线")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Pipeline> findOnePipeline(@NotNull String pipelineId){

        Pipeline pipeline = pipelineService.findPipeline(pipelineId);

        return Result.ok(pipeline);
    }

    //更新
    @RequestMapping(path="/updatePipeline",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipeline",desc = "更新流水线")
    @ApiParam(name = "pipeline",desc = "pipeline",required = true)
    public Result<String> updatePipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

        int i = pipelineService.updatePipeline(pipeline);

        return Result.ok(i);
    }

    //模糊查询
    @RequestMapping(path="/findOneName",method = RequestMethod.POST)
    @ApiMethod(name = "findOneName",desc = "模糊查询")
    @ApiParam(name = "pipelineName",desc = "模糊查询条件",required = true)
    public Result<List<Pipeline>> findOneName(@NotNull String pipelineName){

        List<Pipeline> pipelineQueryList = pipelineService.findLike(pipelineName);

        return Result.ok(pipelineQueryList);
    }
    //查询所有
    @RequestMapping(path="/findAllPipelineStatus",method = RequestMethod.POST)
    @ApiMethod(name = "findOnePipelineStatus",desc = "查询所有流水线状态")
    public Result<List<PipelineStatus>> findOnePipelineStatus(String userId){

        List<PipelineStatus> allStatus = pipelineService.findAllStatus(userId);

        return Result.ok(allStatus);
    }


    @RequestMapping(path="/findUserPipeline",method = RequestMethod.POST)
    @ApiMethod(name = "findUserPipeline",desc = "查询用户流水线")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<Pipeline>> findUserPipeline(@NotNull String userId){

        List<Pipeline> pipelineQueryList = pipelineService.findUserPipeline(userId);

        return Result.ok(pipelineQueryList);
    }
}
