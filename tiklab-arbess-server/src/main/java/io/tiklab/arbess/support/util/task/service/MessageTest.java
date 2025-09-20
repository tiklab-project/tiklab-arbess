package io.tiklab.arbess.support.util.task.service;

import io.tiklab.message.message.model.MessageItem;
import io.tiklab.message.msgsub.service.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageTest implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageTest.class);


    @Override
    public void handleMessage(MessageItem messageItem) {
        logger.info("接收到消息:{}", messageItem.getId());
    }
}
