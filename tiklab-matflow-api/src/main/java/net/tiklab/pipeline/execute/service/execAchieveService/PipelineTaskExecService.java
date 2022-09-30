package net.tiklab.pipeline.execute.service.execAchieveService;

import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.orther.model.PipelineProcess;

public interface PipelineTaskExecService {


    String beginState(PipelineProcess pipelineProcess, PipelineConfig pipelineConfig, int type);

}
