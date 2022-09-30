package net.tiklab.pipeline.execute.service;

import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.execute.service.execAchieveImpl.BuildAchieveServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveImpl.CodeAchieveServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveImpl.DeployAchieveServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveImpl.TestAchieveServiceImpl;
import net.tiklab.pipeline.orther.model.PipelineProcess;
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

    public String beginState(PipelineProcess pipelineProcess, PipelineConfig pipelineConfig, int type){
        String state = null;
        switch (type) {
            case 10 -> state = codeAchieveServiceImpl.clone(pipelineProcess,pipelineConfig);
            case 20 -> state = testAchieveServiceImpl.test(pipelineProcess,pipelineConfig);
            case 30 -> state  = buildAchieveServiceImpl.build(pipelineProcess,pipelineConfig);
            case 40-> state = deployAchieveServiceImpl.deploy(pipelineProcess,pipelineConfig);
        }
        return state;
    }
}
