package io.tiklab.matflow.task.message.controller;


import io.tiklab.core.Result;
import io.tiklab.matflow.task.message.service.TaskMessageTypeService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息控制器
 */

@RestController
@RequestMapping("/message")
@Api(name = "TaskMessageController",desc = "消息")
public class TaskMessageController {


    @Autowired
    TaskMessageTypeService messageTypeService;


    @RequestMapping(path="/findMessageSendType",method = RequestMethod.POST)
    @ApiMethod(name = "messageSendType",desc = "查询消息发送方式")
    public Result<List<String>> messageSendType(){
        List<String> list = messageTypeService.messageSendType();
        return Result.ok(list);
    }




}





























