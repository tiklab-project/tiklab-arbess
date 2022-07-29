package com.tiklab.matfiow.instance.service.execAchieveImpl;

import com.tiklab.matfiow.definition.model.PipelineConfigure;
import com.tiklab.matfiow.definition.service.PipelineCommonService;
import com.tiklab.matfiow.execute.model.PipelineTest;
import com.tiklab.matfiow.execute.service.PipelineTestService;
import com.tiklab.matfiow.instance.model.PipelineExecHistory;
import com.tiklab.matfiow.instance.model.PipelineExecLog;
import com.tiklab.matfiow.instance.model.PipelineProcess;
import com.tiklab.matfiow.instance.service.execAchieveService.TestAchieveService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class TestAchieveServiceImpl implements TestAchieveService {

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl;

    @Autowired
    PipelineCommonService pipelineCommonService;

    // 单元测试
    public int test(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList) {

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志

        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineConfigure pipelineConfigure = pipelineProcess.getPipelineConfigure();

        PipelineTest pipelineTest = pipelineTestService.findOneTest(pipelineConfigure.getTaskId());
        PipelineExecLog pipelineExecLog = commonAchieveServiceImpl.initializeLog(pipelineExecHistory, pipelineConfigure);
        pipelineProcess.setPipelineExecLog(pipelineExecLog);

        String testOrder = pipelineTest.getTestOrder();
        String path = pipelineCommonService.getFileAddress()+pipelineConfigure.getPipeline().getPipelineName();
        try {
            Process process = commonAchieveServiceImpl.process(path, testOrder, null);
            String a = "------------------------------------" + " \n"
                    +"开始测试" + " \n"
                    + "执行 : \"" + testOrder + "\"\n";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+a);
            //设置日志格式
            //InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            int state = commonAchieveServiceImpl.log(process.getInputStream(), pipelineProcess,pipelineExecHistoryList);
            commonAchieveServiceImpl.updateTime(pipelineProcess,beginTime);
            if (state == 0){
                commonAchieveServiceImpl.updateState(pipelineProcess,"测试失败",pipelineExecHistoryList);
                return  0;
            }
        } catch (IOException e) {
            commonAchieveServiceImpl.updateState(pipelineProcess,"测试失败",pipelineExecHistoryList);
            return 0;
        }
        commonAchieveServiceImpl.updateState(pipelineProcess,null,pipelineExecHistoryList);
        return 1;
    }



}
