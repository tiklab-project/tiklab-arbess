package net.tiklab.matflow.task.test.service;

import net.tiklab.matflow.task.task.model.Tasks;

public interface TaskTestExecService {


    boolean test(String pipelineId, Tasks task , int taskType);


}
