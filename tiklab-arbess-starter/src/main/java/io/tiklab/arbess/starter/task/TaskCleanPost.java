package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.service.PostprocessService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.install.runner.TiklabApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TaskCleanPost implements TiklabApplicationRunner {


    @Autowired
    PipelineService pipelineService;

    @Autowired
    TasksService tasksService;

    @Autowired
    PostprocessService postprocessService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("The cache post.....");
        delete();
        logger.info("The cache post end.");
    }


    private void delete(){
        List<Postprocess> pipelinePostTask = postprocessService.findAllPost();
        List<Postprocess> list = pipelinePostTask.stream()
                .filter(postprocess -> !Objects.isNull(postprocess))
                .filter(postprocess -> postprocess.getTaskType().equals(PipelineFinal.TASK_TYPE_SCRIPT))
                .collect(Collectors.toList());

        for (Postprocess postprocess : list) {
            postprocessService.deletePostTask(postprocess.getPostId());
        }

    }


}

















