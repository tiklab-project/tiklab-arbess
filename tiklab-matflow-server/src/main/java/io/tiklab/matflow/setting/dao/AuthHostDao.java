package io.tiklab.matflow.setting.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.setting.entity.AuthHostEntity;
import io.tiklab.matflow.setting.model.AuthHostQuery;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
                .append( "where ((auth_public = ").append(2)
                .append( " and user_id = '").append(hostQuery.getUserId()).append("')") //查询用户私有的主机
                .append(" or ( auth_public = ").append(1).append("))"); //查询公共的主机
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

}
