package net.tiklab.matflow.orther.service;


import com.alibaba.fastjson.JSONObject;
import net.tiklab.matflow.orther.model.PipelineFollow;
import net.tiklab.message.message.model.Message;
import net.tiklab.message.message.model.MessageReceiver;
import net.tiklab.message.message.model.MessageTemplate;
import net.tiklab.message.message.service.MessageService;
import net.tiklab.message.setting.model.MessageType;
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
import java.util.Map;

@Service
@Exporter
public class PipelineHomeServiceImpl implements PipelineHomeService {

    @Autowired
    OpLogService logService;

    @Autowired
    PipelineFollowService pipelineFollowService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineHomeServiceImpl.class);


    //更新收藏信息
    @Override
    public String updateFollow(PipelineFollow pipelineFollow){
      return  pipelineFollowService.updateFollow(pipelineFollow);
    }

    String appName = PipelineUntil.appName;

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
        map.put("userName",user.getName());
        if(user.getNickname()!= null){
            map.put("userName",user.getNickname());
        }

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

        //用户信息
        String userId = LoginContext.getLoginId();
        User user = userService.findOne(userId);
        map.put("userName",user.getName());
        if(user.getNickname()!= null){
            map.put("userName",user.getNickname());
        }

        //接收人
        ArrayList<MessageReceiver> list = new ArrayList<>();
        MessageReceiver messageReceiver = new MessageReceiver();
        messageReceiver.setReceiver(userId);
        messageReceiver.setReceiverName(user.getName());
        list.add(messageReceiver);
        message.setMessageReceiverList(list);

        message.setApplication(appName);
        message.setData(JSONObject.toJSONString(map));
        messageService.sendMessage(message);
    }

}


































