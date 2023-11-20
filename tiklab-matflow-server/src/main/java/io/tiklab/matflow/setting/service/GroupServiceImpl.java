package io.tiklab.matflow.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.setting.dao.GroupDao;
import io.tiklab.matflow.setting.model.Env;
import io.tiklab.matflow.setting.model.Group;
import io.tiklab.matflow.setting.model.GroupQuery;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
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
