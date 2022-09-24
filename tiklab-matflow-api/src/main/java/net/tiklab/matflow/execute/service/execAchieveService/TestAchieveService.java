package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.orther.model.MatFlowProcess;

public interface TestAchieveService {

    /**
     * 测试
     * @param matFlowProcess 配置信息
     * @return 执行状态
     */
    String test(MatFlowProcess matFlowProcess);
}
