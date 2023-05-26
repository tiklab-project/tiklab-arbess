package io.tiklab.matflow.task.code.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.code.model.XcodeBranch;
import io.tiklab.matflow.task.code.model.XcodeRepository;
import io.tiklab.matflow.task.code.service.TaskCodeXcodeService;
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
@RequestMapping("/xcodeAuthorize")
@Api(name = "PipelineCodeThirdController",desc = "git")
public class PipelineCodeXcodeController {

    @Autowired
    private TaskCodeXcodeService taskCodeXcodeService;


    @RequestMapping(path="/findAllRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepository",desc = "获取所有仓库")
    @ApiParam(name = "authId",desc = "回调地址",required = true)
    public Result< List<XcodeRepository>> findAllRepository(@NotNull String authId){

        List<XcodeRepository> allRepository = taskCodeXcodeService.findAllRepository(authId);

        return Result.ok(allRepository);
    }



    @RequestMapping(path="/findAllBranch",method = RequestMethod.POST)
    @ApiMethod(name = "findAllBranch",desc = "获取所有仓库")
    @ApiParam(name = "authId",desc = "回调地址",required = true)
    public Result< List<XcodeBranch>> findAllBranch(@NotNull String authId,String rpyId){

        List<XcodeBranch> allRepository = taskCodeXcodeService.findAllBranch(authId,rpyId);

        return Result.ok(allRepository);
    }



}
