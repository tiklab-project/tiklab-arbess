package io.tiklab.arbess.ws.server;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.SystemException;
import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.support.agent.model.AgentMessage;
import io.tiklab.arbess.support.agent.service.AgentService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.ws.service.WebSocketMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SocketServerHandler implements WebSocketHandler {

    @Autowired
    WebSocketMessageService webSocketMessageService;

    @Autowired
    AgentService agentService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 线程安全的集合，用于存储客户端会话
    public static final Map<String, WebSocketSession> sessionMap = new HashMap<>();


    public static SocketServerHandler instance(){
        return new SocketServerHandler();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立时的处理逻辑
        Agent agent = connentAgent(String.valueOf(session.getUri()));
        sessionMap.put(agent.getAddress(),session);
        agentService.initAgent(agent);
        logger.info("客户端建立连接,{}", agent.getAddress());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 接收到消息时的处理逻辑
        BinaryMessage binaryMessage = (BinaryMessage) message;
        ByteBuffer payload = binaryMessage.getPayload();
        String receivedString = new String(payload.array(), StandardCharsets.UTF_8);
        AgentMessage agentMessage = JSONObject.parseObject(receivedString, AgentMessage.class);
        logger.warn("接受客户端消息,消息类型：{}", agentMessage.getType());
        // 接受消息返回逻辑
        String s = webSocketMessageService.distributeMessage(agentMessage);
        if (StringUtils.isEmpty(s)){
            return;
        }
        // 返回消息
        session.sendMessage(new TextMessage(s));
    }

    /**
     * 发送消息
     * @param id 消息ID
     * @param agentMessage 消息内容
     */
    public void sendHandleMessage(String id, AgentMessage agentMessage){
        WebSocketSession session = sessionMap.get(id);
        if (Objects.isNull(session) || !session.isOpen()) {
            throw new SystemException("客户端推送消息失败，无法获取客户端连接,客户端信息："+id);
        }
        // 接收到消息时的处理逻辑
        String jsonString = JSONObject.toJSONString(agentMessage);
        logger.warn("向客户端推送消息：{}",agentMessage.getType());
        try {
            session.sendMessage(new TextMessage(jsonString));
        } catch (IOException e) {
            throw new SystemException("客户端推送消息失败,错误信息：" + e.getMessage());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 发生传输错误时的处理逻辑
        logger.error("连接过程异常,异常信息：{}", exception.getMessage());
        Agent agent = connentAgent(Objects.requireNonNull(session.getUri()).toString());
        // connetMap.remove(agent.getAddress());
        sessionMap.remove(agent.getAddress(),session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 连接关闭时的处理逻辑
        Agent agent = connentAgent(Objects.requireNonNull(session.getUri()).toString());
        String address = agent.getAddress();
        logger.warn("客户端关闭连接,id:{},原因：{}", address,closeStatus.getReason());
        sessionMap.remove(address,session);
        // agentService.deleteAgent(address);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 解析连接信息
     * @param uri 连接地址
     * @return 连接信息
     */
    public Agent connentAgent(String uri){
        String[] split = Objects.requireNonNull(uri).split("\\?");
        String[] split1 = split[1].split("&");
        Agent agent = new Agent();
        agent.setName(split1[0]);
        agent.setTenantId(split1[1]);
        agent.setIp(split1[2]);
        agent.setCreateTime(PipelineUtil.date(1));
        agent.setAddress(split1[0]+"-"+split1[1]);
        return agent;
    }




}
