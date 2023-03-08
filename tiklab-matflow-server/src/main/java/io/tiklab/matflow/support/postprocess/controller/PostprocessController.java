package io.tiklab.matflow.support.postprocess.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.support.postprocess.model.Postprocess;
import io.tiklab.matflow.support.postprocess.service.PostprocessService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/postprocess")
@Api(name = "PostprocessController",desc = "流水线后置配置")
public class PostprocessController {

    @Autowired
    PostprocessService postServer;

    //更新信息
    @RequestMapping(path="/createPost",method = RequestMethod.POST)
    @ApiMethod(name = "createPost",desc = "创建后置配置")
    @ApiParam(name = "postprocess",desc = "postprocess",required = true)
    public Result<String> createPost(@RequestBody @NotNull @Valid Postprocess postprocess){
        String postId = postServer.createPostTask(postprocess);
        return Result.ok(postId);
    }

    @RequestMapping(path="/updatePost",method = RequestMethod.POST)
    @ApiMethod(name = "updatePost",desc = "更新后置配置")
    @ApiParam(name = "postprocess",desc = "postprocess",required = true)
    public Result<Void> updatePost(@RequestBody @NotNull @Valid Postprocess postprocess){
        postServer.updatePostTask(postprocess);
        return Result.ok();
    }

    @RequestMapping(path="/findPipelinePost",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "taskId",desc = "流水线id",required = true)
    public Result<List<Postprocess>> findAllPipelinePostTask(@NotNull String pipelineId) {
        List<Postprocess> list = postServer.findAllPipelinePostTask(pipelineId);
        return Result.ok(list);
    }

    @RequestMapping(path="/findTaskPost",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "taskId",desc = "流水线id",required = true)
    public Result< List<Postprocess>> findAllTaskPostTask(@NotNull String taskId) {
        List<Postprocess> list = postServer.findAllTaskPostTask(taskId);
        return Result.ok(list);
    }

    @RequestMapping(path="/deletePost",method = RequestMethod.POST)
    @ApiMethod(name = "deletePost",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "postprocessId",desc = "后置任务id",required = true)
    public Result<Void> deletePost(@NotNull String postprocessId) {
         postServer.deletePostTask(postprocessId);
        return Result.ok();
    }





}
