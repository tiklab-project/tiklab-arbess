package com.doublekit.pipeline.execute.controller.git;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.execute.service.codeGit.CodeGiteeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gitee")
@Api(name = "GiteeApiController",desc = "git")
public class GiteeApiController {

    @Autowired
    CodeGiteeApiService codeGiteeApiService;

    @RequestMapping(path="/url",method = RequestMethod.POST)
    @ApiMethod(name = "getCode",desc = "返回获取code的地址")
    public Result<String> getCode(){

        String git = codeGiteeApiService.getCode();

        return Result.ok(git);
    }

    @RequestMapping(path="/code",method = RequestMethod.POST)
    @ApiMethod(name = "getAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<Map<String, String>> getAccessToken(@NotNull String code) throws IOException {
        Map<String, String> accessToken = codeGiteeApiService.getAccessToken(code);
        return Result.ok(accessToken);
    }

    @RequestMapping(path="/getUserMessage",method = RequestMethod.POST)
    @ApiMethod(name = "getUserMessage",desc = "获取用户信息")
    @ApiParam(name = "accessToken",desc = "accessToken",required = true)
    public Result<String> getUserMessage(@NotNull String accessToken){
        String userMessage = codeGiteeApiService.getUserMessage(accessToken);

        return Result.ok(userMessage);
    }


    @RequestMapping(path="/getProof",method = RequestMethod.POST)
    @ApiMethod(name = "getProof",desc = "获取用户信息")
    @ApiParam(name = "proofName",desc = "凭证名称",required = true)
    public Result<String> getProof(@NotNull String proofName, String accessToken){
        String i = codeGiteeApiService.getProof(proofName,accessToken);
        return Result.ok(i);
    }

    @RequestMapping(path="/getAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "getAllStorehouse",desc = "获取所有仓库")
    @ApiParam(name = "accessToken",desc = "accessToken",required = true)
    public Result<List<String>> getAllStorehouse(@NotNull String accessToken) {

        List<String> allStorehouse = codeGiteeApiService.getAllStorehouse(accessToken);

        return Result.ok(allStorehouse);
    }


    @RequestMapping(path="/getBranch",method = RequestMethod.POST)
    @ApiMethod(name = "getBranch",desc = "根据仓库名获取所有分支")
    @ApiParam(name = "proofId",desc = "proofId",required = true)
    public Result<List<String>> getBranch(@NotNull String proofId, String projectName){

        List<String> branch = codeGiteeApiService.getBranch(proofId,projectName);

        return Result.ok(branch);
    }

}

