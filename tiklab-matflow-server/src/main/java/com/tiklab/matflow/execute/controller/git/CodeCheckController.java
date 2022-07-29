package com.tiklab.matflow.execute.controller.git;


import com.tiklab.core.Result;
import com.tiklab.matflow.execute.model.CodeGit.CodeCheckAuth;
import com.tiklab.matflow.execute.service.codeGit.CodeCheckService;
import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
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
