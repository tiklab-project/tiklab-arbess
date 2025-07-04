package io.tiklab.arbess.support.count.controller;

import io.tiklab.arbess.support.count.model.*;
import io.tiklab.arbess.support.count.service.PipelineCountService;
import io.tiklab.arbess.support.count.service.PipelineTestCountService;
import io.tiklab.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipeline/testCount")
public class PipelineTestCountController {

    @Autowired
    PipelineTestCountService countService;


    @RequestMapping(path="/findTestCount",method = RequestMethod.POST)
    public Result<List<PipelineRunDayCount>> findTestCount( @NotNull @Valid String pipelineId){
        PipelineTestCount runDayCounts = countService.findTestCount(pipelineId);
        return Result.ok(runDayCounts);
    }


    @RequestMapping(path="/findTestCodeScanCount",method = RequestMethod.POST)
    public Result<List<PipelineRunDayCount>> findTestCodeScanCount( @NotNull @Valid String pipelineId){
        PipelineTestTestHuboCount runDayCounts = countService.findTestCodeScanCount(pipelineId);
        return Result.ok(runDayCounts);
    }

    @RequestMapping(path="/findTestTestHuboCount",method = RequestMethod.POST)
    public Result<List<PipelineRunDayCount>> findTestTestHuboCount( @NotNull @Valid String pipelineId){
        PipelineTestTestHuboCount runDayCounts = countService.findTestTestHuboCount(pipelineId);
        return Result.ok(runDayCounts);
    }

}
















