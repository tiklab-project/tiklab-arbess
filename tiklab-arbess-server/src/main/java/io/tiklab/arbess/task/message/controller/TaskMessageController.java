package io.tiklab.arbess.task.message.controller;

import io.tiklab.arbess.task.message.service.TaskMessageTypeService;
import io.tiklab.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线消息控制器
 */
@RestController
@RequestMapping("/taskMessage")
public class TaskMessageController {

    @Autowired
    TaskMessageTypeService messageTypeService;

    /**
     * @pi.name:查询消息发送方式
     * @pi.path:/taskMessage/findMessageSendType
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findMessageSendType",method = RequestMethod.POST)
    public Result<List<String>> messageSendType(){
        List<String> list = messageTypeService.messageSendType();
        return Result.ok(list);
    }




}





























