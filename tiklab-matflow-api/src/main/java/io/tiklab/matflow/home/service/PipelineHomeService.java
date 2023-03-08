package io.tiklab.matflow.home.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;

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
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
     * @param map 日志信息
     */
    void log(String logType, String templateId, HashMap<String, Object> map);


    /**
     * 配置全局消息
     * @param templateId 方案id
     * @param map 信息
     */
    void settingMessage(String templateId, HashMap<String, Object> map);


    /**
     * 创建消息
     * @param receiver 接收信息
     * @param map 信息
     */
    void  message(HashMap<String, Object> map, List<String> receiver);

    /**
     * 发送短信
     * @param map 短信内容
     */
    void smsMessage(Map<String,String> map);

    /**
     * 初始化消息，日志信息
     * @param pipeline 流水线
     * @return 信息
     */
    HashMap<String,Object> initMap(Pipeline pipeline);




}
