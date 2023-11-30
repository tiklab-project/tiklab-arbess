package io.tiklab.matflow.task.code.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.code.service.TaskCodeThirdService;
import io.tiklab.matflow.setting.model.AuthThird;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线集成Gitee,Github控制器
 */
@RestController
@RequestMapping("/codeAuthorize")
public class PipelineCodeThirdController {

    @Autowired
    TaskCodeThirdService taskCodeThirdService;

    /**
     * @pi.name:获取第三方临时授权码
     * @pi.path:/codeAuthorize/findCode
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=authThird
     */
    @RequestMapping(path="/findCode",method = RequestMethod.POST)
    public Result<String> getCode(@RequestBody @Valid @NotNull AuthThird authThird){

        String git = taskCodeThirdService.findCode(authThird);

        return Result.ok(git);
    }

    /**
     * @pi.name:获取第三方永久授权码
     * @pi.path:/codeAuthorize/findAccessToken
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=authThird
     */
    @RequestMapping(path="/findAccessToken",method = RequestMethod.POST)
    public Result<String> getAccessToken(@RequestBody @Valid @NotNull AuthThird authThird) throws IOException {
        String proofId = taskCodeThirdService.findAccessToken(authThird);
        return Result.ok(proofId);
    }

    /**
     * @pi.name:查询第三方仓库信息
     * @pi.path:/codeAuthorize/findAllStorehouse
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path="/findAllStorehouse",method = RequestMethod.POST)
    public Result<List<String>> findAllStorehouse(@NotNull String authId) {
        List<String> allStorehouse = taskCodeThirdService.findAllStorehouse(authId);
        return Result.ok(allStorehouse);
    }

    /**
     * @pi.name:查询第三方仓库分支信息
     * @pi.path:/codeAuthorize/findBranch
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     * @pi.param: name=houseName;dataType=string;value=仓库名称;
     */
    @RequestMapping(path="/findBranch",method = RequestMethod.POST)
    public Result<List<String>> findBranch(@NotNull String authId,String houseName){
        List<String> branch = taskCodeThirdService.findBranch(authId,houseName);
        return Result.ok(branch);
    }

    /**
     * @pi.name:获取第三方授权回调地址
     * @pi.path:/codeAuthorize/callbackUrl
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=callbackUrl;dataType=string;value=callbackUrl;
     */
    @RequestMapping(path="/callbackUrl",method = RequestMethod.POST)
    public Result<String> getState(@NotNull  String callbackUrl){
        String url= taskCodeThirdService.callbackUrl(callbackUrl);
        return Result.ok(url);
    }


}


























