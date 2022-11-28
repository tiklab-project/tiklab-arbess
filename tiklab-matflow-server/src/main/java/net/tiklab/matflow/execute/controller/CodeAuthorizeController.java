package net.tiklab.matflow.execute.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.execute.service.achieve.CodeThirdService;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
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
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/codeAuthorize")
@Api(name = "CodeAuthorizeController",desc = "git")
public class CodeAuthorizeController {

    @Autowired
    CodeThirdService codeThirdService;

    @RequestMapping(path="/findCode",method = RequestMethod.POST)
    @ApiMethod(name = "findCode",desc = "返回获取code的地址")
    @ApiParam(name = "callbackUri",desc = "回调地址",required = true)
    public Result<String> getCode(@RequestBody @Valid @NotNull PipelineAuthThird authThird){

        String git = codeThirdService.findCode(authThird);

        return Result.ok(git);
    }

    @RequestMapping(path="/findAccessToken",method = RequestMethod.POST)
    @ApiMethod(name = "findAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<String> getAccessToken(@RequestBody @Valid @NotNull  PipelineAuthThird authThird) throws IOException {
        String proofId = codeThirdService.findAccessToken(authThird);
        return Result.ok(proofId);
    }


    @RequestMapping(path="/findAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "findAllStorehouse",desc = "获取所有仓库")
    @ApiParam(name = "authId",desc = "authId",required = true)
    public Result<List<String>> findAllStorehouse(@NotNull String authId,int type) {
        List<String> allStorehouse = codeThirdService.findAllStorehouse(authId,type);
        return Result.ok(allStorehouse);
    }


    @RequestMapping(path="/findBranch",method = RequestMethod.POST)
    @ApiMethod(name = "findBranch",desc = "根据仓库名获取所有分支")
    @ApiParam(name = "authId",desc = "authId",required = true)
    public Result<List<String>> findBranch(@NotNull String authId,String houseName,int type){
        List<String> branch = codeThirdService.findBranch(authId,houseName,type);
        return Result.ok(branch);
    }

    @RequestMapping(path="/callbackUrl",method = RequestMethod.POST)
    @ApiMethod(name = "callbackUrl",desc = "获取回调地址")
    @ApiParam(name = "callbackUrl",desc = "callbackUrl",required = true)
    public Result<String> getState( String callbackUrl){
        String url= codeThirdService.callbackUrl(callbackUrl);
        return Result.ok(url);
    }


}


























