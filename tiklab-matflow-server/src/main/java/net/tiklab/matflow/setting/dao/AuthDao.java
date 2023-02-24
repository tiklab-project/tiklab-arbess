package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.setting.entity.AuthEntity;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AuthDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 基本认证
     * @param authEntity 基本认证
     * @return 基本认证id
     */
    public  String createAuth(AuthEntity authEntity){
        authEntity.setCreateTime(PipelineUntil.date(1));
        authEntity.setUserId(LoginContext.getLoginId());
        return jpaTemplate.save(authEntity, String.class);
    }

    /**
     * 删除基本认证
     * @param authId 基本认证id
     */
    public  void deleteAuth(String authId){
        jpaTemplate.delete(AuthEntity.class, authId);
    }

    /**
     * 更新基本认证
     * @param authEntity 更新信息
     */
    public  void updateAuth(AuthEntity authEntity){
        jpaTemplate.update(authEntity);
    }

    /**
     * 查询单个基本认证信息
     * @param authId 基本认证id
     * @return 基本认证信息
     */
    public AuthEntity findOneAuth(String authId){
        return jpaTemplate.findOne(AuthEntity.class,authId);
    }

    /**
     * 查询所有基本认证
     * @return 基本认证集合
     */
    public List<AuthEntity> findAllAuth(){
        return jpaTemplate.findAll(AuthEntity.class);
    }


    public List<AuthEntity> findAllAuthList(List<String> idList){
        return jpaTemplate.findList(AuthEntity.class,idList);
    }
    
}
