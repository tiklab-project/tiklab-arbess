package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.MatFlowScm;
import net.tiklab.matflow.setting.service.MatFlowScmService;
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
@RequestMapping("/path")
@Api(name = "MatFlowScmController",desc = "系统信息")
public class MatFlowScmController {

    @Autowired
    MatFlowScmService matFlowScmService;

    //创建
    @RequestMapping(path="/createMatFlowScm",method = RequestMethod.POST)
    @ApiMethod(name = "createMatFlowScm",desc = "创建")
    @ApiParam(name = "matFlowScm",desc = "配置信息",required = true)
    public Result<String> createMatFlowScm(@RequestBody @Valid MatFlowScm matFlowScm) {
        String matFlowScmId = matFlowScmService.createMatFlowScm(matFlowScm);
        return Result.ok(matFlowScmId);
    }

    //删除
    @RequestMapping(path="/deleteMatFlowScm",method = RequestMethod.POST)
    @ApiMethod(name = "deleteMatFlowScm",desc = "删除")
    @ApiParam(name = "matFlowConfigId",desc = "配置id",required = true)
    public Result<Void> deleteMatFlowScm(@NotNull @Valid String pathId) {
        matFlowScmService.deleteMatFlowScm(pathId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateMatFlowScm",method = RequestMethod.POST)
    @ApiMethod(name = "updateMatFlowScm",desc = "更新")
    @ApiParam(name = "matFlowScm",desc = "配置信息",required = true)
    public Result<Void> updateMatFlowScm(@RequestBody @NotNull @Valid MatFlowScm matFlowScm) {
       matFlowScmService.updateMatFlowScm(matFlowScm);
       return Result.ok();
    }

    //查询
    @RequestMapping(path="/findMatFlowScm",method = RequestMethod.POST)
    @ApiMethod(name = "findMatFlowScm",desc = "查询")
    @ApiParam(name = "matFlowConfigId",desc = "配置id",required = true)
    public Result<MatFlowScm> findMatFlowScm(@NotNull @Valid String pathId) {
        MatFlowScm matFlowScm = matFlowScmService.findOneMatFlowScm(pathId);
        return Result.ok(matFlowScm);
    }

    //查询所有
    @RequestMapping(path="/findAllMatFlowScm",method = RequestMethod.POST)
    @ApiMethod(name = "findAllMatFlowScm",desc = "查询所有")
    public Result<List<MatFlowScm>> findAllMatFlowScm() {
        List<MatFlowScm> allMatFlowScm = matFlowScmService.findAllMatFlowScm();
        return Result.ok(allMatFlowScm);
    }

}
