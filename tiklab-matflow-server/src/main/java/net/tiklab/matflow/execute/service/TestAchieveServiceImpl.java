package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineTest;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 测试执行方法
 */

@Service
@Exporter
public class TestAchieveServiceImpl implements TestAchieveService {

    @Autowired
    ConfigCommonService commonService;
    
    // 单元测试
    public boolean test(PipelineProcess pipelineProcess, PipelineTest pipelineTest) {

        //初始化日志
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        Pipeline pipeline = pipelineProcess.getPipeline();
        String log = PipelineUntil.date(4);
        String testOrder = pipelineTest.getTestOrder();
        String path = PipelineUntil.findFileAddress()+pipeline.getPipelineName();
        try {

            String a = log+"开始执行测试" + " \n" +
                    log+ "执行 : \"" + testOrder + "\"";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+a);

            Process process = getOrder(pipelineTest,path);

            if (process == null){
                commonService.execHistory(pipelineProcess, log+"命令错误。");
                return false;
            }

            pipelineProcess.setInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(pipelineTest.getType()));

            int state = commonService.log(pipelineProcess);
            process.destroy();
            if (state == 0){
                process.destroy();
                commonService.execHistory(pipelineProcess, log+"测试执行失败。");
                return false;
            }
        } catch (IOException e) {
            commonService.execHistory(pipelineProcess, log+"日志打印失败"+e);
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
                throw new ApplicationException("不存在maven配置");
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
