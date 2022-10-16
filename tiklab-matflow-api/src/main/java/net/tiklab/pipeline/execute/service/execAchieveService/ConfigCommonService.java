package net.tiklab.pipeline.execute.service.execAchieveService;

import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.definition.model.PipelineConfigOrder;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.execute.model.PipelineProcess;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 执行过程配置的公共方法
 */
public interface ConfigCommonService {


    /**
     * 获取当前系统
     * @return 1.windows 2.linux
     */
    int getSystemType();

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
     Process process(String path,String order) throws IOException;

    /**
     * 更新执行时间
     * @param pipelineProcess 执行信息
     * @param beginTime 开始时间
     */
     void updateTime(PipelineProcess pipelineProcess, long beginTime);

    /**
     * 更新状态
     * @param pipelineProcess 执行信息
     * @param b 异常
     * @param pipelineExecHistoryList 状态集合
     */
      void updateState(PipelineProcess pipelineProcess, boolean b, List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 输出错误信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     * @param e 错误信息
     */
      void  error(PipelineExecHistory pipelineExecHistory, String e, String pipelineId, List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 输出成功信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
      void  success(PipelineExecHistory pipelineExecHistory, String pipelineId, List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 输出停止信息
     * @param pipelineProcess 历史
     * @param pipelineId 流水线id
     */
      void  halt(PipelineProcess pipelineProcess, String pipelineId, List<PipelineExecHistory> pipelineExecHistoryList);

    /**
     * 初始化历史
     * @return 历史
     */
     PipelineExecHistory initializeHistory(Pipeline pipeline, String userId);

    /**
     * 初始化日志
     * @param historyId 历史id
     * @return 日志信息
     */
    PipelineExecLog initializeLog(String historyId, PipelineConfigOrder configOrder);

    /**
     * 执行过程中的历史
     * @param list 历史
     * @param pipelineId 流水线id
     */
    void execHistory(List<PipelineExecHistory> list,String pipelineId,String log,PipelineExecLog pipelineExecLog);

    /**
     * 获取环境配置信息
     * @param type 类型
     * @return 配置信息
     */
    String getScm(int type);

    String formatDateTime(long time);

}
