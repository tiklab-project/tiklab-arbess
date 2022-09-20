package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.orther.model.MatFlowProcess;

public interface BuildAchieveService {

    /**
     * 构建
     * @param matFlowProcess 构建信息
     * @return 构建状态
     */
    int build(MatFlowProcess matFlowProcess);
}
