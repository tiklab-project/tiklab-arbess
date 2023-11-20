package io.tiklab.matflow.task.codescan.service;

import io.tiklab.core.context.AppHomeContext;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.codescan.model.SpotbugsBugSummary;
import io.tiklab.matflow.task.code.service.SpotbugsScanService;
import io.tiklab.matflow.task.codescan.model.TaskCodeScan;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TaskCodeScanExecServiceImpl.class);


    @Override
    public boolean codeScan(String pipelineId, Tasks task , String taskType) {
        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        
        TaskCodeScan taskCodeScan = (TaskCodeScan) task.getTask();
        String name = task.getTaskName();
        taskCodeScan.setType(taskType);

        try {
            boolean status;
            if (TASK_CODESCAN_SONAR.equals(taskCodeScan.getType())){
                String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,1);
                status = sonarOrder(taskId, taskCodeScan,fileAddress);
            }else {

                status = spotbugsOrder(taskId, taskCodeScan,pipelineId);
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


    private Boolean spotbugsOrder(String taskId, TaskCodeScan taskCodeScan, String pipelineId) throws ApplicationException, IOException {
        String spotbugsPath = findSpotbugsAddress();
        String javaPath = utilService.findJavaPath();
        String scanPath = taskCodeScan.getScanPath();
        if (scanPath.contains(DEFAULT_CODE_ADDRESS)){
            String defaultAddress = utilService.findPipelineDefaultAddress(pipelineId, 1);
            scanPath = scanPath.replace(DEFAULT_CODE_ADDRESS,defaultAddress);
        }

        File file = new File(scanPath);
        if (!file.exists()){
            throw new ApplicationException("找不到源码！");
        }

        // xml文件存储地址
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,2);
        String instanceId = pipelineInstanceService.findRunInstanceId(pipelineId);
        long time = new Date().getTime();

        File file1 = new File(fileAddress + "/" + instanceId + "/spotbugs");
        if (!file1.exists()){
            file1.mkdirs();
        }

        String outPath = file1.getAbsolutePath() +"/spotbugs-" + time + ".xml";
        System.out.println(outPath);
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"开始编译源代码......");

        Scm pipelineScm = scmService.findOnePipelineScm(21);
        if (pipelineScm == null) {
            throw new ApplicationException("执行SpotbugsBug扫描需要编译代码，请在工具配置中配置Maven环境！");
        }

        StringBuilder compileOrder = new StringBuilder();
        compileOrder.append(" cd ").append(scanPath)
                .append(" ;").append(pipelineScm.getScmAddress())
                .append("/mvn clean compile ");
        Process compileProcess = PipelineUtil.process(scanPath, String.valueOf(compileOrder));
        logger.warn("执行代码编译：{}",compileOrder);
        if (Objects.isNull(compileProcess)){
            return false;
        }

        boolean compileStatus = tasksInstanceService.readCommandExecResult(compileProcess,null,error(taskCodeScan.getType()), taskId);
        if (!compileStatus){
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"源代码编译完成。");

        StringBuilder order = new StringBuilder();
        order.append("  sh ").append(spotbugsPath) // 执行脚本
                 .append(" -textui ")
                .append(" -xml:withMessages ");
        // 启用断言
        if (taskCodeScan.getOpenAssert()){
            order.append(" -ea ");
        }

        // 启用调试模式
        if (taskCodeScan.getOpenDebug()){
            order.append(" -debug ");
        }

        // 扫描等级
        if ("min".equals(taskCodeScan.getScanGrade())){
            order.append(" -effort:min ");
        }else if ("max".equals(taskCodeScan.getScanGrade())){
            order.append(" -effort:max ");
        }else {
            order.append(" -effort:default ");
        }

        // 扫描错误级别
        if ("max".equals(taskCodeScan.getErrGrade())){
            order.append(" -low ");
        }

        // 指定JDK以及结果输出目录
        order.append(" -javahome ").append(javaPath)
                .append(" -output ").append(outPath);

        // 源码位置
        order.append(" ").append(scanPath);

        logger.warn("执行代码扫描：{}",order);

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"准备扫描源码....");
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"扫描中.....");
        Process process = PipelineUtil.process(scanPath, String.valueOf(order));

        if (Objects.isNull(process)){
            return false;
        }
        boolean status = tasksInstanceService.readCommandExecResult(process,null,error(taskCodeScan.getType()), taskId);
        if (!status){
            return false;
        }
        SpotbugsBugSummary scanSummary = new SpotbugsXmlConfig().findScanSummary(outPath);
        scanSummary.setXmlPath(outPath);
        scanSummary.setPipelineId(pipelineId);
        spotbugsScanService.creatSpotbugs(scanSummary);
        return true;
    }

    public String findSpotbugsAddress(){
        String appHome = AppHomeContext.getAppHome();
        String path;
        if ("null".equals(spotbugsAddress)){
            path = new File(appHome).getParentFile().getParent()+"/embbed/spotbugs-4.8.1/bin";
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















































