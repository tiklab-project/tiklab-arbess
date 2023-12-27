package io.thoughtware.matflow.setting.dao;

import io.thoughtware.matflow.setting.entity.AuthHostGroupDetailsEntity;
import io.thoughtware.matflow.setting.model.AuthHostGroupDetails;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author zcamy
 */
@Repository
public class AuthHostGroupDetailsDao {


    @Autowired
    JpaTemplate jpaTemplate;

    public String creatHostGroupDetails(AuthHostGroupDetails hostGroupDetailsDetails) {
        AuthHostGroupDetailsEntity scanEntity = BeanMapper.map(hostGroupDetailsDetails, AuthHostGroupDetailsEntity.class);
        return jpaTemplate.save(scanEntity,String.class);
    }

    public void updateHostGroupDetails(AuthHostGroupDetails hostGroupDetails) {
        AuthHostGroupDetailsEntity scanEntity = BeanMapper.map(hostGroupDetails, AuthHostGroupDetailsEntity.class);
        jpaTemplate.update(scanEntity);
    }


    public void deleteHostGroupDetails(String groupDetailsId) {
        jpaTemplate.delete(AuthHostGroupDetailsEntity.class,groupDetailsId);
    }


    public AuthHostGroupDetails findOneHostGroupDetails(String groupDetailsId) {
        AuthHostGroupDetailsEntity scanEntity = jpaTemplate.findOne(AuthHostGroupDetailsEntity.class, groupDetailsId);

        return BeanMapper.map(scanEntity,AuthHostGroupDetails.class);
    }


    public List<AuthHostGroupDetails> findAllHostGroupDetails() {
        List<AuthHostGroupDetailsEntity> scanEntityList = jpaTemplate.findAll(AuthHostGroupDetailsEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,AuthHostGroupDetails.class);
    }


    public List<AuthHostGroupDetails> findHostGroupDetailsList(String groupId) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(AuthHostGroupDetailsEntity.class)
                .eq("groupId", groupId);
        QueryCondition queryCondition = queryBuilders
                .get();
        List<AuthHostGroupDetailsEntity> scanEntityList = jpaTemplate.findList(queryCondition, AuthHostGroupDetailsEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,AuthHostGroupDetails.class);
    }
    
    
}
