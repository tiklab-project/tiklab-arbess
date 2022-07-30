package com.tiklab.matflow.execute.controller.git;


import com.tiklab.core.Result;
import com.tiklab.matflow.execute.service.codeGit.CodeGitHubService;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gitHub")
@Api(name = "GitLabApiController",desc = "gitHub")
public class GitHubApiController {

    @Autowired
    CodeGitHubService codeGitHubService;

    @RequestMapping(path="/getCode",method = RequestMethod.POST)
    @ApiMethod(name = "getCode",desc = "返回获取code的Url")
    public Result<String> getCode(){
        String code = codeGitHubService.getCode();
        return Result.ok(code);
    }


    @RequestMapping(path="/getAccessToken",method = RequestMethod.POST)
    @ApiMethod(name = "getAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<String> getAccessToken(@NotNull String code){
        String s = codeGitHubService.getAccessToken(code);
        return Result.ok(s);
    }

    @RequestMapping(path="/getUserMessage",method = RequestMethod.POST)
    @ApiMethod(name = "getUserMessage",desc = "返回获取code")
    @ApiParam(name = "accessToken",desc = "accessToken",required = true)
    public Result<String> getUserMessage(@NotNull String accessToken){
        String s = codeGitHubService.getUserMessage(accessToken);
        return Result.ok(s);
    }

    @RequestMapping(path="/getAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "getAllStorehouse",desc = "获取用户的存储库")
    @ApiParam(name = "proofId",desc = "proofId",required = true)
    public Result<List<String>> getAllStorehouse(@NotNull String proofId){
        List<String> list = codeGitHubService.getAllStorehouse(proofId);
        return Result.ok(list);
    }

    @RequestMapping(path="/getBranch",method = RequestMethod.POST)
    @ApiMethod(name = "getAllStorehouse",desc = "获取用户的存储库")
    @ApiParam(name = "proofId",desc = "proofId",required = true)
    public Result<Map<String, String>> getBranch(@NotNull String proofId,String projectName){
        List<String> branchList = codeGitHubService.getBranch(proofId, projectName);
        return Result.ok(branchList);
    }

    @RequestMapping(path="/getProof",method = RequestMethod.POST)
    @ApiMethod(name = "getProof",desc = "创建GitHub凭证")
    @ApiParam(name = "proof",desc = "凭证信息",required = true)
    public Result<String> getProof(@RequestBody @Valid @NotNull Proof proof){
        String s = codeGitHubService.getProof(proof);
        return Result.ok(s);
    }
















}
