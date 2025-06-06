package io.tiklab.arbess.support.message.controller;

import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.arbess.support.message.model.TaskMessageQuery;
import io.tiklab.arbess.support.message.service.TaskMessageService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipeline/message")
public class TaskMessageController {

    @Autowired
    TaskMessageService messageService;


    @RequestMapping(path="/createTaskMessage",method = RequestMethod.POST)
    public Result<String> createTaskMessage(@RequestBody @Valid @NotNull TaskMessage taskMessage){
        String id = messageService.createTaskMessage(taskMessage);
        return Result.ok(id);
    }

    @RequestMapping(path="/updateTaskMessage",method = RequestMethod.POST)
    public Result<String> updateTaskMessage(@RequestBody @Valid @NotNull TaskMessage taskMessage){
        messageService.updateTaskMessage(taskMessage);
        return Result.ok();
    }

    @RequestMapping(path="/deleteTaskMessage",method = RequestMethod.POST)
    public Result<Void> deleteTaskMessage(@Valid @NotNull String id){
         messageService.deleteTaskMessage(id);
        return Result.ok();
    }

    @RequestMapping(path="/findTaskMessage",method = RequestMethod.POST)
    public Result<TaskMessage> findTaskMessage(@Valid @NotNull String id){
        TaskMessage taskMessage = messageService.findTaskMessage(id);
        return Result.ok(taskMessage);
    }

    @RequestMapping(path="/findTaskMessageList",method = RequestMethod.POST)
    public Result<List<TaskMessage>> findTaskMessageList(@RequestBody @Valid @NotNull TaskMessageQuery messageQuery){
        List<TaskMessage> taskMessageList = messageService.findTaskMessageList(messageQuery);
        return Result.ok(taskMessageList);
    }

    @RequestMapping(path="/findAllTaskMessage",method = RequestMethod.POST)
    public Result<List<TaskMessage>> findAllTaskMessage(){
        List<TaskMessage> taskMessageList = messageService.findAllTaskMessage();
        return Result.ok(taskMessageList);
    }

    @RequestMapping(path="/findTaskMessagePage",method = RequestMethod.POST)
    public Result<List<TaskMessage>> findTaskMessagePage(@RequestBody @Valid @NotNull TaskMessageQuery messageQuery){
        Pagination<TaskMessage> taskMessagePage = messageService.findTaskMessagePage(messageQuery);
        return Result.ok(taskMessagePage);
    }


    @RequestMapping(path="/findMessageSendTypeList",method = RequestMethod.POST)
    public Result<List<String>> findMessageSendType(){
        List<String> list = messageService.findMessageSendTypeList();
        return Result.ok(list);
    }


}


