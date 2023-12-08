package io.thoughtware.matflow.support.util;

import io.thoughtware.core.exception.ApplicationException;

public interface PipelineUtilService {

    /**
     * 获取当前用户存储空间地址
     * @param type 获取类型 1.源文件 2.日志文件
     * @return 地址
     */
    String instanceAddress(int type);


    /**
     * 获取流水线默认位置
     * @param pipelineId 流水线id
     * @param type 获取类型 1.源文件 2.日志文件
     * @return 地址
     */
    String findPipelineDefaultAddress(String pipelineId,int type);

    /**
     * 配置指定位置文件
     * @param pipelineId 流水线id
     * @param regex 匹配规则
     * @return 文件地址
     * @throws ApplicationException 匹配到多个文件/没有匹配到文件
     */
    String findFile(String pipelineId,String fileDir, String regex) throws ApplicationException;


    /**
     * 获取Java安装位置
     * @return Java安装位置
     */
    String findJavaPath();





}
