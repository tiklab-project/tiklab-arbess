package io.tiklab.matflow.task.codescan.service;

import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.codescan.model.TaskCodeScan;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 代码扫描
 */

@Service
@Exporter
public class TaskCodeScanExecServiceImpl implements TaskCodeScanExecService {


    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private ConditionService conditionService;
    
    @Autowired
    private VariableService variableService;
    
    @Autowired
    private ScmService scmService;


    @Override
    public boolean codeScan(String pipelineId, Tasks task , int taskType) {
        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }
        
        TaskCodeScan taskCodeScan = (TaskCodeScan) task.getValues();
        String name = task.getTaskName();


        taskCodeScan.setType(taskType);

        String fileAddress = PipelineUtil.findFileAddress(pipelineId,1);

        try {
            Process process = getOrder(taskId, taskCodeScan,fileAddress);
            if (process == null){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行失败");
                return false;
            }

            boolean status = tasksInstanceService.readCommandExecResult(process,null,error(taskCodeScan.getType()), taskId);
            if (!status){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行失败");
                return false;
            }
        } catch (IOException |ApplicationException e) {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行失败\n"+ PipelineUtil.date(4)+e.getMessage());
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行完成");
        return true;
    }

    private Process getOrder(String taskId, TaskCodeScan taskCodeScan, String path) throws ApplicationException, IOException {
        int type = taskCodeScan.getType();
        String order ;
        String execOrder =  "mvn clean verify sonar:sonar ";
        if (type == 41) {
            Scm pipelineScm = scmService.findOnePipelineScm(21);

            if (pipelineScm == null) {
                throw new ApplicationException("不存在maven配置");
            }
            String mavenAddress = pipelineScm.getScmAddress();
            PipelineUtil.validFile(mavenAddress, 21);

            AuthThird authThird =(AuthThird) taskCodeScan.getAuth();

            if (authThird == null){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行扫描命令："+execOrder);
                order = mavenOrder(execOrder, path);
                return PipelineUtil.process(mavenAddress, order);
            }

            execOrder = execOrder +
                    " -Dsonar.projectKey="+ taskCodeScan.getProjectName()+
                    " -Dsonar.host.url="+ authThird.getServerAddress();
            if (authThird.getAuthType() == 1){
                execOrder = execOrder +
                        " -Dsonar.login="+authThird.getUsername()+
                        " -Dsonar.password="+authThird.getPassword();
            }else {
                execOrder = execOrder +
                        " -Dsonar.login="+authThird.getPrivateKey();
            }
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行扫描命令："+execOrder);
            order = mavenOrder(execOrder, path);
            return PipelineUtil.process(mavenAddress, order);
        }else {
            throw new ApplicationException("未知的任务类型");
        }
    }

    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (PipelineUtil.findSystemType() == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }

    private String[] error(int type){
        String[] strings;
        if (type == 5){
            strings = new String[]{
                    "svn: E170000:",
                    "invalid option;"
            };
            return strings;
        }
        strings = new String[]{

        };
        return strings;
    }


}















































