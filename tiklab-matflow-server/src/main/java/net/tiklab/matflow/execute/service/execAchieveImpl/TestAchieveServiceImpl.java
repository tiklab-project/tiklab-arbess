package net.tiklab.matflow.execute.service.execAchieveImpl;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.MatFlowBuild;
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
import java.util.Objects;

/**
 * 测试执行方法
 */

@Service
@Exporter
public class TestAchieveServiceImpl implements TestAchieveService {

    @Autowired
    MatFlowTestService matFlowTestService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveService;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    // 单元测试
    public String test(MatFlowProcess matFlowProcess) {
        List<MatFlowExecHistory> matFlowExecHistoryList = MatFlowExecServiceImpl.matFlowExecHistoryList;

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志

        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();

        MatFlowTest matFlowTest = matFlowTestService.findOneTest(matFlowConfigure.getTaskId());
        MatFlowExecLog matFlowExecLog = commonAchieveService.initializeLog(matFlowExecHistory, matFlowConfigure);
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);

        String testOrder = matFlowTest.getTestOrder();
        String path = matFlowCommonService.getFileAddress()+ matFlowConfigure.getMatFlow().getMatflowName();
        try {

            String a = "------------------------------------" + " \n"
                    +"开始测试" + " \n"
                    + "执行 : \"" + testOrder + "\"\n";
            matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+a);

            Process process = getOrder(matFlowTest,path);

            if (process == null){
                commonAchieveService.updateTime(matFlowProcess,beginTime);
                commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
                return  "命令错误。";
            }

            int state = commonAchieveService.log(process.getInputStream(), matFlowProcess, matFlowExecHistoryList);

            commonAchieveService.updateTime(matFlowProcess,beginTime);
            if (state == 0){
                commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
                return  "Fail";
            }
        } catch (IOException e) {
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            return "日志打印失败"+e;
        } catch (ApplicationException e) {
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            return e.getMessage();
        }
        commonAchieveService.updateState(matFlowProcess,true, matFlowExecHistoryList);
        return null;
    }

    /**
     * 执行build
     * @param matFlowTest 执行信息
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(MatFlowTest matFlowTest, String path ) throws ApplicationException, IOException {
        String testOrder = matFlowTest.getTestOrder();

        int type = matFlowTest.getType();

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
