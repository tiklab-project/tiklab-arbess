package net.tiklab.pipeline.definition.service;

import net.tiklab.pipeline.definition.model.PipelineConfigOrder;
import java.util.List;

/**
 * 流水线配置
 */
public interface PipelineConfigOrderService {

    /**
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     */
    void deleteConfig(String pipelineId);

    /**
     * 按顺序返回配置
     * @param pipelineId 流水线id
     * @return 配置信息
     */
    List<Object> findAllConfig(String pipelineId);

    /**
     * 流水线配置
     * @param config 流水线配置信息
     */
    void updateConfig(PipelineConfigOrder config);

    /**
     * 根据流水线id查询流水线配置顺序
     * @param pipelineId 流水线id
     * @return 配置顺序
     */
    List<PipelineConfigOrder> findAllPipelineConfig(String pipelineId);

    /**
     * 获取单个配置详情
     * @param pipelineId 流水线id
     * @param type 类型
     * @return 配置详情
     */
    PipelineConfigOrder findOneConfig(String pipelineId,int type);


}
