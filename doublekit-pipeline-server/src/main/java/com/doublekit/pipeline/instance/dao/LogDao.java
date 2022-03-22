package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class LogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param logEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createLog(LogEntity logEntity){

        return jpaTemplate.save(logEntity,String.class);

    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deleteLog(String id){
        jpaTemplate.delete(LogEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param logEntity 更新后流水线日志信息
     */
    public void updateLog(LogEntity logEntity){
        jpaTemplate.update(logEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public LogEntity findOne(String id){
        return jpaTemplate.findOne(LogEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<LogEntity> findAllLog(){ return jpaTemplate.findAll(LogEntity.class); }


    public List<LogEntity> findAllLogList(List<String> idList){

        return jpaTemplate.findList(LogEntity.class,idList);
    }

}
