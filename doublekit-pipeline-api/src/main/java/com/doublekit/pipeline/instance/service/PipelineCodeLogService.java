package com.doublekit.pipeline.instance.service;


import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.PipelineCodeLog;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import java.util.List;
import java.util.Map;

@JoinProvider(model = PipelineCodeLog.class)
public interface PipelineCodeLogService {

    /**
     * 创建
     * @param pipelineCodeLog codeLog信息
     * @return codeLogId
     */
    String createCodeLog(PipelineCodeLog pipelineCodeLog);

    /**
     * 创建所有日志表
     * @return 日志id
     */
    Map<String, String> createCodeLog();

    /**
     * 删除
     * @param codeLogId codeLogId
     */
    void deleteCodeLog(String codeLogId);

    void deleteCodeLog(PipelineExecLog pipelineExecLog);

    /**
     * 更新
     * @param pipelineCodeLog 更新信息
     */
    void updateCodeLog(PipelineCodeLog pipelineCodeLog);

    void updateCodeLog(PipelineExecLog pipelineExecLog);
    /**
     * 查询单个信息
     * @param codeLogId codeLogId
     * @return codeLog信息
     */
    @FindOne
    PipelineCodeLog findOneCodeLog(String codeLogId);

    /**
     * 查询所有信息
     * @return codeLog信息集合
     */
    @FindAll
    List<PipelineCodeLog> findAllCodeLog();

    @FindList
    List<PipelineCodeLog> findAllCodeLogList(List<String> idList);
   
}
