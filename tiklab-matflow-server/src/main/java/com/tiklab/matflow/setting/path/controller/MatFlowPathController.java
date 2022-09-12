package com.tiklab.matflow.setting.path.controller;

import com.tiklab.core.Result;
import com.tiklab.matflow.setting.path.model.MatFlowPath;
import com.tiklab.matflow.setting.path.service.MatFlowPathService;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/path")
@Api(name = "MatFlowPathController",desc = "系统信息")
public class MatFlowPathController {

    @Autowired
    MatFlowPathService matFlowPathService;

    //创建
    @RequestMapping(path="/createMatFlowPath",method = RequestMethod.POST)
    @ApiMethod(name = "createMatFlowPath",desc = "创建")
    @ApiParam(name = "matFlowPath",desc = "配置信息",required = true)
    public Result<String> createMatFlowPath(@RequestBody @Valid MatFlowPath matFlowPath) {
        String matFlowPathId = matFlowPathService.createMatFlowPath(matFlowPath);
        return Result.ok(matFlowPathId);
    }

    //删除
    @RequestMapping(path="/deleteMatFlowPath",method = RequestMethod.POST)
    @ApiMethod(name = "deleteMatFlowPath",desc = "删除")
    @ApiParam(name = "matFlowConfigId",desc = "配置id",required = true)
    public Result<Void> deleteMatFlowPath(@NotNull @Valid String pathId) {
        matFlowPathService.deleteMatFlowPath(pathId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateMatFlowPath",method = RequestMethod.POST)
    @ApiMethod(name = "updateMatFlowPath",desc = "更新")
    @ApiParam(name = "matFlowPath",desc = "配置信息",required = true)
    public Result<Void> updateMatFlowPath(@RequestBody @NotNull @Valid MatFlowPath matFlowPath) {
       matFlowPathService.updateMatFlowPath(matFlowPath);
       return Result.ok();
    }

    //查询
    @RequestMapping(path="/findMatFlowPath",method = RequestMethod.POST)
    @ApiMethod(name = "findMatFlowPath",desc = "查询")
    @ApiParam(name = "matFlowConfigId",desc = "配置id",required = true)
    public Result<MatFlowPath> findMatFlowPath(@NotNull @Valid String pathId) {
        MatFlowPath matFlowPath = matFlowPathService.findOneMatFlowPath(pathId);
        return Result.ok(matFlowPath);
    }

    //查询所有
    @RequestMapping(path="/findAllMatFlowPath",method = RequestMethod.POST)
    @ApiMethod(name = "findAllMatFlowPath",desc = "查询所有")
    public Result<List<MatFlowPath>> findAllMatFlowPath() {
        List<MatFlowPath> allMatFlowPath = matFlowPathService.findAllMatFlowPath();
        return Result.ok(allMatFlowPath);
    }

}
