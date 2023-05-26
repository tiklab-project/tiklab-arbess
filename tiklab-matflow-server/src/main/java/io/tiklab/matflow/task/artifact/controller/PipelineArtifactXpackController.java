package io.tiklab.matflow.task.artifact.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.artifact.model.XpackRepository;
import io.tiklab.matflow.task.artifact.service.TaskArtifactXpackService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.repository.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/xpackAuthorize")
@Api(name = "PipelineCodeThirdController",desc = "git")
public class PipelineArtifactXpackController {

    @Autowired
    private TaskArtifactXpackService TaskArtifactXpackService;


    @RequestMapping(path = "/findAllRepository", method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepository", desc = "获取所有仓库")
    @ApiParam(name = "authId", desc = "回调地址", required = true)
    public Result<List<Repository>> findAllRepository(@NotNull String authId) {

        List<XpackRepository> allRepository = TaskArtifactXpackService.findAllRepository(authId);

        return Result.ok(allRepository);
    }

}