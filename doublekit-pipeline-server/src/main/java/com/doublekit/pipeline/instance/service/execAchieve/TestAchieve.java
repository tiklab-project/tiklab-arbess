package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.service.codeGit.CodeGiteeApiService;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineTestLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
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
    PipelineExecLogService pipelineExecLogService;

    CommonAchieve commonAchieve = new CommonAchieve();


    // 单元测试
    private int unitTesting(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog,List<PipelineExecLog> pipelineExecLogList) {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineTestLog testLog = pipelineExecLog.getTestLog();
        String testOrder = pipelineConfigure.getPipelineTest().getTestOrder();
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = testOrder.split("\n");
        for (String s : split) {
            try {
                Process process = commonAchieve.process(path, s, null);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + a);
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                Map<String, String> map = commonAchieve.log(inputStreamReader, pipelineExecLog);
                if (map.get("state").equals("0")){
                    testLog.setTestRunLog(testLog.getTestRunLog()+map.get("log"));
                    testState(pipelineExecLog,beginTime,"测试执行失败。",pipelineExecLogList);
                    return 0;
                }
                testLog.setTestRunLog(a+map.get("log"));
            } catch (IOException e) {
                testState(pipelineExecLog,beginTime,e.toString(),pipelineExecLogList);
                return 0;
            }
        }
        testState(pipelineExecLog,beginTime,null,pipelineExecLogList);
        return 1;
    }

    private  void testState(PipelineExecLog pipelineExecLog,long beginTime,String e,List<PipelineExecLog> pipelineExecLogList){
        PipelineTestLog testLog = pipelineExecLog.getTestLog();
        testLog.setTestRunStatus(10);
        if (e != null){
            testLog.setTestRunStatus(1);
            commonAchieve.error(pipelineExecLog,"测试执行失败。", pipelineExecLog.getPipelineId());
        }
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        testLog.setTestRunTime(time);
        pipelineExecLog.setTestLog(testLog);
        pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecLogList.add(pipelineExecLog);
    }
}
