package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.achieve.server.*;
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

    @Autowired
    ScriptServer scriptServer;

    public boolean beginCourseState(PipelineProcess pipelineProcess, String configId ,int taskType){
        boolean state = true;
        switch (taskType/10) {
            case 0 -> state = code.clone(pipelineProcess, configId,taskType);
            case 1 -> state = test.test(pipelineProcess, configId,taskType);
            case 2 -> state = build.build(pipelineProcess, configId,taskType);
            case 3 -> state = deploy.deploy(pipelineProcess, configId,taskType);
            case 4 -> state = codeScan.codeScan(pipelineProcess, configId,taskType);
            case 5 -> state = product.product(pipelineProcess,  configId,taskType);
            case 6 -> state = message.message(pipelineProcess, configId,taskType);
            case 7 -> state = scriptServer.scripts(pipelineProcess, configId,taskType);
        }
        return state;
    }

}































