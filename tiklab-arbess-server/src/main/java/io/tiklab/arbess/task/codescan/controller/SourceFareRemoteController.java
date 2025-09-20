package io.tiklab.arbess.task.codescan.controller;

import io.tiklab.arbess.task.codescan.model.SourceFareProject;
import io.tiklab.arbess.task.codescan.model.SourceFareProjectQuery;
import io.tiklab.arbess.task.codescan.model.SourceFareScan;
import io.tiklab.arbess.task.codescan.service.SourceFareRemoteService;
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
@RequestMapping("/sourceFare/remote")
public class SourceFareRemoteController {

    @Autowired
    SourceFareRemoteService sourceFareRemoteService;

    @RequestMapping(path = "/findSourceFareProjectList", method = RequestMethod.POST)
    public Result<List<SourceFareScan>> findSourceFareProjectList(@RequestBody @Valid @NotNull SourceFareProjectQuery projectQuery) {
        List<SourceFareProject> sourceFareScanList = sourceFareRemoteService.findSourceFareProjectList(projectQuery);

        return Result.ok(sourceFareScanList);
    }



}
