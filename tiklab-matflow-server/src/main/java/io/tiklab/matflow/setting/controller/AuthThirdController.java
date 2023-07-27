package io.tiklab.matflow.setting.controller;

import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthThirdService;
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
 * @pi.protocol: http
 * @pi.groupName: 流水线第三方认证控制器
 */
@RestController
@RequestMapping("/authServer")
public class AuthThirdController {

    @Autowired
    AuthThirdService authServerService;

    /**
     * @pi.name:创建流水线第三方认证信息
     * @pi.path:/authServer/createAuthServer
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=authThird
     */
    @RequestMapping(path="/createAuthServer",method = RequestMethod.POST)
    public Result<String> createPipelineAuthServer(@RequestBody @Valid AuthThird authThird) {
        String pipelineAuthServerId = authServerService.createAuthServer(authThird);
        return Result.ok(pipelineAuthServerId);
    }

    /**
     * @pi.name:删除第三方认证信息
     * @pi.path:/authServer/deleteAuthServer
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=serverId;dataType=string;value=serverId;
     */
    @RequestMapping(path="/deleteAuthServer",method = RequestMethod.POST)
    public Result<Void> deletePipelineAuthServer(@NotNull @Valid String serverId) {
        authServerService.deleteAuthServer(serverId);
        return Result.ok();
    }

    /**
     * @pi.name:更新流水线第三方认证信息
     * @pi.path:/authServer/updateAuthServer
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=authThird
     */
    @RequestMapping(path="/updateAuthServer",method = RequestMethod.POST)
    public Result<Void> updatePipelineAuthServer(@RequestBody @NotNull @Valid AuthThird authThird) {
        authServerService.updateAuthServer(authThird);
        return Result.ok();
    }

    /**
     * @pi.name:查询单个第三方认证信息
     * @pi.path:/authServer/findOneAuthServer
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=serverId;dataType=string;value=serverId;
     */
    @RequestMapping(path="/findOneAuthServer",method = RequestMethod.POST)
    public Result<AuthThird> findPipelineAuthServer(@NotNull @Valid String serverId) {
        AuthThird authThird = authServerService.findOneAuthServer(serverId);
        return Result.ok(authThird);
    }

    /**
     * @pi.name:查询所有第三方认证信息
     * @pi.path:/authServer/findAllAuthServer
     * @pi.method:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllAuthServer",method = RequestMethod.POST)
    public Result<List<AuthThird>> findAllPipelineAuthServer() {
        List<AuthThird> allAuthThird = authServerService.findAllAuthServer();
        return Result.ok(allAuthThird);
    }

    /**
     * @pi.name:根据类型单个第三方认证信息
     * @pi.path:/authServer/findAllAuthServerList
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=type;dataType=string;value=gitee;
     */
    @RequestMapping(path="/findAllAuthServerList",method = RequestMethod.POST)
    public Result<List<AuthThird>> findAllAuthServerList(@NotNull String type) {
        List<AuthThird> allAuthThird = authServerService.findAllAuthServerList(type);
        return Result.ok(allAuthThird);
    }


}


















