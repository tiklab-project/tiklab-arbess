package io.tiklab.arbess.ws.service;

import io.tiklab.arbess.support.agent.model.AgentMessage;

public interface WebSocketMessageService {


    /**
     * 分发处理消息
     * @param agentMessage 消息内容
     */
    String distributeMessage(AgentMessage agentMessage);


}
