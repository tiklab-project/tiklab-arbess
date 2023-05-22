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

@RestController
@RequestMapping("/authServer")
@Api(name = "AuthThirdController",desc = "认证")
public class AuthThirdController {

    @Autowired
    AuthThirdService authServerService;


    @RequestMapping(path="/createAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuthServer",desc = "创建")
    @ApiParam(name = "authThird",desc = "配置信息",required = true)
    public Result<String> createPipelineAuthServer(@RequestBody @Valid AuthThird authThird) {
        String pipelineAuthServerId = authServerService.createAuthServer(authThird);
        return Result.ok(pipelineAuthServerId);
    }

    //删除
    @RequestMapping(path="/deleteAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuthServer",desc = "删除")
    @ApiParam(name = "serverId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuthServer(@NotNull @Valid String serverId) {
        authServerService.deleteAuthServer(serverId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuthServer",desc = "更新")
    @ApiParam(name = "authThird",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuthServer(@RequestBody @NotNull @Valid AuthThird authThird) {
        authServerService.updateAuthServer(authThird);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findOneAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuthServer",desc = "查询")
    @ApiParam(name = "serverId",desc = "配置id",required = true)
    public Result<AuthThird> findPipelineAuthServer(@NotNull @Valid String serverId) {
        AuthThird authThird = authServerService.findOneAuthServer(serverId);
        return Result.ok(authThird);
    }

    //查询所有
    @RequestMapping(path="/findAllAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineAuthServer",desc = "查询所有")
    public Result<List<AuthThird>> findAllPipelineAuthServer() {
        List<AuthThird> allAuthThird = authServerService.findAllAuthServer();
        return Result.ok(allAuthThird);
    }

    @RequestMapping(path="/findAllAuthServerList",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuthServerList",desc = "查询所有")
    @ApiParam(name = "type",desc = "类型",required = true)
    public Result<List<AuthThird>> findAllAuthServerList(@NotNull String type) {
        List<AuthThird> allAuthThird = authServerService.findAllAuthServerList(type);
        return Result.ok(allAuthThird);
    }


}


















