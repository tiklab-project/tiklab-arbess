package io.tiklab.matflow.task.script.service;

import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtilService;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.tiklab.matflow.support.util.PipelineFinal.TASK_SCRIPT_BAT;
import static io.tiklab.matflow.support.util.PipelineFinal.TASK_SCRIPT_SHELL;

/**
 * 执行bat,sh脚本
 */

@Service
@Exporter
public class TaskScriptExecServiceImpl implements TaskScriptExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    VariableService variableServer;

    @Autowired
    ConditionService conditionService;

    public boolean scripts(String pipelineId, Tasks task , String taskType) {

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
        // script.setType(taskType);
        String name = task.getTaskName();
        String type = script.getType();
        if (type.equals(TASK_SCRIPT_BAT)){
            name = "Bat脚本";
        }
        if (type.equals(TASK_SCRIPT_SHELL)){
            name = "Shell脚本";
        }
        tasksInstanceService.writeExecLog(taskId,  PipelineUtil.date(4)+"执行："+name);

        // 效验系统是否可以执行当前脚本
        int systemType = PipelineUtil.findSystemType();
        if (systemType == 1 && (type.equals(TASK_SCRIPT_SHELL)) ){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "Windows系统无法执行Shell脚本。");
            return false;
        }
        if (systemType == 2 &&  ( type.equals(TASK_SCRIPT_BAT) )){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "Linux系统无法执行Bat脚本。");
            return false;
        }

        // 替换脚本中的系统变量
        String key = variableServer.replaceVariable(pipelineId, taskId, script.getScriptOrder());

        try {
            if (type.equals(TASK_SCRIPT_BAT)){
                execBat(key,taskId);
            }else {
                execShell(key,taskId);
            }
        }catch (Exception e){
            String message = e.getMessage();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ message);
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+name+"执行失败！");
            return false;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+name+"执行完成。");
        return true;
    }

    /**
     * 执行shell脚本
     * @param scriptOrder 脚本命令
     * @param taskId 任务id
     */
    private void execShell(String scriptOrder,String taskId){

        // 创建临时文件
        String tempFile = PipelineFileUtil.createTempFile(scriptOrder,PipelineFinal.FILE_TYPE_SH);

        if (Objects.isNull(tempFile)){
            throw new ApplicationException("无法获取到执行环境，创建脚本执行文件错误！");
        }

        File file = new File(tempFile);

        String absolutePath = file.getParentFile().getAbsolutePath();
        try {
            // 执行脚本
            String validOrder =" sh -n " + file.getName();
            Process processs = PipelineUtil.process(absolutePath, validOrder);
            tasksInstanceService.readCommandExecResult(processs, PipelineFinal.UTF_8, error(), taskId);
            int exitCodes = processs.waitFor();
            if (exitCodes != 0) {
                PipelineFileUtil.deleteFile(file);
                throw new ApplicationException("Shell脚本语法错误！");
            }

            String order = " sh " + file.getName();
            Process process = PipelineUtil.process(absolutePath, order);
            tasksInstanceService.readCommandExecResult(process,  PipelineFinal.UTF_8,error(), taskId);

            // 获取执行状态
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                PipelineFileUtil.deleteFile(file);
                throw new ApplicationException("Shell脚本执行失败");
            }
            PipelineFileUtil.deleteFile(file);
        }catch (Exception e){
            PipelineFileUtil.deleteFile(file);
            throw new ApplicationException(e.getMessage());
        }

    }

    /**
     * 执行bat及哦啊本
     * @param scriptOrder 脚本命令
     * @param taskId 任务id
     */
    private void execBat(String scriptOrder,String taskId){

        // 创建临时文件
        String tempFile = PipelineFileUtil.createTempFile(scriptOrder,PipelineFinal.FILE_TYPE_BAT);
        if (Objects.isNull(tempFile)){
            throw new ApplicationException("无法获取到执行环境，创建脚本执行文件错误！");
        }
        File file = new File(tempFile);
        String absolutePath = file.getParentFile().getAbsolutePath();
        try {
            String order = " .\\" + file.getName();
            Process process = PipelineUtil.process(absolutePath, order);
            tasksInstanceService.readCommandExecResult(process, PipelineFinal.GBK, error(), taskId);

            // 获取执行状态
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                PipelineFileUtil.deleteFile(file);
                throw new ApplicationException("Bat脚本执行失败");
            }
            PipelineFileUtil.deleteFile(file);
        }catch (Exception e){
            PipelineFileUtil.deleteFile(file);
            throw new ApplicationException(e.getMessage());
        }

    }


    private Map<String,String> error(){
        Map<String,String> map = new HashMap<>();
        map.put("syntax error:","脚本语法错误！");
        map.put("invalid option;","");
        return map;
    }

}





























































