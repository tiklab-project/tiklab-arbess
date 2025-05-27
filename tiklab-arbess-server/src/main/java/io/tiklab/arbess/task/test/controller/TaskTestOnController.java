package io.tiklab.arbess.task.test.controller;

import io.tiklab.arbess.task.test.model.*;
import io.tiklab.arbess.task.test.service.TaskTestOnService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线集成Teston控制器
 */

@RestController
@RequestMapping("/testHubo/message")
public class TaskTestOnController {

    @Autowired
    TaskTestOnService taskTestOnService;

    /**
     * @pi.name:查询所有测试仓库
     * @pi.url:/testOnAuthorize/findAllRepository
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findRepositoryList",method = RequestMethod.POST)
    public Result<List<TestHuboRpy>> findRepositoryList( @RequestBody @Valid @NotNull TestHuboRpyQuery rpyQuery){

        List<TestHuboRpy> allRepository = taskTestOnService.findRepositoryList(rpyQuery);

        return Result.ok(allRepository);
    }


    @RequestMapping(path="/findRepositoryPage",method = RequestMethod.POST)
    public Result<Pagination<TestHuboRpy>> findRepositoryPage( @RequestBody @Valid @NotNull TestHuboRpyQuery rpyQuery){

        Pagination<TestHuboRpy> allRepository = taskTestOnService.findRepositoryPage(rpyQuery);

        return Result.ok(allRepository);
    }


    /**
     * @pi.name:获取测试计划需要的环境
     * @pi.url:/testOnAuthorize/findAllEnv
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=testPlanId;dataType=string;value=testPlanId
     */
    @RequestMapping(path="/findEnvList",method = RequestMethod.POST)
    public Result<List<TestHuboEnv>> findEnvList(@RequestBody @Valid @NotNull TestHuboEnvQuery  envQuery){

        List<TestHuboEnv> planEnv = taskTestOnService.findEnvList(envQuery);

        return Result.ok(planEnv);
    }


    @RequestMapping(path="/findEnvPage",method = RequestMethod.POST)
    public Result<Pagination<TestHuboEnv>> findEnvPage(@RequestBody @Valid @NotNull TestHuboEnvQuery  envQuery){

        Pagination<TestHuboEnv> planEnv = taskTestOnService.findEnvPage(envQuery);

        return Result.ok(planEnv);
    }


    /**
     * @pi.name:查询测试信息测试计划
     * @pi.url:/testOnAuthorize/findAllTestPlan
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=rpyId;dataType=string;value=rpyId;
     */
    @RequestMapping(path="/findTestPlanList",method = RequestMethod.POST)
    public Result<List<TestHuboTestPlan>> findTestPlanList(@RequestBody @Valid @NotNull TestHuboTestPlanQuery testPlanQuery){

        List<TestHuboTestPlan> allTestPlan = taskTestOnService.findTestPlanList(testPlanQuery);

        return Result.ok(allTestPlan);
    }


    @RequestMapping(path="/findTestPlanPage",method = RequestMethod.POST)
    public Result<List<TestHuboTestPlan>> findTestPlanPage(@RequestBody @Valid @NotNull TestHuboTestPlanQuery testPlanQuery){

        Pagination<TestHuboTestPlan> allTestPlan = taskTestOnService.findTestPlanPage(testPlanQuery);

        return Result.ok(allTestPlan);
    }


}
