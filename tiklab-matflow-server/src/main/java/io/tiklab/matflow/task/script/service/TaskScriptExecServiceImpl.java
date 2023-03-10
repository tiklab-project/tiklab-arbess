package io.tiklab.matflow.task.script.service;

import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.script.model.TaskScript;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 执行bat,sh脚本
 */

@Service
@Exporter
public class TaskScriptExecServiceImpl implements TaskScriptExecService {

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private VariableService variableServer;

    @Autowired
    private ConditionService conditionService;

    public boolean scripts(String pipelineId, Tasks task , int taskType) {

        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }

        TaskScript script = (TaskScript) task.getValues();
        script.setType(taskType);
        String name = task.getTaskName();
        int type = script.getType();
        if (type == 71){
            name = "Bat脚本";
        }
        if (type == 72){
            name = "Shell脚本";
        }
        tasksInstanceService.writeExecLog(taskId,  PipelineUtil.date(4)+"执行任务："+name);

        int systemType = PipelineUtil.findSystemType();

        if (systemType == 1 && type == 72 ){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "Windows系统无法执行Shell脚本。");
            return false;
        }

        if (systemType == 2 && type == 71){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "Linux系统无法执行Bat脚本。");
            return false;
        }

        String order = script.getScriptOrder();

        List<String> list = PipelineUtil.execOrder(order);
        if (list.size() == 0){
            tasksInstanceService.writeExecLog(taskId, "\n"+ PipelineUtil.date(4)+"任务："+name+"执行完成。");
            return true;
        }

        try {
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, taskId, s);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "执行："+key );
                String fileAddress = PipelineUtil.findFileAddress(pipelineId,1);
                Process process = PipelineUtil.process(fileAddress, key);
                String enCode = PipelineFinal.UTF_8;
                if (PipelineUtil.findSystemType() == 1){
                    enCode = PipelineFinal.GBK;
                }
                boolean result = tasksInstanceService.readCommandExecResult(process, enCode, list.toArray(new String[0]), taskId);
                if (!result){
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
                    return false;
                }
            }
        }catch (IOException | ApplicationException e){
            String s = PipelineUtil.date(4) + e.getMessage();
            tasksInstanceService.writeExecLog(taskId,s);
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+name+"执行完成。");
        return true;
    }

}





























































