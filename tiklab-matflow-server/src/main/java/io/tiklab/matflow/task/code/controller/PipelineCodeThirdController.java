package io.tiklab.matflow.task.code.controller;


import io.tiklab.core.Result;
import io.tiklab.matflow.task.code.service.TaskCodeThirdService;
import io.tiklab.matflow.setting.model.AuthThird;
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
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/codeAuthorize")
@Api(name = "PipelineCodeThirdController",desc = "git")
public class PipelineCodeThirdController {

    @Autowired
    TaskCodeThirdService taskCodeThirdService;

    @RequestMapping(path="/findCode",method = RequestMethod.POST)
    @ApiMethod(name = "findCode",desc = "返回获取code的地址")
    @ApiParam(name = "callbackUri",desc = "回调地址",required = true)
    public Result<String> getCode(@RequestBody @Valid @NotNull AuthThird authThird){

        String git = taskCodeThirdService.findCode(authThird);

        return Result.ok(git);
    }

    @RequestMapping(path="/findAccessToken",method = RequestMethod.POST)
    @ApiMethod(name = "findAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<String> getAccessToken(@RequestBody @Valid @NotNull AuthThird authThird) throws IOException {
        String proofId = taskCodeThirdService.findAccessToken(authThird);
        return Result.ok(proofId);
    }


    @RequestMapping(path="/findAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "findAllStorehouse",desc = "获取所有仓库")
    @ApiParam(name = "authId",desc = "authId",required = true)
    public Result<List<String>> findAllStorehouse(@NotNull String authId) {
        List<String> allStorehouse = taskCodeThirdService.findAllStorehouse(authId);
        return Result.ok(allStorehouse);
    }


    @RequestMapping(path="/findBranch",method = RequestMethod.POST)
    @ApiMethod(name = "findBranch",desc = "根据仓库名获取所有分支")
    @ApiParam(name = "authId",desc = "authId",required = true)
    public Result<List<String>> findBranch(@NotNull String authId,String houseName){
        List<String> branch = taskCodeThirdService.findBranch(authId,houseName);
        return Result.ok(branch);
    }

    @RequestMapping(path="/callbackUrl",method = RequestMethod.POST)
    @ApiMethod(name = "getState",desc = "获取回调地址")
    @ApiParam(name = "callbackUrl",desc = "callbackUrl",required = true)
    public Result<String> getState(@NotNull  String callbackUrl){
        String url= taskCodeThirdService.callbackUrl(callbackUrl);
        return Result.ok(url);
    }


}


























