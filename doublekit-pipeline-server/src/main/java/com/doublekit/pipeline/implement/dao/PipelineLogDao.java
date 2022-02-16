package com.doublekit.pipeline.implement.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.implement.entity.PipelineLogEntity;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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


    /**
     * 获取最近一次的日志信息
     * @param pipelineId 流水线id
     * @return 构建日志
     */
    public PipelineLogEntity selectLastLog(String pipelineId){

        List<PipelineLogEntity> pipelineLogEntityList = selectNameLog(pipelineId);

        if (pipelineLogEntityList.size() != 0){

            //将同一任务构建历史通过时间排序
            pipelineLogEntityList.sort(new Comparator<PipelineLogEntity>() {
                @Override
                public int compare(PipelineLogEntity pipelineLogEntity1, PipelineLogEntity pipelineLogEntity2) {

                    return pipelineLogEntity1.getLogCreateTime().compareTo(pipelineLogEntity2.getLogCreateTime());
                }
            });
            String LogId = pipelineLogEntityList.get(pipelineLogEntityList.size() - 1).getLogId();

            return selectPipelineLog(LogId);
        }

        return null;
    }

    /**
     * 根据流水线id获取所有日志
     * @param pipelineId 流水线id
     * @return 日志集合
     */
    public List<PipelineLogEntity> selectNameLog(String pipelineId){

        List<PipelineLogEntity> pipelineLogEntityList = selectAllPipelineLog();

        List<PipelineLogEntity> pipelineLogEntities = new ArrayList<>();

        for (PipelineLogEntity pipelineLogEntity : pipelineLogEntityList) {

            if (pipelineLogEntity.getPipelineId().equals(pipelineId)){

                pipelineLogEntities.add(pipelineLogEntity);

            }

        }
        return pipelineLogEntities;

    }

    /**
     * 获取上次成功日志
     * @param pipelineId 流水线id
     * @return 成功日志
     */
    public PipelineLogEntity selectLastSuccess(String pipelineId){

        List<PipelineLogEntity> pipelineLogEntityList = selectNameLog(pipelineId);

        if (pipelineLogEntityList.size() != 0){

            for (PipelineLogEntity pipelineLogEntity : pipelineLogEntityList) {

                if (pipelineLogEntity.getLogRunStatus() == 30){

                    return pipelineLogEntity;

                }
            }
        }
        return null;
}

}
