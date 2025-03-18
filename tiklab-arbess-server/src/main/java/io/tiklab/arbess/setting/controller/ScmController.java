package io.tiklab.arbess.setting.controller;

import io.tiklab.arbess.setting.model.ScmQuery;
import io.tiklab.core.Result;
import io.tiklab.arbess.setting.model.Scm;
import io.tiklab.arbess.setting.service.ScmService;
import io.tiklab.core.page.Pagination;
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
     * @pi.url:/scm/createPipelineScm
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
     * @pi.url:/scm/deletePipelineScm
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
     * @pi.url:/scm/updatePipelineScm
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
     * @pi.url:/scm/findPipelineScm
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
     * @pi.url:/auth/findAllPipelineScm
     * @pi.methodType:post
     * @pi.request-type:none
     */
    @RequestMapping(path="/findAllPipelineScm",method = RequestMethod.POST)
    public Result<List<Scm>> findAllPipelineScm() {
        List<Scm> allScm = scmService.findAllPipelineScm();
        return Result.ok(allScm);
    }

    @RequestMapping(path="/findPipelineScmList",method = RequestMethod.POST)
    public Result<List<Scm>> findPipelineScmList(@RequestBody @Valid @NotNull ScmQuery scmQuery) {
        List<Scm> allScm = scmService.findPipelineScmList(scmQuery);
        return Result.ok(allScm);
    }

    @RequestMapping(path="/findPipelineScmPage",method = RequestMethod.POST)
    public Result<Pagination<Scm>> findPipelineScmPage(@RequestBody @Valid @NotNull ScmQuery scmQuery) {
        Pagination<Scm> allScm = scmService.findPipelineScmPage(scmQuery);
        return Result.ok(allScm);
    }

}
