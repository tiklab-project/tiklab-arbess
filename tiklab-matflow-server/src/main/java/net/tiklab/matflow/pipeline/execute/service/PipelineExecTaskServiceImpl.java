package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.task.common.service.TaskAchieveService;
import net.tiklab.matflow.task.build.service.BuildService;
import net.tiklab.matflow.task.code.service.CodeService;
import net.tiklab.matflow.task.codescan.service.CodeScanService;
import net.tiklab.matflow.task.deploy.service.DeployService;
import net.tiklab.matflow.task.product.service.ProductService;
import net.tiklab.matflow.task.script.service.ScriptService;
import net.tiklab.matflow.task.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    TaskAchieveService message;

    @Autowired
    ScriptService scriptService;

    public boolean beginCourseState(PipelineProcess pipelineProcess, String configId , int taskType, Map<String,String> maps){
        boolean state = true;
        switch (taskType/10) {
            case 0 -> state = code.clone(pipelineProcess, configId,taskType);
            case 1 -> state = test.test(pipelineProcess, configId,taskType);
            case 2 -> state = build.build(pipelineProcess, configId,taskType);
            case 3 -> state = deploy.deploy(pipelineProcess, configId,taskType);
            case 4 -> state = codeScan.codeScan(pipelineProcess, configId,taskType);
            case 5 -> state = product.product(pipelineProcess,  configId,taskType);
            case 6 -> state = message.message(pipelineProcess, configId,taskType,maps);
            case 7 -> state = scriptService.scripts(pipelineProcess, configId,taskType);
        }
        return state;
    }

}































