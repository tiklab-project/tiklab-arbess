package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.orther.model.PipelineFollow;

import java.util.Map;

public interface PipelineHomeService {


    /**
     * 收藏流水线
     * @param pipelineFollow 收藏信息
     */
    String updateFollow(PipelineFollow pipelineFollow);

    /**
     * 创建日志
     * @param type 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
     * @param map 日志信息
     */
    void log(String type, String templateId, Map<String, String> map);


    /**
     * 创建消息
     * @param messageTemplateId 消息类型
     * @param messages 信息
     */
    void message(String messageTemplateId,String messages);






}
