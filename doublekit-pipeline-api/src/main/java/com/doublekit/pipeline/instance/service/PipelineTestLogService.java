package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineTestLog;
import java.util.List;


public interface PipelineTestLogService {

    /**
     * 创建
     * @param PipelineTestLog testLog信息
     * @return testLogId
     */
    String createTestLog(PipelineTestLog PipelineTestLog);

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
    void updateTestLog(PipelineTestLog PipelineTestLog);

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
