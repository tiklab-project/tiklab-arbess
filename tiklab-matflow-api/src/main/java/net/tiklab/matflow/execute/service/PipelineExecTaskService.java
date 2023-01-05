package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.execute.model.PipelineProcess;

import java.util.Map;

public interface PipelineExecTaskService {


    boolean beginCourseState(PipelineProcess pipelineProcess, String configId , int taskType, Map<String,String> maps);


}
