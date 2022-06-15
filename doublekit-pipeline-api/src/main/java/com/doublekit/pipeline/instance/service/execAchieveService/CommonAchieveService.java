package com.doublekit.pipeline.instance.service.execAchieveService;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.CodeGit.FileTree;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public interface CommonAchieveService {

    /**
     * 执行日志
     * @param inputStreamReader 执行信息
     * @param pipelineProcess 执行信息
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
     int log(InputStreamReader inputStreamReader, PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList) throws IOException;

    /**
     * 获取符合条件的文件名
     * @param path 文件地址
     * @param list 存放文件地址
     * @return 文件地址集合
     */
    List<String> getFilePath(File path, List<String> list);

    /**
     * 匹配字符串获取文件名
     * @param path 文件地址
     * @param regex 匹配条件
     * @return 文件地址
     */
      String getFile(String path, String regex);

    /**
     * 调用cmd执行命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
     Process process(String path,String order,String sourceAddress) throws IOException;

    /**
     * 凭证信息（UsernamePassword）方式
     * @param gitUser 用户名
     * @param gitPasswd 密码
     * @return 验证信息
     */
    // UsernamePasswordCredentialsProvider usernamePassword(String gitUser, String gitPasswd);

    /**
     * 删除文件
     * @param file 文件地址
     */
      Boolean deleteFile(File file);

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
     PipelineExecHistory initializeHistory(String historyId, Pipeline pipeline);

    /**
     * 获取文件树
     * @param path 文件地址
     * @param list 存放树的容器
     * @return 树
     */
      List<FileTree> fileTree(File path, List<FileTree> list);


    /**
     * 获取文件流
     * @param path 文件地址
     * @return 文件信息
     */
     List<String> readFile(String path);
}
