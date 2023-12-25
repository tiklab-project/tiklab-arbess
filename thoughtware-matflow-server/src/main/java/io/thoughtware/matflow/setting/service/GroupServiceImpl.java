package io.thoughtware.matflow.setting.service;

import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.matflow.setting.dao.GroupDao;
import io.thoughtware.matflow.setting.model.Group;
import io.thoughtware.matflow.setting.model.GroupQuery;
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


}
