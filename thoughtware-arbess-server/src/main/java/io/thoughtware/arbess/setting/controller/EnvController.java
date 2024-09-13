package io.thoughtware.arbess.setting.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.arbess.setting.model.Env;
import io.thoughtware.arbess.setting.model.EnvQuery;
import io.thoughtware.arbess.setting.service.EnvService;
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
 * @pi.groupName: 流水线环境控制器
 */
@RestController
@RequestMapping("/env")
public class EnvController {

    @Autowired
    EnvService envService;


    /**
     * @pi.name:创建流水线环境
     * @pi.path:/env/createEnv
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param:  model=env;
     */
    @RequestMapping(path="/createEnv",method = RequestMethod.POST)
    public Result<String> createEnv(@RequestBody @Valid Env env) {
        String pipelineAuthHostId = envService.createEnv(env);
        return Result.ok(pipelineAuthHostId);
    }

    /**
     * @pi.name:删除流水线环境
     * @pi.path:/env/deleteEnv
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=envId;dataType=string;value=环境id;
     */
    @RequestMapping(path="/deleteEnv",method = RequestMethod.POST)
    public Result<Void> deleteEnv(@NotNull @Valid String envId) {
        envService.deleteEnv(envId);
        return Result.ok();
    }

    /**
     * @pi.name:创建流水线环境
     * @pi.path:/env/updateEnv
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param:  model=env;
     */
    @RequestMapping(path="/updateEnv",method = RequestMethod.POST)
    public Result<Void> updateEnv(@RequestBody @NotNull @Valid Env env) {
        this.envService.updateEnv(env);
        return Result.ok();
    }


    /**
     * @pi.name:条件查询流水线环境
     * @pi.path:/env/findEnvPage
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param:model=envQuery;
     */
    @RequestMapping(path="/findEnvPage",method = RequestMethod.POST)
    public Result<Pagination<Env>> findEnvPage( @RequestBody @Valid @NotNull EnvQuery envQuery) {
        Pagination<Env> envPage = envService.findEnvPage(envQuery);
        return Result.ok(envPage);
    }

    /**
     * @pi.name:分页查询流水线环境
     * @pi.path:/env/findEnvList
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param:model=envQuery;
     */
    @RequestMapping(path="/findEnvList",method = RequestMethod.POST)
    public Result<List<Env>> findEnvList( @RequestBody @Valid @NotNull EnvQuery envQuery) {
        List<Env> envList = envService.findEnvList(envQuery);
        return Result.ok(envList);
    }

}
