package com.tiklab.matflow.instance.service;

import com.tiklab.matflow.definition.model.PipelineStatus;
import com.tiklab.matflow.instance.model.PipelineActionQuery;
import com.tiklab.matflow.instance.model.PipelineExecState;
import com.tiklab.matflow.instance.model.PipelineFollow;
import com.tiklab.matflow.instance.model.PipelineOpen;

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
    String updateFollow(PipelineFollow pipelineFollow);

    /**
     * 查询用户所有流水线
     * @param userId 用户id
     * @return 流水线
     */
    List<PipelineStatus> findUserPipeline(String userId);

    /**
     * 获取用户7天内的构建状态
     * @param userId 用户id
     * @return 构建状态
     */
    List<PipelineExecState> runState(String userId);


    //List<PipelineAction> findAllAction(String userId);

    /**
     * 查询动态
     * @param pipelineActionQuery 信息
     * @return 动态信息
     */
    PipelineActionQuery findUserAction(PipelineActionQuery pipelineActionQuery);


}
