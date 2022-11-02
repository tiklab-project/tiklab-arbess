package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineProduct;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface ProductAchieveService {

    /**
     * 推送制品代码
     * @param pipelineProcess 执行信息
     * @param product 配置信息
     * @return 执行状态
     */
     boolean product(PipelineProcess pipelineProcess, PipelineProduct product);


}
