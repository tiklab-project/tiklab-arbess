package io.thoughtware.matflow.starter.task;

import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.model.PipelineQuery;
import io.thoughtware.matflow.pipeline.definition.service.PipelineService;
import io.thoughtware.matflow.support.util.service.PipelineUpTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TaskUpdateType implements ApplicationRunner  {


    @Autowired
    PipelineUpTypeService upTypeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PipelineService pipelineService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PipelineQuery pipelineQuery = new PipelineQuery();
        pipelineQuery.setPipelineType(1);
        List<Pipeline> pipelineList = pipelineService.findPipelineList(pipelineQuery);
        for (Pipeline pipeline : pipelineList) {
            String pipelineId = pipeline.getId();
            logger.warn(" 更新流水线：{}，{}：的类型",pipelineId,pipeline.getName());
            upTypeService.updatePipelineType(pipelineId);
        }
    }



}
