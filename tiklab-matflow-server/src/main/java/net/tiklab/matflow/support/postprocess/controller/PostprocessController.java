package net.tiklab.matflow.support.postprocess.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.support.postprocess.model.Postprocess;
import net.tiklab.matflow.support.postprocess.service.PostprocessService;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
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
    public Result<List<Tasks>> findAllPipelinePostTask(@NotNull String pipelineId) {
        List<Tasks> list = postServer.findAllPipelinePostTask(pipelineId);
        return Result.ok(list);
    }

    @RequestMapping(path="/findTaskPost",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "taskId",desc = "流水线id",required = true)
    public Result<List<Tasks>> findAllTaskPostTask(@NotNull String taskId) {
        List<Tasks> list = postServer.findAllTaskPostTask(taskId);
        return Result.ok(list);
    }

    @RequestMapping(path="/deletePost",method = RequestMethod.POST)
    @ApiMethod(name = "deletePost",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "postId",desc = "后置任务id",required = true)
    public Result<Void> deletePost(@NotNull String postId) {
         postServer.deletePostTask(postId);
        return Result.ok();
    }





}
