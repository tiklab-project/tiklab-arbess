package com.doublekit.pipeline.execute.controller.git;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.execute.model.CodeGit.GitCommit;
import com.doublekit.pipeline.execute.service.codeGit.GitCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gitCommit")
@Api(name = "GitCommitController",desc = "commit")
public class GitCommitController {

    @Autowired
    GitCommitService gitCommitService;


    @RequestMapping(path="/getSubmitMassage",method = RequestMethod.POST)
    @ApiMethod(name = "getSubmitMassage",desc = "获取提交信息")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<List<GitCommit>>> getSubmitMassage(String pipelineId){
        List<List<GitCommit>> submitMassage = gitCommitService.getSubmitMassage(pipelineId);
        return Result.ok(submitMassage);
    }
}
