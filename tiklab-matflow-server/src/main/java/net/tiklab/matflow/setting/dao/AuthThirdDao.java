package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.setting.entity.AuthThirdEntity;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        authThirdEntity.setCreateTime(PipelineUntil.date(1));
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


    public List<AuthThirdEntity> findAllAuthServerList(List<String> idList){
        return jpaTemplate.findList(AuthThirdEntity.class,idList);
    }
    
}
