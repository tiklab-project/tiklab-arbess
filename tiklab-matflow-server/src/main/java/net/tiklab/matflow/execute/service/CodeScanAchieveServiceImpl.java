package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineCodeScan;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

        Pipeline pipeline = pipelineProcess.getPipeline();

        String fileAddress = PipelineUntil.findFileAddress();
        commonService.execHistory(pipelineProcess,"开始扫描代码。。。。。。");
        try {
            Process process = getOrder(pipelineProcess,pipelineCodeScan,fileAddress+pipeline.getPipelineName());
            if (process == null){
                commonService.execHistory(pipelineProcess,"代码扫描执行失败");
                return false;
            }

            pipelineProcess.setErrInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(pipelineCodeScan.getType()));


            int log = commonService.log( pipelineProcess);
            if (log == 0){
                commonService.execHistory(pipelineProcess,"代码扫描失败");
                return false;
            }
        } catch (IOException |ApplicationException e) {
            commonService.execHistory(pipelineProcess,"代码扫描执行错误\n"+e.getMessage());
            return false;
        }
        commonService.execHistory(pipelineProcess,"代码扫描完成");
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
                commonService.execHistory(pipelineProcess,"执行扫描命令："+execOrder);
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















































