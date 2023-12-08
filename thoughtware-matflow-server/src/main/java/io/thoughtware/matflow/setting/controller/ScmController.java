package io.thoughtware.matflow.setting.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.setting.model.Scm;
import io.thoughtware.matflow.setting.service.ScmService;
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
     * @pi.name:创建环境配置信息
     * @pi.path:/scm/createPipelineScm
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=scm
     */
    @RequestMapping(path="/createPipelineScm",method = RequestMethod.POST)
    public Result<String> createPipelineScm(@RequestBody @Valid Scm scm) {
        String pipelineScmId = scmService.createPipelineScm(scm);
        return Result.ok(pipelineScmId);
    }

    /**
     * @pi.name:删除环境配置信息
     * @pi.path:/scm/deletePipelineScm
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=scmId;dataType=string;value=scmId;
     */
    @RequestMapping(path="/deletePipelineScm",method = RequestMethod.POST)
    public Result<Void> deletePipelineScm(@NotNull @Valid String scmId) {
        scmService.deletePipelineScm(scmId);
        return Result.ok();
    }

    /**
     * @pi.name:更新环境配置信息
     * @pi.path:/scm/updatePipelineScm
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=scm
     */
    @RequestMapping(path="/updatePipelineScm",method = RequestMethod.POST)
    public Result<Void> updatePipelineScm(@RequestBody @NotNull @Valid Scm scm) {
       scmService.updatePipelineScm(scm);
       return Result.ok();
    }

    /**
     * @pi.name:查询流水线环境配置信息
     * @pi.path:/scm/findPipelineScm
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=scmId;dataType=string;value=scmId;
     */
    @RequestMapping(path="/findPipelineScm",method = RequestMethod.POST)
    public Result<Scm> findPipelineScm(@NotNull @Valid String scmId) {
        Scm scm = scmService.findOnePipelineScm(scmId);
        return Result.ok(scm);
    }

    /**
     * @pi.name:查询所有环境配置信息
     * @pi.path:/auth/findAllPipelineScm
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllPipelineScm",method = RequestMethod.POST)
    public Result<List<Scm>> findAllPipelineScm() {
        List<Scm> allScm = scmService.findAllPipelineScm();
        return Result.ok(allScm);
    }

}
