package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineExecConfigure;
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
    String createConfigure(@NotNull @Valid PipelineConfigure pipelineConfigure);

    /**
     * 创建任务
     * @param pipelineConfigure 配置信息
     * @param pipelineId 流水线id
     */
    void createTask(PipelineConfigure pipelineConfigure,String pipelineId);

    /**
     * 根据流水线id删除流水线配置
     * @param configureId 流水线id
     */
    void deleteConfigure(String configureId);

    /**
     * 根据类型查询配置信息
     * @param pipelineId 流水线id
     * @param type 类型
     * @return 配置信息
     */
    PipelineConfigure findOneConfigure(String pipelineId , int type);

    /**
     * 删除单个任务
     * @param taskId 任务id
     * @param pipelineId 流水线id
     */
    void deleteTask(String taskId,String pipelineId);

    /**
     * 删除所有任务
     * @param pipelineId 流水线id
     */
    void deleteAllTask(String pipelineId);

    /**
     * 更新流水线配置
     * @param pipelineConfigure 配置信息
     */
    void updateConfigure(PipelineConfigure pipelineConfigure);

    /**
     * 更新任务信息
     * @param pipelineExecConfigure 更新信息
     */
    void updateTask(PipelineExecConfigure pipelineExecConfigure);


    /**
     * 查询流水线配置信息
     * @param configureId 流水线配置id
     * @return 配置信息
     */
    @FindOne
    PipelineConfigure findOneConfigure(String configureId);


    /**
     * 查询所有配置文件
     * @return 配置文件列表
     */
    @FindAll
    List<PipelineConfigure> findAllConfigure();

    /**
     * 根据流水线id查询所有配置
     * @param pipelineId 流水线id
     * @return 配置集合
     */
    List<PipelineConfigure> findAllConfigure(String pipelineId);

    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    List<Object> findAll(String pipelineId);

    @FindList
    List<PipelineConfigure> findAllConfigureList(List<String> idList);
}
