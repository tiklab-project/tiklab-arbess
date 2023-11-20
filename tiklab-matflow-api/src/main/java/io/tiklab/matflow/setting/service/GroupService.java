package io.tiklab.matflow.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.setting.model.Group;
import io.tiklab.matflow.setting.model.GroupQuery;

import java.util.List;

@JoinProvider(model = Group.class)
public interface GroupService {

    String createGroup(Group group);

    void updateGroup(Group group);

    void deleteGroup(String groupId);

    @FindOne
    Group findOneGroup(String groupId);

    @FindAll
    List<Group> findAllGroup();

    List<Group> findGroupList(GroupQuery groupQuery);


    Pagination<Group> findGroupPage(GroupQuery groupQuery);


    @FindList
    List<Group> findAllGroupList(List<String> idList);
    
    
}
