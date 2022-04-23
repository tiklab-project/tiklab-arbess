package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineExecHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PipelineExecHistoryDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线历史
     *
     * @param pipelineExecHistoryEntity 流水线历史信息
     * @return 流水线id
     */
    public String createHistory(PipelineExecHistoryEntity pipelineExecHistoryEntity) {

        return jpaTemplate.save(pipelineExecHistoryEntity, String.class);

    }

    /**
     * 删除流水线历史
     *
     * @param id 流水线历史id
     */
    public void deleteHistory(String id) {
        jpaTemplate.delete(PipelineExecHistoryEntity.class, id);
    }

    /**
     * 更新流水线历史
     *
     * @param pipelineExecHistoryEntity 更新后流水线历史信息
     */
    public void updateHistory(PipelineExecHistoryEntity pipelineExecHistoryEntity) {

        jpaTemplate.update(pipelineExecHistoryEntity);

    }

    /**
     * 查询流水线历史
     *
     * @param id 查询id
     * @return 流水线历史信息
     */
    public PipelineExecHistoryEntity findOneHistory(String id) {

        return jpaTemplate.findOne(PipelineExecHistoryEntity.class, id);
    }

    public List<PipelineExecHistoryEntity> findHistoryList(List<String> idList) {

        return jpaTemplate.findList(PipelineExecHistoryEntity.class, idList);
    }

    /**
     * 查询所有流水线历史
     *
     * @return 流水线历史列表
     */
    public List<PipelineExecHistoryEntity> findAllHistory() {

        return jpaTemplate.findAll(PipelineExecHistoryEntity.class);
    }

}