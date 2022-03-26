package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineTestLog;
import java.util.List;
import java.util.Map;

@JoinProvider(model = PipelineTestLog.class)
public interface PipelineTestLogService {

    /**
     * 创建
     * @param PipelineTestLog testLog信息
     * @return testLogId
     */
    String createTestLog(PipelineTestLog pipelineTestLog);

    Map<String, String> createTestLog();

    /**
     * 删除
     * @param testLogId testLogId
     */
    void deleteTestLog(String testLogId);

    void deleteTestLog(PipelineExecLog pipelineExecLog);

    /**
     * 更新
     * @param PipelineTestLog 更新信息
     */
    void updateTestLog(PipelineTestLog pipelineTestLog);

    void updateTestLog(PipelineExecLog pipelineExecLog);

    /**
     * 查询单个信息
     * @param testLogId testLogId
     * @return testLog信息
     */
    @FindOne
    PipelineTestLog findOneTestLog(String testLogId);

    /**
     * 查询所有信息
     * @return testLog信息集合
     */
    @FindAll
    List<PipelineTestLog> findAllTestLog();

    @FindList
    List<PipelineTestLog> findAllTestLogList(List<String> idList);
}
