package io.tiklab.matflow.home.controller;

import io.tiklab.matflow.home.service.PipelineHomeService;
import io.tiklab.postin.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipelineHome")
@Api(name = "PipelineHomeController",desc = "pipelineHome")
public class PipelineHomeController {

    @Autowired
    PipelineHomeService homeService;



}
































