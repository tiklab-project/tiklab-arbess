package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

import java.util.Map;

public interface PipelineExecTaskService {


    boolean beginCourseState(PipelineProcess pipelineProcess, String configId , int taskType, Map<String,String> maps);


}
