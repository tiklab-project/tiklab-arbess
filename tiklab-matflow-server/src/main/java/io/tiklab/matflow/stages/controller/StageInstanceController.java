package io.tiklab.matflow.stages.controller;

import io.tiklab.matflow.stages.model.StageInstance;
import io.tiklab.matflow.stages.service.StageInstanceServer;
import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/stageInstance")
@Api(name = "StageInstanceController",desc = "阶段实例")
public class StageInstanceController {

    @Autowired
    StageInstanceServer stageInstanceServer;

    @RequestMapping(path="/findStageInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLog",desc = "查询日志")
    @ApiParam(name = "instanceId",desc = "流水线实例id",required = true)
    public Result<List<StageInstance>> findAllLog(@NotNull String instanceId){

        List<StageInstance> allLog = stageInstanceServer.findStageExecInstance(instanceId);

        return Result.ok(allLog);
    }


}






































