package io.tiklab.matflow.task.build.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.stages.service.StageInstanceServer;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.build.model.TaskBuild;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.build.model.TaskBuildProductQuery;
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
import java.util.*;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

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

    @Autowired
    StageInstanceServer stageInstanceServer;

    @Autowired
    PipelineInstanceService pipelineInstanceService;

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
        taskBuild.setTaskId(taskId);
        taskBuild.setType(taskType);
        boolean state;
        if (taskType.equals(TASK_BUILD_DOCKER)){
            state = docker(taskBuild, pipelineId);
        }else {
            state = mavenOrNodeJs(taskBuild, pipelineId);
        }

        if (!state){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：" + task.getTaskName()+"执行失败。");
            return false;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行完成");
        return true;
    }

    public Boolean mavenOrNodeJs(TaskBuild taskBuild,String pipelineId){

        String taskId = taskBuild.getTaskId();

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
                Process process ;
                if (type.equals(TASK_BUILD_MAVEN)){
                    process = mavenOrder(key,buildAddress, path);
                }else {
                    process = nodeJsOrder(key,buildAddress, path);
                }
                boolean result = tasksInstanceService.readCommandExecResult(process, null, error(type), taskId);
                if (!result){
                    return false;
                }
            }

            String productRule = taskBuild.getProductRule();

            boolean noNull = PipelineUtil.isNoNull(productRule);
            if (!noNull){
                return true;
            }

        } catch (IOException | ApplicationException e) {
            String s = PipelineUtil.date(4) + e.getMessage();
            tasksInstanceService.writeExecLog(taskId,s);
            return false;
        }
        return true;
    }


    public Boolean docker(TaskBuild taskBuild,String pipelineId){

        String dockerFile = taskBuild.getDockerFile();

        String taskId = taskBuild.getTaskId();
        String type = taskBuild.getType();

        String path = utilService.findPipelineDefaultAddress(pipelineId,1) ;

        if (!Objects.isNull(dockerFile)){
            path = path + dockerFile;
        }

        File file = new File(path + "/Dockerfile");
        if (!file.exists()){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+file.getAbsolutePath()+"下找不到Dockerfile文件！");
            return false;
        }
        boolean result;
        try {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"开始构建镜像");

            String dockerOrder = taskBuild.getDockerOrder();

            // 替换命令中的变量
            String instanceId = findPipelineInstanceId(pipelineId);
            dockerOrder = taskBuildProductService.replace(instanceId,dockerOrder);

            // 替换命令中的系统变量
            String order = variableServer.replaceVariable(pipelineId, taskId, dockerOrder);

            Process process = PipelineUtil.process(path, order);
            result = tasksInstanceService.readCommandExecResult(process, null, error(type), taskId);
            if (!result){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"镜像构建失败");
                return false;
            }
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"镜像构建成功！");
        }catch (Exception e){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"镜像构建失败"+e.getMessage());
            return false;
        }

        // try {
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"保存镜像.....");
        //
        //     String instanceId = findPipelineInstanceId(pipelineId);
        //
        //     String logPath = utilService.findPipelineDefaultAddress(pipelineId,2)+instanceId ;
        //
        //     String imageFile = logPath + "/" + dockerName + ".tar.gz";
        //
        //     String order = "docker save -o  \"" + imageFile + "\" " + name ;
        //
        //     Process process = PipelineUtil.process(path, order);
        //     result = tasksInstanceService.readCommandExecResult(process, null, error(type), taskId);
        //     if (!result){
        //         tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"保存镜像失败");
        //     }
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"保存镜像完成！地址：" + imageFile);
        //
        // }catch (Exception e){
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"保存镜像失败"+e.getMessage());
        //     return false;
        // }
        return result;
    }


    public String findPipelineInstanceId(String pipelineId){

        PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
        pipelineInstanceQuery.setState(PipelineFinal.RUN_RUN);
        pipelineInstanceQuery.setPipelineId(pipelineId);
        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(pipelineInstanceQuery);
        if (pipelineInstanceList.isEmpty()){
            return null;
        }
        PipelineInstance pipelineInstance = pipelineInstanceList.get(0);
        return pipelineInstance.getInstanceId();
    }

    /**
     * 执行build
     * @param path 项目地址
     * @return 执行命令
     */
    private Process mavenOrder(String orders,String address,String path) throws  IOException {
        Scm pipelineScm = scmService.findOnePipelineScm(21);

        if (Objects.isNull(pipelineScm)) {
            throw new ApplicationException("不存在maven配置");
        }

        String serverAddress = pipelineScm.getScmAddress();

        if(PipelineUtil.isNoNull(address)){
            path = path +"/"+ address;
        }

        String order =  mavenOrder(orders,path);
        return PipelineUtil.process(serverAddress, order);
    }

    /**
     * 执行build
     * @param path 项目地址
     * @return 执行命令
     */
    private Process nodeJsOrder(String orders,String address,String path) throws IOException {
        Scm pipelineScm  = scmService.findOnePipelineScm(22);

        if (pipelineScm == null) {
            throw new ApplicationException("不存在npm配置");
        }

        if(PipelineUtil.isNoNull(address)){
            path = path +"/"+ address;
        }

        return PipelineUtil.process(path, orders);
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
        if (type.equals(TASK_BUILD_MAVEN)){
            map.put("BUILD FAILUREl","构建失败！");
            map.put("BUILD FAILURE","构建失败！");
            return map;
        }
        if (type.equals(TASK_BUILD_NODEJS)){
            map.put("npm ERR! errno -4058","npm ERR! errno -4058");
            map.put("npm ERR! code ENOENT","npm ERR! code ENOENT");
            map.put("npm ERR!","");
            return map;
        }
        map.put("Error","构建失败");
        map.put("ERROR","构建失败");
        map.put("docker: Error","Docker构建失败");
        return map;
    }



}
