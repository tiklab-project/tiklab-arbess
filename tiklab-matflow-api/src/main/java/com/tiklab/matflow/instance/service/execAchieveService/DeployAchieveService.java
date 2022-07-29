package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowProcess;

import java.util.List;

public interface DeployAchieveService {

    /**
     * 部署
     * @param matFlowProcess 部署信息
     * @param matFlowExecHistoryList 部署过程
     * @return 部署状态
     */
    int deploy(MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList);
}
