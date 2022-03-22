package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.Configure;
import com.doublekit.pipeline.instance.model.History;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * 流水线配置
 */
@JoinProvider(model = Configure.class)
public interface ConfigureService {

    /**
     * 创建流水线配置
     * @param configure 流水线配置信息
     * @return 流水线配置id
     */
    String createConfigure(@NotNull @Valid Configure configure);

    /**
     * 删除流水线配置
     * @param id 流水线配置id
     */
    void deleteConfig(String id);

    /**
     * 更新配置信息
     * @param configure 配置信息
     */
    String updateConfigure(@NotNull @Valid Configure configure);

    /**
     * 查询流水线配置信息
     * @param configureId 流水线配置id
     * @return 配置信息
     */
    @FindOne
    Configure findConfigure(String configureId);

    /**
     * 查询所有配置文件
     * @return 配置文件列表
     */
    @FindAll
    List<Configure> findAllConfigure();

    /**
     * 获取最近一次的配置信息
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    Configure findTimeId(String pipelineId);

    /**
     * 历史表添加信息
     * @param pipelineId 流水线id
     * @param history 历史信息
     * @return 历史信息
     */
    History addHistoryOne(String pipelineId, History history);

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
    List<Configure> findAllConfigureList(List<String> idList);
}
