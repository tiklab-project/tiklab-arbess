package io.thoughtware.arbess.task.test.controller;

import io.thoughtware.arbess.task.test.model.MavenTest;
import io.thoughtware.arbess.task.test.model.MavenTestQuery;
import io.thoughtware.arbess.task.test.service.MavenTestService;
import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
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
 * @pi.groupName: 流水线单元测试实例控制器
 */

@RestController
@RequestMapping("/maven/test")
public class MavenTestController {

    @Autowired
    MavenTestService mavenTestService;


    /**
     * @pi.name:条件查询流水线测试信息执行实例
     * @pi.path:/maven/test/findMavenTestList
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=testQuery
     */
    @RequestMapping(path="/findMavenTestList",method = RequestMethod.POST)
    public Result<List<MavenTest>> findMavenTestList(@RequestBody @Valid @NotNull MavenTestQuery testQuery){

        List<MavenTest> mavenTestList = mavenTestService.findMavenTestList(testQuery);

        return Result.ok(mavenTestList);
    }

    /**
     * @pi.name:分页查询流水线测试信息执行实例
     * @pi.path:/maven/test/findMavenTestPage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=testQuery
     */
    @RequestMapping(path="/findMavenTestPage",method = RequestMethod.POST)
    public Result<Pagination<MavenTest>> findMavenTestPage(@RequestBody @Valid @NotNull MavenTestQuery testQuery){

        Pagination<MavenTest> mavenTestList = mavenTestService.findMavenTestPage(testQuery);

        return Result.ok(mavenTestList);
    }


    /**
     * @pi.name:删除测试信息执行实例
     * @pi.path:/maven/test/deleteMavenTest
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=testId;dataType=string;value=testId;
     */
    @RequestMapping(path="/deleteMavenTest",method = RequestMethod.POST)
    public Result<Void> deleteMavenTest(@NotNull String testId){

        mavenTestService.deleteMavenTest(testId);

        return Result.ok();
    }



}
