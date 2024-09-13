package io.thoughtware.arbess.setting.dao;

import io.thoughtware.arbess.setting.entity.AuthHostK8sEntity;
import io.thoughtware.arbess.setting.model.AuthHostK8sQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.OrQueryCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.OrQueryBuilders;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthHostK8sDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 主机认证
     * @param authHostK8sEntity 主机认证
     * @return 主机认证id
     */
    public  String createAuthHostK8s(AuthHostK8sEntity authHostK8sEntity){
        return jpaTemplate.save(authHostK8sEntity, String.class);
    }

    /**
     * 删除主机认证
     * @param authHostK8sId 主机认证id
     */
    public  void deleteAuthHostK8s(String authHostK8sId){
        jpaTemplate.delete(AuthHostK8sEntity.class, authHostK8sId);
    }

    /**
     * 更新主机认证
     * @param authHostK8sEntity 更新信息
     */
    public  void updateAuthHostK8s(AuthHostK8sEntity authHostK8sEntity){
        jpaTemplate.update(authHostK8sEntity);
    }

    /**
     * 查询单个主机认证信息
     * @param authHostK8sId 主机认证id
     * @return 主机认证信息
     */
    public AuthHostK8sEntity findOneAuthHostK8s(String authHostK8sId){
        return jpaTemplate.findOne(AuthHostK8sEntity.class,authHostK8sId);
    }

    /**
     * 查询所有主机认证
     * @return 主机认证集合
     */
    public List<AuthHostK8sEntity> findAllAuthHostK8s(){
        return jpaTemplate.findAll(AuthHostK8sEntity.class);
    }


    public List<AuthHostK8sEntity> findAllAuthHostK8sList(List<String> idList){
        return jpaTemplate.findList(AuthHostK8sEntity.class,idList);
    }

    /**
     * 分页查询
     * @param hostQuery 查询条件
     * @return 数据
     */
    public Pagination<AuthHostK8sEntity> findAuthHostK8sPage(AuthHostK8sQuery hostQuery){

        QueryBuilders builders = QueryBuilders.createQuery(AuthHostK8sEntity.class)
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

        return jpaTemplate.findPage(condition, AuthHostK8sEntity.class);
    }


    public List<AuthHostK8sEntity> findAuthHostK8sList(AuthHostK8sQuery hostQuery){

        QueryBuilders builders = QueryBuilders.createQuery(AuthHostK8sEntity.class)
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

        return jpaTemplate.findList(condition, AuthHostK8sEntity.class);
    }



    public Integer findHostNumber() {
        String sql = "SELECT COUNT(*) AS number FROM pip_auth_host ;";
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return ((Long) map.get("number")).intValue();
    }


}
