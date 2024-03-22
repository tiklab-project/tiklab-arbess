package io.thoughtware.matflow.support.util.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.support.util.service.PipelineDataService;
import io.thoughtware.matflow.support.util.service.PipelineUpTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/update/pipeline")
public class PipelineUpTypeController {

    @Autowired
    PipelineUpTypeService upTypeService;



    @RequestMapping(path="/updatePipelineTypeList",method = RequestMethod.POST)
    public Result<String> updatePipelineTypeList() {
        upTypeService.updatePipelineTypeList();
        return Result.ok();
    }

    @RequestMapping(path="/updatePipelineType",method = RequestMethod.POST)
    public Result<String> updatePipelineType(@NotNull String pipelineId) {
        upTypeService.updatePipelineType(pipelineId);
        return Result.ok();
    }






}
