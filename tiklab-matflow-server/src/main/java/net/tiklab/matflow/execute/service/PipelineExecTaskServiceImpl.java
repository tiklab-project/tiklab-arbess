package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.achieve.server.*;
import net.tiklab.matflow.definition.model.PipelinePost;
import net.tiklab.matflow.execute.model.PipelineProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineExecTaskServiceImpl implements PipelineExecTaskService {

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

    public boolean beginCourseState(PipelineProcess pipelineProcess, String configId ,int taskType){
        boolean state = true;
        switch (taskType/10) {
            case 0 -> state = code.clone(pipelineProcess, configId,taskType);
            case 1 -> state = test.test(pipelineProcess, configId,taskType);
            case 2 -> state = build.build(pipelineProcess, configId,taskType);
            case 3 -> state = deploy.deploy(pipelineProcess, configId,taskType);
            case 4 -> state = codeScan.codeScan(pipelineProcess, configId,taskType);
            case 5 -> state = product.product(pipelineProcess,  configId,taskType);
        }
        return state;
    }

    //执行后置任务
    public boolean beginAfterState(PipelineProcess pipelineProcess, PipelinePost config){
       return message.message(pipelineProcess, config.getConfigId());
    }

}































