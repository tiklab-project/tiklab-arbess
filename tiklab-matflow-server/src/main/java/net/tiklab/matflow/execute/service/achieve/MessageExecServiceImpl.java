package net.tiklab.matflow.execute.service.achieve;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.task.PipelineMessage;
import net.tiklab.matflow.definition.service.task.PipelineMessageTypeServer;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.ConfigCommonService;
import net.tiklab.matflow.orther.service.PipelineFinal;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageExecServiceImpl implements MessageExecService {


    @Autowired
    ConfigCommonService commonService;

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

        String log = PipelineUntil.date(4);
        commonService.execHistory(pipelineProcess, log+"开始发送消息");
        Pipeline pipeline = pipelineProcess.getPipeline();

        PipelineMessage configMessage = messageTypeServer.findConfigMessage(configId);

        List<String> typeList = configMessage.getTypeList();

        Map<String, String> map = homeService.initMap(pipeline);

        try {
            for (String s : typeList) {
                if (!PipelineUntil.isNoNull(s)){
                    continue;
                }
                messageType(s,map);
            }
        }catch (ApplicationException e){
            String message = e.getMessage();
            commonService.execHistory(pipelineProcess, log+message);
            return false;
        }

        commonService.execHistory(pipelineProcess, log+"消息发送成功。");
        return true;
    }

    /**
     * 消息发送方式
     * @param type 类型
     * @param map 消息发送内容
     * @throws ApplicationException 消息发送失败
     */
    private void messageType(String type,Map<String,String> map) throws ApplicationException {
        map.put("message","成功");
        switch (type){
            case "site" ->{
                homeService.message(PipelineFinal.MES_TEM_PIPELINE_RUN,PipelineFinal.MES_PIPELINE_RUN,map);
            }
            case "sms" ->{
                homeService.smsMessage(map);
            }
            case "wechat" ->{
                homeService.wechatMarkdownMessage(map);
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
































