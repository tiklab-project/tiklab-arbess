package io.thoughtware.matflow.support.count.controller;

import io.thoughtware.core.Result;
import io.thoughtware.matflow.support.count.model.*;
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

    @RequestMapping(path="/findPipelineLogTypeCount",method = RequestMethod.POST)
    public Result<List<PipelineLogTypeCount>> findPipelineLogTypeCount(@NotNull String pipelineId){
        List<PipelineLogTypeCount> typeCountList = countService.findPipelineLogTypeCount(pipelineId);
        return Result.ok(typeCountList);
    }

    @RequestMapping(path="/findPipelineLogUserCount",method = RequestMethod.POST)
    public Result<List<PipelineLogUserCount>> findPipelineLogUserCount(@NotNull String pipelineId){
        List<PipelineLogUserCount> logUserCountList = countService.findPipelineLogUserCount(pipelineId);
        return Result.ok(logUserCountList);
    }

    @RequestMapping(path="/findLogTypeCount",method = RequestMethod.POST)
    public Result<List<PipelineLogTypeCount>> findLogTypeCount(){
        List<PipelineLogTypeCount> typeCountList = countService.findLogTypeCount();
        return Result.ok(typeCountList);
    }

    @RequestMapping(path="/findLogUserCount",method = RequestMethod.POST)
    public Result<List<PipelineLogUserCount>> findLogUserCount(){
        List<PipelineLogUserCount> logUserCountList = countService.findLogUserCount();
        return Result.ok(logUserCountList);
    }



}
















