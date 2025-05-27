package io.tiklab.arbess.task.test.controller;

import io.tiklab.arbess.task.test.model.RelevanceTestOn;
import io.tiklab.arbess.task.test.model.RelevanceTestOnQuery;
import io.tiklab.arbess.task.test.service.RelevanceTestOnService;
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
 * @pi.groupName: 流水线集成Teston实例控制器
 */

@RestController
@RequestMapping("/testOnRelevance")
public class RelevanceTestOnController {

    @Autowired
    RelevanceTestOnService relevanceTestOnService;

    /**
     * @pi.name:分页查询流水线测试信息执行实例
     * @pi.url:/testOnRelevance/findAllRelevancePage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=relevanceTestOnQuery
     */
    @RequestMapping(path="/findRelevancePage",method = RequestMethod.POST)
    public Result<Pagination<RelevanceTestOn>> findRelevancePage(@RequestBody @Valid @NotNull RelevanceTestOnQuery relevanceTestOnQuery){

        Pagination<RelevanceTestOn> allRelevance = relevanceTestOnService.findRelevancePage(relevanceTestOnQuery);

        return Result.ok(allRelevance);
    }

    /**
     * @pi.name:删除测试信息执行实例
     * @pi.url:/testOnRelevance/deleteRelevance
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=relevanceId;dataType=string;value=relevanceId;
     */
    @RequestMapping(path="/deleteRelevance",method = RequestMethod.POST)
    public Result<Void> deleteRelevance(@NotNull String relevanceId){

        relevanceTestOnService.deleteRelevance(relevanceId);

        return Result.ok();
    }





}
