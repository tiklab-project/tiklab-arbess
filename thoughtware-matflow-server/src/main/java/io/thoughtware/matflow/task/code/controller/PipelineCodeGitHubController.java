package io.thoughtware.matflow.task.code.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.task.code.model.*;
import io.thoughtware.matflow.task.code.service.TaskCodeGitHubService;
import io.thoughtware.matflow.task.code.service.TaskCodeGittokService;
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
 * @pi.groupName: 流水线集成github控制器
 */
@RestController
@RequestMapping("/code/third/github")
public class PipelineCodeGitHubController {

    @Autowired
    TaskCodeGitHubService gitHubService;

    /**
     * @pi.name:获取xcode所有仓库
     * @pi.path:/code/third/github/findStoreHouseList
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findStoreHouseList",method = RequestMethod.POST)
    public Result< List<XcodeRepository>> findAllRepository(@RequestBody @Valid @NotNull ThirdQuery thirdQuery){

        List<ThirdHouse> allRepository = gitHubService.findStoreHouseList(thirdQuery);

        return Result.ok(allRepository);
    }


    /**
     * @pi.name:获取仓库分支信息
     * @pi.path:/code/third/github/findHouseBranchList
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=rpyId;dataType=string;value=rpyId;
     */
    @RequestMapping(path="/findHouseBranchList",method = RequestMethod.POST)
    public Result< List<XcodeBranch>> findHouseBranchList(@RequestBody @Valid @NotNull ThirdQuery thirdQuery){

        List<ThirdBranch> allRepository = gitHubService.findStoreBranchList(thirdQuery);

        return Result.ok(allRepository);
    }



}
