package io.tiklab.matflow.support.trigger.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.support.trigger.model.Trigger;
import io.tiklab.matflow.support.trigger.model.TriggerQuery;
import io.tiklab.matflow.support.trigger.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线触发器控制器
 */
@RestController
@RequestMapping("/trigger")
public class TriggerController {

    @Autowired
    TriggerService triggerServer;

    /**
     * @pi.name:创建流水线触发器
     * @pi.path:/trigger/createTrigger
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=trigger
     */
    @RequestMapping(path="/createTrigger",method = RequestMethod.POST)
    public Result<String> createTrigger(@RequestBody @NotNull @Valid Trigger trigger) {
        String id = triggerServer.createTrigger(trigger);
        return Result.ok(id);
    }

    /**
     * @pi.name:创建流水线触发器信息
     * @pi.path:/trigger/updateTrigger
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=trigger
     */
    @RequestMapping(path="/updateTrigger",method = RequestMethod.POST)
    public Result<Void> updateTrigger(@RequestBody @NotNull @Valid Trigger trigger){
        triggerServer.updateTrigger(trigger);
        return Result.ok();
    }

    /**
     * @pi.name:删除流水线触发器信息
     * @pi.path:/trigger/finAllStage
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=triggerId;dataType=string;value=triggerId;
     */
    @RequestMapping(path="/deleteTrigger",method = RequestMethod.POST)
    public Result<Void> deleteTrigger(@NotNull String triggerId){
        triggerServer.deleteTrigger(triggerId);
        return Result.ok();
    }


    /**
     * @pi.name:查询流水线触发器信息
     * @pi.path:/trigger/findAllTrigger
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findAllTrigger",method = RequestMethod.POST)
    public Result<List<Object>> findAllTrigger(@RequestBody @Valid@NotNull TriggerQuery triggerQuery) {
        List<Object> list = triggerServer.findAllTrigger(triggerQuery);
        return Result.ok(list);
    }




}
