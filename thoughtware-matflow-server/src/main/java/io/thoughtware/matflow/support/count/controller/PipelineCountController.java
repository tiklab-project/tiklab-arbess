package io.thoughtware.matflow.support.count.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.support.count.model.PipelineRunCount;
import io.thoughtware.matflow.support.count.model.PipelineRunCountQuery;
import io.thoughtware.matflow.support.count.model.PipelineRunDayCount;
import io.thoughtware.matflow.support.count.model.PipelineRunResultCount;
import io.thoughtware.matflow.support.count.service.PipelineCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipeline/count")
public class PipelineCountController {

    @Autowired
    PipelineCountService countService;


    @RequestMapping(path="/findPipelineRunTimeSpan",method = RequestMethod.POST)
    public Result<List<PipelineRunDayCount>> findPipelineRunTimeSpan(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        List<PipelineRunDayCount> runDayCounts = countService.findPipelineRunTimeSpan(countQuery);
        return Result.ok(runDayCounts);
    }

    @RequestMapping(path="/findPipelineRunCount",method = RequestMethod.POST)
    public Result<List<PipelineRunCount>> findPipelineRunCount(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        List<PipelineRunCount> runDayCounts = countService.findPipelineRunCount(countQuery);
        return Result.ok(runDayCounts);
    }

    @RequestMapping(path="/findPipelineRunResultCount",method = RequestMethod.POST)
    public Result<List<PipelineRunResultCount>> findPipelineRunResultCount(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        List<PipelineRunResultCount> runDayCounts = countService.findPipelineRunResultCount(countQuery);
        return Result.ok(runDayCounts);
    }

}
















