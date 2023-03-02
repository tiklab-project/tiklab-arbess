package net.tiklab.matflow.task.codescan.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.task.task.service.PipelineTasksService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecLogService;
import net.tiklab.matflow.setting.model.AuthThird;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.codescan.model.TaskCodeScan;
import net.tiklab.rpc.annotation.Exporter;
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
    PipelineExecLogService commonService;

    @Autowired
    PipelineTasksService tasksService;




    /**
     * 扫描代码
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    @Override
    public boolean codeScan(PipelineProcess pipelineProcess, String configId ,int taskType) {
        Pipeline pipeline = pipelineProcess.getPipeline();
        Object o = null;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            // o = stagesTaskServer.findOneStagesTasksTask(configId);
        }
        TaskCodeScan taskCodeScan = (TaskCodeScan) o;
        // String name = taskCodeScan.getName();

        Boolean variableCond = commonService.variableCondition(pipeline.getId(), configId);
        // if (!variableCond){
        //     commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
        //     return true;
        // }
        //
        // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"执行任务："+ name);



        taskCodeScan.setType(taskType);

        String fileAddress = PipelineUtil.findFileAddress(pipeline.getId(),1);

        try {
            Process process = getOrder(pipelineProcess, taskCodeScan,fileAddress);
            if (process == null){
                // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务"+name+"执行失败");
                return false;
            }

            pipelineProcess.setInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(taskCodeScan.getType()));


            int status = commonService.readRunLog( pipelineProcess);
            if (status == 0){
                // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务"+name+"执行失败");
                return false;
            }
        } catch (IOException |ApplicationException e) {
            // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务"+name+"执行失败\n"+ PipelineUtil.date(4)+e.getMessage());
            return false;
        }
        // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务"+name+"执行完成");
        return true;
    }

    private Process getOrder(PipelineProcess pipelineProcess, TaskCodeScan taskCodeScan, String path) throws ApplicationException, IOException {
        int type = taskCodeScan.getType();
        String order ;
        String execOrder =  "mvn clean verify sonar:sonar ";
        if (type == 41) {
            String mavenAddress = commonService.getScm(21);
            if (mavenAddress == null) {
                throw new ApplicationException("不存在maven配置");
            }

            PipelineUtil.validFile(mavenAddress, 21);

            AuthThird authThird =(AuthThird) taskCodeScan.getAuth();

            if (authThird == null){
                commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"执行扫描命令："+execOrder);
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
            commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"执行扫描命令："+execOrder);
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















































