package io.tiklab.matflow.support.trigger.controller;


import io.tiklab.core.Result;
import io.tiklab.matflow.support.trigger.model.Trigger;
import io.tiklab.matflow.support.trigger.service.TriggerService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/trigger")
@Api(name = "TriggerController",desc = "触发器")
public class TriggerController {

    @Autowired
    TriggerService triggerServer;

    //更新信息
    @RequestMapping(path="/updateTrigger",method = RequestMethod.POST)
    @ApiMethod(name = "updateTrigger",desc = "更新触发器")
    @ApiParam(name = "trigger",desc = "触发器配置信息",required = true)
    public Result<Void> updateTrigger(@RequestBody @NotNull @Valid Trigger trigger){
        triggerServer.updateTrigger(trigger);
        return Result.ok();
    }

    @RequestMapping(path="/deleteTrigger",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTrigger",desc = "删除触发器")
    @ApiParam(name = "triggerId",desc = "触发器id",required = true)
    public Result<Void> deleteTrigger(@NotNull String triggerId){
        triggerServer.deleteTrigger(triggerId);
        return Result.ok();
    }

    @RequestMapping(path="/createTrigger",method = RequestMethod.POST)
    @ApiMethod(name = "createTrigger",desc = "创建触发器配置")
    @ApiParam(name = "trigger",desc = "触发器配置信息",required = true)
    public Result<String> createTrigger(@RequestBody @NotNull @Valid Trigger trigger) {
        String id = triggerServer.createTrigger(trigger);
        return Result.ok(id);
    }

    @RequestMapping(path="/findAllTrigger",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTrigger",desc = "查询流水线所有触发器")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllTrigger(@NotNull String pipelineId) {
        List<Object> list = triggerServer.findAllTrigger(pipelineId);
        return Result.ok(list);
    }

    @RequestMapping(path="/findOneTrigger",method = RequestMethod.POST)
    @ApiMethod(name = "findOneTrigger",desc = "触发器配置信息")
    @ApiParam(name = "triggerId",desc = "配置id",required = true)
    public Result<Object> findOneTriggerConfig(@NotNull String triggerId) {
        Object list = triggerServer.findOneTrigger(triggerId);
        return Result.ok(list);
    }



}
