package net.tiklab.matflow.orther.service;


import com.alibaba.fastjson.JSONObject;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.orther.model.PipelineFollow;
import net.tiklab.message.message.model.Message;
import net.tiklab.message.message.model.MessageReceiver;
import net.tiklab.message.message.model.MessageTemplate;
import net.tiklab.message.message.service.MessageService;
import net.tiklab.message.setting.model.MessageType;
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
import net.tiklab.user.user.model.DmUser;
import net.tiklab.user.user.model.DmUserQuery;
import net.tiklab.user.user.model.User;
import net.tiklab.user.user.service.DmUserService;
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
    PipelineFollowService pipelineFollowService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    WebHookService webHookService;

    @Autowired
    WeChatHookService weChatHookService;

    @Autowired
    private DmUserService dmUserService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineHomeServiceImpl.class);


    //更新收藏信息
    @Override
    public String updateFollow(PipelineFollow pipelineFollow){
      return  pipelineFollowService.updateFollow(pipelineFollow);
    }

    String appName = PipelineFinal.appName;

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

        String pipelineId = map.get("pipelineId");
        List<User> userList = findPipelineUser(pipelineId);

        //接收人
        List<MessageReceiver> list = acceptUser(userList);
        message.setMessageReceiverList(list);

        message.setApplication(appName);
        message.setData(JSONObject.toJSONString(map));
        messageService.sendMessage(message);
    }


    /**
     * 添加消息接收人
     * @param userList 接收人列表
     * @return 接收人集合
     */
    private List<MessageReceiver> acceptUser(List<User> userList){
        List<MessageReceiver> list = new ArrayList<>();
        MessageReceiver messageReceiver = new MessageReceiver();
        for (User user : userList) {
            messageReceiver.setReceiver(user.getId());
            messageReceiver.setReceiverName(user.getName());
            list.add(messageReceiver);
        }
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
    public void wechatMarkdownMessage(Map<String,String> map){
        WebHookQuery webHookQuery = new WebHookQuery();
        webHookQuery.setType(2);
        WebHook webHookByType = webHookService.findWebHookByType(webHookQuery);
        if (webHookByType == null){
            return;
        }
        String id = webHookByType.getId();
        WeChatMarkdown weChatMarkdown = new WeChatMarkdown();
        weChatMarkdown.setHookId(id);
        String title = map.get("title");
        String status = map.get("status");
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
     * 初始化消息，日志信息
     * @param pipeline 流水线
     * @return 信息
     */
    public  Map<String,String> initMap(Pipeline pipeline){
        Map<String,String> map = new HashMap<>();
        User user = userService.findOne(LoginContext.getLoginId());
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", pipeline.getPipelineName());
        map.put("name", pipeline.getPipelineName().substring(0,1).toUpperCase());
        map.put("userName", user.getName());
        if (user.getNickname() != null){
            map.put("userName", user.getNickname());
        }
        map.put("color", ""+pipeline.getColor());
        map.put("date",PipelineUntil.date());
        return map;
    }


    /**
     * 查询流水线用户
     * @param pipelineId 流水线id
     * @return 用户列表
     */
    private List<User> findPipelineUser(String pipelineId){
        List<User> userList = new ArrayList<>();

        DmUserQuery dmUserQuery = new DmUserQuery();
        dmUserQuery.setDomainId(pipelineId);
        List<DmUser> dmUserList = dmUserService.findDmUserList(dmUserQuery);

        if (dmUserList == null || pipelineId == null){
            User user = userService.findOne(LoginContext.getLoginId());
            userList.add(user);
            return userList;
        }

        for (DmUser dmUser : dmUserList) {
            userList.add(dmUser.getUser());
        }
        return userList;
    }

}


































