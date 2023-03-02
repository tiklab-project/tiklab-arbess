package net.tiklab.matflow.task.build.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.task.task.service.PipelineTasksService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecLogService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.build.model.TaskBuild;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class TaskBuildExecServiceImpl implements TaskBuildExecService {

    @Autowired
    private PipelineExecLogService commonService;

    @Autowired
    TaskBuildService buildService;

    @Autowired
    PipelineTasksService tasksService;



    // 构建
    public boolean build(PipelineProcess pipelineProcess,String configId ,int taskType)  {

        Pipeline pipeline = pipelineProcess.getPipeline();

        Object o = null;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            // o = stagesTaskServer.findOneStagesTasksTask(configId);
        }

        TaskBuild taskBuild = (TaskBuild) o;
        // String name = taskBuild.getName();

        Boolean variableCond = commonService.variableCondition(pipeline.getId(), configId);
        // if (!variableCond){
        //     commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
        //     return true;
        // }
        //
        // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"执行任务："+name);

        taskBuild.setType(taskType);
        String buildAddress = taskBuild.getBuildAddress();
        String buildOrder = taskBuild.getBuildOrder();

        //项目地址
        String path = PipelineUtil.findFileAddress(pipeline.getId(),1);
        int type = taskBuild.getType();
        try {
            //执行命令
            List<String> list = PipelineUtil.execOrder(buildOrder);
            for (String s : list) {
                String key = commonService.replaceVariable(pipeline.getId(), configId, s);
                commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"执行命令："+ key);
                pipelineProcess.setError(error(type));
                Process process = getOrder(key,type,buildAddress, path);
                // commonService.commandExecState(pipelineProcess,process,name);
            }
        } catch (IOException | ApplicationException e) {
            String s = PipelineUtil.date(4) + e.getMessage();
            commonService.writeExecLog(pipelineProcess,s);
            return false;
        }
        // commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"任务"+name+"执行完成");
        return true;
    }

    /**
     * 执行build
     * @param type 任务类型
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(String orders,int type,String address,String path) throws ApplicationException, IOException {

        String serverAddress = commonService.getScm(type);

        if (serverAddress == null) {
            if (type == 21){
                throw new ApplicationException("不存在maven配置");
            }
            if (type == 22){
                throw new ApplicationException("不存在npm配置");
            }
        }

        if(PipelineUtil.isNoNull(address)){
            path = path +"/"+ address;
        }

        switch (type){
            case 21 -> {
                String order =  mavenOrder(orders,path);
                return PipelineUtil.process(serverAddress, order);
            }
            case 22 -> {
                return PipelineUtil.process(path, orders);
            }
            default -> throw new  ApplicationException("未知的任务类型");
        }
    }

    /**
     * 拼装测试命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @return 命令
     */
    public String mavenOrder(String buildOrder,String path){
        String order;
        int systemType = PipelineUtil.findSystemType();
        order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }

    /**
     * 错误状态
     * @param type 任务类型
     * @return 错误状态
     */
    private String[] error(int type){
        String[] strings;
        if (type == 21){
            strings = new String[]{
                    "BUILD FAILUREl",
                    "ERROR"
            };
            return strings;
        }
        strings = new String[]{
                "npm ERR! errno -4058",
                "npm ERR! code ENOENT"
        };
        return strings;
    }



}
