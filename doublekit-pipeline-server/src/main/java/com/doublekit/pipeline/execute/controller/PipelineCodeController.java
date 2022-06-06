package com.doublekit.pipeline.execute.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.execute.service.PipelineCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineCode")
@Api(name = "PipelineCodeController",desc = "pipelineCode")
public class PipelineCodeController {


    @Autowired
    PipelineCodeService pipelineCodeService;

    @RequestMapping(path="/testPass",method = RequestMethod.POST)
    @ApiMethod(name = "testPass",desc = "测试联通性")
    @ApiParam(name = "url",desc = "代码源地址",required = true)
    public Result<Boolean> testPass(@NotNull String url, String proofId) {
        Boolean pass = pipelineCodeService.checkAuth(url, proofId);
        return Result.ok(pass);
    }
}
