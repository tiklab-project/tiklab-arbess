package io.tiklab.arbess.support.condition.controller;

import io.tiklab.arbess.support.condition.model.Condition;
import io.tiklab.arbess.support.condition.service.ConditionService;
import io.tiklab.core.Result;
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
 * @pi.groupName: 流水线条件控制器
 */
@RestController
@RequestMapping("/pipelineCond")
public class ConditionController {

    @Autowired
    ConditionService conditionServer;

    /**
     * @pi.name:创建条件
     * @pi.url:/pipelineCond/createCond
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=condition
     */
    @RequestMapping(path="/createCond",method = RequestMethod.POST)
    public Result<String> createCond(@RequestBody @NotNull @Valid Condition condition){
        String condId = conditionServer.createCond(condition);
        return Result.ok(condId);
    }

    /**
     * @pi.name:更新条件
     * @pi.url:/pipelineCond/updateCond
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=condition
     */
    @RequestMapping(path="/updateCond",method = RequestMethod.POST)
    public Result<Void> updateCond(@RequestBody @NotNull @Valid Condition condition){
         conditionServer.updateCond(condition);
        return Result.ok();
    }

    /**
     * @pi.name:删除条件
     * @pi.url:/pipelineCond/deleteCond
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=condId;dataType=string;value=condId;
     */
    @RequestMapping(path="/deleteCond",method = RequestMethod.POST)
    public Result<Void> deleteCond(@NotNull String condId){
        conditionServer.deleteCond(condId);
        return Result.ok();
    }

    /**
     * @pi.name:查询任务条件
     * @pi.url:/pipelineCond/findAllTaskCond
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=taskId;dataType=string;value=taskId;
     */
    @RequestMapping(path="/findAllTaskCond",method = RequestMethod.POST)
    public Result<List<Condition>> findAllTaskCond(@NotNull String  taskId){
        List<Condition> condId = conditionServer.findAllTaskCond(taskId);
        return Result.ok(condId);
    }



}

















































