package io.tiklab.arbess.setting.controller;

import io.tiklab.arbess.setting.model.AuthThird;
import io.tiklab.arbess.setting.model.AuthThirdQuery;
import io.tiklab.arbess.setting.service.AuthThirdService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
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
     * @pi.name:创建第三方认证信息
     * @pi.url:/authServer/createAuthServer
     * @pi.methodType:post
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
     * @pi.url:/authServer/deleteAuthServer
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=serverId;dataType=string;value=serverId;
     */
    @RequestMapping(path="/deleteAuthServer",method = RequestMethod.POST)
    public Result<Void> deletePipelineAuthServer(@NotNull @Valid String serverId) {
        authServerService.deleteAuthServer(serverId);
        return Result.ok();
    }

    /**
     * @pi.name:更新第三方认证信息
     * @pi.url:/authServer/updateAuthServer
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=authThird
     */
    @RequestMapping(path="/updateAuthServer",method = RequestMethod.POST)
    public Result<Void> updatePipelineAuthServer(@RequestBody @NotNull @Valid AuthThird authThird) {
        authServerService.updateAuthServer(authThird);
        return Result.ok();
    }

    /**
     * @pi.name:查询第三方认证信息
     * @pi.url:/authServer/findOneAuthServer
     * @pi.methodType:post
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
     * @pi.url:/authServer/findAllAuthServer
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllAuthServer",method = RequestMethod.POST)
    public Result<List<AuthThird>> findAllPipelineAuthServer() {
        List<AuthThird> allAuthThird = authServerService.findAllAuthServer();
        return Result.ok(allAuthThird);
    }

    /**
     * @pi.name:条件查询第三方认证信息
     * @pi.url:/authServer/findAuthServerList
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=thirdQuery;
     */
    @RequestMapping(path="/findAuthServerList",method = RequestMethod.POST)
    public Result<List<AuthThird>> findAllAuthServerList(@RequestBody @Valid @NotNull AuthThirdQuery thirdQuery) {
        List<AuthThird> allAuthThird = authServerService.findAuthServerList(thirdQuery);
        return Result.ok(allAuthThird);
    }

    @RequestMapping(path="/findAuthServerPage",method = RequestMethod.POST)
    public Result<Pagination<AuthThird>> findAuthServerPage(@RequestBody @Valid @NotNull AuthThirdQuery thirdQuery) {
        Pagination<AuthThird> allAuthThird = authServerService.findAuthServerPage(thirdQuery);
        return Result.ok(allAuthThird);
    }


}


















