package net.tiklab.matflow.definition.service;

import java.util.List;

public interface PipelineConfigServer {

    List<Object> findAllConfig(String pipelineId);

}
