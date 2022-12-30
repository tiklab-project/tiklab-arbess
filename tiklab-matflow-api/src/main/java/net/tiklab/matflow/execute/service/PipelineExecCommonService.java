package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineProcess;

import java.io.IOException;

/**
 * 执行过程配置的公共方法
 */
public interface PipelineExecCommonService {


    /**
     * 执行日志
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
    int log( PipelineProcess pipelineProcess) throws IOException;


    /**
     * 获取运行状态
     * @param pipelineProcess 运行信息
     * @param process 运行实例
     * @throws IOException 执行异常
     * @throws ApplicationException 执行错误
     */
    void execState(PipelineProcess pipelineProcess, Process process,String taskName) throws IOException , ApplicationException;


    /**
     * 运行结束更新历史状态
     * @param pipelineId 流水线id
     * @param status 状态 success:10  error:1 halt:20
     */
    void runEnd(String pipelineId ,int status);

    /**
     * 初始化历史
     * @return 历史
     */
    PipelineExecHistory initializeHistory(String pipelineId,int startWAy);


    /**
     * 执行过程中的历史
     */
    void updateExecLog(PipelineProcess pipelineProcess,String log);

    /**
     * 更新执行状态
     * @param historyId 历史id
     * @param state 状态
     */
    void updateState(String historyId, String logId,int state);

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
    String variableKey(String pipelineId,String configId,String order);

}





























