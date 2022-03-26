package com.doublekit.pipeline.definition.controller;


import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
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
import java.util.Map;

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
    public Result<Map<String, String>> createPipeline(@RequestBody @NotNull @Valid Pipeline pipeline){

        Map<String, String> map = pipelineService.createPipeline(pipeline);

        return Result.ok(map);
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

        String pipelineName = pipelineService.updatePipeline(pipeline);

        return Result.ok(pipelineName);
    }

    //模糊查询
    @RequestMapping(path="/findOneName",method = RequestMethod.POST)
    @ApiMethod(name = "findOneName",desc = "模糊查询")
    @ApiParam(name = "pipelineName",desc = "模糊查询条件",required = true)
    public Result<List<Pipeline>> findOneName(@NotNull String pipelineName){

        List<Pipeline> pipelineQueryList = pipelineService.findName(pipelineName);

        return Result.ok(pipelineQueryList);
    }
    //查询所有
    @RequestMapping(path="/findAllPipelineStatus",method = RequestMethod.POST)
    @ApiMethod(name = "findOnePipelineStatus",desc = "查询所有流水线状态")
    public Result<List<PipelineStatus>> findOnePipelineStatus(){

        List<Pipeline> allPipeline = pipelineService.findAllPipeline();

        return Result.ok(allPipeline);
    }
}
