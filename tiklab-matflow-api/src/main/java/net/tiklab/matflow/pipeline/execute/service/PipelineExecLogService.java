package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

import java.io.IOException;

/**
 * 负责更新任务执行状态
 */
public interface PipelineExecLogService {


    /**
     * 执行日志
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
    int readRunLog(PipelineProcess pipelineProcess) throws IOException;

    /**
     * 写入运行日志到文件
     * @param pipelineProcess 运行信息
     * @param log 写入内容
     */
    void writeExecLog(PipelineProcess pipelineProcess,String log);

    /**
     * 任务command执行状态
     * @param pipelineProcess 运行信息
     * @param process 运行实例
     * @param taskName 任务名称
     * @throws IOException 执行异常
     * @throws ApplicationException 执行错误
     */
    void commandExecState(PipelineProcess pipelineProcess, Process process,String taskName)
            throws IOException , ApplicationException;

    /**
     * 运行结束，更新状态
     * @param pipelineId 流水线id
     * @param status 状态 success:10  error:1 halt:20
     */
    void runEnd(String pipelineId ,int status);

    /**
     * 更新执行状态
     * @param historyId 历史id
     * @param state 状态
     */
    void updateLogState(String historyId, String logId,int state);

    /**
     * 获取环境配置信息
     * @param type 类型
     * @return 配置信息
     */
    String getScm(int type);

    /**
     * 替换命令中的环境变量
     * @param pipelineId 流水线id
     * @param configId 任务id
     * @param order 命令
     * @return 替换后的环境变量
     */
    String replaceVariable(String pipelineId,String configId,String order);

    /**
     * 效验条件
     * @param pipelineId 流水线id
     * @param configId 配置id
     * @return 状态 true:条件满足 false:条件不满足
     */
    Boolean variableCondition(String pipelineId,String configId);

}





























