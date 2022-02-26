package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.PipelineLog;
import java.util.List;

@JoinProvider(model = PipelineLog.class)
public interface PipelineLogService {

    /**
     * 创建流水线日志
     * @param pipelineLog 流水线历史日志
     * @return 流水线日志id
     */
     String createPipelineLog(PipelineLog pipelineLog);

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
     void deletePipelineLog(String id);

    /**
     * 更新流水线日志
     * @param pipelineLog 更新后流水线日志信息
     */
     void updatePipelineLog(PipelineLog pipelineLog);

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    @FindOne
     PipelineLog selectPipelineLog(String id);

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    @FindAll
     List<PipelineLog> selectAllPipelineLog();

    /**
     * 创建日志表
     * @param pipelineId 流水线id
     * @return 日志信息
     * @throws Exception 日志转换异常
     */
     String foundPipelineLog(String pipelineId) throws Exception;

    @FindList
    List<PipelineLog> selectAllPipelineLogList(List<String> idList);
}
