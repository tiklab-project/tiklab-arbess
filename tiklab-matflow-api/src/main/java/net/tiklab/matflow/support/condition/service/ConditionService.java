package net.tiklab.matflow.support.condition.service;

import net.tiklab.matflow.support.condition.model.Condition;

import java.util.List;

public interface ConditionService {

    /**
     * 创建条件
     * @param condition 条件
     * @return 条件id
     */
    String createCond(Condition condition);

    /**
     * 删除条件
     * @param condId 条件id
     */
    void deleteCond(String condId);

    /**
     * 更新条件
     * @param condition 条件信息
     */
    void updateCond(Condition condition);

    /**
     * 查询单个条件
     * @param condId 条件id
     * @return 条件信息
     */
    Condition findOneCond(String condId);

    /**
     * 查询任务条件
     * @param taskId 任务id
     * @return 条件集合
     */
    List<Condition> findAllTaskCond(String taskId);

}

































