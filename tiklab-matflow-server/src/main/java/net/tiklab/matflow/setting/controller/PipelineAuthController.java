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
@RequestMapping("/pipelineAuth")
@Api(name = "PipelineAuthController",desc = "流水线授权")
public class PipelineAuthController {

    @Autowired
    PipelineAuthService AuthService;


    @RequestMapping(path="/createAuth",method = RequestMethod.POST)
    @ApiMethod(name = "createAuth",desc = "创建权限信息")
    @ApiParam(name = "pipelineAuth",desc = "权限信息",required = true)
    public Result<String> createAuth(@RequestBody @Valid @NotNull PipelineAuth pipelineAuth) {
        String AuthId = AuthService.createAuth(pipelineAuth);
        return Result.ok(AuthId);
    }


    @RequestMapping(path="/updateAuth",method = RequestMethod.POST)
    @ApiMethod(name = "updateAuth",desc = "更新权限信息")
    @ApiParam(name = "pipelineAuth",desc = "权限信息",required = true)
    public Result<Void> updateAuth(@RequestBody @Valid @NotNull PipelineAuth pipelineAuth) {
        AuthService.updateAuth(pipelineAuth);
        return Result.ok();
    }

    @RequestMapping(path="/deleteAuth",method = RequestMethod.POST)
    @ApiMethod(name = "deleteAuth",desc = "删除权限信息")
    @ApiParam(name = "authId",desc = "权限信息",required = true)
    public Result<Void> deleteAuth(@NotNull String authId) {
        AuthService.deleteAuth(authId);
        return Result.ok();
    }

    @RequestMapping(path="/findOneAuth",method = RequestMethod.POST)
    @ApiMethod(name = "findOneAuth",desc = "删除权限信息")
    @ApiParam(name = "authId",desc = "权限信息",required = true)
    public Result<PipelineAuth> findOneAuth(@NotNull String authId) {
        PipelineAuth oneAuth = AuthService.findOneAuth(authId);
        return Result.ok(oneAuth);
    }


    @RequestMapping(path="/findAllAuth",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuth",desc = "删除权限信息")
    @ApiParam(name = "AuthId",desc = "权限信息",required = true)
    public Result<List<PipelineAuth>> findAllAuth() {
        List<PipelineAuth> allAuth = AuthService.findAllAuth();
        return Result.ok(allAuth);
    }
    
}
