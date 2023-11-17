package io.tiklab.matflow.task.test.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.matflow.task.test.model.*;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

/**
 * 测试执行方法
 */

@Service
@Exporter
public class TaskTestExecServiceImpl implements TaskTestExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    VariableService variableServer;

    @Autowired
    ScmService scmService;

    @Autowired
    ConditionService conditionService;

    @Autowired
    TaskTestOnService taskTestOnService;

    @Autowired
    AuthThirdService authThirdService;

    @Autowired
    RelevanceTestOnService relevanceTestOnService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    MavenTestService mavenTestService;

    private static final Logger logger = LoggerFactory.getLogger(TaskTestExecServiceImpl.class);


    // 单元测试
    public boolean test(String pipelineId, Tasks task , String taskType) {

        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }

        TaskTest taskTest = (TaskTest) task.getValues();
        taskTest.setType(taskType);

        switch (taskType){
            case TASK_TEST_MAVENTEST -> {
                return execTestMaven(taskId,taskTest,pipelineId,task);
            }

            case TASK_TEST_TESTON ->{
                return execTestOn(pipelineId,taskId,taskTest);
            }
            default -> {
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"未知的测试类型："+taskType);
                return false;
            }
        }
    }


    private boolean execTestOn(String pipelineId,String taskId, TaskTest taskTest){

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行TestOn测试：");

        String authId = taskTest.getAuthId();

        // 执行必要信息
        TestOnRepository repository = taskTest.getTestSpace();
        TestOnTestPlan testPlan = taskTest.getTestPlan();
        TestOnPlanTestData testData = new TestOnPlanTestData();
        // 环境
        if (!Objects.isNull(taskTest.getWebEnv())){
            testData.setWebEnv(taskTest.getWebEnv().getUrl());
        }
        if (!Objects.isNull(taskTest.getAppEnv())){
            testData.setAppEnv(taskTest.getAppEnv().getUrl());
        }
        if (!Objects.isNull(taskTest.getApiEnv())){
            testData.setApiEnv(taskTest.getApiEnv().getUrl());
        }
        testData.setTestPlanId(testPlan.getId());
        testData.setRepositoryId(repository.getId());

        logger.info("TestOn执行信息：" + JSONObject.toJSONString(testData));
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行测试执行中...");
        // 执行testOn
        try {
            taskTestOnService.execTestPlan(authId, testData);
        }catch (Exception e){
            TestPlanExecResult  testPlanExecResult = taskTestOnService.findTestPlanExecResult(authId);
            String instanceId = testPlanExecResult.getTestPlanInstance().getId();
            relevanceTestOnService.createRelevance(pipelineId,instanceId,authId);
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"testOn执行失败："+e.getMessage());
            return false;
        }
        AuthThird authServer = authThirdService.findOneAuthServer(authId);
        Integer status = 0;
        // 获取执行结果
        TestPlanExecResult testPlanExecResult;
        try {
             testPlanExecResult = taskTestOnService.findTestPlanExecResult(authId);
        }catch (Exception e){
            if (e instanceof ApplicationException){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取测试结果失败："+e.getMessage());
                return false;
            }
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"系统异常，获取测试结果失败："+e.getMessage());
            return false;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取测试结果...");

        // 判断是否正在运行
        while (Objects.equals(status,1)){
            try {
                testPlanExecResult = findTestPlanExecResult(taskId,authId);
            }catch (Exception e){
                if (e instanceof ApplicationException){
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取测试结果失败："+e.getMessage());
                    return false;
                }
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"系统异常，获取测试结果失败："+e.getMessage());
                return false;
            }
            status = testPlanExecResult.getStatus();
        }
        String instanceId = testPlanExecResult.getTestPlanInstance().getId();

        // 创建对应的测试关系表
        try {
            relevanceTestOnService.createRelevance(pipelineId,instanceId,authId);
        }catch (Exception e){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取测试结果失败："+e.getMessage());
            return false;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"TestOn执行完成！具体信息请在："+
                authServer.getServerAddress() + "/#/repository/report/"+instanceId+"  查看！");

        return true;
    }

    private TestPlanExecResult findTestPlanExecResult(String taskId,String authId){
        // 获取执行结果
        TestPlanExecResult testPlanExecResult;
        try {
            testPlanExecResult = taskTestOnService.findTestPlanExecResult(authId);
        }catch (Exception e){
            throw new ApplicationException(e);
        }
        TestOnPlanInstance testPlanInstance = testPlanExecResult.getTestPlanInstance();
        // 输出最新结果
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + "------------------------------------------------------------------");
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) +
                "  用例数：" + testPlanInstance.getTotal() +
                "    成功数：" + testPlanInstance.getPassNum() +
                "    失败数：" + testPlanInstance.getFailNum() +
                "    成功率：" + testPlanInstance.getPassRate()
        );
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + "------------------------------------------------------------------");


        return testPlanExecResult;
    }


    private boolean execTestMaven(String taskId, TaskTest taskTest,String pipelineId,Tasks task){

        // 效验maven环境
        Scm pipelineScm = scmService.findOnePipelineScm(21);
        if (Objects.isNull(pipelineScm)) {
            throw new ApplicationException(PipelineUtil.date(4)+"不存在maven配置");
        }
        String mavenAddress = pipelineScm.getScmAddress();
        PipelineUtil.validFile(mavenAddress,TASK_TEST_MAVENTEST);


        //初始化日志
        String address = taskTest.getAddress();
        if (address.contains(DEFAULT_CODE_ADDRESS)){
            String path = utilService.findPipelineDefaultAddress(pipelineId,1);
            address = address.replace(DEFAULT_CODE_ADDRESS,path);
        }

        File file = new File(address);
        if (!file.exists() || file.isFile()){
            throw new ApplicationException(PipelineUtil.date(4)+"找不到源代码！");
        }


        String testOrder = taskTest.getTestOrder();

        try {
            List<String> list = PipelineUtil.execOrder(testOrder);
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, taskId, s);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行："+ key );
                Process process = PipelineUtil.process(mavenAddress, testOrder(key, address));

                boolean result = readMavenTestResult(process,pipelineId,taskId,error(taskTest.getType()));

                // boolean result = tasksInstanceService.readCommandExecResult(process, null, error(taskTest.getType()), taskId);

                if (!result){
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：" + task.getTaskName()+"执行失败。");
                    return false;
                }
                process.destroy();
            }
        } catch (IOException e) {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"日志打印失败"+e);
            return false;
        } catch (ApplicationException e) {
            tasksInstanceService.writeExecLog(taskId,e.getMessage());
            return false;
        }
        return true;
    }

    boolean readMavenTestResult(Process process,String pipelineId,String taskId, Map<String,String> error) throws IOException {

        //转换流
        InputStream inputStream = process.getInputStream();
        InputStream errInputStream = process.getErrorStream();

        InputStreamReader inputStreamReader ;
        BufferedReader bufferedReader ;
        if ( Objects.isNull(inputStream)){
            inputStreamReader = PipelineUtil.encode(errInputStream, UTF_8);
        }else {
            inputStreamReader = PipelineUtil.encode(inputStream, UTF_8);
        }

        String s;
        StringBuilder testLog = new StringBuilder();
        bufferedReader = new BufferedReader(inputStreamReader);

        String mavenTestId = mavenTestService.creatMavenTest(new MavenTest());
        boolean skip = true;
        boolean state = true;
        boolean runResult = false;
        String runState = RUN_SUCCESS;
        String errMavenTestId = null;
        //读取执行信息
        while ((s = bufferedReader.readLine()) != null) {

            String s1 = tasksInstanceService.validStatus(s, error);
            if (!Objects.isNull(s1)){
                state = false ;
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + s1);
            }
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + s);

            // 测试开始
            if (s.startsWith("[INFO]  T E S T S")){
                skip = false;
            }
            if (skip){
                continue;
            }

            if (s.contains("-------------------------------------") || s.startsWith("[INFO]  T E S T S")){
                continue;
            }

            testLog.append(PipelineUtil.date(4)).append(s).append("\n");

            // 当前测试类执行结果
            if (s.startsWith("[INFO] Tests ")  && !runResult){
                MavenTest mavenTest = reloadMavenTestResult(s);
                mavenTest.setMessage(testLog.toString());
                mavenTest.setTestId(mavenTestId);
                mavenTestService.creatMavenTest(mavenTest);
                testLog = new StringBuilder();
            }

            // 当前测试类执行结果
            if (s.startsWith("[ERROR] Tests ") && !runResult){
                MavenTest mavenTest = reloadMavenTestResult(s);
                mavenTest.setMessage(testLog.toString());
                mavenTest.setTestId(mavenTestId);
                errMavenTestId = mavenTestService.creatMavenTest(mavenTest);
                runState = RUN_ERROR;
            }

            if (runState.equals(RUN_ERROR) && StringUtils.isEmpty(s) && !runResult){
                MavenTest oneMavenTest = mavenTestService.findOneMavenTest(errMavenTestId);
                oneMavenTest.setMessage(testLog.toString());
                mavenTestService.updateMavenTest(oneMavenTest);
                testLog = new StringBuilder();
            }

            // 测试结束
            if (s.startsWith("[INFO] Results:") ){
                runResult  = true;
            }

            if ((s.startsWith("[INFO] Tests ") || s.startsWith("[ERROR] Tests ")) && runResult){
                MavenTest mavenTest = reloadMavenTestResult(s);
                mavenTest.setId(mavenTestId);
                mavenTest.setPipelineId(pipelineId);
                mavenTest.setTestState(runState);
                mavenTestService.updateMavenTest(mavenTest);
                skip = true;
                runResult  = false;
            }
        }


        //读取err执行信息
        inputStreamReader = PipelineUtil.encode(errInputStream, UTF_8);
        bufferedReader = new BufferedReader(inputStreamReader);

        while ((s = bufferedReader.readLine()) != null) {
            String s1 = tasksInstanceService.validStatus(s, error);
            if (!Objects.isNull(s1)){
                state = false ;
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + s1);
            }
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + s);
        }

        // 关闭
        inputStreamReader.close();
        bufferedReader.close();

        process.destroy();
        return state;
    }

    private MavenTest reloadMavenTestResult(String s){
        MavenTest mavenTest = new MavenTest();

        String[] split = s.split(", ");

        for (int i = 0; i < split.length; i++) {
            if (i > 3){
                continue;
            }
            String[] split1 = split[i].split(": ");
            if (split1[0].contains("run")){
                mavenTest.setAllNumber(split1[1]);
            }
            if (split1[0].contains("Failures")){
                mavenTest.setFailNumber(split1[1]);
            }
            if (split1[0].contains("Errors")){
                mavenTest.setErrorNumber(split1[1]);
            }
            if (split1[0].contains("Skipped")){
                mavenTest.setSkipNumber(split1[1]);
            }
        }

        if (s.contains("<<< FAILURE!")){
            mavenTest.setTestState(RUN_ERROR);
        }else {
            mavenTest.setTestState(RUN_SUCCESS);
        }

        if (!s.contains(" - in ")){
            return mavenTest;
        }

        String[] packagePath = s.split(" - in ");
        mavenTest.setPackagePath(packagePath[1]);
        String[] split2 = packagePath[1].split("\\.");
        mavenTest.setName(split2[split2.length-1]);
        return mavenTest;
    }

    /**
     * 拼装测试命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @return 命令
     */
    private String testOrder(String buildOrder,String path){

        String order;
        int systemType = PipelineUtil.findSystemType();
        order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }

    private Map<String,String> error(String type){
        Map<String,String> map = new HashMap<>();
        map.put("BUILD FAILURE","构建失败！");
        map.put("Compilation failure","编译失败！");
        return map;
    }

}
