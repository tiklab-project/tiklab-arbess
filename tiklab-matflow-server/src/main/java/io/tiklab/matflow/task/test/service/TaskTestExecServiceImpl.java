package io.tiklab.matflow.task.test.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.matflow.task.test.model.*;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 测试执行方法
 */

@Service
@Exporter
public class TaskTestExecServiceImpl implements TaskTestExecService {

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private VariableService variableServer;

    @Autowired
    private ScmService scmService;

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private TaskTestOnService taskTestOnService;

    @Autowired
    private AuthThirdService authThirdService;

    @Autowired
    private RelevanceTestOnService relevanceTestOnService;

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
            case "11","maventest" -> {
                return execTestMaven(taskId,taskTest,pipelineId,task);
            }
            case "teston" ->{
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
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行测试失败："+e.getMessage());
            return false;
        }

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
        TestOnPlanInstance testPlanInstance = testPlanExecResult.getTestPlanInstance();
        // 判断是否正在运行
        while (Objects.equals(status,1)){
            // 获取最新结果
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
            // 输出最新结果
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + "------------------------------------------------------------------");
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) +
                    "  用例数："+testPlanInstance.getTotal() +
                    "    成功数："+testPlanInstance.getFailNum() +
                    "    失败数："+testPlanInstance.getFailNum() +
                    "    成功率："+testPlanInstance.getPassRate()
            );
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + "------------------------------------------------------------------");
            testPlanInstance = testPlanExecResult.getTestPlanInstance();
            status = testPlanExecResult.getStatus();
        }
        // 输出最终结果
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + "------------------------------------------------------------------");
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) +
                "  用例数："+testPlanInstance.getTotal() +
                "    成功数："+testPlanInstance.getFailNum() +
                "    失败数："+testPlanInstance.getFailNum() +
                "    成功率："+testPlanInstance.getPassRate()
        );
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4) + "------------------------------------------------------------------");

        String instanceId = testPlanExecResult.getTestPlanInstance().getId();

        // 创建对应的测试关系表
        try {
            relevanceTestOnService.createRelevance(pipelineId,instanceId);
        }catch (Exception e){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取测试结果失败："+e.getMessage());
            return false;
        }
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"TestOn执行完成！具体信息请在："+authServer.getServerAddress()+"  查看！");

        return true;
    }


    private boolean execTestMaven(String taskId, TaskTest taskTest,String pipelineId,Tasks task){
        //初始化日志
        String testOrder = taskTest.getTestOrder();
        String path = PipelineUtil.findFileAddress(pipelineId,1);
        try {
            List<String> list = PipelineUtil.execOrder(testOrder);
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, taskId, s);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行："+ key );
                Process process = getOrder(taskTest,key,path);
                boolean result = tasksInstanceService.readCommandExecResult(process, null, error(taskTest.getType()), taskId);
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

    /**
     * 执行build
     * @param taskTest 执行信息
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(TaskTest taskTest, String testOrder, String path ) throws ApplicationException, IOException {
        String type = taskTest.getType();
        String order ;
        if (type.equals("11") || type.equals("maventest")) {
            Scm pipelineScm = scmService.findOnePipelineScm(21);

            if (pipelineScm == null) {
                throw new ApplicationException(PipelineUtil.date(4)+"不存在maven配置");
            }
            String mavenAddress = pipelineScm.getScmAddress();
            PipelineUtil.validFile(mavenAddress,"maventest");
            order = testOrder(testOrder, path);
            return PipelineUtil.process(mavenAddress, order);
        }else {
            throw  new ApplicationException(PipelineUtil.date(4)+"未知的任务类型");
        }
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

    private String[] error(String type){
        String[] strings;
        strings = new String[]{
            "BUILD FAILURE","ERROR","Compilation failure"
        };
        return strings;
    }

}
