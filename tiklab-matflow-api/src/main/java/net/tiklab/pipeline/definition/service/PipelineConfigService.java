package net.tiklab.pipeline.definition.service;



import net.tiklab.pipeline.definition.model.PipelineConfig;

import java.util.List;
import java.util.Map;

/**
 * 流水线配置
 */
public interface PipelineConfigService {

    /**
     * 流水线配置
     * @param pipelineConfig 流水线配置信息
     */
    void updateConfig(PipelineConfig pipelineConfig);

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
     * 对配置进行排序
     * @param pipelineId 流水线id
     * @return 配置顺序
     */
    Map<Integer,Integer> findConfig(String pipelineId);

    /**
     * 根据类型返回配置
     * @param type 类型
     * @param pipelineId 流水线id
     * @return 配置
     */
    Object configure(Integer type,String pipelineId);

    /**
     * 获取流水线所有配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    PipelineConfig AllConfig(String pipelineId);

}
