package io.tiklab.matflow.task.test.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.task.test.model.RelevanceTestOn;
import io.tiklab.matflow.task.test.model.RelevanceTestOnQuery;
import io.tiklab.matflow.task.test.service.RelevanceTestOnService;
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
 * @pi.groupName: 流水线集成teston实例控制器
 */

@RestController
@RequestMapping("/testOnRelevance")
public class RelevanceTestOnController {

    @Autowired
    private RelevanceTestOnService relevanceTestOnService;

    /**
     * @pi.name:查询流水线测试信息执行实例
     * @pi.path:/testOnRelevance/findAllRelevance
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/findAllRelevance",method = RequestMethod.POST)
    public Result< List<RelevanceTestOn>> findAllRelevance(@NotNull String pipelineId){

        List<RelevanceTestOn> allRelevance = relevanceTestOnService.findAllRelevance(pipelineId);

        return Result.ok(allRelevance);
    }

    /**
     * @pi.name:条件查询流水线测试信息执行实例
     * @pi.path:/testOnRelevance/findAllRelevancePage
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=relevanceTestOnQuery
     */
    @RequestMapping(path="/findAllRelevancePage",method = RequestMethod.POST)
    public Result<Pagination<RelevanceTestOn>> findAllRelevance(
            @RequestBody @Valid @NotNull RelevanceTestOnQuery relevanceTestOnQuery){

        Pagination<RelevanceTestOn> allRelevance = relevanceTestOnService.findAllRelevancePage(relevanceTestOnQuery);

        return Result.ok(allRelevance);
    }

    /**
     * @pi.name:查询测试信息执行实例
     * @pi.path:/testOnRelevance/deleteRelevance
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=relevanceId;dataType=string;value=relevanceId;
     */
    @RequestMapping(path="/deleteRelevance",method = RequestMethod.POST)
    public Result<Void> deleteRelevance(@NotNull String relevanceId){

        relevanceTestOnService.deleteRelevance(relevanceId);

        return Result.ok();
    }





}
