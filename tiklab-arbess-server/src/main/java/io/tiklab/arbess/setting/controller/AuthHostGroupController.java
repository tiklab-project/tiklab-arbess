package io.tiklab.arbess.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.arbess.setting.model.AuthHostGroup;
import io.tiklab.arbess.setting.model.AuthHostGroupQuery;
import io.tiklab.arbess.setting.service.AuthHostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author zcamy
 * @pi.protocol: http
 * @pi.groupName: 流水线主机组认证控制器
 */
@RestController
@RequestMapping("/authHostGroup")
public class AuthHostGroupController {

    @Autowired
    AuthHostGroupService authHostGroupService;

    /**
     * @pi.name:创建主机组认证信息
     * @pi.path:/authHostGroup/createAuthHostGroup
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param:model=hostGroup
     */
    @RequestMapping(path="/createAuthHostGroup",method = RequestMethod.POST)
    public Result<String> createPipelineAuthHost(@RequestBody @Valid AuthHostGroup hostGroup) {
        String pipelineAuthHostId = authHostGroupService.creatHostGroup(hostGroup);
        return Result.ok(pipelineAuthHostId);
    }

    /**
     * @pi.name:删除主机组认证信息
     * @pi.path:/authHostGroup/deleteAuthHostGroup
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param:name=groupId;dataType=string;value=groupId;
     */
    @RequestMapping(path="/deleteAuthHostGroup",method = RequestMethod.POST)
    public Result<Void> deletePipelineAuthHost(@NotNull @Valid String groupId) {
        authHostGroupService.deleteHostGroup(groupId);
        return Result.ok();
    }

    /**
     * @pi.name:更新主机组认证信息
     * @pi.path:/authHostGroup/updateAuthHostGroup
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param:model=hostGroup
     */
    @RequestMapping(path="/updateAuthHostGroup",method = RequestMethod.POST)
    public Result<Void> updatePipelineAuthHost(@RequestBody @NotNull @Valid AuthHostGroup hostGroup) {
        authHostGroupService.updateHostGroup(hostGroup);
        return Result.ok();
    }

    /**
     * @pi.name:查询主机组认证信息
     * @pi.path:/authHostGroup/findAuthHostGroup
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param:name=groupId;dataType=string;value=groupId;
     */
    @RequestMapping(path="/findAuthHostGroup",method = RequestMethod.POST)
    public Result<AuthHostGroup> findPipelineAuthHost(@NotNull String groupId) {
        AuthHostGroup authHost = authHostGroupService.findOneHostGroup(groupId);
        return Result.ok(authHost);
    }

    /**
     * @pi.name:条件查询主机组认证信息
     * @pi.path:/authHostGroup/findAllAuthHostGroupList
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param:model=groupQuery
     */
    @RequestMapping(path="/findHostGroupList",method = RequestMethod.POST)
    public Result<List<AuthHostGroup>> findHostGroupList(@RequestBody @NotNull @Valid  AuthHostGroupQuery groupQuery) {
        List<AuthHostGroup> authHostGroupList = authHostGroupService.findHostGroupList(groupQuery);
        return Result.ok(authHostGroupList);
    }


}
