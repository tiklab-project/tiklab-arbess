package net.tiklab.matflow.home.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.home.service.PipelineHomeService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
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
































