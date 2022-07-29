package com.tiklab.matflow.instance.controller;


import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowHistoryQuery;
import com.tiklab.matflow.instance.service.MatFlowExecHistoryService;
import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/matFlowHistory")
@Api(name = "MatFlowHistoryController",desc = "流水线历史")
public class MatFlowExecHistoryController {

    @Autowired
    MatFlowExecHistoryService matFlowExecHistoryService;

    //查询所有历史
    @RequestMapping(path="/findAllHistory",method = RequestMethod.POST)
    @ApiMethod(name = "findAllHistory",desc = "查看所有历史")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<MatFlowExecHistory> findAllHistory(@NotNull String matFlowId){

        List<MatFlowExecHistory> allHistory = matFlowExecHistoryService.findAllHistory(matFlowId);
        return Result.ok(allHistory);
    }

    //删除历史
    @RequestMapping(path="/deleteHistory",method = RequestMethod.POST)
    @ApiMethod(name = "deleteHistory",desc = "删除历史")
    @ApiParam(name = "historyId",desc = "流水线id",required = true)
    public Result<Void> deleteHistory(@NotNull String historyId){
       matFlowExecHistoryService.deleteHistory(historyId);
        return Result.ok();
    }


    @RequestMapping(path="/findPageHistory",method = RequestMethod.POST)
    @ApiMethod(name = "findPageHistory",desc = "查询历史信息")
    @ApiParam(name = "matFlowHistoryQueryPage",desc = "条件",required = true)
    public Result<Pagination<MatFlowExecHistory>> findPageHistory(@RequestBody @NotNull MatFlowHistoryQuery matFlowHistoryQuery){
        Pagination<MatFlowExecHistory> list = matFlowExecHistoryService.findPageHistory(matFlowHistoryQuery);
        return Result.ok(list);
    }

}
