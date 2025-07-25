package io.tiklab.arbess.task.checkpoint.service;

import io.tiklab.arbess.task.checkpoint.model.TaskCheckPoint;
import io.tiklab.arbess.task.checkpoint.model.TaskCheckPointQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

@JoinProvider(model = TaskCheckPoint.class)
public interface TaskCheckPointService {

    /**
     * 创建检查点
     * @param checkPoint 检查点信息
     * @return 检查点ID
     */
    String createCheckPoint(TaskCheckPoint checkPoint);

    /**
     * 删除检查点
     * @param id 检查点ID
     */
    void deleteCheckPoint(String id);

    /**
     * 更新检查点
     * @param checkPoint 检查点信息
     */
    void updateCheckPoint(TaskCheckPoint checkPoint);

    /**
     * 查询检查点
     * @param id 检查点ID
     * @return 检查点信息
     */
    @FindOne
    TaskCheckPoint findCheckPoint(String id);

    /**
     * 查询所有检查点
     * @return 检查点列表
     */
    @FindAll
    List<TaskCheckPoint> findAllCheckPoint();

    /**
     * 查询检查点列表
     * @param query 查询条件
     * @return 检查点列表
     */
    @FindList
    List<TaskCheckPoint> findCheckPointList(TaskCheckPointQuery query);

    /**
     * 批量查询检查点
     * @param idList 检查点ID列表
     * @return 检查点列表
     */
    List<TaskCheckPoint> findCheckPointList(List<String> idList);

    /**
     * 分页查询检查点
     * @param query 查询条件
     * @return 检查点列表
     */
    Pagination<TaskCheckPoint> findCheckPointPage(TaskCheckPointQuery query);


}
