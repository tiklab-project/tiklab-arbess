package io.thoughtware.arbess.setting.controller;

import io.thoughtware.arbess.setting.model.AuthHostK8s;
import io.thoughtware.arbess.setting.model.AuthHostK8sQuery;
import io.thoughtware.arbess.setting.service.AuthHostK8sService;
import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
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
 * @pi.groupName: 流水线k8s认证控制器
 */
@RestController
@RequestMapping("/authHostK8s")
public class AuthHostK8sController {

    @Autowired
    AuthHostK8sService authHostK8s;

    /**
     * @pi.name:创建k8s认证信息
     * @pi.path:/authHostK8s/createAuthHostK8s
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=authHostK8s
     */
    @RequestMapping(path="/createAuthHostK8s",method = RequestMethod.POST)
    public Result<String> createPipelineAuthHostK8s(@RequestBody @Valid AuthHostK8s authHostK8s) {
        String pipelineAuthHostK8sId = this.authHostK8s.createAuthHostK8s(authHostK8s);
        return Result.ok(pipelineAuthHostK8sId);
    }

    /**
     * @pi.name:删除k8s认证信息
     * @pi.path:/authHostK8s/deleteAuthHostK8s
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=hostId;dataType=string;value=hostId;
     */
    @RequestMapping(path="/deleteAuthHostK8s",method = RequestMethod.POST)
    public Result<Void> deletePipelineAuthHostK8s(@NotNull @Valid String hostId) {
        authHostK8s.deleteAuthHostK8s(hostId);
        return Result.ok();
    }

    /**
     * @pi.name:更新k8s认证信息
     * @pi.path:/authHostK8s/updateAuthHostK8s
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=authHostK8s
     */
    @RequestMapping(path="/updateAuthHostK8s",method = RequestMethod.POST)
    public Result<Void> updatePipelineAuthHostK8s(@RequestBody @NotNull @Valid AuthHostK8s authHostK8s) {
        this.authHostK8s.updateAuthHostK8s(authHostK8s);
        return Result.ok();
    }

    /**
     * @pi.name:查询单个k8s认证信息
     * @pi.path:/authHostK8s/findAuthHostK8s
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param: name=hostId;dataType=string;value=hostId;
     */
    @RequestMapping(path="/findAuthHostK8s",method = RequestMethod.POST)
    public Result<AuthHostK8s> findPipelineAuthHostK8s(@NotNull @Valid String hostId) {
        AuthHostK8s authHostK8s = this.authHostK8s.findOneAuthHostK8s(hostId);
        return Result.ok(authHostK8s);
    }

    /**
     * @pi.name:查询k8s认证信息
     * @pi.path:/authHostK8s/findAllAuthHostK8sList
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param:name=type;dataType=string;value=aliyun;
     */
    @RequestMapping(path="/findAuthHostK8sList",method = RequestMethod.POST)
    public Result<List<AuthHostK8s>> findAuthHostK8sList(@RequestBody @Valid @NotNull AuthHostK8sQuery hostQuery) {
        List<AuthHostK8s> allAuthHostK8s = authHostK8s.findAuthHostK8sList(hostQuery);
        return Result.ok(allAuthHostK8s);
    }

    /**
     * @pi.name:分页查询k8s认证信息
     * @pi.path:/authHostK8s/findAuthHostK8sPage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param:model=hostQuery;
     */
    @RequestMapping(path="/findAuthHostK8sPage",method = RequestMethod.POST)
    public Result<Pagination<AuthHostK8s>> findAuthHostK8sPage( @RequestBody @Valid @NotNull AuthHostK8sQuery hostQuery) {
        Pagination<AuthHostK8s> allAuthHostK8s = authHostK8s.findAuthHostK8sPage(hostQuery);
        return Result.ok(allAuthHostK8s);
    }



}
