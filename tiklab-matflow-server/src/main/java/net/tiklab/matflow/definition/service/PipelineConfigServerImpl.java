package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.trigger.server.PipelineTriggerConfigServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineConfigServerImpl implements PipelineConfigServer {

    @Autowired
    PipelineTriggerConfigServer beforeConfigServer;

    @Autowired
    PipelineCourseConfigService courseConfigService;

    @Autowired
    PipelineAfterConfigServer afterConfigServer;


    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigServerImpl.class);

    public List<Object> findAllConfig(String pipelineId){

        List<Object> allCourseConfig = courseConfigService.findAllConfig(pipelineId);
        List<Object> allAfterConfig = afterConfigServer.findAllConfig(pipelineId);

        if (allAfterConfig == null || allAfterConfig.size() == 0){
            return allCourseConfig;
        }
        allCourseConfig.add(allAfterConfig);

        return allCourseConfig;

    }

}
