package com.tiklab.matflow.instance.service.execAchieveImpl;

import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.service.MatFlowCommonService;
import com.tiklab.matflow.execute.model.MatFlowTest;
import com.tiklab.matflow.execute.service.MatFlowTestService;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.matflow.instance.model.MatFlowProcess;
import com.tiklab.matflow.instance.service.execAchieveService.TestAchieveService;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class TestAchieveServiceImpl implements TestAchieveService {

    @Autowired
    MatFlowTestService matFlowTestService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    // 单元测试
    public int test(MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) {

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志

        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();

        MatFlowTest matFlowTest = matFlowTestService.findOneTest(matFlowConfigure.getTaskId());
        MatFlowExecLog matFlowExecLog = commonAchieveServiceImpl.initializeLog(matFlowExecHistory, matFlowConfigure);
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);

        String testOrder = matFlowTest.getTestOrder();
        String path = matFlowCommonService.getFileAddress()+ matFlowConfigure.getMatFlow().getMatflowName();
        try {
            Process process = commonAchieveServiceImpl.process(path, testOrder, null);
            String a = "------------------------------------" + " \n"
                    +"开始测试" + " \n"
                    + "执行 : \"" + testOrder + "\"\n";
            matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+a);
            //设置日志格式
            //InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            int state = commonAchieveServiceImpl.log(process.getInputStream(), matFlowProcess, matFlowExecHistoryList);
            commonAchieveServiceImpl.updateTime(matFlowProcess,beginTime);
            if (state == 0){
                commonAchieveServiceImpl.updateState(matFlowProcess,"测试失败", matFlowExecHistoryList);
                return  0;
            }
        } catch (IOException e) {
            commonAchieveServiceImpl.updateState(matFlowProcess,"测试失败", matFlowExecHistoryList);
            return 0;
        }
        commonAchieveServiceImpl.updateState(matFlowProcess,null, matFlowExecHistoryList);
        return 1;
    }



}
