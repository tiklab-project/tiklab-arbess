package com.tiklab.matflow.instance.service;

import com.tiklab.matflow.instance.model.MatFlowExecHistory;

/**
 * 流水线构建
 */
public interface MatFlowExecService {

    /**
     * 开始构建
     * @param matFlowId 流水线id
     * @return 构建开始
     * @throws InterruptedException 等待超时
     */
    int  start(String matFlowId,String userId) throws Exception;

    /**
     * 查询构建状态
     * @param matFlowId 流水线id
     * @return 状态信息
     */
    MatFlowExecHistory findInstanceState(String matFlowId) ;

    /**
     * 判断运行状态
     * @param matFlowId 流水线ID
     * @return 状态
     */
    int findState(String matFlowId);

    /**
     * 关闭运行
     * @param matFlowId 流水线id
     */
    void killInstance(String matFlowId,String userId);



}
