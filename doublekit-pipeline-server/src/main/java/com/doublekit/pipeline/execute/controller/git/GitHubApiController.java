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

@RestController
@RequestMapping("/gitHub")
@Api(name = "GitLabApiController",desc = "gitHub")
public class GitHubApiController {

    @Autowired
    CodeCitLabApiService codeCitLabApiService;

    @RequestMapping(path="/getCode",method = RequestMethod.POST)
    @ApiMethod(name = "getCode",desc = "返回获取code的Url")
    public Result<String> getCode(){
        String code = codeCitLabApiService.code();
        return Result.ok(code);
    }


    @RequestMapping(path="/getAccessToken",method = RequestMethod.POST)
    @ApiMethod(name = "getAccessToken",desc = "获取accessToken")
    @ApiParam(name = "code",desc = "code",required = true)
    public Result<String> getAccessToken(String code){
        String s = codeCitLabApiService.accessToken(code);
        return Result.ok(s);
    }

    @RequestMapping(path="/codeSpace",method = RequestMethod.POST)
    @ApiMethod(name = "codeSpace",desc = "返回获取code")
    @ApiParam(name = "accessToken",desc = "accessToken",required = true)
    public Result<String> codeSpace(String accessToken){
        String s = codeCitLabApiService.codeSpace(accessToken);
        return Result.ok(s);
    }


}
