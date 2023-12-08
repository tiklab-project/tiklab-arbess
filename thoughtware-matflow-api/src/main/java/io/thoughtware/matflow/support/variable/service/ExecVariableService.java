package io.thoughtware.matflow.support.variable.service;

import io.thoughtware.matflow.support.variable.model.ExecVariable;

public interface ExecVariableService {


    void initPipelineVariable(String pipelineId,String taskId);

    void addExecVariable(ExecVariable variable);

}
