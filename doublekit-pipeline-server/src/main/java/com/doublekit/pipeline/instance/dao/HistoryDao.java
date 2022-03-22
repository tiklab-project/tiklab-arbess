package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.dao.PipelineDao;
import com.doublekit.pipeline.instance.entity.HistoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class HistoryDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线历史
     *
     * @param historyEntity 流水线历史信息
     * @return 流水线id
     */
    public String createHistory(HistoryEntity historyEntity) {

        return jpaTemplate.save(historyEntity, String.class);

    }

    /**
     * 删除流水线历史
     *
     * @param id 流水线历史id
     */
    public void deleteHistory(String id) {
        jpaTemplate.delete(HistoryEntity.class, id);
    }

    /**
     * 更新流水线历史
     *
     * @param historyEntity 更新后流水线历史信息
     */
    public void updateHistory(HistoryEntity historyEntity) {

        jpaTemplate.update(historyEntity);

    }

    /**
     * 查询流水线历史
     *
     * @param id 查询id
     * @return 流水线历史信息
     */
    public HistoryEntity findOneHistory(String id) {

        return jpaTemplate.findOne(HistoryEntity.class, id);
    }

    public List<HistoryEntity> findHistoryList(List<String> idList) {

        return jpaTemplate.findList(HistoryEntity.class, idList);
    }

    /**
     * 查询所有流水线历史
     *
     * @return 流水线历史列表
     */
    public List<HistoryEntity> findAllHistory() {

        return jpaTemplate.findAll(HistoryEntity.class);
    }

}