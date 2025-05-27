package io.tiklab.arbess.setting.k8s.dao;

import io.tiklab.arbess.setting.k8s.entity.KubectlEntity;
import io.tiklab.arbess.setting.k8s.model.KubectlQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.OrQueryCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.OrQueryBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KubectlDao {


    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 主机认证
     * @param kubectlEntity 主机认证
     * @return 主机认证id
     */
    public  String createKubectl(KubectlEntity kubectlEntity){
        return jpaTemplate.save(kubectlEntity, String.class);
    }

    /**
     * 删除主机认证
     * @param KubectlId 主机认证id
     */
    public  void deleteKubectl(String KubectlId){
        jpaTemplate.delete(KubectlEntity.class, KubectlId);
    }

    /**
     * 更新主机认证
     * @param kubectlEntity 更新信息
     */
    public  void updateKubectl(KubectlEntity kubectlEntity){
        jpaTemplate.update(kubectlEntity);
    }

    /**
     * 查询单个主机认证信息
     * @param KubectlId 主机认证id
     * @return 主机认证信息
     */
    public KubectlEntity findOneKubectl(String KubectlId){
        return jpaTemplate.findOne(KubectlEntity.class,KubectlId);
    }

    /**
     * 查询所有主机认证
     * @return 主机认证集合
     */
    public List<KubectlEntity> findAllKubectl(){
        return jpaTemplate.findAll(KubectlEntity.class);
    }


    public List<KubectlEntity> findAllKubectlList(List<String> idList){
        return jpaTemplate.findList(KubectlEntity.class,idList);
    }

    /**
     * 分页查询
     * @param hostQuery 查询条件
     * @return 数据
     */
    public Pagination<KubectlEntity> findKubectlPage(KubectlQuery hostQuery){

        QueryCondition condition = QueryBuilders.createQuery(KubectlEntity.class)
                .pagination(hostQuery.getPageParam())
                .orders(hostQuery.getOrderParams())
                .get();

        return jpaTemplate.findPage(condition, KubectlEntity.class);
    }


    public List<KubectlEntity> findKubectlList(KubectlQuery hostQuery){

        QueryCondition condition = QueryBuilders.createQuery(KubectlEntity.class)
                .like("name",  hostQuery.getName())
                .pagination(hostQuery.getPageParam())
                .orders(hostQuery.getOrderParams())
                .get();

        return jpaTemplate.findList(condition, KubectlEntity.class);
    }



    public Integer findHostNumber() {
        String sql = "SELECT COUNT(*) AS number FROM pip_auth_host ;";
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return ((Long) map.get("number")).intValue();
    }


}
