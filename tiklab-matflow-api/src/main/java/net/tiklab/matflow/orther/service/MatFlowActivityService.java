package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.orther.model.MatFlowActivity;
import net.tiklab.matflow.orther.model.MatFlowActivityQuery;

import java.util.List;

public interface MatFlowActivityService {

    /**
     * 创建
     * @param matFlowActivity 动态信息
     */
    void createActive(MatFlowActivity matFlowActivity);

    /**
     * 删除动态
     * @param activeId 动态id
     */
    void deleteActivity(String activeId);

    /**
     * 删除一条流水线的所有动态
     * @param matFlowId 流水线id
     */
    void deleteMatFlowActivity(String matFlowId);

    /**
     * 查询所有动态
     * @return 动态列表
     */
    List<MatFlowActivity> findAllActive();


    /**
     * 对外提供创建动态
     * @param userId 用户偶读
     * @param massage 动态信息
     */
    void createActive(String userId, MatFlow matFlow, String massage);

    /**
     * 查询所有动态
     * @return 动态列表
     */
    List<MatFlowActivity> findUserActivity(MatFlowActivityQuery matFlowActivityQuery);
}
