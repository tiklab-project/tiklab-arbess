package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.agent.ws.config.WebSocketClient;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskInitAgent implements TiklabApplicationRunner {

    @Autowired
    WebSocketClient webSocketClient;
//
//     @Override
//     public void run(ApplicationArguments args) throws Exception {
//         // webSocketClient.initWebSocketConnect();
//     }

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
                    logger.info("Enable automatic connection .....");
                    WebSocketClient.beginConnect = true;
                }
        ).start();

    }
}
