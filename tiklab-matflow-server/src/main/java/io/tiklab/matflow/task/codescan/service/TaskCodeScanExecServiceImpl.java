package io.tiklab.matflow.task.codescan.service;

import io.tiklab.context.AppHomeConfiguration;
import io.tiklab.core.context.AppHomeContext;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.code.model.SpotbugsBugSummary;
import io.tiklab.matflow.task.code.service.SpotbugsScanService;
import io.tiklab.matflow.task.codescan.model.TaskCodeScan;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

/**
 * 代码扫描
 * @author zcamy
 */

@Service
@Exporter
public class TaskCodeScanExecServiceImpl implements TaskCodeScanExecService {


    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    ConditionService conditionService;
    
    @Autowired
    VariableService variableService;
    
    @Autowired
    ScmService scmService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    SpotbugsScanService spotbugsScanService;

    @Autowired
    PipelineInstanceService pipelineInstanceService;

    @Value("${spotbugs.address:null}")
    private String spotbugsAddress;


    @Override
    public boolean codeScan(String pipelineId, Tasks task , String taskType) {
        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        
        TaskCodeScan taskCodeScan = (TaskCodeScan) task.getTask();
        String name = task.getTaskName();
        taskCodeScan.setType(taskType);

        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,1);

        try {
            boolean status;
            if (TASK_CODESCAN_SONAR.equals(taskCodeScan.getType())){
                status = sonarOrder(taskId, taskCodeScan,fileAddress);
            }else {
                status = spotbugsOrder(taskId, taskCodeScan,fileAddress,pipelineId);
            }
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

    private Boolean sonarOrder(String taskId, TaskCodeScan taskCodeScan, String path) throws ApplicationException, IOException {

        String order ;
        String execOrder =  "mvn clean verify sonar:sonar ";
        Scm pipelineScm = scmService.findOnePipelineScm(21);

        if (pipelineScm == null) {
            throw new ApplicationException("不存在maven配置");
        }
        String mavenAddress = pipelineScm.getScmAddress();
        PipelineUtil.validFile(mavenAddress, "maven");

        AuthThird authThird =(AuthThird) taskCodeScan.getAuth();

        Process process;

        if (authThird == null){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + "执行扫描命令："+execOrder);
            order = mavenOrder(execOrder, path);
            process =  PipelineUtil.process(mavenAddress, order);
        }else {
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
            process = PipelineUtil.process(mavenAddress, order);
        }

        if (Objects.isNull(process)){
            return false;
        }
        return tasksInstanceService.readCommandExecResult(process,null,error(taskCodeScan.getType()), taskId);
    }


    private Boolean spotbugsOrder(String taskId, TaskCodeScan taskCodeScan, String path,String pipelineId) throws ApplicationException, IOException {
        String spotbugsPath = findSpotbugsAddress();
        String javaPath = utilService.findJavaPath();

        // xml文件存储地址
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,2);
        String instanceId = pipelineInstanceService.findRunInstanceId(pipelineId);
        long time = new Date().getTime();
        String outPath = fileAddress +"/" + instanceId +"/spotbugs-" + time + ".xml";

        StringBuilder order = new StringBuilder();
        order.append(" sh ").append(spotbugsPath) // 执行脚本
                    .append(" -textui ")
                .append(" -effort:max ")
                .append(" -xml:withMessages ");
        if (taskCodeScan.getOpenAssert()){
            order.append(" -ea ");
        }
        if (taskCodeScan.getOpenDebug()){
            order.append(" -debug ");
        }
        order.append(" -javahome ").append(javaPath)
                .append(" -output ").append(outPath)
                .append(" ").append(path);

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行扫描命令：" + order);

        Process process = PipelineUtil.process(spotbugsAddress, String.valueOf(order));

        if (Objects.isNull(process)){
            return false;
        }

        boolean status = tasksInstanceService.readCommandExecResult(process,null,error(taskCodeScan.getType()), taskId);
        if (!status){
            return false;
        }
        SpotbugsBugSummary scanSummary = new SpotbugsXmlConfig().findScanSummary(outPath);
        scanSummary.setXmlPath(outPath);
        spotbugsScanService.creatSpotbugs(scanSummary);
        return true;
    }

    private String findSpotbugsAddress(){
        String appHome = AppHomeContext.getAppHome();
        String path;
        if ("null".equals(spotbugsAddress)){
            path = new File(appHome).getParentFile().getParent();
        }else {
            path =  appHome + spotbugsAddress;
        }
        return  path + "/spotbugs";
    }

    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (PipelineUtil.findSystemType() == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }

    private Map<String,String> error(String type){
        Map<String,String> map = new HashMap<>();
        if (TASK_CODESCAN_SONAR.equals(type)){
            map.put("svn: E170000:","");
            map.put("invalid option;","");
            map.put("BUILD FAILURE","构建失败！");
            return map;
        }
        if (TASK_CODESCAN_SPOTBUGS.equals(type)){
            map.put("svn: E170000:","");
            map.put("invalid option;","");
            map.put("BUILD FAILURE","构建失败！");
            return map;
        }
        return map;
    }


}















































