package net.tiklab.matflow.achieve.server;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.task.model.PipelineTest;
import net.tiklab.matflow.task.server.PipelineTestService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 测试执行方法
 */

@Service
@Exporter
public class TestServiceImpl implements TestService {

    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineTestService testService;
    
    // 单元测试
    public boolean test(PipelineProcess pipelineProcess, String configId ,int taskType) {

        PipelineTest pipelineTest = testService.findOneTestConfig(configId);
        pipelineTest.setType(taskType);

        //初始化日志
        Pipeline pipeline = pipelineProcess.getPipeline();
        String testOrder = pipelineTest.getTestOrder();
        String path = PipelineUntil.findFileAddress()+pipeline.getName();
        try {
            String a = PipelineUntil.date(4)+"开始执行测试" + " \n" +
                    PipelineUntil.date(4)+ "执行 : \"" + testOrder + "\"";
            commonService.execHistory(pipelineProcess,PipelineUntil.date(4)+a);

            Process process = getOrder(pipelineTest,path);

            if (process == null){
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"命令错误。");
                return false;
            }

            pipelineProcess.setInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(pipelineTest.getType()));

            int state = commonService.log(pipelineProcess);
            process.destroy();
            if (state == 0){
                process.destroy();
                commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"测试执行失败。");
                return false;
            }
        } catch (IOException e) {
            commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"日志打印失败"+e);
            return false;
        } catch (ApplicationException e) {
            commonService.execHistory(pipelineProcess,e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 执行build
     * @param pipelineTest 执行信息
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(PipelineTest pipelineTest, String path ) throws ApplicationException, IOException {
        String testOrder = pipelineTest.getTestOrder();
        int type = pipelineTest.getType();
        String order ;
        if (type == 11) {
            String mavenAddress = commonService.getScm(21);
            if (mavenAddress == null) {
                throw new ApplicationException(PipelineUntil.date(4)+"不存在maven配置");
            }
            PipelineUntil.validFile(mavenAddress,21);
            order = testOrder(testOrder, path);
            return PipelineUntil.process(mavenAddress, order);
        }
        return null;
    }

    /**
     * 拼装测试命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @return 命令
     */
    private String testOrder(String buildOrder,String path){

        String order;
        int systemType = PipelineUntil.findSystemType();
        order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }

    private String[] error(int type){
        String[] strings;
        strings = new String[]{
            "BUILD FAILUREl","ERROR"
        };
        return strings;
    }

}
