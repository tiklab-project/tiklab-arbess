package io.tiklab.matflow.setting.dao;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.setting.entity.AuthHostGroupEntity;
import io.tiklab.matflow.setting.model.AuthHostGroup;
import io.tiklab.matflow.setting.model.AuthHostGroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
