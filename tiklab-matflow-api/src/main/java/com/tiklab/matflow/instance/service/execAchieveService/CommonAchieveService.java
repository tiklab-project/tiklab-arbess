package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.matflow.instance.model.MatFlowProcess;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CommonAchieveService {


    /**
     * 获取当前系统
     * @return 1.windows 2.linux
     */
    int getSystemType();

    /**
     * 执行日志
     * @param inputStream 执行信息
     * @param matFlowProcess 执行信息
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
     int log(InputStream inputStream, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) throws IOException;

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
     * @param matFlowProcess 执行信息
     * @param beginTime 开始时间
     */
     void updateTime(MatFlowProcess matFlowProcess, long beginTime);

    /**
     * 更新状态
     * @param matFlowProcess 执行信息
     * @param e 异常
     * @param matFlowExecHistoryList 状态集合
     */
      void updateState(MatFlowProcess matFlowProcess, String e, List<MatFlowExecHistory> matFlowExecHistoryList);

    /**
     * 输出错误信息
     * @param matFlowExecHistory 历史
     * @param matFlowId 流水线id
     * @param e 错误信息
     */
      void  error(MatFlowExecHistory matFlowExecHistory, String e, String matFlowId, List<MatFlowExecHistory> matFlowExecHistoryList);

    /**
     * 输出成功信息
     * @param matFlowExecHistory 历史
     * @param matFlowId 流水线id
     */
      void  success(MatFlowExecHistory matFlowExecHistory, String matFlowId, List<MatFlowExecHistory> matFlowExecHistoryList);

    /**
     * 输出停止信息
     * @param matFlowProcess 历史
     * @param matFlowId 流水线id
     */
      void  halt(MatFlowProcess matFlowProcess, String matFlowId, List<MatFlowExecHistory> matFlowExecHistoryList);

    /**
     * 初始化日志
     * @param matFlowExecHistory 历史
     * @param matFlowConfigure 配置信息
     * @return 日志
     */
     MatFlowExecLog initializeLog(MatFlowExecHistory matFlowExecHistory, MatFlowConfigure matFlowConfigure);

    /**
     * 初始化历史
     * @param historyId 历史id
     * @return 历史
     */
     MatFlowExecHistory initializeHistory(String historyId, MatFlow matFlow, String userId);

    /**
     * 获取环境配置信息
     * @param type 类型
     * @return 配置信息
     */
    String getPath(int type);

}
