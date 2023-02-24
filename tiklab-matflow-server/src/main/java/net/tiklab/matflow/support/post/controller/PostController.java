package net.tiklab.matflow.support.post.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.support.post.model.Post;
import net.tiklab.matflow.support.post.service.PostService;
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
@RequestMapping("/pipelinePost")
@Api(name = "PostController",desc = "流水线后置配置")
public class PostController {

    @Autowired
    PostService postServer;

    //更新信息
    @RequestMapping(path="/createPost",method = RequestMethod.POST)
    @ApiMethod(name = "createPost",desc = "创建后置配置")
    @ApiParam(name = "post",desc = "post",required = true)
    public Result<String> createPost(@RequestBody @NotNull @Valid Post post){
        String postId = postServer.createPostTask(post);
        return Result.ok(postId);
    }


    @RequestMapping(path="/updatePost",method = RequestMethod.POST)
    @ApiMethod(name = "updatePost",desc = "更新后置配置")
    @ApiParam(name = "post",desc = "post",required = true)
    public Result<Void> updatePost(@RequestBody @NotNull @Valid Post post){
         postServer.updatePostTask(post);
        return Result.ok();
    }


    //根据流水线id查询配置
    @RequestMapping(path="/findAllPost",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfig",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "taskId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllConfig(@NotNull String taskId) {
        List<Object> list = postServer.findAllPostTask(taskId);
        return Result.ok(list);
    }


    //删除配置
    @RequestMapping(path="/deletePost",method = RequestMethod.POST)
    @ApiMethod(name = "deletePost",desc = "根据流水线id查询后置配置")
    @ApiParam(name = "configId",desc = "流水线id",required = true)
    public Result<Void> deletePost(@NotNull String configId) {
         postServer.deletePostTask(configId);
        return Result.ok();
    }





}
