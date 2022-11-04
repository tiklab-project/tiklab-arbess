package net.tiklab.matflow.setting.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.setting.model.PipelineAuthHost;
import net.tiklab.matflow.setting.service.PipelineAuthHostServer;
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
@RequestMapping("/authHost")
@Api(name = "PipelineAuthHostController",desc = "主机认证")
public class PipelineAuthHostController {

    @Autowired
    PipelineAuthHostServer authHost;

    //创建
    @RequestMapping(path="/createAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineAuthHost",desc = "创建")
    @ApiParam(name = "pipelineAuthHost",desc = "配置信息",required = true)
    public Result<String> createPipelineAuthHost(@RequestBody @Valid PipelineAuthHost pipelineAuthHost) {
        String pipelineAuthHostId = authHost.createAuthHost(pipelineAuthHost);
        return Result.ok(pipelineAuthHostId);
    }

    //删除
    @RequestMapping(path="/deleteAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineAuthHost",desc = "删除")
    @ApiParam(name = "hostId",desc = "配置id",required = true)
    public Result<Void> deletePipelineAuthHost(@NotNull @Valid String hostId) {
        authHost.deleteAuthHost(hostId);
        return Result.ok();
    }

    //更新
    @RequestMapping(path="/updateAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineAuthHost",desc = "更新")
    @ApiParam(name = "pipelineAuthHost",desc = "配置信息",required = true)
    public Result<Void> updatePipelineAuthHost(@RequestBody @NotNull @Valid PipelineAuthHost pipelineAuthHost) {
        authHost.updateAuthHost(pipelineAuthHost);
        return Result.ok();
    }

    //查询
    @RequestMapping(path="/findAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineAuthHost",desc = "查询")
    @ApiParam(name = "hostId",desc = "配置id",required = true)
    public Result<PipelineAuthHost> findPipelineAuthHost(@NotNull @Valid String hostId) {
        PipelineAuthHost pipelineAuthHost = authHost.findOneAuthHost(hostId);
        return Result.ok(pipelineAuthHost);
    }

    //查询所有
    @RequestMapping(path="/findAllAuthHost",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPipelineAuthHost",desc = "查询所有")
    public Result<List<PipelineAuthHost>> findAllPipelineAuthHost() {
        List<PipelineAuthHost> allPipelineAuthHost = authHost.findAllAuthHost();
        return Result.ok(allPipelineAuthHost);
    }


}
