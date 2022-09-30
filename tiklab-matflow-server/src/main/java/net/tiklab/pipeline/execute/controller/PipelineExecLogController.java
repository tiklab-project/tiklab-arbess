package net.tiklab.pipeline.execute.controller;


import net.tiklab.core.Result;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.execute.service.PipelineExecLogService;
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
@RequestMapping("/pipelineLog")
@Api(name = "PipelineLogController",desc = "流水线日志")
public class PipelineExecLogController {

    @Autowired
    PipelineExecLogService PipelineExecLogService;

    //查询日志
    @RequestMapping(path="/findAllLog",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLog",desc = "查询日志")
    @ApiParam(name = "historyId",desc = "历史id",required = true)
    public Result<List<PipelineExecLog>> findAllLog(@NotNull String historyId){
        List<PipelineExecLog> allLog = PipelineExecLogService.findAllLog(historyId);
        return Result.ok(allLog);
    }
}
