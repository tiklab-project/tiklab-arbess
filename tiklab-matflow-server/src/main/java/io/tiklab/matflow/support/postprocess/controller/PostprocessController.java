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

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线后置配置控制器
 */
@RestController
@RequestMapping("/postprocess")
public class PostprocessController {

    @Autowired
    PostprocessService postServer;

    /**
     * @pi.name:创建流水线后置配置
     * @pi.path:/postprocess/createPost
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=postprocess
     */
    @RequestMapping(path="/createPost",method = RequestMethod.POST)
    public Result<String> createPost(@RequestBody @NotNull @Valid Postprocess postprocess){
        String postId = postServer.createPostTask(postprocess);
        return Result.ok(postId);
    }

    /**
     * @pi.name:更新流水线后置配置
     * @pi.path:/postprocess/createPost
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=postprocess
     */
    @RequestMapping(path="/updatePost",method = RequestMethod.POST)
    public Result<Void> updatePost(@RequestBody @NotNull @Valid Postprocess postprocess){
        postServer.updatePostTask(postprocess);
        return Result.ok();
    }

    /**
     * @pi.name:查询流水线后置配置信息
     * @pi.path:/postprocess/findPipelinePost
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findPipelinePost",method = RequestMethod.POST)
    public Result<List<Postprocess>> findAllPipelinePostTask(@NotNull String pipelineId) {
        List<Postprocess> list = postServer.findAllPipelinePostTask(pipelineId);
        return Result.ok(list);
    }

    /**
     * @pi.name:查询流水线任务后置配置信息
     * @pi.path:/postprocess/findTaskPost
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findTaskPost",method = RequestMethod.POST)
    public Result< List<Postprocess>> findAllTaskPostTask(@NotNull String taskId) {
        List<Postprocess> list = postServer.findAllTaskPostTask(taskId);
        return Result.ok(list);
    }

    /**
     * @pi.name:删除流水线任务后置配置信息
     * @pi.path:/postprocess/deletePost
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=postprocessId;dataType=string;value=postprocessId;
     */
    @RequestMapping(path="/deletePost",method = RequestMethod.POST)
    public Result<Void> deletePost(@NotNull String postprocessId) {
         postServer.deletePostTask(postprocessId);
        return Result.ok();
    }

    /**
     * @pi.name:查询后置配置信息
     * @pi.path:/postprocess/findOnePost
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=postprocessId;dataType=string;value=postprocessId;
     */
    @RequestMapping(path="/findOnePost",method = RequestMethod.POST)
    public Result<Postprocess> findOnePostOrTask(@NotNull String postprocessId) {
        Postprocess postOrTask = postServer.findOnePostOrTask(postprocessId);
        return Result.ok(postOrTask);
    }



}
