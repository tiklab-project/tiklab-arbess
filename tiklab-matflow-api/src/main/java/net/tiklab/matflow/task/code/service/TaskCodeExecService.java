package net.tiklab.matflow.task.code.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.task.task.model.Tasks;

public interface TaskCodeExecService {


    boolean clone(String pipelineId, Tasks task , int taskType) throws ApplicationException;


}
