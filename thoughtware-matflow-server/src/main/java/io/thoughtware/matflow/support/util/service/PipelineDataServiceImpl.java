package io.thoughtware.matflow.support.util.service;

import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.service.PipelineService;
import io.thoughtware.message.message.model.MessageDmNotice;
import io.thoughtware.message.message.model.MessageDmNoticeQuery;
import io.thoughtware.message.message.service.MessageDmNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineDataServiceImpl implements PipelineDataService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    MessageDmNoticeService messageDmNoticeService;


    @Override
    public void cleanMessageData(){

        List<Pipeline> allPipeline = pipelineService.findAllPipeline();

        for (Pipeline pipeline : allPipeline) {
            String domainId = pipeline.getId();

            MessageDmNoticeQuery messageDmNoticeQuery = new MessageDmNoticeQuery();
            messageDmNoticeQuery.setDomainId(domainId);
            List<MessageDmNotice> messageDmNoticeList =
                    messageDmNoticeService.findMessageDmNoticeList(messageDmNoticeQuery);

            if (!messageDmNoticeList.isEmpty()){
                continue;
            }

            // 克隆消息模版
            messageDmNoticeService.initMessageDmNotice(domainId);
        }
    }

}















