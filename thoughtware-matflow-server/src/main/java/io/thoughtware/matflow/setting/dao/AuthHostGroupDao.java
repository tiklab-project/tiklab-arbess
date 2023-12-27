package io.thoughtware.matflow.setting.dao;

import io.thoughtware.matflow.setting.entity.AuthHostGroupEntity;
import io.thoughtware.matflow.setting.model.AuthHostGroup;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.matflow.setting.model.AuthHostGroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;

/**
 * @author zcamy
 */
@Repository
public class AuthHostGroupDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String creatHostGroup(AuthHostGroup hostGroup) {
        AuthHostGroupEntity scanEntity = BeanMapper.map(hostGroup, AuthHostGroupEntity.class);
        return jpaTemplate.save(scanEntity,String.class);
    }

    public void updateHostGroup(AuthHostGroup hostGroup) {
        AuthHostGroupEntity scanEntity = BeanMapper.map(hostGroup, AuthHostGroupEntity.class);
        jpaTemplate.update(scanEntity);
    }


    public void deleteHostGroup(String groupId) {
        jpaTemplate.delete(AuthHostGroupEntity.class,groupId);
    }


    public AuthHostGroup findOneHostGroup(String groupId) {
        AuthHostGroupEntity scanEntity = jpaTemplate.findOne(AuthHostGroupEntity.class, groupId);

        return BeanMapper.map(scanEntity,AuthHostGroup.class);
    }


    public List<AuthHostGroup> findAllHostGroup() {
        List<AuthHostGroupEntity> scanEntityList = jpaTemplate.findAll(AuthHostGroupEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,AuthHostGroup.class);
    }


    public List<AuthHostGroup> findHostGroupList(AuthHostGroupQuery groupQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(AuthHostGroupEntity.class)
                .eq("userId", groupQuery.getUserId());
        QueryCondition queryCondition = queryBuilders
                .orders(OrderBuilders.instance().desc("createTime").get())
                .get();
        List<AuthHostGroupEntity> scanEntityList = jpaTemplate.findList(queryCondition, AuthHostGroupEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,AuthHostGroup.class);
    }
    
    
}
