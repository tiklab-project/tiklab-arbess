package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.instance.model.PipelineAction;

import java.util.List;

public interface PipelineActionService {

    /**
     * 创建
     * @param pipelineAction 动态信息
     */
    void createActive(PipelineAction pipelineAction);

    /**
     * 删除动态
     * @param activeId 动态id
     */
    void deleteAction(String activeId);

    /**
     * 删除一条流水线的所有动态
     * @param pipelineId 流水线id
     */
    void deletePipelineAction(String pipelineId);

    /**
     * 查询所有动态
     * @return 动态列表
     */
    List<PipelineAction> findAllActive();


    /**
     * 对外提供创建动态
     * @param userId 用户偶读
     * @param massage 动态信息
     */
    void createActive(String userId, Pipeline pipeline, String massage);


    List<PipelineAction> findUserAction(String userId);
}
