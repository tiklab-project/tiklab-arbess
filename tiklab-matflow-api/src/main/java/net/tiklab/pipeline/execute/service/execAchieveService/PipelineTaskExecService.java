package net.tiklab.pipeline.execute.service.execAchieveService;

import net.tiklab.pipeline.definition.model.PipelineConfigOrder;
import net.tiklab.pipeline.execute.model.PipelineProcess;

public interface PipelineTaskExecService {


    String beginState(PipelineProcess pipelineProcess, PipelineConfigOrder oneConfig, int type);

}
