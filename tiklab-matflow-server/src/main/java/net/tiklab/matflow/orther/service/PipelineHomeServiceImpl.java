package net.tiklab.matflow.orther.service;


import com.alibaba.fastjson.JSONObject;
import net.tiklab.matflow.orther.model.PipelineFollow;
import net.tiklab.message.message.model.*;
import net.tiklab.message.message.service.MessageService;
import net.tiklab.oplog.log.modal.OpLog;
import net.tiklab.oplog.log.modal.OpLogTemplate;
import net.tiklab.oplog.log.service.OpLogService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.todotask.task.service.TaskService;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;

@Service
@Exporter
public class PipelineHomeServiceImpl implements PipelineHomeService {

    @Autowired
    OpLogService logService;

    @Autowired
    PipelineFollowService pipelineFollowService;

    //@Autowired
    //TaskService taskService;

    @Autowired
    MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineHomeServiceImpl.class);


    //更新收藏信息
    @Override
    public String updateFollow(PipelineFollow pipelineFollow){
      return  pipelineFollowService.updateFollow(pipelineFollow);
    }

    String appName = PipelineUntil.appName;

    /**
     * 创建日志
     * @param type 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
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
    public void message(String messageTemplateId,String messages){
        Message message = new Message();
        message.setApplication(appName);
        message.setSender(appName);
        //模板
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setId(messageTemplateId);
        message.setMessageTemplate(messageTemplate);
        //接收人
        ArrayList<MessageReceiver> list = new ArrayList<>();
        MessageReceiver messageReceiver = new MessageReceiver();
        String userId = LoginContext.getLoginId();
        messageReceiver.setReceiver(userId);
        list.add(messageReceiver);
        message.setMessageReceiverList(list);
        //消息信息
        message.setData("{\"message\":\" "+messages+" \",\"state\":\" \"}");
        messageService.sendMessage(message);
    }


    @Value("${server.port:8080}")
    int port;


    public String findAddress(String type,String pipelineId){
        String ip ;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "localhost"  ;
        }
        return "http://"+ip+":"+port+"/#"+findRouting(type,pipelineId);
    }


    private  String findRouting(String type,String pipelineId){
        switch (type) {
            case "pipeline" -> {
                return "/index/task/" + pipelineId + "/work";
            }
            case "pipelineConfig" -> {
                return "/index/task/" + pipelineId + "/config";
            }
            case "pipelineExec" -> {
                return "/index/task/" + pipelineId + "/structure";
            }
            case "proof" -> {
                return "/index/system/proof";
            }
            case "other" -> {
                return "/index/pipeline";
            }
        }
        return null;
    }

}
