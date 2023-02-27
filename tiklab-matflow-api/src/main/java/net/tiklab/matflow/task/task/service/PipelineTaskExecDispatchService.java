package net.tiklab.matflow.task.task.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

import java.util.Map;

public interface PipelineTaskExecDispatchService {


    boolean beginCourseState(PipelineProcess pipelineProcess, String configId , int taskType, Map<String,String> maps);


}
