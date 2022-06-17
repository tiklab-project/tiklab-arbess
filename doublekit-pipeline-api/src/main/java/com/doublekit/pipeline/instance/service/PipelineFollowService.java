package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineFollow;

import java.util.List;

public interface PipelineFollowService {

    /**
     * 创建关注
     * @param pipelineFollow 关注
     * @return 关注id
     */
    String updateFollow(PipelineFollow pipelineFollow);

    /**
     * 删除关注
     * @param followId 关注id
     */
    void deleteFollow(String followId);
    


   
    /**
     * 用户关注的流水线
     * @param userId 用户id
     * @return 经常关注的流水线
     */
    List<PipelineStatus> findAllFollow(String userId, List<PipelineStatus> allStatus);


    /**
     * 查询所有流水线
     * @param userId 用户id
     * @return 流水线信息
     */
    List<PipelineStatus> findUserPipeline(String userId,List<PipelineStatus> allStatus);

    /**
     * 查询单个信息
     * @param followId 关注id
     * @return 关注信息
     */

    PipelineFollow findOneFollow(String followId);

    /**
     * 查询所有关注
     * @return 关注集合
     */

    List<PipelineFollow> findAllFollow();

    List<PipelineFollow> findAllFollowList(List<String> idList);
    
}
