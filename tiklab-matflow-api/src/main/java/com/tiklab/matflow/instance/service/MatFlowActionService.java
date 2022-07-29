package com.tiklab.matflow.instance.service;

import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.instance.model.MatFlowAction;
import com.tiklab.matflow.instance.model.MatFlowActionQuery;

import java.util.List;

public interface MatFlowActionService {

    /**
     * 创建
     * @param matFlowAction 动态信息
     */
    void createActive(MatFlowAction matFlowAction);

    /**
     * 删除动态
     * @param activeId 动态id
     */
    void deleteAction(String activeId);

    /**
     * 删除一条流水线的所有动态
     * @param matFlowId 流水线id
     */
    void deleteMatFlowAction(String matFlowId);

    /**
     * 查询所有动态
     * @return 动态列表
     */
    List<MatFlowAction> findAllActive();


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
    List<MatFlowAction> findUserAction(MatFlowActionQuery matFlowActionQuery);
}
