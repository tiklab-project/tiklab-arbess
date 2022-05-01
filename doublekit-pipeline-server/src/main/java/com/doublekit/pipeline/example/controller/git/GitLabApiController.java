package com.doublekit.pipeline.example.controller.git;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.core.Result;
import com.doublekit.pipeline.example.service.codeGit.CodeCitLabApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gitLab")
@Api(name = "GitLabApiController",desc = "gitLab")
public class GitLabApiController {

    @Autowired
    CodeCitLabApiService codeCitLabApiService;

    @RequestMapping(path="/code",method = RequestMethod.POST)
    @ApiMethod(name = "getCode",desc = "返回获取code")
    public Result<Void> getCode(){
        codeCitLabApiService.code();
        return Result.ok();
    }

}
