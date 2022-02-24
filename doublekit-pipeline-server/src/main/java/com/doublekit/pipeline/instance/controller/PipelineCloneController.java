package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;

import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.instance.service.PipelineCloneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineClone")
@Api(name = "PipelineCloneController",desc = "流水线历史")
public class PipelineCloneController {

    @Autowired
    PipelineCloneService pipelineCloneService;


    private static Logger logger = LoggerFactory.getLogger(PipelineCloneController.class);

    //查询所有
    @RequestMapping(path="/gitClone",method = RequestMethod.POST)
    @ApiMethod(name = "gitClone",desc = "克隆代码")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> gitClone(@NotNull String pipelineId) throws Exception {

      pipelineCloneService.gitClone(pipelineId);

        return Result.ok();
    }
}
