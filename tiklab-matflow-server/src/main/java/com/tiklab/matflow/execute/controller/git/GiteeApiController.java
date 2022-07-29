package com.tiklab.matflow.execute.controller.git;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.tiklab.matflow.execute.service.codeGit.CodeGiteeApiService;
import com.tiklab.matflow.setting.proof.model.Proof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    @ApiParam(name = "callbackUri",desc = "回调地址",required = true)
    public Result<String> getCode(String callbackUri){

        String git = codeGiteeApiService.getCode(callbackUri);

        return Result.ok(git);
    }

    @RequestMapping(path="/code",method = RequestMethod.POST)
    @ApiMethod(name = "getAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<Map<String, String>> getAccessToken(@NotNull String code,String callbackUri) throws IOException {
        Map<String, String> accessToken = codeGiteeApiService.getAccessToken(code,callbackUri);
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
    @ApiMethod(name = "getProof",desc = "创建gitee凭证")
    @ApiParam(name = "proof",desc = "凭证信息",required = true)
    public Result<String> getProof(@RequestBody @Valid @NotNull Proof proof){
        String i = codeGiteeApiService.getProof(proof);
        return Result.ok(i);
    }

    @RequestMapping(path="/getAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "getAllStorehouse",desc = "获取所有仓库")
    @ApiParam(name = "ProofId",desc = "ProofId",required = true)
    public Result<List<String>> getAllStorehouse(@NotNull String proofId) {
        List<String> allStorehouse = codeGiteeApiService.getAllStorehouse(proofId);
        return Result.ok(allStorehouse);
    }


    @RequestMapping(path="/getBranch",method = RequestMethod.POST)
    @ApiMethod(name = "getBranch",desc = "根据仓库名获取所有分支")
    @ApiParam(name = "proofId",desc = "proofId",required = true)
    public Result<List<String>> getBranch(@NotNull String proofId, String projectName){
        List<String> branch = codeGiteeApiService.getBranch(proofId,projectName);
        return Result.ok(branch);
    }

    @RequestMapping(path="/getState",method = RequestMethod.POST)
    @ApiMethod(name = "getState",desc = "获取授权状态")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<Integer> getState(String code ,int state){
        int states = codeGiteeApiService.getState(code,state);
        return Result.ok(states);
    }

}

