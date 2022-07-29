package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowProcess;

import java.util.List;

public interface CodeAchieveService {

    /**
     * 源码管理
     * @param matFlowProcess 执行信息
     * @param matFlowExecHistoryList 执行过程
     * @return 执行状态
     */
    int clone(MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList);
}
