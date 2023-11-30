package io.tiklab.matflow.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.setting.model.Group;
import io.tiklab.matflow.setting.model.GroupQuery;
import io.tiklab.matflow.setting.service.GroupService;
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
 * @pi.groupName: 流水线组控制器
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;


    /**
     * @pi.name:创建流水线组
     * @pi.path:/group/createGroup
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=group;
     */
    @RequestMapping(path="/createGroup",method = RequestMethod.POST)
    public Result<String> createGroup(@RequestBody @Valid Group group) {
        String pipelineAuthHostId = groupService.createGroup(group);
        return Result.ok(pipelineAuthHostId);
    }


    /**
     * @pi.name:删除流水线组
     * @pi.path:/group/deleteGroup
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=groupId;dataType=string;value=流水线组id;
     */
    @RequestMapping(path="/deleteGroup",method = RequestMethod.POST)
    public Result<Void> deleteGroup(@NotNull @Valid String groupId) {
        groupService.deleteGroup(groupId);
        return Result.ok();
    }

    /**
     * @pi.name:更新流水线组
     * @pi.path:/group/updateGroup
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=group;
     */
    @RequestMapping(path="/updateGroup",method = RequestMethod.POST)
    public Result<Void> updateGroup(@RequestBody @NotNull @Valid Group group) {
        this.groupService.updateGroup(group);
        return Result.ok();
    }

    /**
     * @pi.name:分页查询流水线组
     * @pi.path:/group/findGroupPage
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=group;
     */
    @RequestMapping(path="/findGroupPage",method = RequestMethod.POST)
    public Result<Pagination<Group>> findGroupPage( @RequestBody @Valid @NotNull GroupQuery groupQuery) {
        Pagination<Group> groupPage = groupService.findGroupPage(groupQuery);
        return Result.ok(groupPage);
    }

    /**
     * @pi.name:多条件查询流水线组
     * @pi.path:/group/findGroupList
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=group;
     */
    @RequestMapping(path="/findGroupList",method = RequestMethod.POST)
    public Result<List<Group>> findGroupList( @RequestBody @Valid @NotNull GroupQuery groupQuery) {
        List<Group> groupList = groupService.findGroupList(groupQuery);
        return Result.ok(groupList);
    }

}
