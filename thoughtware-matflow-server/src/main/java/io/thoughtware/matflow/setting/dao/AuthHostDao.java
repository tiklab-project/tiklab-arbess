package io.thoughtware.matflow.setting.dao;

import io.thoughtware.matflow.setting.model.AuthHostQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.matflow.setting.entity.AuthHostEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询
     * @param hostQuery 查询条件
     * @return 数据
     */
    public Pagination<AuthHostEntity> findAuthHostPage(AuthHostQuery hostQuery){

        StringBuilder sql = new StringBuilder();
        String type = hostQuery.getType();

        sql.append( " select * from pip_auth_host ")
                .append( "where 1 = 1");
        if (!type.equals("all")){
            sql.append(" and type = '").append(hostQuery.getType()).append("'"); // 根据类型查询
        }
        if (!StringUtils.isEmpty(hostQuery.getName())){
            sql.append(" and ( name like '%").append(hostQuery.getName()).append("%'"); // 名称模糊查询
            sql.append(" or ip like '%").append(hostQuery.getIp()).append("%')"); // 名称模糊查询
        }

        return jpaTemplate.getJdbcTemplate().findPage(sql.toString(), null,
                hostQuery.getPageParam(), new BeanPropertyRowMapper<>(AuthHostEntity.class));
    }



    public Integer findHostNumber() {
        String sql = "SELECT COUNT(*) AS number FROM pip_auth_host ;";
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return ((Long) map.get("number")).intValue();
    }


}
