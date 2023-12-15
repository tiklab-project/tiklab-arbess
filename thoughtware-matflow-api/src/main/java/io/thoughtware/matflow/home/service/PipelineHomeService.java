package io.thoughtware.matflow.home.service;

import io.thoughtware.matflow.pipeline.definition.model.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流水线首页服务接口
 */

public interface PipelineHomeService {

    /**
     * 创建日志
     * @param logType 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param map 日志信息
     */
    void log(String logType, Map<String, Object> map);


    /**
     * 配置全局消息
     * @param templateId 方案id
     * @param map 信息
     */
    void settingMessage(String templateId, Map<String, Object> map);


    /**
     * 创建消息
     * @param receiver 接收信息
     * @param map 信息
     */
    void  message(Map<String, Object> map, List<String> receiver);

    /**
     * 发送短信
     * @param map 短信内容
     */
    void smsMessage(Map<String,Object> map);

    /**
     * 初始化消息，日志信息
     * @param pipeline 流水线
     * @return 信息
     */
    Map<String,Object> initMap(Pipeline pipeline);




}
