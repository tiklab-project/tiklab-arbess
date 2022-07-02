package com.doublekit.pipeline.instance.service.webSocket;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/socket")
@Api(name = "StartWebSocketController",desc = "Socket消息推送")
public class StartWebSocketController {

    private static Logger logger = LoggerFactory.getLogger(StartWebSocketController.class);

    @RequestMapping(path="/start",method = RequestMethod.POST)
    @ApiMethod(name = "start",desc = "通过接口推送socket")
    @ApiParam(name = "s",desc = "s",required = true)
    public Result<String> start(String s ,String pipelineId) {
        new WebSocketServer().sendMessageToUser(s, pipelineId);
        return Result.ok(pipelineId);
    }

}
