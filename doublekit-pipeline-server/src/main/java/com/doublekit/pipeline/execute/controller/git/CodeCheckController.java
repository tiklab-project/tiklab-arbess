package com.doublekit.pipeline.execute.controller.git;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.execute.model.CodeGit.CodeCheckAuth;
import com.doublekit.pipeline.execute.service.codeGit.CodeCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/codeCheck")
@Api(name = "codeCheckController",desc = "codeCheck")
public class CodeCheckController {

    @Autowired
    CodeCheckService codeCheckService;

    @RequestMapping(path="/checkAuth",method = RequestMethod.POST)
    @ApiMethod(name = "checkAuth",desc = "验证联通性")
    @ApiParam(name = "url",desc = "code地址",required = true)
    public Result<Boolean> checkAuth( @RequestBody @Valid @NotNull CodeCheckAuth codeCheckAuth){
        Boolean checkAuth = codeCheckService.checkAuth(codeCheckAuth);
        return Result.ok(checkAuth);
    }


}
