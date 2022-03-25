package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.instance.model.PipelineDeployLog;

import java.util.List;
import java.util.Map;

public interface PipelineDeployLogService {

    /**
     * 创建
     * @param pipelineDeployLog deployLog信息
     * @return deployLogId
     */
    String createDeployLog(PipelineDeployLog pipelineDeployLog);

    /**
     * 删除
     * @param deployLogId deployLogId
     */
    void deleteDeployLog(String deployLogId);

    /**
     * 更新
     * @param pipelineDeployLog 更新信息
     */
    void updateDeployLog(PipelineDeployLog pipelineDeployLog);

    /**
     * 查询单个信息
     * @param deployLogId deployLogId
     * @return deployLog信息
     */
    @FindOne
    PipelineDeployLog findOneDeployLog(String deployLogId);

    /**
     * 查询所有信息
     * @return deployLog信息集合
     */
    @FindAll
    List<PipelineDeployLog> findAllDeployLog();

    @FindList
    List<PipelineDeployLog> findAllDeployLogList(List<String> idList);
}
