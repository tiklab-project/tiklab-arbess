package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import net.tiklab.matflow.execute.model.PipelineProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineTaskExecServiceImpl implements PipelineTaskExecService {

    @Autowired
    CodeAchieveService code ;

    @Autowired
    BuildAchieveService build ;

    @Autowired
    TestAchieveService test;

    @Autowired
    DeployAchieveService deploy ;

    public boolean beginState(PipelineProcess pipelineProcess, PipelineConfigOrder oneConfig, int type){
        boolean state = true;
        if (type < 10){
            state = code.clone(pipelineProcess,oneConfig.getPipelineCode());
        }else if (10<type && type<20){
            state = test.test(pipelineProcess,oneConfig.getPipelineTest());
        }else if (20<type && type<30){
            state  = build.build(pipelineProcess, oneConfig.getPipelineBuild());
        }else if (30<type && type<40){
            state  = deploy.deploy(pipelineProcess, oneConfig.getPipelineDeploy());
        }
        return state;
    }
}
