package io.tiklab.matflow.setting.controller;

import io.tiklab.matflow.setting.model.Auth;
import io.tiklab.matflow.setting.service.AuthService;
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
     * @pi.name:createAuth
     * @pi.path:/auth/createAuth
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=auth
     */
    @RequestMapping(path="/createAuth",method = RequestMethod.POST)
    public Result<String> createPipelineAuth(@RequestBody @Valid Auth auth) {
        String pipelineAuthId = authService.createAuth(auth);
        return Result.ok(pipelineAuthId);
    }

    /**
     * @pi.name:deleteAuth
     * @pi.path:/auth/deleteAuth
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/deleteAuth",method = RequestMethod.POST)
    public Result<Void> deletePipelineAuth(@NotNull @Valid String authId) {
        authService.deleteAuth(authId);
        return Result.ok();
    }

    /**
     * @pi.name:updateAuth
     * @pi.path:/auth/createAuth
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=auth
     */
    @RequestMapping(path="/updateAuth",method = RequestMethod.POST)
    public Result<Void> updatePipelineAuth(@RequestBody @NotNull @Valid Auth auth) {
        authService.updateAuth(auth);
        return Result.ok();
    }

    /**
     * @pi.name:findOneAuth
     * @pi.path:/auth/findOneAuth
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findOneAuth",method = RequestMethod.POST)
    public Result<Auth> findPipelineAuth(@NotNull @Valid String authId) {
        Auth auth = authService.findOneAuth(authId);
        return Result.ok(auth);
    }

    /**
     * @pi.name:查询所有认证信息
     * @pi.path:/auth/findAllAuth
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllAuth",method = RequestMethod.POST)
    public Result<List<Auth>> findAllPipelineAuth() {
        List<Auth> allAuth = authService.findAllAuth();
        return Result.ok(allAuth);
    }

    
}
