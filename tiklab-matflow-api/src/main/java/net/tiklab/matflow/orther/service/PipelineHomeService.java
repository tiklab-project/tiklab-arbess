package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.definition.model.Pipeline;

import java.util.Map;

public interface PipelineHomeService {

    /**
     * 创建日志
     * @param logType 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
     * @param map 日志信息
     */
    void log(String logType,String module, String templateId, Map<String, String> map);


    /**
     * 配置全局消息
     * @param templateId 方案id
     * @param map 信息
     */
    void settingMessage(String templateId,Map<String, String> map);


    /**
     * 创建消息
     * @param messageTemplateId 消息模板
     * @param mesType 消息类型
     * @param map 信息
     */
    void message(String messageTemplateId, String mesType, Map<String, String> map);


    /**
     * 企业微信发送消息
     * @param map 消息
     */
    void wechatMarkdownMessage(Map<String,String> map);


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
    Map<String,String> initMap(Pipeline pipeline);



}
