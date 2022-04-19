package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.model.PipelineTest;
import com.doublekit.pipeline.example.service.PipelineTestService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStreamReader;
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

    CommonAchieve commonAchieve = new CommonAchieve();


    // 单元测试
    public int unitTesting(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList) {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
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
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                Map<String, String> map = commonAchieve.log(inputStreamReader, pipelineExecHistory);
                if (map.get("state").equals("0")){
                    commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                    pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+map.get("log"));
                    commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"测试执行失败。",pipelineExecHistoryList);
                    return 0;
                }
                pipelineExecLog.setLogRunLog(a+map.get("log"));
            } catch (IOException e) {
                commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
                return 0;
            }
        }
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }



}
