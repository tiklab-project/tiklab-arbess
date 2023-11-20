package io.tiklab.matflow.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.setting.model.AuthHost;
import io.tiklab.matflow.setting.model.AuthHostQuery;
import io.tiklab.matflow.setting.model.Env;
import io.tiklab.matflow.setting.model.EnvQuery;
import io.tiklab.matflow.setting.service.AuthHostService;
import io.tiklab.matflow.setting.service.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @pi.protocol: http
 * @pi.groupName: 流水线主机认证控制器
 */
@RestController
@RequestMapping("/env")
public class EnvController {

    @Autowired
    EnvService envService;

    @RequestMapping(path="/createEnv",method = RequestMethod.POST)
    public Result<String> createEnv(@RequestBody @Valid Env env) {
        String pipelineAuthHostId = envService.createEnv(env);
        return Result.ok(pipelineAuthHostId);
    }


    @RequestMapping(path="/deleteEnv",method = RequestMethod.POST)
    public Result<Void> deleteEnv(@NotNull @Valid String envId) {
        envService.deleteEnv(envId);
        return Result.ok();
    }


    @RequestMapping(path="/updateEnv",method = RequestMethod.POST)
    public Result<Void> updateEnv(@RequestBody @NotNull @Valid Env env) {
        this.envService.updateEnv(env);
        return Result.ok();
    }


    @RequestMapping(path="/findEnvPage",method = RequestMethod.POST)
    public Result<Pagination<Env>> findEnvPage( @RequestBody @Valid @NotNull EnvQuery envQuery) {
        Pagination<Env> envPage = envService.findEnvPage(envQuery);
        return Result.ok(envPage);
    }

    @RequestMapping(path="/findEnvList",method = RequestMethod.POST)
    public Result<List<Env>> findEnvList( @RequestBody @Valid @NotNull EnvQuery envQuery) {
        List<Env> envList = envService.findEnvList(envQuery);
        return Result.ok(envList);
    }

}
