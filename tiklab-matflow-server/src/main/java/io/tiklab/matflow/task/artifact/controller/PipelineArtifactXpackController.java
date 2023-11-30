package io.tiklab.matflow.task.artifact.controller;

import io.tiklab.core.Result;
import io.tiklab.matflow.task.artifact.model.XpackRepository;
import io.tiklab.matflow.task.artifact.service.TaskArtifactXpackService;
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
@RequestMapping("/xpackAuthorize")
public class PipelineArtifactXpackController {

    @Autowired
    TaskArtifactXpackService TaskArtifactXpackService;

    /**
     * @pi.name:获取所有制品库信息
     * @pi.path:/xpackAuthorize/findAllRepository
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param: name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path = "/findAllRepository", method = RequestMethod.POST)
    public Result<List<XpackRepository>> findAllRepository(@NotNull String authId) {

        List<XpackRepository> allRepository = TaskArtifactXpackService.findAllRepository(authId);

        return Result.ok(allRepository);
    }

}