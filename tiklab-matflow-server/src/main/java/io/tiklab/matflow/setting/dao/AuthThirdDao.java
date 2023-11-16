package io.tiklab.matflow.setting.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.setting.entity.AuthThirdEntity;
import io.tiklab.matflow.setting.model.AuthThirdQuery;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class AuthThirdDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 第三方认证
     * @param authThirdEntity 第三方认证
     * @return 第三方认证id
     */
    public  String createAuthServer(AuthThirdEntity authThirdEntity){
        authThirdEntity.setCreateTime(PipelineUtil.date(1));
        authThirdEntity.setUserId(LoginContext.getLoginId());
        return jpaTemplate.save(authThirdEntity, String.class);
    }

    /**
     * 删除第三方认证
     * @param authServerId 第三方认证id
     */
    public  void deleteAuthServer(String authServerId){
        jpaTemplate.delete(AuthThirdEntity.class, authServerId);
    }

    /**
     * 更新第三方认证
     * @param authThirdEntity 更新信息
     */
    public  void updateAuthServer(AuthThirdEntity authThirdEntity){
        jpaTemplate.update(authThirdEntity);
    }

    /**
     * 查询单个第三方认证信息
     * @param authServerId 第三方认证id
     * @return 第三方认证信息
     */
    public AuthThirdEntity findOneAuthServer(String authServerId){
        return jpaTemplate.findOne(AuthThirdEntity.class,authServerId);
    }

    /**
     * 查询所有第三方认证
     * @return 第三方认证集合
     */
    public List<AuthThirdEntity> findAllAuthServer(){
        return jpaTemplate.findAll(AuthThirdEntity.class);
    }

    public List<AuthThirdEntity> findAuthServerList(AuthThirdQuery thirdQuery){
        QueryBuilders builders = QueryBuilders.createQuery(AuthThirdEntity.class)
                .eq("userId", thirdQuery.getUsrId())
                .eq("type",thirdQuery.getType());
        QueryCondition queryCondition = builders.orders(thirdQuery.getOrderParams()).get();

        List<AuthThirdEntity> entityList = jpaTemplate.findList(queryCondition, AuthThirdEntity.class);
        if (Objects.isNull(entityList) || entityList.isEmpty()){
            return Collections.emptyList();
        }
        return entityList;
    }


    public  Pagination<AuthThirdEntity> findAuthServerPage(AuthThirdQuery thirdQuery){
        QueryBuilders builders = QueryBuilders.createQuery(AuthThirdEntity.class)
                .eq("userId", thirdQuery.getUsrId())
                .eq("type",thirdQuery.getType());

        QueryCondition queryCondition = builders.orders(thirdQuery.getOrderParams()).get();

        return jpaTemplate.findPage(queryCondition, AuthThirdEntity.class);
    }


    public List<AuthThirdEntity> findAllAuthServerList(List<String> idList){
        return jpaTemplate.findList(AuthThirdEntity.class,idList);
    }
    
}
