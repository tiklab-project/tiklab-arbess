package com.doublekit.pipeline.execute.controller.git;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.execute.service.codeGit.CodeCitLabApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gitHub")
@Api(name = "GitLabApiController",desc = "gitHub")
public class GitHubApiController {

    @Autowired
    CodeCitLabApiService codeCitLabApiService;

    @RequestMapping(path="/getCode",method = RequestMethod.POST)
    @ApiMethod(name = "getCode",desc = "返回获取code的Url")
    public Result<String> getCode(){
        String code = codeCitLabApiService.getCode();
        return Result.ok(code);
    }


    @RequestMapping(path="/getAccessToken",method = RequestMethod.POST)
    @ApiMethod(name = "getAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<String> getAccessToken(String code){
        String s = codeCitLabApiService.getAccessToken(code);
        return Result.ok(s);
    }

    @RequestMapping(path="/getUserMessage",method = RequestMethod.POST)
    @ApiMethod(name = "getUserMessage",desc = "返回获取code")
    @ApiParam(name = "accessToken",desc = "accessToken",required = true)
    public Result<String> getUserMessage(String accessToken){
        String s = codeCitLabApiService.getUserMessage(accessToken);
        return Result.ok(s);
    }

    @RequestMapping(path="/getAllStorehouse",method = RequestMethod.POST)
    @ApiMethod(name = "getAllStorehouse",desc = "获取用户的存储库")
    @ApiParam(name = "accessToken",desc = "accessToken",required = true)
    public Result<Map<String, String>> getAllStorehouse(String accessToken){
        Map<String, String> map = codeCitLabApiService.getAllStorehouse(accessToken);
        return Result.ok(map);
    }

    @RequestMapping(path="/getBranch",method = RequestMethod.POST)
    @ApiMethod(name = "getAllStorehouse",desc = "获取用户的存储库")
    @ApiParam(name = "accessToken",desc = "accessToken",required = true)
    public Result<Map<String, String>> getBranch(String accessToken,String name){
        List<String> branchList = codeCitLabApiService.getBranch(accessToken, name);
        return Result.ok(branchList);
    }


}
