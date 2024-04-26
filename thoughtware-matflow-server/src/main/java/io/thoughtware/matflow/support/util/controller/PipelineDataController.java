package io.thoughtware.matflow.support.util.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.support.util.service.PipelineDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/clean/data")
public class PipelineDataController {

    @Autowired
    PipelineDataService dataService;

    @RequestMapping(path="/cleanMessageData",method = RequestMethod.POST)
    public Result<String> createTrigger() {
        dataService.cleanMessageData();
        return Result.ok();
    }








}
