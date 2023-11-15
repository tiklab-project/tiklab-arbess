package io.tiklab.matflow.task.codescan.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.task.code.service.SpotbugsScanService;
import io.tiklab.matflow.task.codescan.model.SpotbugsBugInstance;
import io.tiklab.matflow.task.codescan.model.SpotbugsBugPackageStats;
import io.tiklab.matflow.task.codescan.model.SpotbugsBugQuery;
import io.tiklab.matflow.task.codescan.model.SpotbugsBugSummary;
import io.tiklab.matflow.task.codescan.service.SpotbugsXmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zcamy
 */
@RestController
@RequestMapping("/spotbugsScan")
public class SpotbugsScanController {

    @Autowired
    SpotbugsScanService spotbugsScanService;


    @RequestMapping(path = "/findScanBugsList", method = RequestMethod.POST)
    public Result<List<SpotbugsBugSummary>> findSpotbugsList(@RequestBody @Valid @NotNull SpotbugsBugQuery spotbugsBugQuery) {
        List<SpotbugsBugSummary> spotbugsList = spotbugsScanService.findSpotbugsList(spotbugsBugQuery);

        return Result.ok(spotbugsList);
    }

    @RequestMapping(path = "/findSpotbugsPage", method = RequestMethod.POST)
    public Result<Pagination<SpotbugsBugSummary>> findSpotbugsPage(@RequestBody @Valid @NotNull SpotbugsBugQuery spotbugsBugQuery) {

        Pagination<SpotbugsBugSummary> spotbugsList = spotbugsScanService.findSpotbugsPage(spotbugsBugQuery);

        return Result.ok(spotbugsList);
    }

    @RequestMapping(path = "/findBugs", method = RequestMethod.POST)
    public Result<List<SpotbugsBugInstance>> findAllRepository(@NotNull String xmlPath) {
        List<SpotbugsBugInstance> scanBugs = new SpotbugsXmlConfig().findScanBugs(xmlPath);

        return Result.ok(scanBugs);
    }

    @RequestMapping(path = "/deleteSpotbugs", method = RequestMethod.POST)
    public Result<List<SpotbugsBugPackageStats>> deleteSpotbugs(@NotNull String bugId) {

        spotbugsScanService.deleteSpotbugs(bugId);
        return Result.ok();
    }



    @RequestMapping(path = "/findScanSummary", method = RequestMethod.POST)
    public Result<SpotbugsBugSummary> findScanSummary(@NotNull String xmlPath) {



        return Result.ok();
    }












}
