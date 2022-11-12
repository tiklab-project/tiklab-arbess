package net.tiklab.matflow.orther.service;


import com.alibaba.fastjson.JSONObject;
import net.tiklab.matflow.orther.model.PipelineFollow;
import net.tiklab.message.message.model.Message;
import net.tiklab.message.message.model.MessageReceiver;
import net.tiklab.message.message.model.MessageTemplate;
import net.tiklab.message.message.service.MessageService;
import net.tiklab.oplog.log.modal.OpLog;
import net.tiklab.oplog.log.modal.OpLogTemplate;
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
     * @param type 日志类型
     * @param templateId 模板id
     * @param map 日志信息
     */
    @Override
    public void log(String type, String templateId,Map<String, String> map){
        OpLog log = new OpLog();
        OpLogTemplate opLogTemplate = new OpLogTemplate();
        opLogTemplate.setId(templateId);
        log.setActionType(type);
        log.setModule(appName);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        User user = new User();
        String loginId = LoginContext.getLoginId();
        user.setId(loginId);
        log.setUser(user);
        log.setBgroup(appName);
        log.setOpLogTemplate(opLogTemplate);
        log.setContent(JSONObject.toJSONString(map));
        logService.createLog(log);
    }


    /**
     * 创建消息
     */
    @Override
    public void message(String messageTemplateId,Map<String, String> map){
        Message message = new Message();
        message.setApplication(appName);
        //模板
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setId(messageTemplateId);
        message.setMessageTemplate(messageTemplate);
        //接收人
        ArrayList<MessageReceiver> list = new ArrayList<>();
        MessageReceiver messageReceiver = new MessageReceiver();
        String userId = LoginContext.getLoginId();
        User user = userService.findOne(userId);
        messageReceiver.setReceiver(userId);
        messageReceiver.setReceiverName(user.getName());
        list.add(messageReceiver);
        message.setMessageReceiverList(list);
        map.put("userName",user.getName());
        message.setData(JSONObject.toJSONString(map));
        messageService.sendMessage(message);
    }

}


































