package net.tiklab.matflow.task.task.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.task.model.TaskInstance;

import java.util.List;
/**
 * 任务日志服务接口
 */
@JoinProvider(model = TaskInstance.class)
public interface TaskInstanceService {

    /**
     * 创建任务日志
     * @param taskInstance 日志实体
     * @return 任务日志id
     */
     String createLog(TaskInstance taskInstance);

    /**
     * 根据历史删除日志
     * @param instanceId 历史id
     */
    void deleteInstanceLog(String instanceId);

    /**
     * 更新日志信息
     * @param taskInstance 信息
     */
    void updateLog(TaskInstance taskInstance);

    /**
     * 查询单个日志
     * @param logId 日志id
     * @return 日志
     */
    TaskInstance findOneLog(String logId);

    /**
     * 查询日志信息
     * @param instance 历史id
     * @return 信息集合
     */
    List<TaskInstance> findAllLog(String instance);

    /**
     * 根据阶段查询对应日志
     * @param stagesId 阶段id
     * @return 日志
     */
    List<TaskInstance> findAllStagesLog(String instance, String stagesId);

    /**
     * 查询所有任务日志
     * @return 任务日志列表
     */
    @FindAll
     List<TaskInstance> findAllLog();


    @FindList
    List<TaskInstance> findAllLogList(List<String> idList);
}
