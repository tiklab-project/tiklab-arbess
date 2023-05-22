package io.tiklab.matflow.task.test.service;

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
import io.tiklab.matflow.task.test.model.TaskTest;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.teston.testplan.execute.model.TestPlanTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
                return execTestOn(taskId,taskTest);
            }
            default -> {
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"未知的测试类型："+taskType);
                return false;
            }
        }
        // //初始化日志
        // String testOrder = taskTest.getTestOrder();
        // String path = PipelineUtil.findFileAddress(pipelineId,1);
        // try {
        //     List<String> list = PipelineUtil.execOrder(testOrder);
        //     for (String s : list) {
        //         String key = variableServer.replaceVariable(pipelineId, taskId, s);
        //         tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行："+ key );
        //         Process process = getOrder(taskTest,key,path);
        //         boolean result = tasksInstanceService.readCommandExecResult(process, null, error(taskType), taskId);
        //         if (!result){
        //             tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
        //             return false;
        //         }
        //         process.destroy();
        //     }
        // } catch (IOException e) {
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"日志打印失败"+e);
        //     return false;
        // } catch (ApplicationException e) {
        //     tasksInstanceService.writeExecLog(taskId,e.getMessage());
        //     return false;
        // }
    }

    private boolean execTestOn(String taskId, TaskTest taskTest){

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行TestOn测试：");

        String authId = taskTest.getAuthId();

        // String testSpaceName = taskTest.getTestSpace();
        // String testPlanName = taskTest.getTestPlan();

        // Repository repository;
        // try {
        //     repository = taskTestOnService.findOneRepository(authId, testSpaceName);
        // }catch (Exception e){
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取仓库地址失败："+e.getMessage());
        //     return false;
        // }
        //
        // TestPlan testPlan;
        // try {
        //     testPlan = taskTestOnService.findOneTestPlan(authId, testPlanName);
        // }catch (Exception e){
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取测试计划失败："+e.getMessage());
        //     return false;
        // }


        TestPlanTestData testData = new TestPlanTestData();
        // testData.setApiEnv(apiEnv);
        // testData.setAppEnv(appEnv);
        // testData.setWebEnv(webEnv);
        // testData.setTestPlanId(testPlan.getId());
        // testData.setRepositoryId(repository.getId());

        try {
            taskTestOnService.execTestPlan(authId,testData);
        }catch (Exception e){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行测试失败："+e.getMessage());
            return false;
        }
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"TestOn执行完成！具体信息请在："+authServer.getServerAddress()+"查看！");

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
