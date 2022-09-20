package net.tiklab.matflow.execute.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.service.MatFlowExecLogService;
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
@RequestMapping("/matFlowLog")
@Api(name = "MatFlowLogController",desc = "流水线日志")
public class MatFlowExecLogController {

    @Autowired
    MatFlowExecLogService MatFlowExecLogService;

    //查询日志
    @RequestMapping(path="/findAllLog",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLog",desc = "查询日志")
    @ApiParam(name = "historyId",desc = "历史id",required = true)
    public Result<List<MatFlowExecLog>> findAllLog(@NotNull String historyId){
        List<MatFlowExecLog> allLog = MatFlowExecLogService.findAllLog(historyId);
        return Result.ok(allLog);
    }
}
