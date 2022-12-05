package net.tiklab.matflow.definition.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineConfigServerImpl implements PipelineConfigServer {


    @Autowired
    PipelineCourseConfigService courseConfigService;

    @Autowired
    PipelineAfterConfigServer afterConfigServer;

    @Autowired
    PipelineService pipelineService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigServerImpl.class);

    //查询流水线所有配置（包括后置任务）
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





























