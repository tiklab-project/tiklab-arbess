package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowProcess;

import java.util.List;

public interface StructureAchieveService {

    /**
     * 构建
     * @param matFlowProcess 构建信息
     * @return 构建状态
     */
    int structure(MatFlowProcess matFlowProcess);
}
