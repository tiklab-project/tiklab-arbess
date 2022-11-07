package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.execute.model.PipelineProcess;

public interface PipelineTaskExecService {


    boolean beginState(PipelineProcess pipelineProcess, Object oneConfig, int type);

}
