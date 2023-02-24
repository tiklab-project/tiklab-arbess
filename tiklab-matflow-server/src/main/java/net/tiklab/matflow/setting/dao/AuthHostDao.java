package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.setting.entity.AuthHostEntity;
import net.tiklab.utils.context.LoginContext;
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
        authHostEntity.setCreateTime(PipelineUntil.date(1));
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
