package io.thoughtware.matflow.setting.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;
import io.thoughtware.matflow.setting.model.Group;
import io.thoughtware.matflow.setting.model.GroupQuery;

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