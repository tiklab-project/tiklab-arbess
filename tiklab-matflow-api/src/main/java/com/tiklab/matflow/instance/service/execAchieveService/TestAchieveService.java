package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowProcess;

import java.util.List;

public interface TestAchieveService {

    /**
     * 测试
     * @param matFlowProcess 配置信息
     * @return 执行状态
     */
    int test(MatFlowProcess matFlowProcess);
}
