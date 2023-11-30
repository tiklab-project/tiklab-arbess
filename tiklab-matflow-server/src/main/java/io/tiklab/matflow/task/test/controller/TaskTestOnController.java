package io.tiklab.matflow.task.test.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.test.model.TestOnRepository;
import io.tiklab.matflow.task.test.model.TestOnTestPlan;
import io.tiklab.matflow.task.test.service.TaskTestOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线集成Teston控制器
 */

@RestController
@RequestMapping("/testOnAuthorize")
public class TaskTestOnController {

    @Autowired
    TaskTestOnService taskTestOnService;

    /**
     * @pi.name:查询所有测试仓库
     * @pi.path:/testOnAuthorize/findAllRepository
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findAllRepository",method = RequestMethod.POST)
    public Result<List<TestOnRepository>> findAllRepository(@NotNull String authId){

        List<TestOnRepository> allRepository = taskTestOnService.findAllRepository(authId);

        return Result.ok(allRepository);
    }

    /**
     * @pi.name:查询测试信息环境
     * @pi.path:/testOnAuthorize/findAllEnv
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=rpyId;dataType=string;value=rpyId;
     * @pi.param: name=env;dataType=string;value=env;
     */
    @RequestMapping(path="/findAllEnv",method = RequestMethod.POST)
    public Result<List<Object>> findAllBranch(@NotNull String authId,String rpyId,String env){

        List<Object> allEnv = taskTestOnService.findAllEnv(authId, rpyId,env);

        return Result.ok(allEnv);
    }


    /**
     * @pi.name:查询测试信息测试计划
     * @pi.path:/testOnAuthorize/findAllEnv
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=rpyId;dataType=string;value=rpyId;
     */
    @RequestMapping(path="/findAllTestPlan",method = RequestMethod.POST)
    public Result<List<TestOnTestPlan>> findAllBranch(@NotNull String authId,String rpyId){

        List<TestOnTestPlan> allTestPlan = taskTestOnService.findAllTestPlan(authId, rpyId);

        return Result.ok(allTestPlan);
    }


}
