package com.tiklab.matflow.instance.controller;


import com.tiklab.core.Result;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.matflow.instance.service.MatFlowExecLogService;
import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
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
