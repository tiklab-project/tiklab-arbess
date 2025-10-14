package io.tiklab.arbess.support.util.task.service;

import io.tiklab.arbess.agent.ws.config.WebSocketClient;
import io.tiklab.install.runner.TiklabApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskInitAgent implements TiklabApplicationRunner {

    @Autowired
    WebSocketClient webSocketClient;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run() {
        logger.info("The init agent.....");
        webSocketClient.initWebSocketConnect();
        logger.info("The init agent end.");

        new Thread(
            () -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // logger.info("start auto connect.....");
                WebSocketClient.beginConnect = true;
            }
        ).start();

    }
}

