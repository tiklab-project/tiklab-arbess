package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.PipelineAfterConfig;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.achieve.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineTaskExecServiceImpl implements PipelineTaskExecService {

    @Autowired
    CodeService code ;

    @Autowired
    BuildService build ;

    @Autowired
    TestService test;

    @Autowired
    DeployService deploy ;

    @Autowired
    CodeScanService codeScan;

    @Autowired
    ProductService product;

    @Autowired
    MessageExecService message;

    public boolean beginCourseState(PipelineProcess pipelineProcess, PipelineCourseConfig config){
        int taskType = config.getTaskType();
        boolean state = true;
        switch (taskType/10) {
            case 0 -> state = code.clone(pipelineProcess, config);
            case 1 -> state = test.test(pipelineProcess, config);
            case 2 -> state = build.build(pipelineProcess, config);
            case 3 -> state = deploy.deploy(pipelineProcess, config);
            case 4 -> state = codeScan.codeScan(pipelineProcess, config);
            case 5 -> state = product.product(pipelineProcess,  config);
        }
        return state;
    }


    public boolean beginAfterState(PipelineProcess pipelineProcess, PipelineAfterConfig config){
       return message.message(pipelineProcess, config.getConfigId());
    }

}































