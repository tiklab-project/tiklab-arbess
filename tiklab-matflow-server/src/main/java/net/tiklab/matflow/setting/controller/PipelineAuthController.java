package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.PipelineAuth;
import net.tiklab.matflow.setting.service.PipelineAuthServer;
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
@RequestMapping("/pipelineAuth")
@Api(name = "PipelineAuthController",desc = "流水线授权")
public class PipelineAuthController {

    @Autowired
    PipelineAuthServer authService;


    @RequestMapping(path="/createAuth",method = RequestMethod.POST)
    @ApiMethod(name = "createAuth",desc = "创建权限信息")
    @ApiParam(name = "pipelineAuth",desc = "认证信息",required = true)
    public Result<String> createAuth( @RequestBody @Valid @NotNull PipelineAuth pipelineAuth) {
        String authId = authService.createAuth(pipelineAuth);
        return Result.ok(authId);
    }


    @RequestMapping(path="/updateAuth",method = RequestMethod.POST)
    @ApiMethod(name = "updateAuth",desc = "更新权限信息")
    @ApiParam(name = "pipelineAuth",desc = "认证信息",required = true)
    public Result<Void> updateAuth(@RequestBody @Valid @NotNull PipelineAuth pipelineAuth) {
        authService.updateAuth(pipelineAuth);
        return Result.ok();
    }

    @RequestMapping(path="/deleteAuth",method = RequestMethod.POST)
    @ApiMethod(name = "deleteAuth",desc = "删除权限信息")
    @ApiParam(name = "type",desc = "类型",required = true)
    public Result<Void> deleteAuth(@NotNull int type, String authId) {
        authService.deleteAuth(type,authId);
        return Result.ok();
    }


    @RequestMapping(path="/findAllAuth",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuth",desc = "删除权限信息")
    @ApiParam(name = "type",desc = "类型",required = true)
    public Result<List<Object>> findAllAuth(@NotNull int type) {
        List<Object> allAuth = authService.findAllAuth(type);
        return Result.ok(allAuth);
    }
    
}
