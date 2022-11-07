package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineConfigOrder;

import java.util.List;
import java.util.Map;

public interface PipelineConfigService {

    /**
     * 获取所有配置详情
     * @param allPipelineConfig 配置
     * @return 配置
     */
    List<Object> findAllConfig(List<PipelineConfigOrder> allPipelineConfig);

    /**
     * 获取配置详情
     * @param configOrder 配置信息
     * @return 配置信息
     */
    Object findOneConfig(PipelineConfigOrder configOrder);

    /**
     * 更新配置
     * @param config 原配置
     * @param typeConfig 新配置
     * @param types 类型
     * @return 动态信息
     */
    Map<String, String> updateConfig(PipelineConfigOrder config, PipelineConfigOrder typeConfig, String types);

    /**
     * 创建配置
     * @param config 配置信息
     * @param types 类型
     * @param size 已存在的配置数量
     * @return 动态信息
     */
    Map<String, String> createConfig(PipelineConfigOrder config, String types, int size);

    /**
     * 删除配置
     * @param typeConfig 配置信息
     * @param types 类型
     * @return 动态信息
     */
    Map<String, String> deleteConfig( PipelineConfigOrder typeConfig ,String types);

    /**
     * 效验配置必填字段
     * @param configOrderList 配置顺序信息
     * @return 效验结果
     */
    Map<String, String> configValid(List<PipelineConfigOrder> configOrderList);




}
