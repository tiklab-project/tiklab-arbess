package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.Pipeline;

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
    void updatePipeline(@NotNull @Valid Pipeline pipeline);

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
    @FindList
    List<Pipeline> selectAllPipeline();






}
