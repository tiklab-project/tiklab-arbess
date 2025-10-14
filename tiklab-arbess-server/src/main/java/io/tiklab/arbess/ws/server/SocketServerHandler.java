package io.tiklab.arbess.ws.server;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.agent.ws.config.FileChunkMessage;
import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.support.agent.model.AgentMessage;
import io.tiklab.arbess.support.agent.service.AgentService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.build.controller.FileTransferManager;
import io.tiklab.arbess.task.build.controller.FileTransferSession;
import io.tiklab.arbess.ws.service.WebSocketMessageService;
import io.tiklab.core.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

@Service
public class SocketServerHandler implements WebSocketHandler {

    @Autowired
    WebSocketMessageService webSocketMessageService;

    @Autowired
    AgentService agentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Queue<AgentMessage> dataList = new ConcurrentLinkedQueue<>();

    // 多线程消费队列
    private final ExecutorService consumerExecutor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()  // 可根据实际需求调整线程数
    );

    // 定时调度线程池，用于轮询队列
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

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
        try {
            agentService.initAgent(agent);
        }catch (Exception e){
            logger.error("初始化Agent失败", e);
            sessionMap.remove(agent.getAddress(),session);
            // throw new SystemException("初始化Agent失败："+e.getMessage());
            return;
        }
        logger.info("客户端建立连接,{}", agent.getAddress());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 接收到消息时的处理逻辑
        BinaryMessage binaryMessage = (BinaryMessage) message;
        ByteBuffer payload = binaryMessage.getPayload();
        String receivedString = new String(payload.array(), StandardCharsets.UTF_8);

        AgentMessage agentMessage;
        try {
            agentMessage = JSONObject.parseObject(receivedString, AgentMessage.class);
            if (agentMessage == null) {
                logger.warn("Parsed AgentMessage is null, ignoring.");
                return;
            }
        } catch (Exception e) {
            logger.error("Failed to parse AgentMessage from payload: {}", receivedString, e);
            return;
        }
        // AgentMessage agentMessage = JSONObject.parseObject(receivedString, AgentMessage.class);
        logger.info("accept client messages,message type：{} , message:{}", agentMessage.getType(),message);
        String type = agentMessage.getType();
        if (type.equals("file")) {// 解析内部消息
            logger.info("file message......");
            saveFile(agentMessage);
        } else {
            try {
                // 每次入队前都创建新的对象，防止外部修改
                AgentMessage msgCopy = new AgentMessage();
                msgCopy.setType(agentMessage.getType());
                msgCopy.setMessage(agentMessage.getMessage());
                msgCopy.setPipelineId(agentMessage.getPipelineId()); // 根据实际字段复制
                msgCopy.setTenantId(agentMessage.getTenantId()); // 根据实际字段复制
                msgCopy.setSessionId(agentMessage.getSessionId()); // 根据实际字段复制

                dataList.add(msgCopy);
            }catch (Exception e){
                logger.error("Failed to add message to queue: {}", agentMessage, e);
            }
        }
    }


    @PostConstruct
    public void initConsumer() {
        // 每 100ms 检查一次队列
        scheduler.scheduleWithFixedDelay(() -> {
            try {
                AgentMessage message;
                while ((message = dataList.poll()) != null) {
                    processMessageWithTimeout(message, 3); // 单条消息 3 秒超时
                }
            } catch (Exception e) {
                logger.error("队列消费异常", e);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void processMessageWithTimeout(AgentMessage message, int timeoutSeconds) {
        Future<?> future = consumerExecutor.submit(() -> {
            try {
                webSocketMessageService.distributeMessage(message);
            } catch (Exception e) {
                logger.error("处理消息失败: {}", message, e);
            }
        });

        try {
            future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            logger.warn("消息处理超时，丢弃消息: {}", message);
            future.cancel(true); // 尝试取消任务
        } catch (Exception e) {
            logger.error("处理消息异常: {}", message, e);
        }
    }

    // @Scheduled(fixedDelay = 200)
    // public void syncMessage() {
    //     ExecutorService executor = Executors.newSingleThreadExecutor();
    //     try {
    //         Future<?> future = executor.submit(() -> {
    //             AgentMessage message = dataList.poll();
    //             if (message != null) {
    //                 webSocketMessageService.distributeMessage(message);
    //             }
    //         });
    //
    //         // 设置超时 3 秒，如果超时则放弃
    //         future.get(3, TimeUnit.SECONDS);
    //
    //     } catch (TimeoutException e) {
    //         logger.warn("syncMessage 执行超时，放弃当前更新信息....");
    //     } catch (Exception e) {
    //         logger.error("同步消息失败", e);
    //     } finally {
    //         executor.shutdown();
    //     }
    // }

    private void saveFile(AgentMessage agentMessage){
        Object inner = agentMessage.getMessage();
        if (inner == null) {
            logger.warn("file 消息为空，忽略");
            return;
        }

        // 反序列化为 FileChunkMessage
        FileChunkMessage fileChunkMsg = JSONObject.parseObject(inner.toString(), FileChunkMessage.class);

        String sessionId = fileChunkMsg.getSessionId();
        FileTransferSession fileSession = FileTransferManager.get(sessionId);
        if (fileSession == null) {
            logger.warn("未找到 sessionId={} 的下载会话", sessionId);
            return;
        }
        switch (fileChunkMsg.getType()) {
            case "chunk" -> {
                byte[] bytes = Base64.getDecoder().decode(fileChunkMsg.getData());
                try {
                    fileSession.write(bytes);
                } catch (IOException e) {
                    logger.error("写入文件分片失败", e);
                    fileSession.error(e);
                }
            }
            case "complete" -> {
                fileSession.complete();
                FileTransferManager.remove(sessionId);
                logger.info("文件传输完成, sessionId={}", sessionId);
            }
            case "error" -> {
                fileSession.error(new RuntimeException(fileChunkMsg.getMessage()));
                FileTransferManager.remove(sessionId);
                logger.error("文件传输失败, sessionId={}, 错误={}", sessionId, fileChunkMsg.getMessage());
            }
            default -> logger.warn("未知的 file 消息类型: {}", fileChunkMsg.getType());
        }
    }

    /**
     * 发送消息
     * @param id 消息ID
     * @param agentMessage 消息内容
     */
    public void sendHandleMessage(String id, AgentMessage agentMessage){
        WebSocketSession session = sessionMap.get(id);
        if (Objects.isNull(session) || !session.isOpen()) {
            throw new SystemException("客户端推送消息失败，无法获取客户端连接, 客户端信息：" + id);
        }

        // 将消息序列化为 JSON
        String jsonString = JSONObject.toJSONString(agentMessage);
        logger.info("push messages to the client：{}", agentMessage.getType());

        try {
            // 分块发送消息
            sendPartialMessage(session, jsonString); // 每块大小 1024 字节
        } catch (IOException e) {
            throw new SystemException("客户端推送消息失败, 错误信息：" + e.getMessage());
        }
    }

    public void sendPartialMessage(WebSocketSession session, String message) throws IOException {
        int length = message.length();
        int chunkSize = 1024;

        for (int start = 0; start < length; start += chunkSize) {
            int end = Math.min(start + chunkSize, length);
            String chunk = message.substring(start, end);

            boolean isLast = (end >= length);
            session.sendMessage(new TextMessage(chunk, isLast));
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
        return true;
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
