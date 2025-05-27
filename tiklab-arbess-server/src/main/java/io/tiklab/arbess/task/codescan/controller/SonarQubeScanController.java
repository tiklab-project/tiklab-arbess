package io.tiklab.arbess.task.codescan.controller;

import io.tiklab.arbess.task.codescan.model.SonarQubeScanQuery;
import io.tiklab.arbess.task.codescan.model.SonarQubeScan;
import io.tiklab.arbess.task.codescan.service.SonarQubeScanService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
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
 * @pi.groupName: 流水线代码扫描SonarQubeScan控制器
 */
@RestController
@RequestMapping("/sonarQubeScan")
public class SonarQubeScanController {

    @Autowired
    SonarQubeScanService sonarQubeScanService;

    @RequestMapping(path = "/findSonarQubeScanList", method = RequestMethod.POST)
    public Result<List<SonarQubeScan>> findSonarQubeScanList(@RequestBody @Valid @NotNull SonarQubeScanQuery sonarQubeScanQuery) {
        List<SonarQubeScan> sonarQubeScanList = sonarQubeScanService.findSonarQubeScanList(sonarQubeScanQuery);

        return Result.ok(sonarQubeScanList);
    }

    @RequestMapping(path = "/findSonarQubeScanPage", method = RequestMethod.POST)
    public Result<Pagination<SonarQubeScan>> findSonarQubeScanPage(@RequestBody @Valid @NotNull SonarQubeScanQuery sonarQubeScanQuery) {

        Pagination<SonarQubeScan> sonarQubeScanList = sonarQubeScanService.findSonarQubeScanPage(sonarQubeScanQuery);

        return Result.ok(sonarQubeScanList);
    }


    @RequestMapping(path = "/deleteSonarQubeScan", method = RequestMethod.POST)
    public Result<Valid> deleteSonarQubeScan(@NotNull String id) {

        sonarQubeScanService.deleteSonarQubeScan(id);
        return Result.ok();
    }












}
