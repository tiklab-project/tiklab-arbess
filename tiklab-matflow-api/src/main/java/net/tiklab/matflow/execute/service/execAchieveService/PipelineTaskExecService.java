package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface PipelineTaskExecService {


    String beginState(PipelineProcess pipelineProcess, PipelineConfigOrder oneConfig, int type);

}
