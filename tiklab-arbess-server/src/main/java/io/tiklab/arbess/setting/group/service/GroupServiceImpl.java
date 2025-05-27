package io.tiklab.arbess.setting.group.service;

import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.setting.group.dao.GroupDao;
import io.tiklab.arbess.setting.group.model.Group;
import io.tiklab.arbess.setting.group.model.GroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    GroupDao groupDao;

    @Override
    public String createGroup(Group group) {
        group.setCreateTime(PipelineUtil.date(1));
        return groupDao.creatGroup(group);
    }

    @Override
    public void updateGroup(Group group) {
        groupDao.updateGroup(group);
    }

    @Override
    public void deleteGroup(String groupId) {
        groupDao.deleteGroup(groupId);
    }

    @Override
    public Group findOneGroup(String groupId) {
        Group group = groupDao.findOneGroup(groupId);
        joinTemplate.joinQuery(group);
        return group;
    }

    @Override
    public List<Group> findAllGroup() {
        return groupDao.findAllGroup();
    }

    @Override
    public List<Group> findGroupList(GroupQuery groupQuery) {
        List<Group> groupList = groupDao.findGroupList(groupQuery);
        joinTemplate.joinQuery(groupList);
        return groupList;
    }

    @Override
    public Pagination<Group> findGroupPage(GroupQuery groupQuery) {
        Pagination<Group> groupPage = groupDao.findGroupPage(groupQuery);
        List<Group> dataList = groupPage.getDataList();
        joinTemplate.joinQuery(dataList);
        return PaginationBuilder.build(groupPage,dataList);
    }

    @Override
    public List<Group> findAllGroupList(List<String> idList) {
        List<Group> groupList = groupDao.findAllGroupList(idList);
        joinTemplate.joinQuery(groupList);
        return groupList;
    }


    @Override
    public Integer findGroupNumber() {
        return groupDao.findGroupNumber();
    }


}
