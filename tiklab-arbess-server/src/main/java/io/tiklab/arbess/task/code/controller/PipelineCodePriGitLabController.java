package io.tiklab.arbess.task.code.controller;

import io.tiklab.arbess.task.code.model.ThirdBranch;
import io.tiklab.arbess.task.code.model.ThirdHouse;
import io.tiklab.arbess.task.code.model.ThirdQuery;
import io.tiklab.arbess.task.code.service.TaskCodeGitLabService;
import io.tiklab.arbess.task.code.service.TaskCodePriGitLabService;
import io.tiklab.core.Result;
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
 * @pi.groupName: 流水线集成gitlab控制器
 */
@RestController
@RequestMapping("/code/third/pri/v4/gitlab")
public class PipelineCodePriGitLabController {

    @Autowired
    TaskCodePriGitLabService priGitLabService;

    /**
     * @pi.name:获取自建GitLab所有仓库
     * @pi.url:/code/third/gitlab/findStoreHouseList
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findStoreHouseList",method = RequestMethod.POST)
    public Result<List<ThirdHouse>> findStoreHouseList(@RequestBody @Valid @NotNull ThirdQuery thirdQuery){

        List<ThirdHouse> allRepository = priGitLabService.findStoreHouseList(thirdQuery);

        return Result.ok(allRepository);
    }


    /**
     * @pi.name:获取仓库分支信息
     * @pi.url:/code/third/gitlab/findHouseBranchList
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=rpyId;dataType=string;value=rpyId;
     */
    @RequestMapping(path="/findHouseBranchList",method = RequestMethod.POST)
    public Result<List<ThirdBranch>> findHouseBranchList(@RequestBody @Valid @NotNull ThirdQuery thirdQuery){

        List<ThirdBranch> allRepository = priGitLabService.findStoreBranchList(thirdQuery);

        return Result.ok(allRepository);
    }



}
