package io.thoughtware.arbess.support.variable.service;

import io.thoughtware.arbess.support.variable.model.ExecVariable;

public interface ExecVariableService {


    void initPipelineVariable(String pipelineId,String taskId);

    void addExecVariable(ExecVariable variable);

}
