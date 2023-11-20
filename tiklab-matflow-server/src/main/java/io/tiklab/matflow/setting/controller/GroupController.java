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
 * @pi.groupName: 流水线主机认证控制器
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;
    
    @RequestMapping(path="/createGroup",method = RequestMethod.POST)
    public Result<String> createGroup(@RequestBody @Valid Group group) {
        String pipelineAuthHostId = groupService.createGroup(group);
        return Result.ok(pipelineAuthHostId);
    }

    
    @RequestMapping(path="/deleteGroup",method = RequestMethod.POST)
    public Result<Void> deleteGroup(@NotNull @Valid String groupId) {
        groupService.deleteGroup(groupId);
        return Result.ok();
    }

   
    @RequestMapping(path="/updateGroup",method = RequestMethod.POST)
    public Result<Void> updateGroup(@RequestBody @NotNull @Valid Group group) {
        this.groupService.updateGroup(group);
        return Result.ok();
    }
    

    @RequestMapping(path="/findGroupPage",method = RequestMethod.POST)
    public Result<Pagination<Group>> findGroupPage( @RequestBody @Valid @NotNull GroupQuery groupQuery) {
        Pagination<Group> groupPage = groupService.findGroupPage(groupQuery);
        return Result.ok(groupPage);
    }

    @RequestMapping(path="/findGroupList",method = RequestMethod.POST)
    public Result<List<Group>> findGroupList( @RequestBody @Valid @NotNull GroupQuery groupQuery) {
        List<Group> groupList = groupService.findGroupList(groupQuery);
        return Result.ok(groupList);
    }

}
