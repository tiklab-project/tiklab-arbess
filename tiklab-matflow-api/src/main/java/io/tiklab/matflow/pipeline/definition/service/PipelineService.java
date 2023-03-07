package io.tiklab.matflow.pipeline.definition.service;


import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineExecMessage;
import io.tiklab.user.user.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线服务接口
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
    void deletePipeline(@NotNull String pipelineId);

    /**
     * 更新流水线
     * @param pipeline 更新后流水线信息
     */
    void updatePipeline(@NotNull @Valid Pipeline pipeline);

    /**
     * 查询单个流水线
     * @param pipelineId 流水线id
     * @return 流水线信息
     */
    @FindOne
    Pipeline findPipelineById(@NotNull String pipelineId);

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    @FindAll
    List<Pipeline> findAllPipeline();

    @FindList
    List<Pipeline> findAllPipelineList(List<String> idList);

    /**
     * 获取用户流水线
     * @param userId 用户id
     * @return 流水线信息
     */
    List<Pipeline> findUserPipeline(String userId);

    /**
     * 获取流水线信息
     * @param userId 用户Id
     * @return 流水线信息
     */
    List<PipelineExecMessage> findUserPipelineExecMessage(String userId);

    /**
     * 获取用户收藏的流水线
     * @param userId 用户id
     * @return 流水线信息
     */
    List<PipelineExecMessage> findUserFollowPipeline(String userId);

    /**
     * 模糊查询流水线
     * @param pipelineName 流水线名称
     * @param userId 用户id
     * @return 流水线
     */
    List<PipelineExecMessage> findPipelineByName(String pipelineName, String userId);


    /**
     * 查询拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    List<User> findPipelineUser(String pipelineId);

}


































