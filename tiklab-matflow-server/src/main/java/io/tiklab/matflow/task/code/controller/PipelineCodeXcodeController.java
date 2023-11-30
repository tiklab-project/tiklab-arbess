package io.tiklab.matflow.task.code.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.code.model.XcodeBranch;
import io.tiklab.matflow.task.code.model.XcodeRepository;
import io.tiklab.matflow.task.code.service.TaskCodeXcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线集成Xcode控制器
 */
@RestController
@RequestMapping("/xcodeAuthorize")
public class PipelineCodeXcodeController {

    @Autowired
    TaskCodeXcodeService taskCodeXcodeService;

    /**
     * @pi.name:获取xcode所有仓库
     * @pi.path:/xcodeAuthorize/findAllRepository
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findAllRepository",method = RequestMethod.POST)
    public Result< List<XcodeRepository>> findAllRepository(@NotNull String authId){

        List<XcodeRepository> allRepository = taskCodeXcodeService.findAllRepository(authId);

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
    @RequestMapping(path="/findAllBranch",method = RequestMethod.POST)
    public Result< List<XcodeBranch>> findAllBranch(@NotNull String authId,String rpyId){

        List<XcodeBranch> allRepository = taskCodeXcodeService.findAllBranch(authId,rpyId);

        return Result.ok(allRepository);
    }



}
