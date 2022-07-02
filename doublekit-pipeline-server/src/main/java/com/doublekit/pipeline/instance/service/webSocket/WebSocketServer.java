package com.doublekit.pipeline.instance.service.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiAlitripBtripFlightCitySuggestRequest;
import com.doublekit.core.exception.ApplicationException;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.service.PipelineExecService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketServer implements WebSocketHandler {


    @Autowired
    PipelineExecService pipelineExecService;

    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * userMap:使用线程安全map存储用户连接webscoket信息
     *
     * @since JDK 1.7
     */
    private final static Map<String, WebSocketSession> deviceMap = new ConcurrentHashMap<String, WebSocketSession>();

    /**
     * 关闭websocket时调用该方法
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = this.getDeviceId(session);
        if (StringUtils.isNoneBlank(userId)) {
            deviceMap.remove(userId);
            logger.info("该" + userId + "用户连接关闭");
        } else {
            logger.info("关闭时，获取用户id为空");
        }

    }

    /**
     * 建立websocket连接时调用该方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String deviceId = this.getDeviceId(session);
        if (StringUtils.isNoneBlank(deviceId)) {
            logger.info("该" + deviceId + "建立服务端连接成功");

            deviceMap.put(deviceId, session);
            JSONObject params = new JSONObject();
            params.put("sid", deviceId);
            params.put("msg", "建立服务端连接成功！");
            String paramsString = params.toString();
            session.sendMessage(new TextMessage(paramsString));
        }

    }

    /**
     * 客户端调用websocket.send时候，会调用该方法,进行数据通信
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        String deviceId = this.getDeviceId(session);
        logger.info("该" + deviceId + "用户发送的消息是：" + msg);
        JSONObject params = new JSONObject();
        int state = pipelineExecService.findState(msg);
        if (state == 0){
            params.put("data",0);
            message = new TextMessage(params.toString());
            session.sendMessage(message);
            return;
        }
        PipelineExecHistory instanceState = pipelineExecService.findInstanceState(msg);
        params.put("data", Objects.requireNonNullElse(instanceState, 0));
        message = new TextMessage(params.toString());
        session.sendMessage(message);
    }

    /**
     * 传输过程出现异常时，调用该方法
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
        WebSocketMessage<String> message = new TextMessage("异常信息："
                + e.getMessage());
        session.sendMessage(message);
    }

    /**
     * org.springframework.web.socket.WebSocketHandler#supportsPartialMessages()
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * sendMessageToUser:发给指定用户
     */
    public void sendMessageToUser(String userId, String contents) {
        WebSocketSession session = deviceMap.get(userId);
        if (session != null && session.isOpen()) {
            logger.info("连接成功 。。 ");
            try {
                JSONObject params = new JSONObject();
                params.put("sid", userId);
                params.put("contents", contents);
                params.put("msg", "发送成功！");
                params.put("type", "login");
                TextMessage message = new TextMessage(params.toString());
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("连接失败 。。 ");
            //throw new ApplicationException("用户离线" + userId);
        }
    }

    /**
     * sendMessageToAllUsers:发给所有的设备
     */
    public void sendMessageToAllUsers(String contents) {
        Set<String> userIds = deviceMap.keySet();
        for (String userId : userIds) {
            this.sendMessageToUser(userId, contents);
        }
    }

    /**
     * getDeviceId:获取设备id
     * @param session session
     * @author liuchao
     * @since JDK 1.7
     */
    private String getDeviceId(WebSocketSession session) {
        try {
//            String deviceId = (String) session.getAttributes().get("currentDevice");
            String deviceId = session.getId();
            return deviceId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}