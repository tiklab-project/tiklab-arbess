package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;

import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.instance.service.GitCloneService;
import com.doublekit.pipeline.instance.service.PipelineLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineLog")
@Api(name = "PipelineLogController",desc = "流水线日志")
public class PipelineLogController {

    @Autowired
    PipelineLogService PipelineLogService;


    private static Logger logger = LoggerFactory.getLogger(PipelineLogController.class);

    //查询所有
    @RequestMapping(path="/findLog",method = RequestMethod.POST)
    @ApiMethod(name = "findLog",desc = "克隆代码")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> findLog(@NotNull String pipelineId) throws Exception {

        String structure = PipelineLogService.structure(pipelineId);

        return Result.ok(structure);
    }
}
