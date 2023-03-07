package io.tiklab.matflow.setting.controller;

import io.tiklab.matflow.setting.model.Auth;
import io.tiklab.matflow.setting.service.AuthService;
import io.tiklab.core.Result;
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
import java.util.List;

/**
 * 流水线基本认证控制器
 */
@RestController
@RequestMapping("/auth")
@Api(name = "AuthController",desc = "流水线授权")
public class AuthController {

    @Autowired
    AuthService authService;


    @RequestMapping(path="/createAuth",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuth",desc = "创建")
    @ApiParam(name = "auth",desc = "配置信息",required = true)
    public Result<String> createPipelineAuth(@RequestBody @Valid Auth auth) {
        String pipelineAuthId = authService.createAuth(auth);
        return Result.ok(pipelineAuthId);
    }

    //删除
    @RequestMapping(path="/deleteAuth",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuth",desc = "删除")
    @ApiParam(name = "authId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuth(@NotNull @Valid String authId) {
        authService.deleteAuth(authId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuth",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuth",desc = "更新")
    @ApiParam(name = "auth",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuth(@RequestBody @NotNull @Valid Auth auth) {
        authService.updateAuth(auth);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findOneAuth",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuth",desc = "查询")
    @ApiParam(name = "authId",desc = "配置id",required = true)
    public Result<Auth> findPipelineAuth(@NotNull @Valid String authId) {
        Auth auth = authService.findOneAuth(authId);
        return Result.ok(auth);
    }

    //查询所有
    @RequestMapping(path="/findAllAuth",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineAuth",desc = "查询所有")
    public Result<List<Auth>> findAllPipelineAuth() {
        List<Auth> allAuth = authService.findAllAuth();
        return Result.ok(allAuth);
    }

    
}
