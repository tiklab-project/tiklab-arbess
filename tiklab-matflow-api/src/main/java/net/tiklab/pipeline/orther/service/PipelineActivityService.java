package net.tiklab.pipeline.orther.service;

import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.orther.model.PipelineActivity;
import net.tiklab.pipeline.orther.model.PipelineActivityQuery;

import java.util.List;

public interface PipelineActivityService {

    /**
     * 创建
     * @param pipelineActivity 动态信息
     */
    void createActive(PipelineActivity pipelineActivity);

    /**
     * 删除动态
     * @param activeId 动态id
     */
    void deleteActivity(String activeId);

    /**
     * 删除一条流水线的所有动态
     * @param pipelineId 流水线id
     */
    void deletePipelineActivity(String pipelineId);

    /**
     * 查询所有动态
     * @return 动态列表
     */
    List<PipelineActivity> findAllActive();


    /**
     * 对外提供创建动态
     * @param userId 用户偶读
     * @param massage 动态信息
     */
    void createActive(String userId, Pipeline pipeline, String massage);

    /**
     * 查询所有动态
     * @return 动态列表
     */
    List<PipelineActivity> findUserActivity(PipelineActivityQuery pipelineActivityQuery);
}
