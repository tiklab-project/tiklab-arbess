package io.thoughtware.arbess.setting.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.arbess.setting.model.Group;
import io.thoughtware.arbess.setting.model.GroupQuery;

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


    Integer findGroupNumber();
    
    
}
