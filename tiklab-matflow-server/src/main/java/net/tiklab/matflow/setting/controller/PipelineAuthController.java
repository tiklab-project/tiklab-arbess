package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.PipelineAuth;
import net.tiklab.matflow.setting.service.PipelineAuthService;
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
@RequestMapping("/auth")
@Api(name = "PipelineAuthController",desc = "流水线授权")
public class PipelineAuthController {

    @Autowired
    PipelineAuthService authService;


    @RequestMapping(path="/createAuth",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuth",desc = "创建")
    @ApiParam(name = "pipelineAuth",desc = "配置信息",required = true)
    public Result<String> createPipelineAuth(@RequestBody @Valid PipelineAuth pipelineAuth) {
        String pipelineAuthId = authService.createAuth(pipelineAuth);
        return Result.ok(pipelineAuthId);
    }

    //删除
    @RequestMapping(path="/deleteAuth",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuth",desc = "删除")
    @ApiParam(name = "authId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuth(@NotNull @Valid String authId) {
        authService.deleteAuth(authId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuth",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuth",desc = "更新")
    @ApiParam(name = "pipelineAuth",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuth(@RequestBody @NotNull @Valid PipelineAuth pipelineAuth) {
        authService.updateAuth(pipelineAuth);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findOneAuth",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuth",desc = "查询")
    @ApiParam(name = "authId",desc = "配置id",required = true)
    public Result<PipelineAuth> findPipelineAuth(@NotNull @Valid String authId) {
        PipelineAuth pipelineAuth = authService.findOneAuth(authId);
        return Result.ok(pipelineAuth);
    }

    //查询所有
    @RequestMapping(path="/findAllAuth",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineAuth",desc = "查询所有")
    public Result<List<PipelineAuth>> findAllPipelineAuth() {
        List<PipelineAuth> allPipelineAuth = authService.findAllAuth();
        return Result.ok(allPipelineAuth);
    }

    
}
