package net.tiklab.matflow.achieve.server;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.task.model.PipelineCodeScan;
import net.tiklab.matflow.task.server.PipelineCodeScanService;
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
    PipelineCodeScanService codeScanService;

    /**
     * 扫描代码
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    @Override
    public boolean codeScan(PipelineProcess pipelineProcess, String configId ,int taskType) {

        // String configId = config.getConfigId();
        PipelineCodeScan pipelineCodeScan = codeScanService.findOneCodeScanConfig(configId);
        pipelineCodeScan.setType(taskType);

        Pipeline pipeline = pipelineProcess.getPipeline();
        String log = PipelineUntil.date(4);
        String fileAddress = PipelineUntil.findFileAddress();
        commonService.execHistory(pipelineProcess,log+"开始扫描代码。。。。。。");
        try {
            Process process = getOrder(pipelineProcess,pipelineCodeScan,fileAddress+pipeline.getName());
            if (process == null){
                commonService.execHistory(pipelineProcess,log+"代码扫描执行失败");
                return false;
            }

            pipelineProcess.setInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(pipelineCodeScan.getType()));


            int status = commonService.log( pipelineProcess);
            if (status == 0){
                commonService.execHistory(pipelineProcess,log+"代码扫描失败");
                return false;
            }
        } catch (IOException |ApplicationException e) {
            commonService.execHistory(pipelineProcess,log+"代码扫描执行错误\n"+log+e.getMessage());
            return false;
        }
        commonService.execHistory(pipelineProcess,log+"代码扫描完成");
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
                commonService.execHistory(pipelineProcess,PipelineUntil.date(4)+"执行扫描命令："+execOrder);
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
            commonService.execHistory(pipelineProcess,PipelineUntil.date(4)+"执行扫描命令："+execOrder);
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














































