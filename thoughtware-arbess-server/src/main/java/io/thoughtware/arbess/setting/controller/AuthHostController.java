package io.thoughtware.arbess.setting.controller;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.arbess.setting.model.AuthHost;
import io.thoughtware.arbess.setting.model.AuthHostQuery;
import io.thoughtware.arbess.setting.service.AuthHostService;
import io.thoughtware.core.Result;
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
 * @pi.groupName: 流水线主机认证控制器
 */
@RestController
@RequestMapping("/authHost")
public class AuthHostController {

    @Autowired
    AuthHostService authHost;

    /**
     * @pi.name:创建主机认证信息
     * @pi.path:/authHost/createAuthHost
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=authHost
     */
    @RequestMapping(path="/createAuthHost",method = RequestMethod.POST)
    public Result<String> createPipelineAuthHost(@RequestBody @Valid AuthHost authHost) {
        String pipelineAuthHostId = this.authHost.createAuthHost(authHost);
        return Result.ok(pipelineAuthHostId);
    }

    /**
     * @pi.name:删除主机认证信息
     * @pi.path:/authHost/deleteAuthHost
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=hostId;dataType=string;value=hostId;
     */
    @RequestMapping(path="/deleteAuthHost",method = RequestMethod.POST)
    public Result<Void> deletePipelineAuthHost(@NotNull @Valid String hostId) {
        authHost.deleteAuthHost(hostId);
        return Result.ok();
    }

    /**
     * @pi.name:更新主机认证信息
     * @pi.path:/authHost/updateAuthHost
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=authHost
     */
    @RequestMapping(path="/updateAuthHost",method = RequestMethod.POST)
    public Result<Void> updatePipelineAuthHost(@RequestBody @NotNull @Valid AuthHost authHost) {
        this.authHost.updateAuthHost(authHost);
        return Result.ok();
    }

    /**
     * @pi.name:查询单个主机认证信息
     * @pi.path:/authHost/findAuthHost
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param: name=hostId;dataType=string;value=hostId;
     */
    @RequestMapping(path="/findAuthHost",method = RequestMethod.POST)
    public Result<AuthHost> findPipelineAuthHost(@NotNull @Valid String hostId) {
        AuthHost authHost = this.authHost.findOneAuthHost(hostId);
        return Result.ok(authHost);
    }

    /**
     * @pi.name:查询主机认证信息
     * @pi.path:/authHost/findAllAuthHostList
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param:name=type;dataType=string;value=aliyun;
     */
    @RequestMapping(path="/findAuthHostList",method = RequestMethod.POST)
    public Result<List<AuthHost>> findAuthHostList(@RequestBody @Valid @NotNull AuthHostQuery hostQuery) {
        List<AuthHost> allAuthHost = authHost.findAuthHostList(hostQuery);
        return Result.ok(allAuthHost);
    }

    /**
     * @pi.name:分页查询主机认证信息
     * @pi.path:/authHost/findAuthHostPage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param:model=hostQuery;
     */
    @RequestMapping(path="/findAuthHostPage",method = RequestMethod.POST)
    public Result<Pagination<AuthHost>> findAuthHostPage( @RequestBody @Valid @NotNull AuthHostQuery hostQuery) {
        Pagination<AuthHost> allAuthHost = authHost.findAuthHostPage(hostQuery);
        return Result.ok(allAuthHost);
    }



}
