package io.tiklab.arbess.pipeline.definition.service;

import io.tiklab.arbess.pipeline.definition.model.PipelineFollow;
import io.tiklab.arbess.pipeline.definition.model.PipelineFollowQuery;

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
     * 删除流水线相关的所有收藏
     * @param pipelineId 流水线id
     */
    void deletePipelineFollow(String pipelineId);

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

    /**
     * 根据ID列表批量查询收藏信息
     * @param idList 收藏ID列表
     * @return 收藏信息列表
     */
    List<PipelineFollow> findAllFollowList(List<String> idList);
    
}
