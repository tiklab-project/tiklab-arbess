package io.tiklab.matflow.task.build.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.artifact.model.XpackRepository;
import io.tiklab.matflow.task.artifact.service.TaskArtifactXpackService;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.build.service.TaskBuildProductService;
import io.tiklab.xpack.repository.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线集成xpack控制器
 */
@RestController
@RequestMapping("/app")
public class packController {

    @Autowired
    private TaskBuildProductService taskBuildProductService;


    @RequestMapping(path = "/findAllRepository", method = RequestMethod.POST)
    public Result<List<Repository>> findAllRepository(@NotNull String authId) {

        taskBuildProductService.createBuildProduct(new TaskBuildProduct("111","333"));

        return Result.ok();
    }

}