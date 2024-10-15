package io.tiklab.arbess.pipeline.definition.service;


import io.tiklab.arbess.pipeline.definition.model.PipelineQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineRecently;
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


    Pipeline findPipelineByIdNoQuery(String pipelineId);

    /**
     * 该接口返回用户流水线
     * @param pipelineId 流水线Id
     * @return 流水线
     */
    Pipeline findOnePipeline(String pipelineId);

    /**
     * 该接口返回用户流水线
     * @param pipelineId 流水线Id
     * @return 流水线
     */
    Pipeline findPipelineNoQuery(String pipelineId);

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    @FindAll
    List<Pipeline> findAllPipeline();


    List<Pipeline> findAllPipelineNoQuery();

    @FindList
    List<Pipeline> findAllPipelineList(List<String> idList);

    /**
     * 获取用户流水线
     * @return 流水线信息
     */
    List<Pipeline> findUserPipeline(PipelineQuery query);

    /**
     * 分页查询流水线信息
     * @param query 查询条件
     * @return 流水线信息
     */
    Pagination<Pipeline> findUserPipelinePage(PipelineQuery query);


    List<Pipeline> findPipelineList(PipelineQuery query);


    /**
     * 查询拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    List<User> findPipelineUser(String pipelineId);


    /**
     * 查询当前用户最近构建的流水线
     * @return 流水线信息
     */
    List<PipelineRecently> findPipelineRecently(String userId,int number);


    /**
     * 获取clone的流水线名称
     * @param pipelineId 流水线ID
     * @return 流水线民初
     */
    String findPipelineCloneName(String pipelineId);


    /**
     * 流水线克隆
     * @param pipelineId 流水线id
     */
    void pipelineClone(String pipelineId,String pipelineName);


    /**
     * 获取最近打开的流水线
     * @param number 数量
     * @return 流水线
     */
    List<Pipeline> findRecentlyPipeline(Integer number,String pipelineId);

}


































