package io.tiklab.matflow.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/scm")
@Api(name = "ScmController",desc = "系统信息")
public class ScmController {

    @Autowired
    ScmService scmService;

    //创建
    @RequestMapping(path="/createPipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineScm",desc = "创建")
    @ApiParam(name = "scm",desc = "配置信息",required = true)
    public Result<String> createPipelineScm(@RequestBody @Valid Scm scm) {
        String pipelineScmId = scmService.createPipelineScm(scm);
        return Result.ok(pipelineScmId);
    }

    //删除
    @RequestMapping(path="/deletePipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineScm",desc = "删除")
    @ApiParam(name = "pipelineConfigId",desc = "配置id",required = true)
    public Result<Void> deletePipelineScm(@NotNull @Valid String scmId) {
        scmService.deletePipelineScm(scmId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updatePipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineScm",desc = "更新")
    @ApiParam(name = "scm",desc = "配置信息",required = true)
    public Result<Void> updatePipelineScm(@RequestBody @NotNull @Valid Scm scm) {
       scmService.updatePipelineScm(scm);
       return Result.ok();
    }

    //查询
    @RequestMapping(path="/findPipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineScm",desc = "查询")
    @ApiParam(name = "pipelineConfigId",desc = "配置id",required = true)
    public Result<Scm> findPipelineScm(@NotNull @Valid String scmId) {
        Scm scm = scmService.findOnePipelineScm(scmId);
        return Result.ok(scm);
    }

    //查询所有
    @RequestMapping(path="/findAllPipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineScm",desc = "查询所有")
    public Result<List<Scm>> findAllPipelineScm() {
        List<Scm> allScm = scmService.findAllPipelineScm();
        return Result.ok(allScm);
    }

}
