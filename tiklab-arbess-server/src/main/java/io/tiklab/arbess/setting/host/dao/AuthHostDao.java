package io.tiklab.arbess.setting.host.dao;

import io.tiklab.arbess.setting.host.model.AuthHostQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.OrQueryCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.OrQueryBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.setting.host.entity.AuthHostEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

        QueryBuilders builders = QueryBuilders.createQuery(AuthHostEntity.class)
                .eq("type", hostQuery.getType());

        if (!StringUtils.isEmpty(hostQuery.getName())){
            OrQueryCondition orQueryCondition = OrQueryBuilders.instance()
                    .like("ip", hostQuery.getName())
                    .like("name", hostQuery.getName())
                    .get();
            builders = builders.or(orQueryCondition);
        }

        QueryCondition condition = builders
                .pagination(hostQuery.getPageParam())
                .orders(hostQuery.getOrderParams())
                .get();

        return jpaTemplate.findPage(condition, AuthHostEntity.class);
    }


    public List<AuthHostEntity> findAuthHostList(AuthHostQuery hostQuery){

        QueryBuilders builders = QueryBuilders.createQuery(AuthHostEntity.class)
                .eq("type", hostQuery.getType());

        if (!StringUtils.isEmpty(hostQuery.getName())){
            OrQueryCondition orQueryCondition = OrQueryBuilders.instance()
                    .like("ip", hostQuery.getName())
                    .like("name", hostQuery.getName())
                    .get();
            builders = builders.or(orQueryCondition);
        }

        QueryCondition condition = builders
                .pagination(hostQuery.getPageParam())
                .orders(hostQuery.getOrderParams())
                .get();

        return jpaTemplate.findList(condition, AuthHostEntity.class);
    }



    public Integer findHostNumber() {
        String sql = "SELECT COUNT(*) AS number FROM pip_auth_host ;";
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return ((Long) map.get("number")).intValue();
    }


}
