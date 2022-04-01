package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.setting.proof.model.Proof;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 流水线配置
 */
@JoinProvider(model = PipelineConfigure.class)
public interface PipelineConfigureService {

    /**
     * 创建流水线配置
     * @param pipelineConfigure 流水线配置信息
     * @return 流水线配置id
     */
    Map<String, String> createConfigure(@NotNull @Valid PipelineConfigure pipelineConfigure);

    /**
     * 删除流水线配置
     * @param pipelineId 流水线id
     */
    void deletePipelineIdConfigure(String pipelineId);

    /**
     * 更新配置信息
     * @param pipelineConfigure 配置信息
     */
    void updateConfigure(@NotNull @Valid PipelineConfigure pipelineConfigure);

    /**
     * 查询流水线配置信息
     * @param configureId 流水线配置id
     * @return 配置信息
     */
    @FindOne
    PipelineConfigure findOneConfigure(String configureId);

    /**
     * 根据流水线id获取配置信息
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    PipelineConfigure findPipelineIdConfigure(String pipelineId);

    /**
     * 查询code凭证
     * @param pipelineConfigure 配置信息
     * @return 凭证
     */
    Proof findCodeProof(PipelineConfigure pipelineConfigure);

    /**
     * 查询Deploy凭证
     * @param pipelineConfigure 配置信息
     * @return 凭证
     */
    Proof findDeployProof(PipelineConfigure pipelineConfigure);

    /**
     * 查询所有配置文件
     * @return 配置文件列表
     */
    @FindAll
    List<PipelineConfigure> findAllConfigure();

    @FindList
    List<PipelineConfigure> findAllConfigureList(List<String> idList);
}
