package io.thoughtware.arbess.ws.service;

import io.thoughtware.arbess.support.agent.model.AgentMessage;

public interface WebSocketMessageService {


    /**
     * 分发处理消息
     * @param agentMessage 消息内容
     */
    String distributeMessage(AgentMessage agentMessage);


}
