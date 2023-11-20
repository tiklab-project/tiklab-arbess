package io.tiklab.matflow.task.test.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.task.test.model.MavenTest;
import io.tiklab.matflow.task.test.model.MavenTestQuery;
import io.tiklab.matflow.task.test.model.RelevanceTestOn;
import io.tiklab.matflow.task.test.model.RelevanceTestOnQuery;
import io.tiklab.matflow.task.test.service.MavenTestService;
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
@RequestMapping("/maven/test")
public class MavenTestController {

    @Autowired
    MavenTestService mavenTestService;


    /**
     * @pi.name:条件查询流水线测试信息执行实例
     * @pi.path:/testOnRelevance/findAllRelevancePage
     * @pi.method:post
     * @pi.request-type:json
     * @pi.param: model=relevanceTestOnQuery
     */
    @RequestMapping(path="/findMavenTestList",method = RequestMethod.POST)
    public Result<List<MavenTest>> findMavenTestList(@RequestBody @Valid @NotNull MavenTestQuery testQuery){

        List<MavenTest> mavenTestList = mavenTestService.findMavenTestList(testQuery);

        return Result.ok(mavenTestList);
    }


    @RequestMapping(path="/findMavenTestPage",method = RequestMethod.POST)
    public Result<Pagination<MavenTest>> findMavenTestPage(@RequestBody @Valid @NotNull MavenTestQuery testQuery){

        Pagination<MavenTest> mavenTestList = mavenTestService.findMavenTestPage(testQuery);

        return Result.ok(mavenTestList);
    }


    @RequestMapping(path="/deleteMavenTest",method = RequestMethod.POST)
    public Result<Void> deleteMavenTest(@NotNull String testId){

        mavenTestService.deleteMavenTest(testId);

        return Result.ok();
    }



}