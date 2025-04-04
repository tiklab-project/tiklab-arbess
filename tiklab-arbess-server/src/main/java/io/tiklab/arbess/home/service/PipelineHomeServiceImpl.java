package io.tiklab.arbess.home.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.message.message.model.Message;
import io.tiklab.message.message.model.MessageReceiver;
import io.tiklab.message.message.model.SendMessageNotice;
import io.tiklab.message.message.service.SendMessageNoticeService;
import io.tiklab.message.setting.model.MessageType;
import io.tiklab.privilege.permission.modal.DomainPermissionsQuery;
import io.tiklab.privilege.permission.service.PermissionService;
import io.tiklab.security.logging.logging.model.Logging;
import io.tiklab.security.logging.logging.model.LoggingType;
import io.tiklab.security.logging.logging.service.LoggingByTempService;
import io.tiklab.user.user.model.User;
import io.tiklab.user.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PipelineHomeServiceImpl implements PipelineHomeService {

    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    LoggingByTempService logService;

    @Autowired
    SendMessageNoticeService dispatchNoticeService;

    @Value("${base.url:null}")
    String baseUrl;

    String appName = PipelineFinal.appName;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public final ExecutorService executorService = Executors.newCachedThreadPool();

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
        logger.info("创建日志......");
        executorService.submit(() -> {
            try {
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
            }catch (Exception e){
                logger.error("日志创建失败:{}",e.getMessage());
            }
        });
    }

    /**
     * 配置全局消息
     * @param templateId 方案id
     * @param map 信息
     */
    @Override
    public void settingMessage(String templateId,Map<String, Object> map){
        logger.info("发送消息......");
        executorService.submit(() -> {
            try {
                String link = (String) map.get("link");
                map.put("qywxurl", link);
                SendMessageNotice dispatchNotice = new SendMessageNotice();
                dispatchNotice.setId(templateId);
                String jsonString = JSONObject.toJSONString(map);
                dispatchNotice.setEmailData(jsonString);
                dispatchNotice.setDingdingData(jsonString);
                dispatchNotice.setSiteData(jsonString);
                dispatchNotice.setQywechatData(jsonString);
                dispatchNotice.setBaseUrl(baseUrl);
                String pipelineName = (String) map.get("pipelineName");
                dispatchNotice.setLink(link);
                dispatchNotice.setAction(pipelineName);
                dispatchNotice.setSendId(LoginContext.getLoginId());
                if (!Objects.isNull(map.get("dmMessage")) && (Boolean)map.get("dmMessage")){
                    String pipelineId = (String) map.get("pipelineId");
                    dispatchNotice.setDomainId(pipelineId);
                    dispatchNoticeService.sendDmMessageNotice(dispatchNotice);
                }else {
                    dispatchNoticeService.sendMessageNotice(dispatchNotice);
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            };
        });


    }

    /**
     * 发送消息（指定类型）
     * @param receiver 接收信息
     * @param map 信息
     */
    @Override
    public void message(Map<String, Object> map,List<String> receiver){
        logger.info("发送指定类型消息......");
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
        String pipelineName = (String) map.get("pipelineName");
        message.setAction(pipelineName);
        dispatchNoticeService.sendMessage(message);
    }


    /**
     * 发送短信
     * @param map 短信内容
     */
    @Override
    public void smsMessage(Map<String,Object> map){

    }

    @Override
    public Boolean findPermissions(String domainId,String permission){
        DomainPermissionsQuery permissionsQuery = new DomainPermissionsQuery();
        permissionsQuery.setDomainId(domainId);
        permissionsQuery.setUserId(LoginContext.getLoginId());
        Set<String> permissions = permissionService.findDomainPermissions(permissionsQuery);
        return permissions.contains(permission);
    }

}


































