package net.tiklab.matflow.achieve.server;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.execute.service.PipelineExecLogService;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.task.model.PipelineMessage;
import net.tiklab.matflow.task.model.PipelineUserMessage;
import net.tiklab.matflow.task.server.PipelineMessageTypeServer;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.tiklab.matflow.orther.until.PipelineFinal.*;

/**
 * 消息
 */


@Service
@Exporter
public class MessageExecServiceImpl implements MessageExecService {


    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    PipelineMessageTypeServer messageTypeServer;

    @Autowired
    PipelineExecLogService execLogService;

    private static final Logger logger = LoggerFactory.getLogger(MessageExecServiceImpl.class);

    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    public boolean message(PipelineProcess pipelineProcess, String configId,int taskType) {

        commonService.updateExecLog(pipelineProcess, "\n"+PipelineUntil.date(4)+"执行任务：消息通知.....");

        String historyId = pipelineProcess.getHistoryId();
        List<PipelineExecLog> allLog = execLogService.findAllLog(historyId);

        //获取项目执行情况
        int i = 10;
        for (PipelineExecLog pipelineExecLog : allLog) {
            int state = pipelineExecLog.getRunState();
            if (state == 1 || state == 20){
                i = state;
            }
        }

        PipelineMessage configMessage = messageTypeServer.findConfigMessage(configId);

        //需要发送消息的人
        List<PipelineUserMessage> list = new ArrayList<>();
        List<PipelineUserMessage> userList = configMessage.getUserList();

        if (userList == null || userList.size() == 0){
            commonService.updateExecLog(pipelineProcess, "\n"+PipelineUntil.date(4)+"任务：消息通知执行完成。");
            return true;
        }
        Pipeline pipeline = pipelineProcess.getPipeline();
        HashMap<String, Object> map = homeService.initMap(pipeline);

        for (PipelineUserMessage userMessage : userList) {
            int type = userMessage.getType();
            if (type == 1){
                list.add(userMessage);
            }
            if (i == 10 && type == 2){
                list.add(userMessage);
            }
            if (i == 1 && type == 3){
                list.add(userMessage);
            }
        }


        if (i == 1){
            map.put("message","执行失败");
        }
        if (i == 10){
            map.put("message","执行成功");
        }
        if (i == 20){
            map.put("message","停止执行");
        }

        if (list.size() == 0){
            commonService.updateExecLog(pipelineProcess, "\n"+PipelineUntil.date(4)+"任务：消息通知执行完成。");
            return true;
        }

        //消息发送类型
        List<String> typeList = configMessage.getTypeList();


        try {
            for (String type : typeList) {
                if (!PipelineUntil.isNoNull(type)){
                    continue;
                }
                messageType(pipelineProcess,type,list,map);
            }
        }catch (ApplicationException e){
            String message = e.getMessage();
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+message);
            return false;
        }
        commonService.updateExecLog(pipelineProcess, "\n"+PipelineUntil.date(4)+"任务：消息通知执行完成。");
        return true;
    }

    /**
     * 消息发送方式
     * @param type 类型
     * @throws ApplicationException 消息发送失败
     */
    private void messageType(PipelineProcess pipelineProcess, String type, List<PipelineUserMessage> userList,HashMap<String, Object> map) throws ApplicationException {

        map.put("mesType",MES_PIPELINE_RUN);
        switch (type){
            case MES_SENT_SITE ->{
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：站内信");
                map.put("sendWay",MES_SENT_SITE);
                List<String> list = new ArrayList<>();
                for (PipelineUserMessage message : userList) {
                    list.add(message.getUser().getId());
                }
                homeService.message(map,list);
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "站内信发送成功");
            }
            case "sms" ->{
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：短信");
                homeService.smsMessage(new HashMap<>());
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "短信发送成功。");
            }
            case "wechat" ->{
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：微信机器人消息");
                map.put("sendWay",MES_SENT_WECHAT);
                homeService.message(map,new ArrayList<>());
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "微信机器人消息发送成功");
            }
            case "mail" ->{
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：邮箱消息");
                map.put("sendWay",MES_SENT_EMAIL);
                List<String> list = new ArrayList<>();
                for (PipelineUserMessage message : userList) {
                    list.add(message.getUser().getEmail());
                }
                homeService.message(map,list);
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "邮箱消息发送成功");
            }
            case MES_SENT_DINGDING ->{
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：钉钉机器人消息");
                map.put("sendWay",MES_SENT_DINGDING);
                homeService.message(map,new ArrayList<>());
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+ "钉钉机器人消息成功");
            }
            default -> {
                throw new ApplicationException("没有该类型的消息提醒:"+ type);
            }
        }


    }

}
































