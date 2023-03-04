package net.tiklab.matflow.task.script.service;

import net.tiklab.matflow.task.task.model.Tasks;

public interface TaskScriptExecService {


    boolean scripts(String pipelineId, Tasks task , int taskType);

}
