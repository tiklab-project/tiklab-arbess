package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineTest;
import com.doublekit.pipeline.execute.service.PipelineTestService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class TestAchieve {

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    CommonAchieve commonAchieve;


    public int testStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
       return unitTesting(pipelineConfigure,pipelineExecHistory,pipelineExecHistoryList);
    }

    // 单元测试
    private int unitTesting(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList) {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志
        PipelineExecLog pipelineExecLog = commonAchieve.initializeLog(pipelineExecHistory, pipelineConfigure);
        PipelineTest pipelineTest = pipelineTestService.findOneTest(pipelineConfigure.getTaskId());

        String testOrder = pipelineTest.getTestOrder();
        String path = "D:\\clone\\"+pipelineConfigure.getPipeline().getPipelineName();
        try {
            Process process = commonAchieve.process(path, testOrder, null);
            String a = "执行 : \"" + testOrder  + "\" \n";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+a);
            //设置日志格式
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            int state = commonAchieve.log(inputStreamReader, pipelineExecHistory,pipelineExecHistoryList,pipelineExecLog);
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            if (state == 0){
                commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"测试失败",pipelineExecHistoryList);
                return  0;
            }
        } catch (IOException e) {
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"测试失败",pipelineExecHistoryList);
            return 0;
        }
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }



}
