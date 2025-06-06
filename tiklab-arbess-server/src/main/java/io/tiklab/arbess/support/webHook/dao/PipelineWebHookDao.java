package io.tiklab.arbess.support.webHook.dao;

import io.tiklab.arbess.support.webHook.entity.WebHookEntity;
import io.tiklab.arbess.support.webHook.model.WebHookQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PipelineWebHookDao {

    @Autowired
    JpaTemplate jpaTemplate;
   
    public String createWebHook(WebHookEntity webHook) {
        return jpaTemplate.save(webHook,String.class);
    }

   
    public void deleteWebHook(String id) {
        jpaTemplate.delete(WebHookEntity.class,id);
    }

   
    public void updateWebHook(WebHookEntity webHook) {
        jpaTemplate.update(webHook);
    }

   
    public WebHookEntity findWebHook(String id) {
        return jpaTemplate.findOne(WebHookEntity.class,id);
    }

   
    public List<WebHookEntity> findAllWebHook() {
        List<WebHookEntity> webHookEntityList = jpaTemplate.findAll(WebHookEntity.class);
        if (Objects.isNull(webHookEntityList)){
            return new ArrayList<>();
        }

        return webHookEntityList;
    }

   
    public List<WebHookEntity> findWebHookList(WebHookQuery webHookQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(WebHookEntity.class)
                .like("name", webHookQuery.getName(),false)
                .eq("pipelineId", webHookQuery.getPipelineId())
                .eq("key", webHookQuery.getKey())
                .eq("type", webHookQuery.getType())
                .orders(webHookQuery.getOrderParams())
                .get();
        List<WebHookEntity> webHookEntityList = jpaTemplate.findList(queryCondition, WebHookEntity.class);
        if (Objects.isNull(webHookEntityList) || webHookEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return webHookEntityList;
    }

   
    public List<WebHookEntity> findWebHookList(List<String> idList) {
        List<WebHookEntity> webHookEntityList = jpaTemplate.findList(WebHookEntity.class, idList);
         if (Objects.isNull(webHookEntityList)){
              return new ArrayList<>();
         }
        return webHookEntityList;
    }

   
    public Pagination<WebHookEntity> findWebHookPage(WebHookQuery webHookQuery) {

        QueryCondition queryCondition = QueryBuilders.createQuery(WebHookEntity.class)
                .like("name", webHookQuery.getName(),false)
                .eq("pipelineId", webHookQuery.getPipelineId())
                .eq("key", webHookQuery.getKey())
                .eq("type", webHookQuery.getType())
                .orders(webHookQuery.getOrderParams())
                .pagination(webHookQuery.getPageParam())
                .get();
        Pagination<WebHookEntity> webHookEntityPage = jpaTemplate.findPage(queryCondition, WebHookEntity.class);

        List<WebHookEntity> dataList = webHookEntityPage.getDataList();
         if (Objects.isNull(dataList) || dataList.isEmpty()){
              return PaginationBuilder. build(webHookEntityPage, new ArrayList<>());
         }

        return  PaginationBuilder. build(webHookEntityPage, dataList);
    }
}
