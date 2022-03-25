package com.doublekit.pipeline.instance.controller.git;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.example.service.codeGit.CodeGiteeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

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
    public Result<String> getAccessToken(@NotNull String code) throws IOException {

        String accessToken = codeGiteeApiService.getAccessToken(code);

        return Result.ok(accessToken);
    }

    @RequestMapping(path="/getUserMessage",method = RequestMethod.POST)
    @ApiMethod(name = "getUserMessage",desc = "获取proofId")
    public Result<String> getUserMessage(){
        String userMessage = codeGiteeApiService.getUserMessage();

        return Result.ok(userMessage);
    }

    @RequestMapping(path="/getAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "getAllStorehouse",desc = "获取所有仓库")
    public Result<List<String>> getAllStorehouse() {

        List<String> allStorehouse = codeGiteeApiService.getAllStorehouse();

        return Result.ok(allStorehouse);
    }


    @RequestMapping(path="/getBranch",method = RequestMethod.POST)
    @ApiMethod(name = "getBranch",desc = "根据仓库名获取所有分支")
    @ApiParam(name = "projectName",desc = "projectName",required = true)
    public Result<List<String>> getBranch(@NotNull String projectName){

        List<String> branch = codeGiteeApiService.getBranch(projectName);

        return Result.ok(branch);
    }

}

