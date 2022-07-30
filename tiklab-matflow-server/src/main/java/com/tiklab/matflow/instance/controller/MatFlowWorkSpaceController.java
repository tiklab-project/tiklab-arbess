package com.tiklab.matflow.instance.controller;


import com.tiklab.core.Result;
import com.tiklab.matflow.execute.model.CodeGit.FileTree;
import com.tiklab.matflow.execute.model.CodeGit.GitCommit;
import com.tiklab.matflow.instance.service.MatFlowWorkSpaceService;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/matFlowWorkSpace")
@Api(name = "MatFlowWorkSpaceController",desc = "流水线工作空间")
public class MatFlowWorkSpaceController {

    @Autowired
    MatFlowWorkSpaceService matFlowWorkSpaceService;


    @RequestMapping(path="/fileTree",method = RequestMethod.POST)
    @ApiMethod(name = "fileTree",desc = "判断是否执行")
    @ApiParam(name = "userId",desc = "用户id",required = true)
    public Result<List<FileTree>> fileTree(@NotNull String userId,String matFlowId) {
        List<FileTree> map = matFlowWorkSpaceService.fileTree(matFlowId,userId);
        return Result.ok(map);
    }

    @RequestMapping(path="/readFile",method = RequestMethod.POST)
    @ApiMethod(name = "readFile",desc = "判断是否执行")
    @ApiParam(name = "path",desc = "文件地址",required = true)
    public Result< List<String>> readFile(@NotNull String path) {
        List<String> s = matFlowWorkSpaceService.readFile(path);
        return Result.ok(s);
    }

    @RequestMapping(path="/getSubmitMassage",method = RequestMethod.POST)
    @ApiMethod(name = "getSubmitMassage",desc = "获取提交信息")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<List<List<GitCommit>>> getSubmitMassage(String matFlowId){
        List<List<GitCommit>> submitMassage = matFlowWorkSpaceService.getSubmitMassage(matFlowId);
        return Result.ok(submitMassage);
    }

}
