package net.tiklab.pipeline.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.pipeline.setting.model.PipelineScm;
import net.tiklab.pipeline.setting.service.PipelineScmService;
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
@RequestMapping("/scm")
@Api(name = "PipelineScmController",desc = "系统信息")
public class PipelineScmController {

    @Autowired
    PipelineScmService pipelineScmService;

    //创建
    @RequestMapping(path="/createPipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineScm",desc = "创建")
    @ApiParam(name = "pipelineScm",desc = "配置信息",required = true)
    public Result<String> createPipelineScm(@RequestBody @Valid PipelineScm pipelineScm) {
        String pipelineScmId = pipelineScmService.createPipelineScm(pipelineScm);
        return Result.ok(pipelineScmId);
    }

    //删除
    @RequestMapping(path="/deletePipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineScm",desc = "删除")
    @ApiParam(name = "pipelineConfigId",desc = "配置id",required = true)
    public Result<Void> deletePipelineScm(@NotNull @Valid String scmId) {
        pipelineScmService.deletePipelineScm(scmId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updatePipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineScm",desc = "更新")
    @ApiParam(name = "pipelineScm",desc = "配置信息",required = true)
    public Result<Void> updatePipelineScm(@RequestBody @NotNull @Valid PipelineScm pipelineScm) {
       pipelineScmService.updatePipelineScm(pipelineScm);
       return Result.ok();
    }

    //查询
    @RequestMapping(path="/findPipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineScm",desc = "查询")
    @ApiParam(name = "pipelineConfigId",desc = "配置id",required = true)
    public Result<PipelineScm> findPipelineScm(@NotNull @Valid String scmId) {
        PipelineScm pipelineScm = pipelineScmService.findOnePipelineScm(scmId);
        return Result.ok(pipelineScm);
    }

    //查询所有
    @RequestMapping(path="/findAllPipelineScm",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineScm",desc = "查询所有")
    public Result<List<PipelineScm>> findAllPipelineScm() {
        List<PipelineScm> allPipelineScm = pipelineScmService.findAllPipelineScm();
        return Result.ok(allPipelineScm);
    }

}
