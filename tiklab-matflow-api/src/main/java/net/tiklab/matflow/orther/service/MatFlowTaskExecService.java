package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.orther.model.MatFlowProcess;

public interface MatFlowTaskExecService {

    Integer beginState(MatFlowProcess matFlowProcess, int type);

}
