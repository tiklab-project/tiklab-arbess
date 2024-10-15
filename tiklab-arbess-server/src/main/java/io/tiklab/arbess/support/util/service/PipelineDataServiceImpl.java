package io.tiklab.arbess.support.util.service;

import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.message.message.model.MessageDmNotice;
import io.tiklab.message.message.model.MessageDmNoticeQuery;
import io.tiklab.message.message.model.MessageNoticePatch;
import io.tiklab.message.message.service.MessageDmNoticeService;
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

        List<Pipeline> allPipeline = pipelineService.findAllPipelineNoQuery();

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
            MessageNoticePatch messageNoticePatch = new MessageNoticePatch();
            messageNoticePatch.setDomainId(domainId);
            messageNoticePatch.setUserList(List.of(pipeline.getUser().getId()));
            messageDmNoticeService.initMessageDmNotice(messageNoticePatch);
        }
    }

}















