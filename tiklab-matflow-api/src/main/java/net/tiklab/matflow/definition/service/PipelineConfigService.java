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
     * 配置信息
     * @param message 执行类型 create:创建,update:更新,delete:删除
     * @param types 类型
     * @param config 更新配置
     * @param typeConfig 原配置
     * @return 更新信息
     */
    Map<String,String> config(String message,String types,PipelineConfigOrder config,PipelineConfigOrder typeConfig);


    /**
     * 效验配置必填字段
     * @param configOrderList 配置顺序信息
     * @return 效验结果
     */
    Map<String, String> configValid(List<PipelineConfigOrder> configOrderList);




}
