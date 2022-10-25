package net.tiklab.matflow.orther.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.execute.model.FileTree;
import net.tiklab.matflow.execute.model.GitCommit;
import net.tiklab.matflow.orther.model.PipelineActivity;
import net.tiklab.matflow.orther.model.PipelineMessage;
import net.tiklab.matflow.orther.model.PipelineTask;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.orther.service.PipelineWorkSpaceService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineWorkSpace")
@Api(name = "PipelineWorkSpaceController",desc = "流水线工作空间")
public class PipelineWorkSpaceController {

    @Autowired
    PipelineWorkSpaceService pipelineWorkSpaceService;

    @Autowired
    PipelineHomeService pipelineHomeService;


    @RequestMapping(path="/fileTree",method = RequestMethod.POST)
    @ApiMethod(name = "fileTree",desc = "判断是否执行")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<FileTree>> fileTree(@NotNull String userId,String pipelineId) {
        List<FileTree> map = pipelineWorkSpaceService.fileTree(pipelineId,userId);
        return Result.ok(map);
    }

    @RequestMapping(path="/readFile",method = RequestMethod.POST)
    @ApiMethod(name = "readFile",desc = "判断是否执行")
    @ApiParam(name = "path",desc = "文件地址",required = true)
    public Result< List<String>> readFile(@NotNull String path) {
        List<String> s = PipelineUntil.readFile(path);
        return Result.ok(s);
    }

    @RequestMapping(path="/getSubmitMassage",method = RequestMethod.POST)
    @ApiMethod(name = "getSubmitMassage",desc = "获取提交信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<List<GitCommit>>> getSubmitMassage(String pipelineId){
        List<List<GitCommit>> submitMassage = pipelineWorkSpaceService.getSubmitMassage(pipelineId);
        return Result.ok(submitMassage);
    }

    @RequestMapping(path="/findLog",method = RequestMethod.POST)
    @ApiMethod(name = "findLog",desc = "获取提交信息")
    public Result<List<PipelineActivity>> findLog(){
        List<PipelineActivity> log = pipelineHomeService.findLog();
        return Result.ok(log);
    }

    @RequestMapping(path="/findTask",method = RequestMethod.POST)
    @ApiMethod(name = "findTask",desc = "获取提交信息")
    public Result<List<PipelineTask>> findTask(){
        List<PipelineTask> task = pipelineHomeService.findTask();
        return Result.ok(task);
    }

    @RequestMapping(path="/findMessage",method = RequestMethod.POST)
    @ApiMethod(name = "findMessage",desc = "获取提交信息")
    public Result<List<PipelineMessage>> findMessage(){
        List<PipelineMessage> message = pipelineHomeService.findMessage();
        return Result.ok(message);
    }


}
