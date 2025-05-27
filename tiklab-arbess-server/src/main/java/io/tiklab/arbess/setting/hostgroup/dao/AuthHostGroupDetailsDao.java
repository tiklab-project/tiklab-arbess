package io.tiklab.arbess.setting.hostgroup.dao;

import io.tiklab.arbess.setting.hostgroup.entity.AuthHostGroupDetailsEntity;
import io.tiklab.arbess.setting.hostgroup.model.AuthHostGroupDetails;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
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
