package net.tiklab.matflow.task.codescan.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesTaskService;
import net.tiklab.matflow.pipeline.definition.service.PipelineTasksService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.task.codescan.model.PipelineCodeScan;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 代码扫描
 */

@Service
@Exporter
public class CodeScanServiceImpl implements CodeScanService {


    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineTasksService tasksService;

    @Autowired
    PipelineStagesTaskService stagesTaskServer;


    /**
     * 扫描代码
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    @Override
    public boolean codeScan(PipelineProcess pipelineProcess, String configId ,int taskType) {
        Pipeline pipeline = pipelineProcess.getPipeline();
        Object o;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            o = stagesTaskServer.findOneStagesTasksTask(configId);
        }
        PipelineCodeScan pipelineCodeScan = (PipelineCodeScan) o;
        String name = pipelineCodeScan.getName();

        Boolean variableCond = commonService.variableCond(pipeline.getId(), configId);
        if (!variableCond){
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
            return true;
        }

        commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"执行任务："+ name);



        pipelineCodeScan.setType(taskType);

        String fileAddress = PipelineUntil.findFileAddress(pipeline.getId(),1);

        try {
            Process process = getOrder(pipelineProcess,pipelineCodeScan,fileAddress);
            if (process == null){
                commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"任务"+name+"执行失败");
                return false;
            }

            pipelineProcess.setInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(pipelineCodeScan.getType()));


            int status = commonService.log( pipelineProcess);
            if (status == 0){
                commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"任务"+name+"执行失败");
                return false;
            }
        } catch (IOException |ApplicationException e) {
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"任务"+name+"执行失败\n"+PipelineUntil.date(4)+e.getMessage());
            return false;
        }
        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"任务"+name+"执行完成");
        return true;
    }

    private Process getOrder(PipelineProcess pipelineProcess,PipelineCodeScan pipelineCodeScan, String path) throws ApplicationException, IOException {
        int type = pipelineCodeScan.getType();
        String order ;
        String execOrder =  "mvn clean verify sonar:sonar ";
        if (type == 41) {
            String mavenAddress = commonService.getScm(21);
            if (mavenAddress == null) {
                throw new ApplicationException("不存在maven配置");
            }

            PipelineUntil.validFile(mavenAddress, 21);

            PipelineAuthThird authThird =(PipelineAuthThird) pipelineCodeScan.getAuth();

            if (authThird == null){
                commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"执行扫描命令："+execOrder);
                order = mavenOrder(execOrder, path);
                return PipelineUntil.process(mavenAddress, order);
            }

            execOrder = execOrder +
                    " -Dsonar.projectKey="+pipelineCodeScan.getProjectName()+
                    " -Dsonar.host.url="+ authThird.getServerAddress();
            if (authThird.getAuthType() == 1){
                execOrder = execOrder +
                        " -Dsonar.login="+authThird.getUsername()+
                        " -Dsonar.password="+authThird.getPassword();
            }else {
                execOrder = execOrder +
                        " -Dsonar.login="+authThird.getPrivateKey();
            }
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"执行扫描命令："+execOrder);
            order = mavenOrder(execOrder, path);
            return PipelineUntil.process(mavenAddress, order);
        }else {
            throw new ApplicationException("未知的任务类型");
        }
    }

    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (PipelineUntil.findSystemType() == 1){
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















































