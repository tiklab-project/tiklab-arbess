package net.tiklab.matflow.pipeline.instance.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 流水线实例控制器
 */
@RestController
@RequestMapping("/instance")
@Api(name = "PipelineInstanceController",desc = "流水线实例")
public class PipelineInstanceController {

    @Autowired
    PipelineInstanceService instanceService;

    //删除历史
    @RequestMapping(path="/deleteInstance",method = RequestMethod.POST)
    @ApiMethod(name = "deleteInstance",desc = "删除流水线实例")
    @ApiParam(name = "instanceId",desc = "流水线实例id",required = true)
    public Result<Void> deleteInstance(@NotNull String instanceId){
        instanceService.deleteInstance(instanceId);
        return Result.ok();
    }

    @RequestMapping(path="/findPipelineInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineInstance",desc = "查询流水线实例")
    @ApiParam(name = "query",desc = "查询条件",required = true)
    public Result<Pagination<PipelineInstance>> findPipelineInstance(
            @RequestBody @NotNull @Valid  PipelineInstanceQuery query){
        Pagination<PipelineInstance> list =
                instanceService.findPipelineInstance(query);
        return Result.ok(list);
    }
    
    @RequestMapping(path="/findUserInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findUserInstance",desc = "查询用户实例")
    @ApiParam(name = "query",desc = "查询条件",required = true)
    public Result<Pagination<PipelineInstance>>  findUserInstance(
            @RequestBody @Valid @NotNull PipelineInstanceQuery query){
        Pagination<PipelineInstance> userAllInstance = instanceService.findUserInstance(query);
        return Result.ok(userAllInstance);
    }

    
    
    

}
