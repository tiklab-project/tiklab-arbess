package io.tiklab.matflow.setting.dao;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.setting.entity.EnvEntity;
import io.tiklab.matflow.setting.entity.GroupEntity;
import io.tiklab.matflow.setting.model.Env;
import io.tiklab.matflow.setting.model.Group;
import io.tiklab.matflow.setting.model.GroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class GroupDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String creatGroup(Group group) {
        GroupEntity scanEntity = BeanMapper.map(group, GroupEntity.class);
        return jpaTemplate.save(scanEntity,String.class);
    }

    public void updateGroup(Group group) {
        GroupEntity groupEntity = BeanMapper.map(group, GroupEntity.class);
        jpaTemplate.update(groupEntity);
    }


    public void deleteGroup(String groupId) {
        jpaTemplate.delete(GroupEntity.class,groupId);
    }


    public Group findOneGroup(String groupId) {
        GroupEntity scanEntity = jpaTemplate.findOne(GroupEntity.class, groupId);

        return BeanMapper.map(scanEntity,Group.class);
    }


    public List<Group> findAllGroup() {
        List<GroupEntity> scanEntityList = jpaTemplate.findAll(GroupEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,Group.class);
    }


    public List<Group> findGroupList(GroupQuery groupQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(GroupEntity.class)
                .eq("userId", groupQuery.getUserId())
                .like("groupName",groupQuery.getGroupName());
        QueryCondition queryCondition = queryBuilders.orders(OrderBuilders.instance().desc("createTime").get())
                .get();
        List<GroupEntity> scanEntityList = jpaTemplate.findList(queryCondition, GroupEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,Group.class);
    }


    public Pagination<Group> findGroupPage(GroupQuery groupQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(GroupEntity.class)
                .eq("userId", groupQuery.getUserId())
                .like("groupName",groupQuery.getGroupName());
        QueryCondition queryCondition = queryBuilders
                .orders(OrderBuilders.instance().desc("createTime").get())
                .pagination(groupQuery.getPageParam())
                .get();
        Pagination<GroupEntity> entityPage = jpaTemplate.findPage(queryCondition, GroupEntity.class);
        List<GroupEntity> dataList = entityPage.getDataList();
        if (dataList == null || dataList.isEmpty()){
            return PaginationBuilder.build(entityPage,Collections.emptyList());
        }
        List<Group> groups = BeanMapper.mapList(dataList, Group.class);
        return PaginationBuilder.build(entityPage,groups);
    }


    public List<Group> findAllGroupList(List<String> idList){
        List<GroupEntity> envEntities = jpaTemplate.findList(GroupEntity.class, idList);
        return BeanMapper.mapList(envEntities,Group.class);
    }
    
    
}
