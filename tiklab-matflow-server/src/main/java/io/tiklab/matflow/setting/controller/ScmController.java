package io.tiklab.matflow.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
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
 * @pi.groupName: 流水线环境配置控制器
 */
@RestController
@RequestMapping("/scm")
public class ScmController {

    @Autowired
    ScmService scmService;

    /**
     * @pi.name:创建流水线环境配置信息
     * @pi.path:/scm/createPipelineScm
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=scm
     */
    @RequestMapping(path="/createPipelineScm",method = RequestMethod.POST)
    public Result<String> createPipelineScm(@RequestBody @Valid Scm scm) {
        String pipelineScmId = scmService.createPipelineScm(scm);
        return Result.ok(pipelineScmId);
    }

    /**
     * @pi.name:删除流水线环境配置信息
     * @pi.path:/scm/deletePipelineScm
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=scmId;dataType=string;value=scmId;
     */
    @RequestMapping(path="/deletePipelineScm",method = RequestMethod.POST)
    public Result<Void> deletePipelineScm(@NotNull @Valid String scmId) {
        scmService.deletePipelineScm(scmId);
        return Result.ok();
    }

    /**
     * @pi.name:更新流水线环境配置信息
     * @pi.path:/scm/updatePipelineScm
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=scm
     */
    @RequestMapping(path="/updatePipelineScm",method = RequestMethod.POST)
    public Result<Void> updatePipelineScm(@RequestBody @NotNull @Valid Scm scm) {
       scmService.updatePipelineScm(scm);
       return Result.ok();
    }

    /**
     * @pi.name:查询单个流水线环境配置信息
     * @pi.path:/scm/findPipelineScm
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=scmId;dataType=string;value=scmId;
     */
    @RequestMapping(path="/findPipelineScm",method = RequestMethod.POST)
    public Result<Scm> findPipelineScm(@NotNull @Valid String scmId) {
        Scm scm = scmService.findOnePipelineScm(scmId);
        return Result.ok(scm);
    }

    /**
     * @pi.name:查询所有流水线环境配置信息
     * @pi.path:/auth/findAllPipelineScm
     * @pi.method:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllPipelineScm",method = RequestMethod.POST)
    public Result<List<Scm>> findAllPipelineScm() {
        List<Scm> allScm = scmService.findAllPipelineScm();
        return Result.ok(allScm);
    }

}
