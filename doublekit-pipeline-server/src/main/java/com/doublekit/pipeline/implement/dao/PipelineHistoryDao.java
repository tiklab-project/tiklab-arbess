package com.doublekit.pipeline.implement.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.implement.entity.PipelineHistoryEntity;
import com.doublekit.pipeline.implement.entity.PipelineLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class PipelineHistoryDao {

    private static Logger logger = LoggerFactory.getLogger(PipelineDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线历史
     *
     * @param pipelineHistoryEntity 流水线历史信息
     * @return 流水线id
     */
    public String createPipelineHistory(PipelineHistoryEntity pipelineHistoryEntity) {

        return jpaTemplate.save(pipelineHistoryEntity, String.class);

    }

    /**
     * 删除流水线历史
     *
     * @param id 流水线历史id
     */
    public void deletePipelineHistory(String id) {
        jpaTemplate.delete(PipelineHistoryEntity.class, id);
    }

    /**
     * 更新流水线历史
     *
     * @param pipelineHistoryEntity 更新后流水线历史信息
     */
    public void updatePipelineHistory(PipelineHistoryEntity pipelineHistoryEntity) {

        jpaTemplate.update(pipelineHistoryEntity);

    }

    /**
     * 查询流水线历史
     *
     * @param id 查询id
     * @return 流水线历史信息
     */
    public PipelineHistoryEntity selectPipelineHistory(String id) {

        return jpaTemplate.findOne(PipelineHistoryEntity.class, id);
    }

    public List<PipelineHistoryEntity> selectPipelineHistoryList(List<String> idList) {

        return jpaTemplate.findList(PipelineHistoryEntity.class, idList);
    }

    /**
     * 查询所有流水线历史
     *
     * @return 流水线历史列表
     */
    public List<PipelineHistoryEntity> selectAllPipelineHistory() {

        return jpaTemplate.findAll(PipelineHistoryEntity.class);
    }

    /**
     * 根据流水线id查询所有流水线历史
     *
     * @param pipelineId 流水线id
     * @return 流水线历史列表
     */
    public List<PipelineHistoryEntity> selectAllPipelineHistory(String pipelineId) {

        List<PipelineHistoryEntity> pipelineHistoryEntityList = selectAllPipelineHistory();

        List<PipelineHistoryEntity> HistoryList = new ArrayList<>();

        for (PipelineHistoryEntity pipelineHistoryEntity : pipelineHistoryEntityList) {

            if (pipelineHistoryEntity.getPipelineId().equals(pipelineId)) {

                HistoryList.add(pipelineHistoryEntity);

            }
        }

        return HistoryList;
    }

    /**
     * 根据流水线id获取最近一次的构建历史
     * @param pipelineId 流水线id
     * @return 构建历史信息
     */
    public PipelineHistoryEntity selectLastHistory(String pipelineId) {

        List<PipelineHistoryEntity> pipelineHistoryEntityList = selectAllPipelineHistory(pipelineId);

        if (pipelineHistoryEntityList.size() != 0) {

            //将同一任务构建历史通过时间排序
            pipelineHistoryEntityList.sort(new Comparator<PipelineHistoryEntity>() {
                @Override
                public int compare(PipelineHistoryEntity pipelineHistoryEntity1, PipelineHistoryEntity pipelineHistoryEntity12) {

                    return pipelineHistoryEntity1.getHistoryCreateTime().compareTo(pipelineHistoryEntity12.getHistoryCreateTime());
                }
            });

            return pipelineHistoryEntityList.get(pipelineHistoryEntityList.size() - 1);

        }
        return null;
    }
}