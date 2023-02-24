package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.AuthHost;
import net.tiklab.matflow.setting.service.AuthHostService;
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
import java.util.List;

@RestController
@RequestMapping("/authHost")
@Api(name = "AuthHostController",desc = "主机认证")
public class AuthHostController {

    @Autowired
    AuthHostService authHost;

    //创建
    @RequestMapping(path="/createAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuthHost",desc = "创建")
    @ApiParam(name = "authHost",desc = "配置信息",required = true)
    public Result<String> createPipelineAuthHost(@RequestBody @Valid AuthHost authHost) {
        String pipelineAuthHostId = this.authHost.createAuthHost(authHost);
        return Result.ok(pipelineAuthHostId);
    }

    //删除
    @RequestMapping(path="/deleteAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuthHost",desc = "删除")
    @ApiParam(name = "hostId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuthHost(@NotNull @Valid String hostId) {
        authHost.deleteAuthHost(hostId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuthHost",desc = "更新")
    @ApiParam(name = "authHost",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuthHost(@RequestBody @NotNull @Valid AuthHost authHost) {
        this.authHost.updateAuthHost(authHost);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuthHost",desc = "查询")
    @ApiParam(name = "hostId",desc = "配置id",required = true)
    public Result<AuthHost> findPipelineAuthHost(@NotNull @Valid String hostId) {
        AuthHost authHost = this.authHost.findOneAuthHost(hostId);
        return Result.ok(authHost);
    }

    //查询所有
    @RequestMapping(path="/findAllAuthHostList",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuthHostList",desc = "查询所有")
    @ApiParam(name = "type",desc = "类型",required = true)
    public Result<List<AuthHost>> findAllAuthHostList(@NotNull int type) {
        List<AuthHost> allAuthHost = authHost.findAllAuthHostList(type);
        return Result.ok(allAuthHost);
    }


}
