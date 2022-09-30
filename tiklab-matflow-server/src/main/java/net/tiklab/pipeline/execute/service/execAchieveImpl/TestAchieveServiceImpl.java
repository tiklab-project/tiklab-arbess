package net.tiklab.pipeline.execute.service.execAchieveImpl;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.orther.service.PipelineFileService;
import net.tiklab.pipeline.definition.model.PipelineTest;
import net.tiklab.pipeline.definition.service.PipelineTestService;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.execute.service.PipelineExecServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveService.TestAchieveService;
import net.tiklab.pipeline.orther.model.PipelineProcess;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * 测试执行方法
 */

@Service
@Exporter
public class TestAchieveServiceImpl implements TestAchieveService {

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    ConfigCommonServiceImpl commonAchieveService;

    @Autowired
    PipelineFileService pipelineFileService;

    // 单元测试
    public String test(PipelineProcess pipelineProcess, PipelineConfig pipelineConfig) {
        List<PipelineExecHistory> pipelineExecHistoryList = PipelineExecServiceImpl.pipelineExecHistoryList;
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();

        PipelineTest pipelineTest = pipelineConfig.getPipelineTest();
        Pipeline pipeline = pipelineTest.getPipeline();


        PipelineExecLog pipelineExecLog = commonAchieveService.initializeLog(pipelineExecHistory, pipelineTest,20);
        pipelineProcess.setPipelineExecLog(pipelineExecLog);

        String testOrder = pipelineTest.getTestOrder();
        String path = pipelineFileService.getFileAddress()+pipeline.getPipelineName();
        try {

            String a = "------------------------------------" + " \n"
                    +"开始测试" + " \n"
                    + "执行 : \"" + testOrder + "\"\n";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+a);

            Process process = getOrder(pipelineTest,path);

            if (process == null){
                commonAchieveService.updateTime(pipelineProcess,beginTime);
                commonAchieveService.updateState(pipelineProcess,false, pipelineExecHistoryList);
                return  "命令错误。";
            }

            int state = commonAchieveService.log(process.getInputStream(), pipelineProcess, pipelineExecHistoryList);

            commonAchieveService.updateTime(pipelineProcess,beginTime);
            if (state == 0){
                commonAchieveService.updateState(pipelineProcess,false, pipelineExecHistoryList);
                return  "Fail";
            }
        } catch (IOException e) {
            commonAchieveService.updateState(pipelineProcess,false, pipelineExecHistoryList);
            return "日志打印失败"+e;
        } catch (ApplicationException e) {
            commonAchieveService.updateState(pipelineProcess,false, pipelineExecHistoryList);
            return e.getMessage();
        }
        commonAchieveService.updateState(pipelineProcess,true, pipelineExecHistoryList);
        return null;
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
            String mavenAddress = commonAchieveService.getScm(21);
            if (mavenAddress == null) {
                throw new ApplicationException("不存在maven配置");
            }
            order = testOrder(testOrder, path, "/");
            return commonAchieveService.process(mavenAddress, order);
        }
        return null;
    }

    /**
     * 拼装测试命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @param buildAddress 模块地址
     * @return 命令
     */
    private String testOrder(String buildOrder,String path,String buildAddress){

        String order;
        int systemType = commonAchieveService.getSystemType();
        order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        if (!Objects.equals(buildAddress, "/")){
            order = order + buildAddress;
        }
        return order;
    }

}
