package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.task.build.service.TaskBuildExecService;
import net.tiklab.matflow.task.code.service.TaskCodeExecService;
import net.tiklab.matflow.task.codescan.service.TaskCodeScanExecService;
import net.tiklab.matflow.task.deploy.service.TaskDeployExecService;
import net.tiklab.matflow.task.message.service.TaskMessageExecService;
import net.tiklab.matflow.task.product.service.TaskProductExecService;
import net.tiklab.matflow.task.script.service.TaskScriptExecService;
import net.tiklab.matflow.task.test.service.TaskTestExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PipelineExecTaskServiceImpl implements PipelineExecTaskService {

    @Autowired
    TaskCodeExecService code ;

    @Autowired
    TaskBuildExecService build ;

    @Autowired
    TaskTestExecService test;

    @Autowired
    TaskDeployExecService deploy ;

    @Autowired
    TaskCodeScanExecService codeScan;

    @Autowired
    TaskProductExecService product;

    @Autowired
    TaskMessageExecService message;

    @Autowired
    TaskScriptExecService taskScriptExecService;

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
            case 7 -> state = taskScriptExecService.scripts(pipelineProcess, configId,taskType);
        }
        return state;
    }

}































