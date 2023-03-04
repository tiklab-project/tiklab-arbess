package net.tiklab.matflow.task.test.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.setting.model.Scm;
import net.tiklab.matflow.setting.service.ScmService;
import net.tiklab.matflow.support.condition.service.ConditionService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.support.variable.service.VariableService;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.TasksInstanceService;
import net.tiklab.matflow.task.test.model.TaskTest;
import net.tiklab.rpc.annotation.Exporter;
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
    
    // 单元测试
    public boolean test(String pipelineId, Tasks task , int taskType) {

        String taskId = task.getTaskId();
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }


        TaskTest taskTest = (TaskTest) task.getValues();

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行任务："+ task.getTaskName());

        taskTest.setType(taskType);

        //初始化日志
        String testOrder = taskTest.getTestOrder();

        String path = PipelineUtil.findFileAddress(pipelineId,1);
        try {

            List<String> list = PipelineUtil.execOrder(testOrder);
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, "configId", s);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行："+ key );
                Process process = getOrder(taskTest,key,path);
                tasksInstanceService.readCommandExecResult(process,null,error(taskTest.getType()),taskId);
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
        int type = taskTest.getType();
        String order ;
        if (type == 11) {
            Scm pipelineScm = scmService.findOnePipelineScm(21);

            if (pipelineScm == null) {
                throw new ApplicationException(PipelineUtil.date(4)+"不存在maven配置");
            }
            String mavenAddress = pipelineScm.getScmAddress();
            PipelineUtil.validFile(mavenAddress,21);
            order = testOrder(testOrder, path);
            return PipelineUtil.process(mavenAddress, order);
        }else {
            throw  new ApplicationException("未知的任务类型");
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

    private String[] error(int type){
        String[] strings;
        strings = new String[]{
            "BUILD FAILUREl","ERROR"
        };
        return strings;
    }

}
