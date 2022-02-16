package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineQuery;
import com.doublekit.pipeline.definition.model.PipelineStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
     * @param id 流水线id
     */
    void deletePipeline(@NotNull String id);

    /**
     * 更新流水线
     * @param pipeline 更新后流水线信息
     */
    String updatePipeline(@NotNull @Valid Pipeline pipeline);

    /**
     * 查询单个流水线
     * @param id 流水线id
     * @return 流水线信息
     */
    @FindOne
    Pipeline selectPipeline(@NotNull String id);

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    @FindAll
    List<Pipeline> selectAllPipeline();

    @FindList
    List<Pipeline> selectAllPipelineList(List<String> idList);

    /**
     * 根据名称模糊查询
     * @param pipelineQuery 查询条件
     * @return 查询到的集合
     */
    List<Pipeline> selectName(PipelineQuery pipelineQuery);


    /**
     *查询流水线状态
     * @return 状态集合
     */
    List<PipelineStatus> selectAll();


}
