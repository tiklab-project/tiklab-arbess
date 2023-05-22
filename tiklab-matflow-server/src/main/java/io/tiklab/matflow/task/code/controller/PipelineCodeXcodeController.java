package io.tiklab.matflow.task.code.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.code.service.TaskCodeXcodeService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xcode.branch.model.Branch;
import io.tiklab.xcode.repository.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/xcodeAuthorize")
@Api(name = "PipelineCodeThirdController",desc = "git")
public class PipelineCodeXcodeController {

    @Autowired
    private TaskCodeXcodeService taskCodeXcodeService;


    @RequestMapping(path="/findAllRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepository",desc = "获取所有仓库")
    @ApiParam(name = "authId",desc = "回调地址",required = true)
    public Result< List<Repository>> findAllRepository(@NotNull String authId){

        List<Repository> allRepository = taskCodeXcodeService.findAllRepository(authId);

        return Result.ok(allRepository);
    }



    @RequestMapping(path="/findAllBranch",method = RequestMethod.POST)
    @ApiMethod(name = "findAllBranch",desc = "获取所有仓库")
    @ApiParam(name = "authId",desc = "回调地址",required = true)
    public Result< List<Repository>> findAllBranch(@NotNull String authId,String rpyName){

        List<Branch> allRepository = taskCodeXcodeService.findAllBranch(authId,rpyName);

        return Result.ok(allRepository);
    }



}
