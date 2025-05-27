package io.tiklab.arbess.setting.k8s.controller;

import io.tiklab.arbess.setting.k8s.model.Kubectl;
import io.tiklab.arbess.setting.k8s.model.KubectlQuery;
import io.tiklab.arbess.setting.k8s.service.KubectlService;
import io.tiklab.core.Result;
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
 * @pi.groupName: 流水线k8s认证控制器
 */
@RestController
@RequestMapping("/kubectl")
public class KubectlController {

    @Autowired
    KubectlService kubectl;

    /**
     * @pi.name:创建k8s认证信息
     * @pi.url:/kubectl/createKubectl
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=kubectl
     */
    @RequestMapping(path="/createKubectl",method = RequestMethod.POST)
    public Result<String> createPipelineKubectl(@RequestBody @Valid Kubectl kubectl) {
        String pipelineKubectlId = this.kubectl.createKubectl(kubectl);
        return Result.ok(pipelineKubectlId);
    }

    /**
     * @pi.name:删除k8s认证信息
     * @pi.url:/kubectl/deleteKubectl
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=id;dataType=string;value=id;
     */
    @RequestMapping(path="/deleteKubectl",method = RequestMethod.POST)
    public Result<Void> deletePipelineKubectl(@NotNull @Valid String id) {
        kubectl.deleteKubectl(id);
        return Result.ok();
    }

    /**
     * @pi.name:更新k8s认证信息
     * @pi.url:/kubectl/updateKubectl
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=kubectl
     */
    @RequestMapping(path="/updateKubectl",method = RequestMethod.POST)
    public Result<Void> updatePipelineKubectl(@RequestBody @NotNull @Valid Kubectl kubectl) {
        this.kubectl.updateKubectl(kubectl);
        return Result.ok();
    }

    /**
     * @pi.name:查询单个k8s认证信息
     * @pi.url:/kubectl/findKubectl
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param: name=id;dataType=string;value=id;
     */
    @RequestMapping(path="/findKubectl",method = RequestMethod.POST)
    public Result<Kubectl> findPipelineKubectl(@NotNull @Valid String id) {
        Kubectl kubectl = this.kubectl.findOneKubectl(id);
        return Result.ok(kubectl);
    }

    /**
     * @pi.name:查询k8s认证信息
     * @pi.url:/kubectl/findAllKubectlList
     * @pi.methodType:post
     * @pi.request-type:formdata
     * @pi.param:name=type;dataType=string;value=aliyun;
     */
    @RequestMapping(path="/findKubectlList",method = RequestMethod.POST)
    public Result<List<Kubectl>> findKubectlList(@RequestBody @Valid @NotNull KubectlQuery hostQuery) {
        List<Kubectl> allKubectl = kubectl.findKubectlList(hostQuery);
        return Result.ok(allKubectl);
    }

    /**
     * @pi.name:分页查询k8s认证信息
     * @pi.url:/kubectl/findKubectlPage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param:model=hostQuery;
     */
    @RequestMapping(path="/findKubectlPage",method = RequestMethod.POST)
    public Result<Pagination<Kubectl>> findKubectlPage(@RequestBody @Valid @NotNull KubectlQuery hostQuery) {
        Pagination<Kubectl> allKubectl = kubectl.findKubectlPage(hostQuery);
        return Result.ok(allKubectl);
    }



}
