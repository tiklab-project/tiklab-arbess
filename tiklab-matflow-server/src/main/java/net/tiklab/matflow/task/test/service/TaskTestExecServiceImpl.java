package net.tiklab.matflow.task.test.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.task.task.service.PipelineTasksService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecLogService;
import net.tiklab.matflow.support.util.PipelineUtil;
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
    PipelineExecLogService commonService;

    @Autowired
    PipelineTasksService tasksService;
    
    // 单元测试
    public boolean test(PipelineProcess pipelineProcess, String configId ,int taskType) {
        Pipeline pipeline = pipelineProcess.getPipeline();
        Object o = null;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            // o = stagesTaskServer.findOneStagesTasksTask(configId);
        }
        TaskTest taskTest = (TaskTest) o;
        Boolean variableCond = commonService.variableCondition(pipeline.getId(), configId);
        // String name = taskTest.getName();
        if (!variableCond){
            // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
            return true;
        }
        // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"执行任务："+name);

        taskTest.setType(taskType);

        //初始化日志
        String testOrder = taskTest.getTestOrder();

        String path = PipelineUtil.findFileAddress(pipeline.getId(),1);
        try {

            List<String> list = PipelineUtil.execOrder(testOrder);
            for (String s : list) {
                String key = commonService.replaceVariable(pipeline.getId(), configId, s);
                commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"执行："+ key );
                Process  process = getOrder(taskTest,s,path);
                pipelineProcess.setError(error(taskTest.getType()));
                // commonService.commandExecState(pipelineProcess,process,name);
                process.destroy();
            }
        } catch (IOException e) {
            commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"日志打印失败"+e);
            return false;
        } catch (ApplicationException e) {
            commonService.writeExecLog(pipelineProcess,e.getMessage());
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
            String mavenAddress = commonService.getScm(21);
            if (mavenAddress == null) {
                throw new ApplicationException(PipelineUtil.date(4)+"不存在maven配置");
            }
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
