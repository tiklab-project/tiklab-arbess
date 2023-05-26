package io.tiklab.matflow.task.test.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.test.model.TestOnRepository;
import io.tiklab.matflow.task.test.model.TestOnTestPlan;
import io.tiklab.matflow.task.test.service.TaskTestOnService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/testOnAuthorize")
@Api(name = "TaskTestOnController",desc = "teston")
public class TaskTestOnController {


    @Autowired
    private TaskTestOnService taskTestOnService;


    @RequestMapping(path="/findAllRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepository",desc = "获取所有仓库")
    @ApiParam(name = "authId",desc = "回调地址",required = true)
    public Result<List<TestOnRepository>> findAllRepository(@NotNull String authId){

        List<TestOnRepository> allRepository = taskTestOnService.findAllRepository(authId);

        return Result.ok(allRepository);
    }

    @RequestMapping(path="/findAllEnv",method = RequestMethod.POST)
    @ApiMethod(name = "findAllEnv",desc = "获取所有环境")
    @ApiParam(name = "authId",desc = "回调地址",required = true)
    public Result<List<Object>> findAllBranch(@NotNull String authId,String rpyId,String env){

        List<Object> allEnv = taskTestOnService.findAllEnv(authId, rpyId,env);

        return Result.ok(allEnv);
    }



    @RequestMapping(path="/findAllTestPlan",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTestPlan",desc = "获取所有测试计划")
    @ApiParam(name = "authId",desc = "回调地址",required = true)
    public Result<List<TestOnTestPlan>> findAllBranch(@NotNull String authId,String rpyId){

        List<TestOnTestPlan> allTestPlan = taskTestOnService.findAllTestPlan(authId, rpyId);

        return Result.ok(allTestPlan);
    }


}
