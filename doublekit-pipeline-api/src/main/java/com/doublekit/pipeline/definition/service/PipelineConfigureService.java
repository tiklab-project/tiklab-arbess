package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@JoinProvider(model = PipelineConfigure.class)
public interface PipelineConfigureService {

    /**
     * 创建流水线配置
     * @param pipelineConfigure 流水线配置信息
     * @return 流水线配置id
     */
    String createPipelineConfigure(@NotNull @Valid PipelineConfigure pipelineConfigure);

    /**
     * 删除流水线配置
     * @param id 流水线配置id
     */
    void deletePipelineConfigure(String id);

    /**
     * 更新配置信息
     * @param pipelineConfigure 配置信息
     */
    String updatePipelineConfigure(@NotNull @Valid PipelineConfigure pipelineConfigure);

    /**
     * 查询流水线配置信息
     * @param id 流水线配置id
     * @return 配置信息
     */
    @FindOne
    PipelineConfigure selectPipelineConfigure(String id);

    /**
     * 查询所有配置文件
     * @return 配置文件列表
     */
    @FindAll
    List<PipelineConfigure> selectAllPipelineConfigure();

    /**
     * 获取最近一次的配置信息
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    PipelineConfigure selectTimeId(String pipelineId);

    PipelineHistory pipelineHistoryOne(String pipelineId, PipelineHistory pipelineHistory);

    Proof getProof(String pipelineId);

    @FindList
    List<PipelineConfigure> selectAllPipelineConfigureList(List<String> idList);
}
