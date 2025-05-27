package io.tiklab.arbess.setting.auth.controller;

import io.tiklab.arbess.setting.auth.model.Auth;
import io.tiklab.arbess.setting.auth.service.AuthService;
import io.tiklab.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @pi.protocol: http
 * @pi.groupName: 流水线基本认证控制器
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * @pi.name:创建流水线基本认证
     * @pi.url:/auth/createAuth
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=auth
     */
    @RequestMapping(path="/createAuth",method = RequestMethod.POST)
    public Result<String> createAuth(@RequestBody @Valid Auth auth) {
        String pipelineAuthId = authService.createAuth(auth);
        return Result.ok(pipelineAuthId);
    }

    /**
     * @pi.name:删除流水线基本认证
     * @pi.url:/auth/deleteAuth
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=认证id;
     */
    @RequestMapping(path="/deleteAuth",method = RequestMethod.POST)
    public Result<Void> deleteAuth(@NotNull @Valid String authId) {
        authService.deleteAuth(authId);
        return Result.ok();
    }

    /**
     * @pi.name:跟新流水线基本认证
     * @pi.url:/auth/createAuth
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=auth
     */
    @RequestMapping(path="/updateAuth",method = RequestMethod.POST)
    public Result<Void> updateAuth(@RequestBody @NotNull @Valid Auth auth) {
        authService.updateAuth(auth);
        return Result.ok();
    }

    /**
     * @pi.name:查询流水线基本认证
     * @pi.url:/auth/findOneAuth
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findOneAuth",method = RequestMethod.POST)
    public Result<Auth> findOneAuth(@NotNull @Valid String authId) {
        Auth auth = authService.findOneAuth(authId);
        return Result.ok(auth);
    }

    /**
     * @pi.name:查询所有认证信息
     * @pi.url:/auth/findAllAuth
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllAuth",method = RequestMethod.POST)
    public Result<List<Auth>> findAllAuth() {
        List<Auth> allAuth = authService.findAllAuth();
        return Result.ok(allAuth);
    }

    
}
