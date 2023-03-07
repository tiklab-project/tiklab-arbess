package io.tiklab.matflow.home.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.home.service.PipelineHomeService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pipelineHome")
@Api(name = "PipelineHomeController",desc = "pipelineHome")
public class PipelineHomeController {

    @Autowired
    PipelineHomeService homeService;

    @RequestMapping(path="/messageSendType",method = RequestMethod.POST)
    @ApiMethod(name = "messageSendType",desc = "判断是否存在消息配置")
    public Result<List<String>> messageSendType(){

        List<String> list = homeService.messageSendType();

        return Result.ok(list);
    }

}
































