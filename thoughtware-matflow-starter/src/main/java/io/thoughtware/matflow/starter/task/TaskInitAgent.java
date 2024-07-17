package io.thoughtware.matflow.starter.task;

import io.thoughtware.matflow.agent.ws.config.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskInitAgent implements ApplicationRunner  {

    @Autowired
    WebSocketClient webSocketClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        webSocketClient.initWebSocketConnect();
    }


}
