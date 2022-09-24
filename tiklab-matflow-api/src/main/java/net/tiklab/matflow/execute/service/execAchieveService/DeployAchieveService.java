package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.orther.model.MatFlowProcess;

public interface DeployAchieveService {

    /**
     * 部署
     * @param matFlowProcess 部署信息
     * @return 部署状态
     */
    String deploy(MatFlowProcess matFlowProcess);
}
