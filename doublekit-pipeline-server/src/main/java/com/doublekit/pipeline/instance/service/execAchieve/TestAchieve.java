package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.execute.model.PipelineTest;
import com.doublekit.pipeline.execute.service.PipelineTestService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class TestAchieve {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    CommonAchieve commonAchieve;


    public String testStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
       return unitTesting(pipelineConfigure,pipelineExecHistory,pipelineExecHistoryList);
    }

    // 单元测试
    private String unitTesting(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList) {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineTest pipelineTest = pipelineTestService.findOneTest(pipelineConfigure.getTaskId());
        String testOrder = pipelineTest.getTestOrder();
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = testOrder.split("\n");
        for (String s : split) {
            try {
                Process process = commonAchieve.process(path, s, null);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog() + a);
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
                Map<String, String> map = commonAchieve.log(inputStreamReader, pipelineExecHistory,pipelineExecHistoryList);
                if (map.get("state").equals("0")){
                    pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+map.get("log"));
                    commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"日志打印错误",pipelineExecHistoryList);
                    commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                    return "测试执行失败。";
                }
                pipelineExecLog.setRunLog(a+map.get("log"));
            } catch (IOException e) {
                commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"测试命令错误",pipelineExecHistoryList);
                commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                return e.toString();
            }
        }
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return null;
    }



}
