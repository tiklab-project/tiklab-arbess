package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.pipeline.instance.model.TaskInstanceLog;

import java.util.List;
/**
 * 流水线日志
 */
@JoinProvider(model = TaskInstanceLog.class)
public interface TaskInstanceLogService {

    /**
     * 创建流水线日志
     * @param taskInstanceLog 流水线历史日志
     * @return 流水线日志id
     */
     String createLog(TaskInstanceLog taskInstanceLog);

    /**
     * 删除流水线日志
     * @param logId 流水线日志id
     */
     void deleteLog(String logId);

    /**
     * 根据历史删除日志
     * @param historyId 历史id
     */
    void deleteHistoryLog(String historyId);

    /**
     * 更新日志信息
     * @param taskInstanceLog 信息
     */
    void updateLog(TaskInstanceLog taskInstanceLog);

    //查询单个
    TaskInstanceLog findOneLog(String logId);

    /**
     * 查询日志信息
     * @param historyId 历史id
     * @return 信息集合
     */
    List<TaskInstanceLog> findAllLog(String historyId);

    /**
     * 根据阶段查询对应日志
     * @param stagesId 阶段id
     * @return 日志
     */
    List<TaskInstanceLog> findAllStagesLog(String historyId, String stagesId);

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    @FindAll
     List<TaskInstanceLog> findAllLog();


    @FindList
    List<TaskInstanceLog> findAllLogList(List<String> idList);
}
