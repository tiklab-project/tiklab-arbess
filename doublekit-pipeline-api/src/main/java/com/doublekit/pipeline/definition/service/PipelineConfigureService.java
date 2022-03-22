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
    String createConfigure(@NotNull @Valid PipelineConfigure pipelineConfigure);

    /**
     * 删除流水线配置
     * @param id 流水线配置id
     */
    void deleteConfig(String id);

    /**
     * 更新配置信息
     * @param pipelineConfigure 配置信息
     */
    String updateConfigure(@NotNull @Valid PipelineConfigure pipelineConfigure);

    /**
     * 查询流水线配置信息
     * @param configureId 流水线配置id
     * @return 配置信息
     */
    @FindOne
    PipelineConfigure findConfigure(String configureId);

    /**
     * 查询所有配置文件
     * @return 配置文件列表
     */
    @FindAll
    List<PipelineConfigure> findAllConfigure();

    /**
     * 获取最近一次的配置信息
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    PipelineConfigure findTimeId(String pipelineId);

    /**
     * 历史表添加信息
     * @param pipelineId 流水线id
     * @param pipelineExecHistory 历史信息
     * @return 历史信息
     */
    PipelineExecHistory addHistoryOne(String pipelineId, PipelineExecHistory pipelineExecHistory);

    /**
     * 获取克隆凭证信息
     * @param pipelineId 流水线id
     * @return 凭证信息
     */
    Proof getProofIdGit(String pipelineId);

    /**
     * 获取部署凭证信息
     * @param pipelineId 流水线id
     * @return 凭证信息
     */
    Proof getProofIdDeploy(String pipelineId);

    @FindList
    List<PipelineConfigure> findAllConfigureList(List<String> idList);
}
