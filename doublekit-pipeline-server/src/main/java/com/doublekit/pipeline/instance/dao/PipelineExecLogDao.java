package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineExecLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PipelineExecLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param pipelineExecLogEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createLog(PipelineExecLogEntity pipelineExecLogEntity){

        return jpaTemplate.save(pipelineExecLogEntity,String.class);

    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deleteLog(String id){
        jpaTemplate.delete(PipelineExecLogEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param pipelineExecLogEntity 更新后流水线日志信息
     */
    public void updateLog(PipelineExecLogEntity pipelineExecLogEntity){
        jpaTemplate.update(pipelineExecLogEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public PipelineExecLogEntity findOne(String id){
        return jpaTemplate.findOne(PipelineExecLogEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<PipelineExecLogEntity> findAllLog(){ return jpaTemplate.findAll(PipelineExecLogEntity.class); }


    public List<PipelineExecLogEntity> findAllLogList(List<String> idList){

        return jpaTemplate.findList(PipelineExecLogEntity.class,idList);
    }

}
