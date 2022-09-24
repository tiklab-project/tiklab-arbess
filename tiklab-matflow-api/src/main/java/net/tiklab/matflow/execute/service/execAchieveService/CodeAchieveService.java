package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.orther.model.MatFlowProcess;

public interface CodeAchieveService {

    /**
     * 源码管理
     * @param matFlowProcess 执行信息
     * @return 执行状态
     */
    String clone(MatFlowProcess matFlowProcess);
}
