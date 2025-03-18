package io.tiklab.arbess.pipeline.instance.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线实例控制器
 */
@RestController
@RequestMapping("/instance")
public class PipelineInstanceController {

    @Autowired
    PipelineInstanceService instanceService;

    /**
     * @pi.name:删除流水线执行实例
     * @pi.url:/instance/deleteInstance
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=instanceId;dataType=string;value=实例id;
     */
    @RequestMapping(path="/deleteInstance",method = RequestMethod.POST)
    public Result<Void> deleteInstance(@NotNull String instanceId){
        instanceService.deleteInstance(instanceId);
        return Result.ok();
    }

    /**
     * @pi.name:查询流水线实例
     * @pi.url:/follow/findPipelineInstance
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=query
     */
    @RequestMapping(path="/findPipelineInstance",method = RequestMethod.POST)
    public Result<Pagination<PipelineInstance>> findPipelineInstance(@RequestBody @NotNull @Valid  PipelineInstanceQuery query){
        Pagination<PipelineInstance> list = instanceService.findPipelineInstance(query);
        return Result.ok(list);
    }

    /**
     * @pi.name:查询用户流水线实例
     * @pi.url:/follow/findUserInstance
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=query
     */
    @RequestMapping(path="/findUserInstance",method = RequestMethod.POST)
    public Result<Pagination<PipelineInstance>>  findUserInstance (
            @RequestBody @Valid @NotNull PipelineInstanceQuery query){
        Pagination<PipelineInstance> userAllInstance = instanceService.findUserInstance(query);
        return Result.ok(userAllInstance);
    }

    /**
     * @pi.name:查询单个流水线实例
     * @pi.url:/instance/findOneInstance
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=instanceId;dataType=string;value=实例id;
     */
    @RequestMapping(path="/findOneInstance",method = RequestMethod.POST)
    public Result<Pagination<PipelineInstance>>  findOneInstance( @NotNull String instanceId){
        PipelineInstance lastInstance = instanceService.findOneInstance(instanceId);
        return Result.ok(lastInstance);
    }
    
    

}
