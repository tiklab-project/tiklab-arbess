package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.user.user.model.DmUser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线
 */
@JoinProvider(model = Pipeline.class)
public interface PipelineService {

    /**
     * 创建流水线
     * @param pipeline 流水线信息
     * @return 流水线id
     */
    String createPipeline(@NotNull @Valid Pipeline pipeline);

    /**
     * 删除流水线
     * @param pipelineId 流水线id
     */
    void deletePipeline(@NotNull String pipelineId,String userId);

    /**
     * 更新流水线
     * @param pipeline 更新后流水线信息
     */
    int updatePipeline(@NotNull @Valid Pipeline pipeline);

    /**
     * 查询单个流水线
     * @param pipelineId 流水线id
     * @return 流水线信息
     */
    @FindOne
    Pipeline findPipeline(@NotNull String pipelineId);

    /**
     * 根据流水线id获取配置信息
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    List<PipelineConfigure> findPipelineConfigure(String pipelineId);

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    @FindAll
    List<Pipeline> findAllPipeline();

    @FindList
    List<Pipeline> findAllPipelineList(List<String> idList);


    List<Pipeline> findUserPipeline(String userId);


    StringBuilder findUserPipelineId(String userId);

    /**
     * 根据名称模糊查询
     * @param pipelineName 查询条件
     * @return 查询到的集合
     */
    List<Pipeline> findLike(String pipelineName,String userId);


    /**
     *查询流水线状态
     * @param allPipeline 用户id
     * @return 状态集合
     */
    List<PipelineStatus> findAllStatus(List<Pipeline> allPipeline);

    /**
     * 获取用户7天内的执行历史
     * @param userId 用户id
     * @return 历史信息
     */
    List<PipelineExecHistory> findAllUserHistory(String userId);

    /**
     * 获取拥有此流水线的用户
     * @param PipelineId 流水线id
     * @return 用户信息
     */
    List<DmUser> findPipelineUser(String PipelineId);
}
