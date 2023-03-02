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
import java.util.List;

@RestController
@RequestMapping("/instance")
@Api(name = "PipelineInstanceController",desc = "流水线历史")
public class PipelineInstanceController {

    @Autowired
    PipelineInstanceService pipelineInstanceService;

    //查询所有历史
    @RequestMapping(path="/findAllInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findAllInstance",desc = "查看所有历史")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineInstance> findAllInstance(@NotNull String pipelineId){

        List<PipelineInstance> allInstance = pipelineInstanceService.findAllInstance(pipelineId);
        return Result.ok(allInstance);
    }

    //删除历史
    @RequestMapping(path="/deleteInstance",method = RequestMethod.POST)
    @ApiMethod(name = "deleteInstance",desc = "删除历史")
    @ApiParam(name = "instanceId",desc = "流水线id",required = true)
    public Result<Void> deleteInstance(@NotNull String instanceId){
       pipelineInstanceService.deleteInstance(instanceId);
        return Result.ok();
    }

    @RequestMapping(path="/findPageInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findPageInstance",desc = "查询历史信息")
    @ApiParam(name = "pipelineInstanceQueryPage",desc = "条件",required = true)
    public Result<Pagination<PipelineInstance>> findPageInstance(
            @RequestBody @NotNull @Valid  PipelineInstanceQuery pipelineInstanceQuery){
        Pagination<PipelineInstance> list =
                pipelineInstanceService.findPageInstance(pipelineInstanceQuery);
        return Result.ok(list);
    }
    
    @RequestMapping(path="/findUserAllInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findUserAllInstance",desc = "分页")
    @ApiParam(name = "pipelineInstanceQuery",desc = "分页信息",required = true)
    public Result<Pagination<PipelineInstance>>  findUserAllInstance(
            @RequestBody @Valid @NotNull PipelineInstanceQuery pipelineInstanceQuery){
        Pagination<PipelineInstance> userAllInstance =
                pipelineInstanceService.findUserAllInstance(pipelineInstanceQuery);
        return Result.ok(userAllInstance);
    }

    @RequestMapping(path="/findUserRunPageInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findUserRunPageInstance",desc = "判断是否执行")
    @ApiParam(name = "query",desc = "分页信息",required = true)
    public Result<Pagination<PipelineInstance>> findUserRunPageInstance(
            @RequestBody @Valid @NotNull PipelineInstanceQuery query) {
        Pagination<PipelineInstance> userRunPageInstance =
                pipelineInstanceService.findUserRunPageInstance(query);
        return Result.ok(userRunPageInstance);
    }
    
    
    

}
