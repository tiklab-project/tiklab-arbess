package io.thoughtware.arbess.task.artifact.controller;

import io.thoughtware.arbess.task.artifact.model.XpackRepository;
import io.thoughtware.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线集成Xpack控制器
 */
@RestController
@RequestMapping("/xpackAuthorize")
public class PipelineArtifactXpackController {

    @Autowired
    io.thoughtware.arbess.task.artifact.service.TaskArtifactXpackService TaskArtifactXpackService;

    /**
     * @pi.name:查询Xpack所有制品库信息
     * @pi.path:/xpackAuthorize/findAllRepository
     * @pi.methodType:post
     * @pi.request-type: formdata
     * @pi.param:name=authId;dataType=string;value=authId;
     */
    @RequestMapping(path = "/findAllRepository", method = RequestMethod.POST)
    public Result<List<XpackRepository>> findAllRepository(@NotNull String authId) {

        List<XpackRepository> allRepository = TaskArtifactXpackService.findAllRepository(authId);

        return Result.ok(allRepository);
    }

}