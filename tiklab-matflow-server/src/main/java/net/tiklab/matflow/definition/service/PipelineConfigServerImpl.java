package net.tiklab.matflow.definition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineConfigServerImpl implements PipelineConfigServer {

    @Autowired
    PipelineBeforeConfigServer beforeConfigServer;

    @Autowired
    PipelineCourseConfigService courseConfigService;

    public List<Object> findAllConfig(String pipelineId){

        List<Object> allCourseConfig = courseConfigService.findAllConfig(pipelineId);

        List<Object> allBeforeConfig = beforeConfigServer.findAllConfig(pipelineId);
        if (allBeforeConfig == null || allBeforeConfig.size() == 0){
            return allCourseConfig;
        }

        allCourseConfig.add(allBeforeConfig);

        return allCourseConfig;

    }



}
