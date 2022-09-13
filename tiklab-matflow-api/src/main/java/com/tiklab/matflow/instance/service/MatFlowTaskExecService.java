package com.tiklab.matflow.instance.service;

import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowProcess;

import java.util.List;

public interface MatFlowTaskExecService {

    Integer beginState(MatFlowProcess matFlowProcess, int type);

}
