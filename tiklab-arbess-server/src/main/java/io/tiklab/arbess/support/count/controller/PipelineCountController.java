package io.tiklab.arbess.support.count.controller;

import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.core.Result;
import io.tiklab.arbess.support.count.model.*;
import io.tiklab.arbess.support.count.model.*;
import io.tiklab.arbess.support.count.service.PipelineCountService;
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

    @RequestMapping(path="/findRunTimeSpan",method = RequestMethod.POST)
    public Result<List<PipelineDayCount>> findRunTimeSpan(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        List<PipelineDayCount> runDayCounts = countService.findRunTimeSpan(countQuery);
        return Result.ok(runDayCounts);
    }

    @RequestMapping(path="/findRunResultSpan",method = RequestMethod.POST)
    public Result<List<PipelineDayResultCount>> findRunResultSpan(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        List<PipelineDayResultCount> runDayCounts = countService.findRunResultSpan(countQuery);
        return Result.ok(runDayCounts);
    }

    @RequestMapping(path="/findRunNumberSpan",method = RequestMethod.POST)
    public Result<List<PipelineDayResultCount>> findRunNumberSpan(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        PipelineDayNumberCount runDayCounts = countService.findRunNumberSpan(countQuery);
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

    @RequestMapping(path="/findRunResultCount",method = RequestMethod.POST)
    public Result<PipelineRunResultCount> findRunResultCount(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        PipelineRunResultCount runDayCounts = countService.findRunResultCount(countQuery);
        return Result.ok(runDayCounts);
    }

    @RequestMapping(path="/findDayRateCount",method = RequestMethod.POST)
    public Result<PipelineDayRateCount> findDayRateCount(@RequestBody @NotNull @Valid PipelineRunCountQuery countQuery){
        List<PipelineDayRateCount> runDayCounts = countService.findDayRateCount(countQuery);
        return Result.ok(runDayCounts);
    }

    @RequestMapping(path="/findPipelineSurveyCount",method = RequestMethod.POST)
    public Result<PipelineSurveyCount> findPipelineRunResultCount(@NotNull String pipelineId){
        PipelineSurveyCount surveyCount = countService.findPipelineSurveyCount(pipelineId);
        return Result.ok(surveyCount);
    }

    @RequestMapping(path="/findPipelineSurveyResultCount",method = RequestMethod.POST)
    public Result<PipelineSurveyResultCount> findPipelineSurveyResultCount(@NotNull String pipelineId){
        PipelineSurveyResultCount resultCount = countService.findPipelineSurveyResultCount(pipelineId);
        return Result.ok(resultCount);
    }

    @RequestMapping(path="/findPipelineInstanceCount",method = RequestMethod.POST)
    public Result<PipelineInstanceCount> findPipelineInstanceCount(@RequestBody @NotNull @Valid PipelineInstanceQuery query){
        PipelineInstanceCount resultCount = countService.findPipelineInstanceCount(query);
        return Result.ok(resultCount);
    }

    @RequestMapping(path="/findSurveyCount",method = RequestMethod.POST)
    public Result<PipelineSurveyCount> findSurveyCount(){
        PipelineSurveyCount surveyCount = countService.findSurveyCount();
        return Result.ok(surveyCount);
    }

    @RequestMapping(path="/findSurveyResultCount",method = RequestMethod.POST)
    public Result<PipelineSurveyResultCount> findSurveyResultCount(){
        PipelineSurveyResultCount resultCount = countService.findSurveyResultCount();
        return Result.ok(resultCount);
    }

    @RequestMapping(path="/findRecentDaysFormatted",method = RequestMethod.POST)
    public Result<PipelineSurveyResultCount> findRecentDaysFormatted(@NotNull Integer day){
        List<String> resultCount = countService.findDaysFormatted(day);
        return Result.ok(resultCount);
    }

}
















