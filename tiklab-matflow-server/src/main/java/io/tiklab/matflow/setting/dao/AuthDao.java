package io.tiklab.matflow.setting.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.setting.entity.AuthMatFlowEntity;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流水线基本认证数据访问
 */
@Repository
public class AuthDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 基本认证
     * @param authMatFlowEntity 基本认证
     * @return 基本认证id
     */
    public  String createAuth(AuthMatFlowEntity authMatFlowEntity){
        authMatFlowEntity.setCreateTime(PipelineUtil.date(1));
        authMatFlowEntity.setUserId(LoginContext.getLoginId());
        return jpaTemplate.save(authMatFlowEntity, String.class);
    }

    /**
     * 删除基本认证
     * @param authId 基本认证id
     */
    public  void deleteAuth(String authId){
        jpaTemplate.delete(AuthMatFlowEntity.class, authId);
    }

    /**
     * 更新基本认证
     * @param authMatFlowEntity 更新信息
     */
    public  void updateAuth(AuthMatFlowEntity authMatFlowEntity){
        jpaTemplate.update(authMatFlowEntity);
    }

    /**
     * 查询单个基本认证信息
     * @param authId 基本认证id
     * @return 基本认证信息
     */
    public AuthMatFlowEntity findOneAuth(String authId){
        return jpaTemplate.findOne(AuthMatFlowEntity.class,authId);
    }

    /**
     * 查询所有基本认证
     * @return 基本认证集合
     */
    public List<AuthMatFlowEntity> findAllAuth(){
        return jpaTemplate.findAll(AuthMatFlowEntity.class);
    }


    public List<AuthMatFlowEntity> findAllAuthList(List<String> idList){
        return jpaTemplate.findList(AuthMatFlowEntity.class,idList);
    }
    
}
