package io.tiklab.arbess.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.setting.model.Group;
import io.tiklab.arbess.setting.model.GroupQuery;

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
