package net.tiklab.matflow.task.script.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

public interface ScriptService {


    boolean scripts(PipelineProcess pipelineProcess, String configId , int taskType);

}
