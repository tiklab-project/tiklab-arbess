package io.tiklab.arbess.task.tool.service;

import io.tiklab.arbess.task.tool.model.TaskHostOrder;
import io.tiklab.arbess.task.tool.model.TaskHostOrderQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

@JoinProvider(model = TaskHostOrder.class)
public interface TaskHostOrderService {

    /**
     * 创建主机命令
     * @param hostOrder 主机命令信息
     * @return 主机命令ID
     */
    String createHostOrder(TaskHostOrder hostOrder);

    /**
     * 删除主机命令
     * @param id 主机命令ID
     */
    void deleteHostOrder(String id);

    /**
     * 更新主机命令
     * @param hostOrder 主机命令信息
     */
    void updateHostOrder(TaskHostOrder hostOrder);

    /**
     * 查询主机命令
     * @param id 主机命令ID
     * @return 主机命令信息
     */
    @FindOne
    TaskHostOrder findHostOrder(String id);

    /**
     * 查询所有主机命令
     * @return 主机命令列表
     */
    @FindAll
    List<TaskHostOrder> findAllHostOrder();

    /**
     * 查询主机命令列表
     * @param query 查询条件
     * @return 主机命令列表
     */
    @FindList
    List<TaskHostOrder> findHostOrderList(TaskHostOrderQuery query);

    /**
     * 批量查询主机命令
     * @param idList 主机命令ID列表
     * @return 主机命令列表
     */
    List<TaskHostOrder> findHostOrderList(List<String> idList);

    /**
     * 分页查询主机命令
     * @param query 查询条件
     * @return 主机命令列表
     */
    Pagination<TaskHostOrder> findHostOrderPage(TaskHostOrderQuery query);


}
