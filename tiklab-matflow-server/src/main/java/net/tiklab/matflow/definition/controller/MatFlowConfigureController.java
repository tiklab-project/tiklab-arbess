package net.tiklab.matflow.definition.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.model.MatFlowExecConfigure;
import net.tiklab.matflow.definition.service.MatFlowConfigureService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/matFlowConfigure")
@Api(name = "MatFlowConfigureController",desc = "流水线配置")
public class MatFlowConfigureController {

    @Autowired
    MatFlowConfigureService matFlowConfigureService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowConfigureController.class);


    //根据流水线id查询配置信息
    @RequestMapping(path="/findAllConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "findAll",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<List<Object>> findAll(@NotNull String matFlowId) {
        List<Object> list = matFlowConfigureService.findAll(matFlowId);
        return Result.ok(list);
    }

    //更新信息
    @RequestMapping(path="/updateConfigure",method = RequestMethod.POST)
    @ApiMethod(name = "updateConfigure",desc = "更新流水线配置")
    @ApiParam(name = "matFlowExecConfigure",desc = "matFlowExecConfigure",required = true)
    public Result<Void> updateConfigure(@RequestBody @NotNull @Valid MatFlowExecConfigure matFlowExecConfigure){
        matFlowConfigureService.updateTask(matFlowExecConfigure);
        return Result.ok();
    }

    //根据流水线id查询配置
    @RequestMapping(path="/findAll",method = RequestMethod.POST)
    @ApiMethod(name = "findAllConfigure",desc = "根据流水线id查询配置信息")
    @ApiParam(name = "matFlowId",desc = "流水线id",required = true)
    public Result<List<Object>> findAllConfigure(@NotNull String matFlowId) {
        List<MatFlowConfigure> list = matFlowConfigureService.findAllConfigure(matFlowId);
        return Result.ok(list);
    }


}
