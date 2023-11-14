package io.tiklab.matflow.task.codescan.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.code.model.SpotbugsBugFileStats;
import io.tiklab.matflow.task.code.model.SpotbugsBugInstance;
import io.tiklab.matflow.task.code.model.SpotbugsBugPackageStats;
import io.tiklab.matflow.task.code.model.SpotbugsBugSummary;
import io.tiklab.matflow.task.code.service.SpotbugsScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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


    @RequestMapping(path = "/findScanBugs", method = RequestMethod.POST)
    public Result<List<SpotbugsBugInstance>> findScanBugs(@NotNull String xmlPath) {


        return Result.ok();
    }

    @RequestMapping(path = "/findBugFileStats", method = RequestMethod.POST)
    public Result<List<SpotbugsBugFileStats>> findAllRepository(@NotNull String xmlPath) {


        return Result.ok();
    }

    @RequestMapping(path = "/findBugPackageStats", method = RequestMethod.POST)
    public Result<List<SpotbugsBugPackageStats>> findBugPackageStats(@NotNull String xmlPath) {


        return Result.ok();
    }



    @RequestMapping(path = "/findScanSummary", method = RequestMethod.POST)
    public Result<SpotbugsBugSummary> findScanSummary(@NotNull String xmlPath) {



        return Result.ok();
    }












}
