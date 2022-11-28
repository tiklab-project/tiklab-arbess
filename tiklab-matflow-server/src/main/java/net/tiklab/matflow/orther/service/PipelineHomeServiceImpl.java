package net.tiklab.matflow.orther.service;


import com.alibaba.fastjson.JSONObject;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.message.message.model.Message;
import net.tiklab.message.message.model.MessageReceiver;
import net.tiklab.message.message.model.MessageTemplate;
import net.tiklab.message.message.service.MessageService;
import net.tiklab.message.setting.model.MessageType;
import net.tiklab.message.sms.modal.Sms;
import net.tiklab.message.sms.service.SmsSignCfgService;
import net.tiklab.message.webhook.modal.WeChatMarkdown;
import net.tiklab.message.webhook.modal.WebHook;
import net.tiklab.message.webhook.modal.WebHookQuery;
import net.tiklab.message.webhook.service.WeChatHookService;
import net.tiklab.message.webhook.service.WebHookService;
import net.tiklab.oplog.log.modal.OpLog;
import net.tiklab.oplog.log.modal.OpLogTemplate;
import net.tiklab.oplog.log.modal.OpLogType;
import net.tiklab.oplog.log.service.OpLogService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import net.tiklab.user.user.service.UserService;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineHomeServiceImpl implements PipelineHomeService {

    @Autowired
    OpLogService logService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    WebHookService webHookService;

    @Autowired
    WeChatHookService weChatHookService;

    @Autowired
    private SmsSignCfgService smsSignCfgService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineHomeServiceImpl.class);

    String appName = PipelineFinal.appName;

    /**
     * 初始化消息，日志信息
     * @param pipeline 流水线
     * @return 信息
     */
    public Map<String,String> initMap(Pipeline pipeline){
        Map<String,String> map = new HashMap<>();
        User user = pipeline.getUser();
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", pipeline.getPipelineName());
        map.put("name", pipeline.getPipelineName().substring(0,1).toUpperCase());
        map.put("userName", user.getName());
        map.put("rootId",user.getId());
        if (user.getNickname() != null){
            map.put("userName", user.getNickname());
        }
        map.put("color", ""+pipeline.getColor());
        map.put("date",PipelineUntil.date(1));
        return map;
    }

    /**
     * 创建日志
     * @param logType 日志类型
     * @param templateId 模板code
     * @param map 日志信息
     */
    @Override
    public void log(String logType,String module, String templateId,Map<String, String> map){
        OpLog log = new OpLog();

        //消息类型
        OpLogType opLogType = new OpLogType();
        opLogType.setId(logType);
        log.setActionType(opLogType);

        log.setModule(module);

        //模板
        OpLogTemplate opLogTemplate = new OpLogTemplate();
        opLogTemplate.setId(templateId);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        log.setOpLogTemplate(opLogTemplate);

        //用户信息
        String userId = LoginContext.getLoginId();
        User user = userService.findOne(userId);
        log.setUser(user);

        log.setBgroup(appName);
        log.setContent(JSONObject.toJSONString(map));
        logService.createLog(log);
    }


    /**
     * 发送消息
     * @param messageTemplateId 消息模板
     * @param mesType 消息类型
     * @param map 信息
     */
    @Override
    public void message(String messageTemplateId,String mesType,Map<String, String> map){

        Message message = new Message();

        //消息类型
        MessageType messageType = new MessageType();
        messageType.setId(mesType);

        //模板
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setId(messageTemplateId);
        messageTemplate.setMsgType(messageType);
        message.setMessageTemplate(messageTemplate);

        //接收人
        List<MessageReceiver> list = findReceiver(map.get("rootId"));
        message.setMessageReceiverList(list);

        message.setApplication(appName);
        message.setData(JSONObject.toJSONString(map));
        messageService.sendMessage(message);
    }

    /**
     * 添加消息接收人
     * @param userId 负责人
     * @return 接收人集合
     */
    private List<MessageReceiver> findReceiver(String userId){
        List<MessageReceiver> list = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        if (!loginId.equals(userId) ){
            MessageReceiver messageReceiver = new MessageReceiver();
            User user = userService.findOne(userId);
            messageReceiver.setReceiver(user.getId());
            messageReceiver.setReceiverName(user.getName());
            list.add(messageReceiver);
        }
        MessageReceiver messageReceiver = new MessageReceiver();
        User user = userService.findOne(LoginContext.getLoginId());
        messageReceiver.setReceiver(user.getId());
        messageReceiver.setReceiverName(user.getName());
        list.add(messageReceiver);
        return list;
    }


    /**
     * 企业微信发送消息
     * @param map 消息
     */
    public void wechatMarkdownMessage(Map<String,String> map) throws  ApplicationException {
        WebHookQuery webHookQuery = new WebHookQuery();
        webHookQuery.setType(2);
        WebHook webHookByType = webHookService.findWebHookByType(webHookQuery);
        if (webHookByType == null){
            throw new ApplicationException("用户并未配置企业微信消息通知，消息发送失败。");
        }
        String id = webHookByType.getId();
        WeChatMarkdown weChatMarkdown = new WeChatMarkdown();
        weChatMarkdown.setHookId(id);
        String title = map.get("title");
        String status = map.get("message");
        String userName = map.get("userName");
        String pipelineName = map.get("pipelineName");
        String date = map.get("date");
        String color = "info";
        if (status.equals("error")){
            color = "warning";
        }
        if (status.equals("halt")){
            color = "comment";
        }
        String message =
                title +
                "\n>流水线名称:<font color=\"info\">"+  pipelineName+"</font>" +
                "\n>执行人:<font color=\"comment\">"+  userName+"</font>"+
                "\n>状态:<font color=\""+color+"\">"+status+"</font>" +
                "\n>执行时间:<font color=\"comment\">"+date+"</font>";
        weChatMarkdown.setContent(message);
        weChatHookService.sendWechatMarkdown(weChatMarkdown);
    }


    /**
     * 发送短信
     * @param map 短信内容
     */
    public void smsMessage(Map<String,String> map) throws  ApplicationException{

        String rootId = map.get("rootId");
        List<User> allUser = findAllUser(rootId);
        String phones = findUserPhone(allUser);
        if (!PipelineUntil.isNoNull(phones)){
           throw  new ApplicationException("当前用户与项目负责人都未配置手机号，消息发送失败。");
        }
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("projectName",map.get("pipelineName"));
        stringMap.put("userName",map.get("userName"));

        Sms sms = new Sms();
        sms.setPhones(phones);
        sms.setTemplateCode("SMS_261110255");
        sms.setSignName("字节加速");
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(stringMap);
        sms.setTemplateParam(jsonObject);
        try {
            smsSignCfgService.generalSms(sms);
        } catch (Exception e) {
            throw new ApplicationException("消息发送失败:"+e.getMessage());
        }


    }


    /**
     * 获取发送短信的用户手机号
     * @param list 用户集合
     * @return 手机号
     */
    private String findUserPhone(List<User> list){
        StringBuilder phones = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String phone = list.get(i).getPhone();
            if (!PipelineUntil.isNoNull(phone)){
                continue;
            }
            if (i == list.size()-1){
                phones.append(phone);
                return phones.toString();
            }
            phones.append(",").append(phone);
        }
        return phones.toString();
    }


    /**
     * 获取流水线执行人和负责人
     * @param userId 负责人id
     * @return 流水线执行人和负责人
     */
    private List<User> findAllUser(String userId){
        List<User> list = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        if (!loginId.equals(userId) ){
            User user = userService.findOne(userId);
            list.add(user);
        }
        User user = userService.findOne(loginId);
        list.add(user);
        return list;
    }


}


































