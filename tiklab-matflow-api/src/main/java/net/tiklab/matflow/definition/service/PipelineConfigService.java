package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineConfigOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PipelineConfigService {

    List<Object> findAllConfig(List<PipelineConfigOrder> allPipelineConfig);

    PipelineConfigOrder findConfig(PipelineConfigOrder typeConfig,int type);

    Map<String, String> updateConfig(PipelineConfigOrder config, PipelineConfigOrder typeConfig, String types);

    Map<String, String> createConfig(PipelineConfigOrder config, String types, int size);

    Map<String, String> deleteConfig( PipelineConfigOrder typeConfig ,String types);

}
