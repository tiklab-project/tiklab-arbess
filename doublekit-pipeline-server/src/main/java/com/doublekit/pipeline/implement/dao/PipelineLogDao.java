package com.doublekit.pipeline.implement.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.implement.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.implement.entity.PipelineLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineLogDao {


    private static Logger logger = LoggerFactory.getLogger(PipelineDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param pipelineLogEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createPipelineLog(PipelineLogEntity pipelineLogEntity){

        return jpaTemplate.save(pipelineLogEntity,String.class);

    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deletePipelineLog(String id){
        jpaTemplate.delete(PipelineLogEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param pipelineLogEntity 更新后流水线日志信息
     */
    public void updatePipelineLog(PipelineLogEntity pipelineLogEntity){
        jpaTemplate.update(pipelineLogEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public PipelineLogEntity selectPipelineLog(String id){
        return jpaTemplate.findOne(PipelineLogEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<PipelineLogEntity> selectAllPipelineLog(){ return jpaTemplate.findAll(PipelineLogEntity.class); }


    public List<PipelineLogEntity> selectAllPipelineLogList(List<String> idList){

        return jpaTemplate.findList(PipelineLogEntity.class,idList);
    }
}
