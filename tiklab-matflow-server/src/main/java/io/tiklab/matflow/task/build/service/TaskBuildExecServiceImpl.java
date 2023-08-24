package io.tiklab.matflow.task.build.service;

import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.build.model.TaskBuild;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class TaskBuildExecServiceImpl implements TaskBuildExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    VariableService variableServer;
    
    @Autowired
    ConditionService conditionService;

    @Autowired
    ScmService scmService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    TaskBuildProductService taskBuildProductService;

    // 构建
    public boolean build(String pipelineId, Tasks task , String taskType)  {

        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }

        TaskBuild taskBuild = (TaskBuild) task.getTask();
        String name = task.getTaskName();
        taskBuild.setType(taskType);
        String buildAddress = taskBuild.getBuildAddress();
        String buildOrder = taskBuild.getBuildOrder();

        //项目地址
        String path = utilService.findPipelineDefaultAddress(pipelineId,1);
        String  type = taskBuild.getType();
        try {
            //执行命令
            List<String> list = PipelineUtil.execOrder(buildOrder);
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, taskId, s);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行命令："+ key);
                Process process = getOrder(key,type,buildAddress, path);
                boolean result = tasksInstanceService.readCommandExecResult(process, null, error(type), taskId);
                if (!result){
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
                    return false;
                }
            }

            String productRule = taskBuild.getProductRule();

            boolean noNull = PipelineUtil.isNoNull(productRule);
            if (!noNull){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行完成");
                return true;
            }

            // 匹配制品
            String productAddress;
            try {
                productAddress = utilService.findFile(pipelineId,taskBuild.getProductRule());
            }catch (ApplicationException e){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+e);
                return false;
            }

            if (path == null){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"匹配不到制品！");
                return false;
            }

            File file = new File(productAddress);

            // 默认路径
            TaskInstance execInstance = tasksInstanceService.findExecInstance(taskId);

            String defaultAddress = utilService.findPipelineDefaultAddress(pipelineId, 2);

            String fileAddress = defaultAddress + execInstance.getInstanceId()+"/"+file.getName();

            // 创建流水线运行时产生的制品信息
            TaskBuildProduct taskBuildProduct = new TaskBuildProduct(execInstance.getInstanceId());
            taskBuildProduct.setKey(PipelineFinal.DEFAULT_ARTIFACT_ADDRESS);
            taskBuildProduct.setValue(fileAddress);
            taskBuildProduct.setType(PipelineFinal.DEFAULT_TYPE);

            TaskBuildProduct taskBuildProducts = new TaskBuildProduct(execInstance.getInstanceId());
            taskBuildProducts.setKey(PipelineFinal.DEFAULT_ARTIFACT_NAME);
            taskBuildProducts.setValue(file.getName());
            taskBuildProducts.setType(PipelineFinal.DEFAULT_TYPE);

            taskBuildProductService.createBuildProduct(taskBuildProduct);
            taskBuildProductService.createBuildProduct(taskBuildProducts);

            // 移动文件
            FileUtils.moveFile(file, new File(fileAddress));

            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"匹配到制品："+productAddress);


        } catch (IOException | ApplicationException e) {
            String s = PipelineUtil.date(4) + e.getMessage();
            tasksInstanceService.writeExecLog(taskId,s);
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行完成");
        return true;
    }

    /**
     * 执行build
     * @param type 任务类型
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(String orders,String type,String address,String path) throws ApplicationException, IOException {
        Scm pipelineScm = null;
        if (type.equals("21") || type.equals("maven")){
            pipelineScm = scmService.findOnePipelineScm(21);
        }
        if (type.equals("22") || type.equals("nodejs")){
            pipelineScm = scmService.findOnePipelineScm(22);
        }

        if (pipelineScm == null) {
            if (type.equals("21") || type.equals("maven")){
                throw new ApplicationException("不存在maven配置");
            }
            if (type.equals("22") || type.equals("nodejs")){
                throw new ApplicationException("不存在npm配置");
            }
            throw new ApplicationException("获取环境配置信息失败！");
        }

        String serverAddress = pipelineScm.getScmAddress();

        if(PipelineUtil.isNoNull(address)){
            path = path +"/"+ address;
        }

        switch (type){
            case "21","maven" -> {
                String order =  mavenOrder(orders,path);
                return PipelineUtil.process(serverAddress, order);
            }
            case "22","nodejs" -> {
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
    private Map<String,String> error(String type){
        Map<String,String> map = new HashMap<>();
        if (type.equals("21") || type.equals("maven")){
            map.put("BUILD FAILUREl","构建失败！");
            map.put("BUILD FAILURE","构建失败！");
            return map;
        }
        map.put("npm ERR! errno -4058","npm ERR! errno -4058");
        map.put("npm ERR! code ENOENT","npm ERR! code ENOENT");
        map.put("npm ERR!","");
        return map;
    }



}
