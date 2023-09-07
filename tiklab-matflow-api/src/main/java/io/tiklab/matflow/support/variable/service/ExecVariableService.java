package io.tiklab.matflow.support.variable.service;

import io.tiklab.matflow.support.variable.model.ExecVariable;

public interface ExecVariableService {


    void initPipelineVariable(String pipelineId,String taskId);

    void addExecVariable(ExecVariable variable);

}
