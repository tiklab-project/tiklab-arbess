package com.tiklab.matfiow.instance.service.execAchieveService;

import com.tiklab.matfiow.definition.model.Pipeline;
import com.tiklab.matfiow.definition.model.PipelineConfigure;
import com.tiklab.matfiow.instance.model.PipelineExecHistory;
import com.tiklab.matfiow.instance.model.PipelineExecLog;
import com.tiklab.matfiow.instance.model.PipelineProcess;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CommonAchieveService {

    /**
     * 执行日志
     * @param inputStream 执行信息
     * @param pipelineProcess 执行信息
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
     int log(InputStream inputStream, PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList) throws IOException;

    /**
     * 调用cmd执行命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
     Process process(String path,String order,String sourceAddress) throws IOException;

    /**
     * 更新执行时间
     * @param pipelineProcess 执行信息
     * @param beginTime 开始时间
     */
     void updateTime(PipelineProcess pipelineProcess, long beginTime);

    /**
     * 更新状态
     * @param pipelineProcess 执行信息
     * @param e 异常
     * @param pipelineExecHistoryList 状态集合
     */
      void updateState(PipelineProcess pipelineProcess,String e,List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 输出错误信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     * @param e 错误信息
     */
      void  error(PipelineExecHistory pipelineExecHistory, String e, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 输出成功信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
      void  success(PipelineExecHistory pipelineExecHistory, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 输出停止信息
     * @param pipelineProcess 历史
     * @param pipelineId 流水线id
     */
      void  halt(PipelineProcess pipelineProcess, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 初始化日志
     * @param pipelineExecHistory 历史
     * @param pipelineConfigure 配置信息
     * @return 日志
     */
     PipelineExecLog initializeLog(PipelineExecHistory pipelineExecHistory, PipelineConfigure pipelineConfigure);

    /**
     * 初始化历史
     * @param historyId 历史id
     * @return 历史
     */
     PipelineExecHistory initializeHistory(String historyId, Pipeline pipeline, String userId);

}
