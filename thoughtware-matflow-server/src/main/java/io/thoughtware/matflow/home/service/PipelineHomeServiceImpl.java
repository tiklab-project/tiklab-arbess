package io.thoughtware.matflow.home.service;

import com.alibaba.fastjson.JSONObject;
import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.matflow.support.util.PipelineUtil;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.message.message.model.Message;
import io.thoughtware.message.message.model.MessageReceiver;
import io.thoughtware.message.message.model.SendMessageNotice;
import io.thoughtware.message.message.service.SendMessageNoticeService;
import io.thoughtware.message.message.service.SingleSendMessageService;
import io.thoughtware.message.setting.model.MessageType;
import io.thoughtware.message.sms.modal.Sms;
import io.thoughtware.security.logging.model.Logging;
import io.thoughtware.security.logging.model.LoggingType;
import io.thoughtware.security.logging.service.LoggingByTempService;
import io.thoughtware.user.user.model.User;
import io.thoughtware.user.user.service.UserService;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.GZIPOutputStream;

@Service
public class PipelineHomeServiceImpl implements PipelineHomeService {

    @Autowired
    UserService userService;

    @Autowired
    SingleSendMessageService sendMessage;

    @Autowired
    LoggingByTempService logService;

    @Autowired
    SendMessageNoticeService dispatchNoticeService;


    @Value("${base.url:null}")
    String baseUrl;

    String appName = PipelineFinal.appName;

    /**
     * 初始化消息，日志信息
     * @param pipeline 流水线
     * @return 信息
     */
    @Override
    public HashMap<String,Object> initMap(Pipeline pipeline){
        HashMap<String,Object> map = new HashMap<>();
        String userId = LoginContext.getLoginId();
        if (Objects.isNull(userId)){
            userId = pipeline.getUser().getId();
        }
        User user = userService.findOne(userId);
        map.put("pipelineId", pipeline.getId());
        map.put("pipelineName", pipeline.getName());
        map.put("userName", user.getName());
        if (user.getNickname() != null){
            map.put("userName", user.getNickname());
        }
        return map;
    }

    /**
     * 创建日志
     * @param logType 日志类型
     * @param map 日志信息
     */
    @Override
    public void log(String logType, Map<String, Object> map){

        Logging log = new Logging();

        //消息类型
        LoggingType opLogType = new LoggingType();
        opLogType.setId(logType);
        log.setActionType(opLogType);
        log.setModule("pipeline");

        //用户信息
        String userId = LoginContext.getLoginId();
        User user = userService.findOne(userId);

        String link = (String) map.get("link");
        String pipelineName = (String) map.get("pipelineName");

        log.setUser(user);
        log.setLink(link);
        log.setAction(pipelineName);
        log.setBaseUrl(baseUrl);
        log.setBgroup(appName);
        log.setData(JSONObject.toJSONString(map));

        logService.createLog(log);

    }

    /**
     * 配置全局消息
     * @param templateId 方案id
     * @param map 信息
     */
    @Override
    public void settingMessage(String templateId,Map<String, Object> map){
        SendMessageNotice dispatchNotice = new SendMessageNotice();
        dispatchNotice.setId(templateId);
        String jsonString = JSONObject.toJSONString(map);
        dispatchNotice.setEmailData(jsonString);
        dispatchNotice.setDingdingData(jsonString);
        dispatchNotice.setSiteData(jsonString);
        dispatchNotice.setQywechatData(jsonString);
        dispatchNotice.setBaseUrl(baseUrl);
        String link = (String) map.get("link");
        String pipelineName = (String) map.get("pipelineName");
        dispatchNotice.setLink(link);
        dispatchNotice.setAction(pipelineName);
        dispatchNotice.setSendId(LoginContext.getLoginId());
        dispatchNoticeService.createMessageItem(dispatchNotice);
    }


    /**
     * 发送消息（站内信）
     * @param receiver 接收信息
     * @param map 信息
     */
    @Override
    public void message(Map<String, Object> map,List<String> receiver){

        Message message = new Message();

        String sendWay = (String)map.get("sendWay");
        String mesType = (String)map.get("mesType");

        //消息类型
        MessageType messageType = new MessageType();
        messageType.setId(mesType);

        message.setMessageType(messageType);
        //发送方式
        message.setMessageSendTypeId(sendWay);
        message.setData(map);
        message.setBaseUrl(baseUrl);

        List<MessageReceiver> list = new ArrayList<>();
        for (String s : receiver) {
            MessageReceiver messageReceiver = new MessageReceiver();
            messageReceiver.setUserId(s);
            messageReceiver.setPhone(s);
            messageReceiver.setEmail(s);
            list.add(messageReceiver);
        }
        message.setMessageReceiverList(list);
        message.setSendId(LoginContext.getLoginId());

        sendMessage.sendMessage(message);
    }

    /**
     * 发送短信
     * @param map 短信内容
     */
    @Override
    public void smsMessage(Map<String,Object> map){

    }




}


































