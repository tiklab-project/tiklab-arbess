package io.tiklab.arbess.setting.dao;

import io.tiklab.arbess.setting.entity.AuthEntity;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.eam.common.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流水线基本认证数据访问
 */
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
        authEntity.setCreateTime(PipelineUtil.date(1));
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


    public Integer findAuthNumber() {
        String sql = "SELECT COUNT(*) AS number FROM pip_auth;";
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return ((Long) map.get("number")).intValue();
    }

    
}
