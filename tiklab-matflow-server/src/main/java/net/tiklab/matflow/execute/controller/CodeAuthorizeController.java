package net.tiklab.matflow.execute.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.execute.service.CodeAuthorizeService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/codeAuthorize")
@Api(name = "CodeAuthorizeController",desc = "git")
public class CodeAuthorizeController {

    @Autowired
    CodeAuthorizeService codeAuthorizeService;

    @RequestMapping(path="/findCode",method = RequestMethod.POST)
    @ApiMethod(name = "findCode",desc = "返回获取code的地址")
    @ApiParam(name = "callbackUri",desc = "回调地址",required = true)
    public Result<String> getCode(int type){

        String git = codeAuthorizeService.findCode(type);

        return Result.ok(git);
    }

    @RequestMapping(path="/findAccessToken",method = RequestMethod.POST)
    @ApiMethod(name = "findAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<String> getAccessToken(@NotNull String code,int type) throws IOException {
        String proofId = codeAuthorizeService.findAccessToken(code, type);
        return Result.ok(proofId);
    }


    @RequestMapping(path="/findAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "findAllStorehouse",desc = "获取所有仓库")
    @ApiParam(name = "proofId",desc = "proofId",required = true)
    public Result<List<String>> findAllStorehouse(@NotNull String proofId,int type) {
        List<String> allStorehouse = codeAuthorizeService.findAllStorehouse(proofId,type);
        return Result.ok(allStorehouse);
    }


    @RequestMapping(path="/findBranch",method = RequestMethod.POST)
    @ApiMethod(name = "findBranch",desc = "根据仓库名获取所有分支")
    @ApiParam(name = "proofId",desc = "proofId",required = true)
    public Result<List<String>> findBranch(@NotNull String proofId,String houseName,int type){
        List<String> branch = codeAuthorizeService.findBranch(proofId,houseName,type);
        return Result.ok(branch);
    }

    @RequestMapping(path="/updateProof",method = RequestMethod.POST)
    @ApiMethod(name = "updateProof",desc = "创建gitee凭证")
    @ApiParam(name = "proofId",desc = "凭证信息",required = true)
    public Result<String> updateProof( @NotNull String proofId,String name){
        codeAuthorizeService.updateProof(proofId,name);
        return Result.ok();
    }

    @RequestMapping(path="/findState",method = RequestMethod.POST)
    @ApiMethod(name = "findState",desc = "获取授权状态")
    public Result<Integer> getState(){
        int states = codeAuthorizeService.findState();
        return Result.ok(states);
    }

    @RequestMapping(path="/findMessage",method = RequestMethod.POST)
    @ApiMethod(name = "findMessage",desc = "创建gitee凭证")
    @ApiParam(name = "authId",desc = "授权信息",required = true)
    public Result<String> findMessage(@NotNull String authId){
        String message = codeAuthorizeService.findMessage(authId);
        return Result.ok(message);
    }

}


























