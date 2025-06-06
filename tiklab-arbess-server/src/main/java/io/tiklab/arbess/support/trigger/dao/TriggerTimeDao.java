package io.tiklab.arbess.support.trigger.dao;

import io.tiklab.arbess.support.trigger.entity.TriggerTimeEntity;
import io.tiklab.arbess.support.trigger.model.TriggerTimeQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class TriggerTimeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param triggerTimeEntity time信息
     * @return timeId
     */
    public String createTime(TriggerTimeEntity triggerTimeEntity){
        return jpaTemplate.save(triggerTimeEntity,String.class);
    }

    /**
     * 删除time
     * @param timeId timeId
     */
    public void deleteTime(String timeId){
        jpaTemplate.delete(TriggerTimeEntity.class,timeId);
    }

    /**
     * 更新time
     * @param triggerTimeEntity 更新信息
     */
    public void updateTime(TriggerTimeEntity triggerTimeEntity){
        jpaTemplate.update(triggerTimeEntity);
    }

    /**
     * 查询单个time信息
     * @param timeId timeId
     * @return time信息
     */
    public TriggerTimeEntity findOneTime(String timeId){
        return jpaTemplate.findOne(TriggerTimeEntity.class,timeId);
    }

    /**
     * 查询所有time信息
     * @return time信息集合
     */
    public List<TriggerTimeEntity> findAllTime(){
        return jpaTemplate.findAll(TriggerTimeEntity.class);
    }


    public List<TriggerTimeEntity> findTriggerTimeList(List<String> idList){
        return jpaTemplate.findList(TriggerTimeEntity.class,idList);
    }


    public List<TriggerTimeEntity> findTriggerTimeList(TriggerTimeQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerTimeEntity.class)
                .eq("execStatus", query.getExecStatus())
                .eq("taskType", query.getTaskType())
                .eq("triggerId", query.getTriggerId())
                .eq("corn", query.getCorn())
                .orders(query.getOrderParams())
                .get();
        List<TriggerTimeEntity> timeEntityList = jpaTemplate.findList(queryCondition, TriggerTimeEntity.class);
        if (Objects.isNull(timeEntityList) || timeEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return timeEntityList;
    }


    public Pagination<TriggerTimeEntity> findTriggerTimePage(TriggerTimeQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerTimeEntity.class)
                .eq("execStatus", query.getExecStatus())
                .eq("taskType", query.getTaskType())
                .eq("triggerId", query.getTriggerId())
                .eq("corn", query.getCorn())
                .orders(query.getOrderParams())
                .pagination(query.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition, TriggerTimeEntity.class);
    }


}
