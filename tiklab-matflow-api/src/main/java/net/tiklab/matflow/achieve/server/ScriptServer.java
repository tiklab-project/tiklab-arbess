package net.tiklab.matflow.achieve.server;

import net.tiklab.matflow.execute.model.PipelineProcess;

public interface ScriptServer {


    boolean scripts(PipelineProcess pipelineProcess, String configId , int taskType);

}
