package io.tiklab.arbess.pipeline.definition.service;

public interface PipelineYamlService {

    /**
     * 导入流水线YAML配置
     * @param pipelineId 流水线id
     * @return YAML配置内容
     */
    String importPipelineYaml(String pipelineId);

}
