package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.orther.model.MatFlowProcess;

public interface MatFlowTaskExecService {

    String beginState(MatFlowProcess matFlowProcess, int type);

}
