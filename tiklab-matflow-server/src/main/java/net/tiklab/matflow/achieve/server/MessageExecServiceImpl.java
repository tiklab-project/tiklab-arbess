package net.tiklab.matflow.achieve.server;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.task.model.PipelineMessage;
import net.tiklab.matflow.task.model.PipelineUserMessage;
import net.tiklab.matflow.task.server.PipelineMessageTypeServer;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.until.PipelineFinal;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    private static final Logger logger = LoggerFactory.getLogger(MessageExecServiceImpl.class);

    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    public boolean message(PipelineProcess pipelineProcess, String configId) {

        commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"执行任务：消息通知.....");

        Pipeline pipeline = pipelineProcess.getPipeline();

        PipelineMessage configMessage = messageTypeServer.findConfigMessage(configId);

        List<String> typeList = configMessage.getTypeList();

        List<PipelineUserMessage> userList = configMessage.getUserList();

        Map<String, String> map = homeService.initMap(pipeline);

        try {
            for (String s : typeList) {
                if (!PipelineUntil.isNoNull(s)){
                    continue;
                }
                messageType(pipelineProcess,s,map);
            }
        }catch (ApplicationException e){
            String message = e.getMessage();
            commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+message);
            return false;
        }
        return true;
    }

    /**
     * 消息发送方式
     * @param type 类型
     * @param map 消息发送内容
     * @throws ApplicationException 消息发送失败
     */
    private void messageType(PipelineProcess pipelineProcess,String type,Map<String,String> map) throws ApplicationException {
        map.put("message","成功");
        switch (type){
            case "site" ->{
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：站内信");
                homeService.message(PipelineFinal.MES_TEM_PIPELINE_RUN,PipelineFinal.MES_PIPELINE_RUN,map);
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+ "站内信发送成功");
            }
            case "sms" ->{
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：短信");
                homeService.smsMessage(map);
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+ "短信发送成功。");
            }
            case "wechat" ->{
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+ "发送消息，类型：企业微信机器人消息");
                homeService.wechatMarkdownMessage(map);
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+ "企业微信机器人消息发送成功。");
            }
            case "mail" ->{
                return;
            }
            default -> {
                throw new ApplicationException("没有该类型的消息提醒:"+ type);
            }
        }


    }

}
































