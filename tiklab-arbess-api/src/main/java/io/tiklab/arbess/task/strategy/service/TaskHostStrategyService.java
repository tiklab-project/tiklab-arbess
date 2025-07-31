package io.tiklab.arbess.task.strategy.service;

import io.tiklab.arbess.task.strategy.model.TaskHostStrategy;
import io.tiklab.arbess.task.strategy.model.TaskHostStrategyQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;

import java.util.List;

public interface TaskHostStrategyService {

    /**
     * 创建策略
     * @param Strategy 策略信息
     * @return 策略ID
     */
    String createStrategy(TaskHostStrategy Strategy);

    /**
     * 删除策略
     * @param id 策略ID
     */
    void deleteStrategy(String id);

    /**
     * 更新策略
     * @param Strategy 策略信息
     */
    void updateStrategy(TaskHostStrategy Strategy);

    /**
     * 查询策略
     * @param id 策略ID
     * @return 策略信息
     */
    @FindOne
    TaskHostStrategy findStrategy(String id);

    /**
     * 查询所有策略
     * @return 策略列表
     */
    @FindAll
    List<TaskHostStrategy> findAllStrategy();

    /**
     * 查询策略列表
     * @param query 查询条件
     * @return 策略列表
     */
    @FindList
    List<TaskHostStrategy> findStrategyList(TaskHostStrategyQuery query);

    /**
     * 批量查询策略
     * @param idList 策略ID列表
     * @return 策略列表
     */
    List<TaskHostStrategy> findStrategyList(List<String> idList);

    /**
     * 分页查询策略
     * @param query 查询条件
     * @return 策略列表
     */
    Pagination<TaskHostStrategy> findStrategyPage(TaskHostStrategyQuery query);

}
