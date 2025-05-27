package io.tiklab.arbess.setting.hostgroup.dao;

import io.tiklab.arbess.setting.hostgroup.entity.AuthHostGroupEntity;
import io.tiklab.arbess.setting.hostgroup.model.AuthHostGroup;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.setting.hostgroup.model.AuthHostGroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Integer findHostGroupNumber() {
        String sql = "SELECT COUNT(*) AS number FROM pip_auth_host_group ;";
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return ((Long) map.get("number")).intValue();
    }
    
}
