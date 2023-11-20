package io.tiklab.matflow.setting.dao;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.setting.entity.EnvEntity;
import io.tiklab.matflow.setting.model.Env;
import io.tiklab.matflow.setting.model.EnvQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;

@Repository
public class EnvDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String creatEnv(Env env) {
        EnvEntity scanEntity = BeanMapper.map(env, EnvEntity.class);
        return jpaTemplate.save(scanEntity,String.class);
    }

    public void updateEnv(Env env) {
        EnvEntity envEntity = BeanMapper.map(env, EnvEntity.class);
        jpaTemplate.update(envEntity);
    }


    public void deleteEnv(String groupId) {
        jpaTemplate.delete(EnvEntity.class,groupId);
    }


    public Env findOneEnv(String groupId) {
        EnvEntity scanEntity = jpaTemplate.findOne(EnvEntity.class, groupId);

        return BeanMapper.map(scanEntity,Env.class);
    }


    public List<Env> findAllEnv() {
        List<EnvEntity> scanEntityList = jpaTemplate.findAll(EnvEntity.class);
        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,Env.class);
    }


    public List<Env> findEnvList(EnvQuery envQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(EnvEntity.class)
                .eq("userId", envQuery.getUserId())
                .like("envName",envQuery.getEnvName());
        QueryCondition queryCondition = queryBuilders.orders(OrderBuilders.instance().desc("createTime").get())
                .get();
        List<EnvEntity> scanEntityList = jpaTemplate.findList(queryCondition, EnvEntity.class);

        if (scanEntityList == null || scanEntityList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(scanEntityList,Env.class);
    }


    public Pagination<Env> findEnvPage(EnvQuery envQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(EnvEntity.class)
                .eq("userId", envQuery.getUserId())
                .like("envName",envQuery.getEnvName());
        QueryCondition queryCondition = queryBuilders
                .orders(OrderBuilders.instance().desc("createTime").get())
                .pagination(envQuery.getPageParam())
                .get();
        Pagination<EnvEntity> entityPage = jpaTemplate.findPage(queryCondition, EnvEntity.class);
        List<EnvEntity> dataList = entityPage.getDataList();
        if (dataList == null || dataList.isEmpty()){
            return PaginationBuilder.build(entityPage,Collections.emptyList());
        }
        List<Env> envs = BeanMapper.mapList(dataList, Env.class);
        return PaginationBuilder.build(entityPage,envs);
    }


    public List<Env> findAllEnvList(List<String> idList){
        List<EnvEntity> envEntities = jpaTemplate.findList(EnvEntity.class, idList);
        return BeanMapper.mapList(envEntities,Env.class);
    }
    
}










