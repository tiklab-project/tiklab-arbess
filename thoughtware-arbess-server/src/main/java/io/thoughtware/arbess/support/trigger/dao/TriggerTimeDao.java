package io.thoughtware.arbess.support.trigger.dao;

import io.thoughtware.arbess.support.trigger.entity.TriggerTimeEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    public List<TriggerTimeEntity> findAllTimeList(List<String> idList){
        return jpaTemplate.findList(TriggerTimeEntity.class,idList);
    }
}
