package io.tiklab.matflow.setting.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.setting.entity.AuthHostEntity;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthHostDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 主机认证
     * @param authHostEntity 主机认证
     * @return 主机认证id
     */
    public  String createAuthHost(AuthHostEntity authHostEntity){
        authHostEntity.setCreateTime(PipelineUtil.date(1));
        authHostEntity.setUserId(LoginContext.getLoginId());
        return jpaTemplate.save(authHostEntity, String.class);
    }

    /**
     * 删除主机认证
     * @param authHostId 主机认证id
     */
    public  void deleteAuthHost(String authHostId){
        jpaTemplate.delete(AuthHostEntity.class, authHostId);
    }

    /**
     * 更新主机认证
     * @param authHostEntity 更新信息
     */
    public  void updateAuthHost(AuthHostEntity authHostEntity){
        jpaTemplate.update(authHostEntity);
    }

    /**
     * 查询单个主机认证信息
     * @param authHostId 主机认证id
     * @return 主机认证信息
     */
    public AuthHostEntity findOneAuthHost(String authHostId){
        return jpaTemplate.findOne(AuthHostEntity.class,authHostId);
    }

    /**
     * 查询所有主机认证
     * @return 主机认证集合
     */
    public List<AuthHostEntity> findAllAuthHost(){
        return jpaTemplate.findAll(AuthHostEntity.class);
    }


    public List<AuthHostEntity> findAllAuthHostList(List<String> idList){
        return jpaTemplate.findList(AuthHostEntity.class,idList);
    }
    
}
