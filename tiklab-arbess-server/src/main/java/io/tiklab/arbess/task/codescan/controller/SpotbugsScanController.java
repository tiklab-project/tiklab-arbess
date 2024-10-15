package io.tiklab.arbess.task.codescan.controller;

import io.tiklab.arbess.task.codescan.model.SpotbugsBugSummary;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.arbess.task.code.service.SpotbugsScanService;
import io.tiklab.arbess.task.codescan.model.SpotbugsBugInstance;
import io.tiklab.arbess.task.codescan.model.SpotbugsBugPackageStats;
import io.tiklab.arbess.task.codescan.model.SpotbugsBugQuery;
import io.tiklab.arbess.task.codescan.service.SpotbugsXmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线代码扫描Spotbugs控制器
 */
@RestController
@RequestMapping("/spotbugsScan")
public class SpotbugsScanController {

    @Autowired
    SpotbugsScanService spotbugsScanService;

    /**
     * @pi.name:条件查询代码扫描结果
     * @pi.path:/spotbugsScan/findScanBugsList
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=spotbugsBugQuery
     */
    @RequestMapping(path = "/findScanBugsList", method = RequestMethod.POST)
    public Result<List<SpotbugsBugSummary>> findSpotbugsList(@RequestBody @Valid @NotNull SpotbugsBugQuery spotbugsBugQuery) {
        List<SpotbugsBugSummary> spotbugsList = spotbugsScanService.findSpotbugsList(spotbugsBugQuery);

        return Result.ok(spotbugsList);
    }

    /**
     * @pi.name:分页查询代码扫描结果
     * @pi.path:/spotbugsScan/findSpotbugsPage
     * @pi.methodType:post
     * @pi.request-type:json
     * @pi.param: model=spotbugsBugQuery
     */
    @RequestMapping(path = "/findSpotbugsPage", method = RequestMethod.POST)
    public Result<Pagination<SpotbugsBugSummary>> findSpotbugsPage(@RequestBody @Valid @NotNull SpotbugsBugQuery spotbugsBugQuery) {

        Pagination<SpotbugsBugSummary> spotbugsList = spotbugsScanService.findSpotbugsPage(spotbugsBugQuery);

        return Result.ok(spotbugsList);
    }

    /**
     * @pi.name:查询代码扫描详情
     * @pi.path:/spotbugsScan/findBugs
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=xmlPath;dataType=string;value=xmlPath;
     */
    @RequestMapping(path = "/findBugs", method = RequestMethod.POST)
    public Result<List<SpotbugsBugInstance>> findAllRepository(@NotNull String xmlPath) {
        List<SpotbugsBugInstance> scanBugs = new SpotbugsXmlConfig().findScanBugs(xmlPath);

        return Result.ok(scanBugs);
    }


    /**
     * @pi.name:删除代码扫描结果
     * @pi.path:/spotbugsScan/deleteSpotbugs
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=bugId;dataType=string;value=bugId;
     */
    @RequestMapping(path = "/deleteSpotbugs", method = RequestMethod.POST)
    public Result<List<SpotbugsBugPackageStats>> deleteSpotbugs(@NotNull String bugId) {

        spotbugsScanService.deleteSpotbugs(bugId);
        return Result.ok();
    }












}
