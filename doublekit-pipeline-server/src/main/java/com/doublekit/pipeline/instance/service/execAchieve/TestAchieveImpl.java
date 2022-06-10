package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineTest;
import com.doublekit.pipeline.execute.service.PipelineTestService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;


public class TestAchieveImpl {

    @Autowired
    PipelineTestService pipelineTestService;


    // 单元测试
    public int test(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList) {

        CommonAchieveImpl commonAchieveImpl = new CommonAchieveImpl();

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志

        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineConfigure pipelineConfigure = pipelineProcess.getPipelineConfigure();

        PipelineTest pipelineTest = pipelineTestService.findOneTest(pipelineConfigure.getTaskId());
        PipelineExecLog pipelineExecLog = commonAchieveImpl.initializeLog(pipelineExecHistory, pipelineConfigure);
        pipelineProcess.setPipelineExecLog(pipelineExecLog);

        String testOrder = pipelineTest.getTestOrder();
        String path = "D:\\clone\\"+pipelineConfigure.getPipeline().getPipelineName();
        try {
            Process process = commonAchieveImpl.process(path, testOrder, null);
            String a = "------------------------------------" + " \n"
                    +"开始测试" + " \n"
                    + "执行 : \"" + testOrder + "\"\n";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+a);
            //设置日志格式
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            int state = commonAchieveImpl.log(inputStreamReader, pipelineProcess,pipelineExecHistoryList);
            commonAchieveImpl.updateTime(pipelineProcess,beginTime);
            if (state == 0){
                commonAchieveImpl.updateState(pipelineProcess,"测试失败",pipelineExecHistoryList);
                return  0;
            }
        } catch (IOException e) {
            commonAchieveImpl.updateState(pipelineProcess,"测试失败",pipelineExecHistoryList);
            return 0;
        }
        commonAchieveImpl.updateState(pipelineProcess,null,pipelineExecHistoryList);
        return 1;
    }



}
