package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineFollow;
import com.doublekit.pipeline.instance.model.PipelineOpen;

import java.util.List;

public interface PipelineHomeService {

    /**
     * 获取经常打开列表
     * @param userId 用户id
     * @return 列表
     */
    List<PipelineOpen> findAllOpen(String userId);

    /**
     * 获取用户所有收藏
     * @param userId 用户id
     * @return 收藏列表
     */
    List<PipelineStatus> findAllFollow(String userId);


    /**
     * 收藏流水线
     * @param pipelineFollow 收藏信息
     */
    void updateFollow(PipelineFollow pipelineFollow);

    /**
     * 查询用户所有流水线
     * @param userId 用户id
     * @return 流水线
     */
    List<PipelineStatus> findUserPipeline(String userId);


}
