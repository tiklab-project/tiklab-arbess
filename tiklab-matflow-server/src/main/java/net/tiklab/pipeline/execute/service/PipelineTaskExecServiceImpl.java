package net.tiklab.pipeline.execute.service;

import net.tiklab.pipeline.definition.model.PipelineConfigOrder;
import net.tiklab.pipeline.execute.service.execAchieveImpl.BuildAchieveServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveImpl.CodeAchieveServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveImpl.DeployAchieveServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveImpl.TestAchieveServiceImpl;
import net.tiklab.pipeline.execute.model.PipelineProcess;
import net.tiklab.pipeline.execute.service.execAchieveService.PipelineTaskExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineTaskExecServiceImpl implements PipelineTaskExecService {

    @Autowired
    CodeAchieveServiceImpl codeAchieveServiceImpl ;

    @Autowired
    BuildAchieveServiceImpl buildAchieveServiceImpl ;

    @Autowired
    TestAchieveServiceImpl testAchieveServiceImpl;

    @Autowired
    DeployAchieveServiceImpl deployAchieveServiceImpl ;

    public String beginState(PipelineProcess pipelineProcess, PipelineConfigOrder oneConfig, int type){
        String state = null;
        if (type < 10){
            state = codeAchieveServiceImpl.clone(pipelineProcess,oneConfig.getPipelineCode());
        }else if (10<type && type<20){
            state = testAchieveServiceImpl.test(pipelineProcess,oneConfig.getPipelineTest());
        }else if (20<type && type<30){
            state  = buildAchieveServiceImpl.build(pipelineProcess, oneConfig.getPipelineBuild());
        }else if (30<type && type<40){
            deployAchieveServiceImpl.deploy(pipelineProcess, oneConfig.getPipelineDeploy());
        }
        return state;
    }
}
