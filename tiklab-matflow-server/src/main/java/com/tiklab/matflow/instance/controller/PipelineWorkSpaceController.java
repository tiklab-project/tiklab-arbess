package com.tiklab.matflow.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.tiklab.matflow.execute.model.CodeGit.FileTree;
import com.tiklab.matflow.execute.model.CodeGit.GitCommit;
import com.tiklab.matflow.instance.service.PipelineWorkSpaceService;
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
        List<String> s = pipelineWorkSpaceService.readFile(path);
        return Result.ok(s);
    }

    @RequestMapping(path="/getSubmitMassage",method = RequestMethod.POST)
    @ApiMethod(name = "getSubmitMassage",desc = "获取提交信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<List<GitCommit>>> getSubmitMassage(String pipelineId){
        List<List<GitCommit>> submitMassage = pipelineWorkSpaceService.getSubmitMassage(pipelineId);
        return Result.ok(submitMassage);
    }

}
