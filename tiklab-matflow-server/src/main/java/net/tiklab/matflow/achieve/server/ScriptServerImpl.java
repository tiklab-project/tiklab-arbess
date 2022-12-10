package net.tiklab.matflow.achieve.server;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.task.model.PipelineScript;
import net.tiklab.matflow.task.server.PipelineScriptServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 执行bat,sh脚本
 */

@Service
@Exporter
public class ScriptServerImpl implements ScriptServer {


    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineScriptServer scriptServer;

    public boolean scripts(PipelineProcess pipelineProcess, String configId , int taskType) {

        PipelineScript script = scriptServer.findScript(configId);
        script.setType(taskType);

        Pipeline pipeline = pipelineProcess.getPipeline();



        return true;
    }

    private void exec(PipelineScript script){

        int type = script.getType();

        int systemType = PipelineUntil.findSystemType();
        if (systemType == 1 ){
            if (type == 71){

            }
            if (type == 72){
                throw new ApplicationException("Windows系统无法执行Shell脚本。");
            }
        }

        if (systemType == 2 ){
            if (type == 71){
                throw new ApplicationException("Linux系统无法执行Bat脚本。");
            }
            if (type == 72){

            }
        }

    }

    private void execShell(){}

    private void execBat(){}

}




































