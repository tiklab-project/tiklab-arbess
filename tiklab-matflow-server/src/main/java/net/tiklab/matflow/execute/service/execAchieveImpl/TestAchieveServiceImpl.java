package net.tiklab.matflow.execute.service.execAchieveImpl;

import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.service.MatFlowCommonService;
import net.tiklab.matflow.definition.model.MatFlowTest;
import net.tiklab.matflow.definition.service.MatFlowTestService;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.service.MatFlowExecServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveService.TestAchieveService;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 测试执行方法
 */

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
    public int test(MatFlowProcess matFlowProcess) {
        List<MatFlowExecHistory> matFlowExecHistoryList = MatFlowExecServiceImpl.matFlowExecHistoryList;

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
            Process process = commonAchieveServiceImpl.process(path, testOrder);
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
