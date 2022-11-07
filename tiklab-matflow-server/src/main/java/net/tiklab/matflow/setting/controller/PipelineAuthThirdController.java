package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.setting.service.PipelineAuthThirdServer;
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
@RequestMapping("/authServer")
@Api(name = "PipelineAuthThirdController",desc = "认证")
public class PipelineAuthThirdController {

    @Autowired
    PipelineAuthThirdServer authServerService;


    @RequestMapping(path="/createAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuthServer",desc = "创建")
    @ApiParam(name = "pipelineAuthThird",desc = "配置信息",required = true)
    public Result<String> createPipelineAuthServer(@RequestBody @Valid PipelineAuthThird pipelineAuthThird) {
        String pipelineAuthServerId = authServerService.createAuthServer(pipelineAuthThird);
        return Result.ok(pipelineAuthServerId);
    }

    //删除
    @RequestMapping(path="/deleteAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuthServer",desc = "删除")
    @ApiParam(name = "serverId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuthServer(@NotNull @Valid String serverId) {
        authServerService.deleteAuthServer(serverId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuthServer",desc = "更新")
    @ApiParam(name = "pipelineAuthThird",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuthServer(@RequestBody @NotNull @Valid PipelineAuthThird pipelineAuthThird) {
        authServerService.updateAuthServer(pipelineAuthThird);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findOneAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuthServer",desc = "查询")
    @ApiParam(name = "serverId",desc = "配置id",required = true)
    public Result<PipelineAuthThird> findPipelineAuthServer(@NotNull @Valid String serverId) {
        PipelineAuthThird pipelineAuthThird = authServerService.findOneAuthServer(serverId);
        return Result.ok(pipelineAuthThird);
    }

    //查询所有
    @RequestMapping(path="/findAllAuthServer",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineAuthServer",desc = "查询所有")
    public Result<List<PipelineAuthThird>> findAllPipelineAuthServer() {
        List<PipelineAuthThird> allPipelineAuthThird = authServerService.findAllAuthServer();
        return Result.ok(allPipelineAuthThird);
    }

    @RequestMapping(path="/findAllAuthServerList",method = RequestMethod.POST)
    @ApiMethod(name = "findAllAuthServerList",desc = "查询所有")
    @ApiParam(name = "type",desc = "类型",required = true)
    public Result<List<PipelineAuthThird>> findAllAuthServerList(@NotNull int type) {
        List<PipelineAuthThird> allPipelineAuthThird = authServerService.findAllAuthServerList(type);
        return Result.ok(allPipelineAuthThird);
    }


}


















