package net.tiklab.matflow.definition.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.definition.service.PipelineConfigServer;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineConfig")
@Api(name = "PipelineConfigController",desc = "流水线配置")
public class PipelineConfigController {


    @Autowired
    PipelineConfigServer configServer;

    @RequestMapping(path="/findAllConfig",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result< List<Object>> updateConfigure( @NotNull String pipelineId){
        List<Object> allConfig = configServer.findAllConfig(pipelineId);
        return Result.ok(allConfig);
    }

}

























