package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineCodeScan;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuth;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Timestamp;

@Service
@Exporter
public class CodeScanAchieveServiceImpl implements CodeScanService {


    @Autowired
    ConfigCommonService commonService;

    /**
     * 扫描代码
     * @param pipelineProcess 执行信息
     * @param pipelineCodeScan 配置信息
     * @return 执行状态
     */
    @Override
    public boolean codeScan(PipelineProcess pipelineProcess, PipelineCodeScan pipelineCodeScan) {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineProcess.setBeginTime(beginTime);
        Pipeline pipeline = pipelineProcess.getPipeline();
        String fileAddress = PipelineUntil.findFileAddress();
        commonService.execHistory(pipelineProcess,"开始扫描代码。。。。。。");
        try {
            Process process = getOrder(pipelineProcess,pipelineCodeScan,fileAddress+pipeline.getPipelineName());
            if (process == null){
                commonService.execHistory(pipelineProcess,"代码扫描执行失败");
                return commonService.updateState(pipelineProcess,false);
            }
            int log = commonService.log(process.getInputStream(), process.getErrorStream(), pipelineProcess);
            if (log == 0){
                commonService.execHistory(pipelineProcess,"代码扫描失败");
                return commonService.updateState(pipelineProcess,false);
            }
        } catch (IOException |ApplicationException e) {
            commonService.execHistory(pipelineProcess,"代码扫描执行错误\n"+e.getMessage());
            return commonService.updateState(pipelineProcess,false);
        }
        commonService.execHistory(pipelineProcess,"代码扫描完成");
        return commonService.updateState(pipelineProcess,true);
    }

    private Process getOrder(PipelineProcess pipelineProcess,PipelineCodeScan pipelineCodeScan, String path) throws ApplicationException, IOException {
        int type = pipelineCodeScan.getType();
        String order ;
        String execOrder =  "mvn verify sonar:sonar ";
        if (type == 41) {
            String mavenAddress = commonService.getScm(21);
            if (mavenAddress == null) {
                throw new ApplicationException("不存在maven配置");
            }

            PipelineAuth pipelineAuth = pipelineCodeScan.getPipelineAuth();
            if (pipelineAuth == null){
                commonService.execHistory(pipelineProcess,"执行扫描命令："+execOrder);
                order = mavenOrder(execOrder, path);
                return PipelineUntil.process(mavenAddress, order);
            }

            execOrder = execOrder +
                    " -Dsonar.projectKey="+pipelineCodeScan.getProjectName()+
                    " -Dsonar.host.url="+pipelineAuth.getUrl();
            if (pipelineAuth.getAuthType() == 1){
                execOrder = execOrder +
                        " -Dsonar.host.username="+pipelineAuth.getUsername()+
                        " -Dsonar.host.password="+pipelineAuth.getPassword();
            }else {
                execOrder = execOrder +
                        " -Dsonar.login="+pipelineAuth.getToken();
            }
            commonService.execHistory(pipelineProcess,"执行扫描命令："+execOrder);
            order = mavenOrder(execOrder, path);
            return PipelineUntil.process(mavenAddress, order);
        }
        return null;
    }

    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (PipelineUntil.findSystemType() == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }


}















































