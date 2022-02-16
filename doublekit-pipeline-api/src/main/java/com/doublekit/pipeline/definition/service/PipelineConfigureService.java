package com.doublekit.pipeline.definition.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
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

    PipelineConfigure selectTimeId(String pipelineId);

    @FindList
    List<PipelineConfigure> selectAllPipelineConfigureList(List<String> idList);
}
