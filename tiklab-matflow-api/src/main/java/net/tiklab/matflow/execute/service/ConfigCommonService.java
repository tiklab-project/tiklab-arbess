package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;

import java.io.IOException;
import java.util.List;

/**
 * 执行过程配置的公共方法
 */
public interface ConfigCommonService {


    /**
     * 执行日志
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
     int log( PipelineProcess pipelineProcess) throws IOException;


    /**
     * 运行结束更新历史状态
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     * @param status 状态 success error halt
     */
    void runEnd(PipelineExecHistory pipelineExecHistory, String pipelineId ,String status);

    /**
     * 初始化历史
     * @return 历史
     */
     PipelineExecHistory initializeHistory(Pipeline pipeline);

    /**
     * 初始化日志
     * @param historyId 历史id
     * @return 日志信息
     */
    PipelineExecLog initializeLog(String historyId, PipelineConfigOrder configOrder);

    /**
     * 执行过程中的历史
     */
    void execHistory(PipelineProcess pipelineProcess,String log);


    /**
     * 更新执行状态
     * @param historyId 历史id
     * @param time 时间
     * @param state 状态
     */
    void updateState(String historyId, List<Integer> time, int state);

    /**
     * 获取环境配置信息
     * @param type 类型
     * @return 配置信息
     */
    String getScm(int type);

}
