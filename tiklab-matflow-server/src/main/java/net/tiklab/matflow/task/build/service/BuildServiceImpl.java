package net.tiklab.matflow.task.build.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesTaskService;
import net.tiklab.matflow.pipeline.definition.service.PipelineTasksService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.task.build.model.PipelineBuild;
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
public class BuildServiceImpl implements BuildService {

    @Autowired
    private PipelineExecCommonService commonService;

    @Autowired
    PipelineBuildService buildService;

    @Autowired
    PipelineTasksService tasksService;

    @Autowired
    PipelineStagesTaskService stagesTaskServer;


    // 构建
    public boolean build(PipelineProcess pipelineProcess,String configId ,int taskType)  {

        Pipeline pipeline = pipelineProcess.getPipeline();

        Object o;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            o = stagesTaskServer.findOneStagesTasksTask(configId);
        }

        PipelineBuild pipelineBuild = (PipelineBuild) o;
        String name = pipelineBuild.getName();

        Boolean variableCond = commonService.variableCond(pipeline.getId(), configId);
        if (!variableCond){
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
            return true;
        }

        commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"执行任务："+name);

        pipelineBuild.setType(taskType);
        String buildAddress = pipelineBuild.getBuildAddress();
        String buildOrder = pipelineBuild.getBuildOrder();

        //项目地址
        String path = PipelineUntil.findFileAddress(pipeline.getId(),1);
        int type = pipelineBuild.getType();
        try {
            //执行命令
            List<String> list = PipelineUntil.execOrder(buildOrder);
            for (String s : list) {
                String key = commonService.variableKey(pipeline.getId(), configId, s);
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"执行命令："+ key);
                pipelineProcess.setError(error(type));
                Process process = getOrder(key,type,buildAddress, path);
                commonService.execState(pipelineProcess,process,name);
            }
        } catch (IOException | ApplicationException e) {
            String s = PipelineUntil.date(4) + e.getMessage();
            commonService.updateExecLog(pipelineProcess,s);
            return false;
        }
        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"任务"+name+"执行完成");
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

        if(PipelineUntil.isNoNull(address)){
            path = path +"/"+ address;
        }

        switch (type){
            case 21 -> {
                String order =  mavenOrder(orders,path);
                return PipelineUntil.process(serverAddress, order);
            }
            case 22 -> {
                return PipelineUntil.process(path, orders);
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
        int systemType = PipelineUntil.findSystemType();
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
