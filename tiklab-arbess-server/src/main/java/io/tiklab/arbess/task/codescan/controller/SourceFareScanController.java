package io.tiklab.arbess.task.codescan.controller;

import io.tiklab.arbess.task.codescan.model.SourceFareScan;
import io.tiklab.arbess.task.codescan.model.SourceFareScanQuery;
import io.tiklab.arbess.task.codescan.service.SourceFareScanService;
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
 * @pi.groupName: 流水线代码扫描SourceFareScan控制器
 */
@RestController
@RequestMapping("/sourceFareScan")
public class SourceFareScanController {

    @Autowired
    SourceFareScanService sourceFareScanService;

    @RequestMapping(path = "/findSourceFareScanList", method = RequestMethod.POST)
    public Result<List<SourceFareScan>> findSourceFareScanList(@RequestBody @Valid @NotNull SourceFareScanQuery sourceFareScanQuery) {
        List<SourceFareScan> sourceFareScanList = sourceFareScanService.findSourceFareScanList(sourceFareScanQuery);

        return Result.ok(sourceFareScanList);
    }

    @RequestMapping(path = "/findSourceFareScanPage", method = RequestMethod.POST)
    public Result<Pagination<SourceFareScan>> findSourceFareScanPage(@RequestBody @Valid @NotNull SourceFareScanQuery sourceFareScanQuery) {

        Pagination<SourceFareScan> sourceFareScanList = sourceFareScanService.findSourceFareScanPage(sourceFareScanQuery);

        return Result.ok(sourceFareScanList);
    }


    @RequestMapping(path = "/deleteSourceFareScan", method = RequestMethod.POST)
    public Result<Valid> deleteSourceFareScan(@NotNull String id) {

        sourceFareScanService.deleteSourceFareScan(id);
        return Result.ok();
    }












}
