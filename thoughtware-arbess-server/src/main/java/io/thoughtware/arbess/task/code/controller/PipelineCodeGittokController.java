package io.thoughtware.arbess.task.code.controller;

import io.thoughtware.arbess.task.code.model.*;
import io.thoughtware.arbess.task.code.service.TaskCodeGittokService;
import io.thoughtware.core.Result;
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
 * @pi.groupName: 流水线集成Xcode控制器
 */
@RestController
@RequestMapping("/code/third/gittok")
public class PipelineCodeGittokController {

    @Autowired
    TaskCodeGittokService gittokService;

    /**
     * @pi.name:获取xcode所有仓库
     * @pi.path:/xcodeAuthorize/findAllRepository
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findStoreHouseList",method = RequestMethod.POST)
    public Result<List<ThirdHouse>> findStoreHouseList(@RequestBody @Valid @NotNull ThirdQuery thirdQuery){

        List<ThirdHouse> allRepository = gittokService.findStoreHouseList(thirdQuery);

        return Result.ok(allRepository);
    }


    /**
     * @pi.name:获取仓库分支信息
     * @pi.path:/xcodeAuthorize/findAllBranch
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=rpyId;dataType=string;value=rpyId;
     */
    @RequestMapping(path="/findHouseBranchList",method = RequestMethod.POST)
    public Result<List<ThirdBranch>> findHouseBranchList(@RequestBody @Valid @NotNull ThirdQuery thirdQuery){

        List<ThirdBranch> allRepository = gittokService.findHouseBranchList(thirdQuery);

        return Result.ok(allRepository);
    }



}
