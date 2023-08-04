package io.tiklab.matflow.pipeline.definition.service;

import io.tiklab.matflow.pipeline.definition.model.PipelineFollow;
import io.tiklab.matflow.pipeline.definition.model.PipelineFollowQuery;

import java.util.List;

/**
 * 流水线收藏服务接口
 */
public interface PipelineFollowService {

    /**
     * 创建收藏
     * @param pipelineFollow 收藏模型
     */
    void updateFollow(PipelineFollow pipelineFollow);

    /**
     * 获取用户收藏的流水线
     * @param userId 用户id
     * @return 收藏的流水线
     */
    List<PipelineFollow> findUserFollowPipeline(String userId);

    /**
     * 条件查询
     * @param followQuery 条件
     * @return 查询结果
     */
    List<PipelineFollow> findFollowQueryList(PipelineFollowQuery followQuery);

    /**
     * 删除收藏
     * @param followId 收藏id
     */
    void deleteFollow(String followId);

    /**
     * 查询单个信息
     * @param followId 收藏id
     * @return 收藏信息
     */
    PipelineFollow findOneFollow(String followId);

    /**
     * 查询所有收藏
     * @return 收藏集合
     */
    List<PipelineFollow> findAllFollow();

    List<PipelineFollow> findAllFollowList(List<String> idList);
    
}
