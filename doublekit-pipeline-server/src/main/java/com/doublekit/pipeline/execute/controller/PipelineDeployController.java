package com.doublekit.pipeline.execute.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.execute.service.PipelineDeployService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineDeploy")
@Api(name = "PipelineDeployController",desc = "pipelineDeploy")
public class PipelineDeployController {

    @Autowired
    PipelineDeployService pipelineDeployService;

    @RequestMapping(path="/testPass",method = RequestMethod.POST)
    @ApiMethod(name = "testPass",desc = "测试联通性")
    @ApiParam(name = "proof",desc = "凭证",required = true)
    public Result<Boolean> testPass( @RequestBody  @NotNull Proof proof) {
        Boolean pass = pipelineDeployService.testSshSftp(proof);
        return Result.ok(pass);
    }

}
