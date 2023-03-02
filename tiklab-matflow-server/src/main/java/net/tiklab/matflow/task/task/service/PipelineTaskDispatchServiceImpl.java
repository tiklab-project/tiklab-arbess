package net.tiklab.matflow.task.task.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.artifact.model.TaskArtifact;
import net.tiklab.matflow.task.artifact.service.TaskArtifactService;
import net.tiklab.matflow.task.build.model.TaskBuild;
import net.tiklab.matflow.task.build.service.TaskBuildService;
import net.tiklab.matflow.task.code.model.TaskCode;
import net.tiklab.matflow.task.code.service.TaskCodeService;
import net.tiklab.matflow.task.codescan.model.TaskCodeScan;
import net.tiklab.matflow.task.codescan.service.TaskCodeScanService;
import net.tiklab.matflow.task.deploy.model.TaskDeploy;
import net.tiklab.matflow.task.deploy.service.TaskDeployService;
import net.tiklab.matflow.task.test.model.TaskTest;
import net.tiklab.matflow.task.test.service.TaskTestService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineTaskDispatchServiceImpl implements PipelineTaskDispatchService {

    @Autowired
    TaskCodeService codeService;

    @Autowired
    TaskBuildService buildService;

    @Autowired
    TaskTestService testService;

    @Autowired
    TaskDeployService deployService;

    @Autowired
    TaskCodeScanService codeScanService;

    @Autowired
    TaskArtifactService productServer;

    @Override
    public void createDifferentTask(String taskId,int taskType){
        String taskName = initDifferentTaskName(taskType);
        switch (taskType/10) {
            case 0 -> {
                TaskCode task = new TaskCode();
                task.setTaskId(taskId);
                task.setTaskName(taskName);
                codeService.createCode(task);
            }
            case 1 -> {
                TaskTest task = new TaskTest();
                task.setTaskId(taskId);
                task.setTaskName(taskName);
                testService.createTest(task);
            }
            case 2 -> {
                TaskBuild task = new TaskBuild();
                task.setTaskId(taskId);
                task.setTaskName(taskName);
                buildService.createBuild(task);
            }
            case 3 -> {
                TaskDeploy task = new TaskDeploy();
                task.setTaskId(taskId);
                task.setAuthType(1);
                task.setTaskName(taskName);
                deployService.createDeploy(task);
            }
            case 4 -> {
                TaskCodeScan task = new TaskCodeScan();
                task.setTaskId(taskId);
                task.setTaskName(taskName);
                codeScanService.createCodeScan(task);
            }
            case 5 -> {
                TaskArtifact task = new TaskArtifact();
                task.setTaskId(taskId);
                task.setTaskName(taskName);
                productServer.createProduct(task);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    @Override
    public void deleteDifferentTask(String taskId,int taskType){
        switch (taskType/10) {
            case 0 -> codeService.deleteCodeConfig(taskId);
            case 1 -> testService.deleteTestConfig(taskId);
            case 2 -> buildService.deleteBuildConfig(taskId);
            case 3 -> deployService.deleteDeployConfig(taskId);
            case 4 -> codeScanService.deleteCodeScanConfig(taskId);
            case 5 -> productServer.deleteProductConfig(taskId);
            default -> throw new ApplicationException("无法更新未知的配置类型。");
        }

    }

    @Override
    public void updateDifferentTask(String taskId,int taskType,Object o){
        String object = JSON.toJSONString(o);
        switch (taskType/10) {
            case 0 -> {
                TaskCode taskCode = JSON.parseObject(object, TaskCode.class);
                TaskCode oneCodeConfig = codeService.findOneCodeConfig(taskId);
                String id;
                if (oneCodeConfig == null){
                    id = codeService.createCode(new TaskCode());
                }else {
                    id = oneCodeConfig.getTaskId();
                }
                taskCode.setTaskId(id);
                taskCode.setType(taskType);
                codeService.updateCode(taskCode);
            }
            case 1 -> {
                TaskTest taskTest = JSON.parseObject(object, TaskTest.class);
                TaskTest oneTestConfig = testService.findOneTestConfig(taskId);
                String id;
                if (oneTestConfig == null){
                    id = testService.createTest(new TaskTest());
                }else {
                    id = oneTestConfig.getTaskId();
                }
                taskTest.setTaskId(id);
                testService.updateTest(taskTest);
            }
            case 2 -> {
                TaskBuild taskBuild = JSON.parseObject(object, TaskBuild.class);
                TaskBuild oneBuildConfig = buildService.findOneBuildConfig(taskId);
                String id;
                if (oneBuildConfig == null){
                    id = buildService.createBuild(new TaskBuild());
                }else {
                    id = oneBuildConfig.getTaskId();
                }
                taskBuild.setTaskId(id);
                buildService.updateBuild(taskBuild);
            }
            case 3 -> {
                TaskDeploy taskDeploy = JSON.parseObject(object, TaskDeploy.class);
                TaskDeploy oneDeployConfig = deployService.findOneDeployConfig(taskId);
                String id;
                if (oneDeployConfig == null){
                    id = deployService.createDeploy(new TaskDeploy());
                }else {
                    id = oneDeployConfig.getTaskId();
                }
                taskDeploy.setTaskId(id);
                deployService.updateDeploy(taskDeploy);
            }
            case 4 -> {
                TaskCodeScan taskCodeScan = JSON.parseObject(object, TaskCodeScan.class);
                TaskCodeScan oneCodeScanConfig = codeScanService.findOneCodeScanConfig(taskId);
                String id;
                if (oneCodeScanConfig == null){
                    id = codeScanService.createCodeScan(new TaskCodeScan());
                }else {
                    id = oneCodeScanConfig.getTaskId();
                }
                taskCodeScan.setTaskId(id);
                codeScanService.updateCodeScan(taskCodeScan);
            }
            case 5 -> {
                TaskArtifact taskArtifact = JSON.parseObject(object, TaskArtifact.class);
                TaskArtifact oneProductConfig = productServer.findOneProductConfig(taskId);
                String id;
                if (oneProductConfig == null){
                    id = productServer.createProduct(new TaskArtifact());
                }else {
                    id = oneProductConfig.getTaskId();
                }
                taskArtifact.setTaskId(id);
                productServer.updateProduct(taskArtifact);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    @Override
    public String initDifferentTaskName(int taskType){
        switch (taskType) {
            case 1 -> {
                return "通用Git";
            }
            case 2 -> {
                return "Gitee";
            }
            case 3 -> {
                return "GitHub";
            }
            case 4 -> {
                return "GitLab";
            }
            case 5 -> {
                return "Svn";
            }
            case 11 -> {
                return "Maven单元测试";
            }
            case 21 -> {
                return "Maven构建";
            }
            case 22 -> {
                return "Node.js";
            }
            case 31 -> {
                return "虚拟机";
            }
            case 32 -> {
                return "Docker";
            }
            case 41 -> {
                return "sonarQuebe";
            }
            case 51 -> {
                return "Nexus";
            }
            case 52 -> {
                return "SSH";
            }
            case 61 -> {
                return "消息通知";
            }
            case 71 -> {
                return "执行Bat脚本";
            }
            case 72 -> {
                return "执行Shell脚本";
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Object findOneDifferentTask(String configId,int taskType,int taskSort,String name){
        switch (taskType/10) {
            case 0 -> {
                TaskCode oneCodeConfig = codeService.findOneCodeConfig(configId);
                oneCodeConfig.setType(taskType);
                oneCodeConfig.setSort(taskSort);
                return oneCodeConfig;
            }
            case 1 -> {
                TaskTest oneTestConfig = testService.findOneTestConfig(configId);
                oneTestConfig.setType(taskType);
                oneTestConfig.setSort(taskSort);
                return oneTestConfig;
            }
            case 2 -> {
                TaskBuild oneBuildConfig = buildService.findOneBuildConfig(configId);
                oneBuildConfig.setType(taskType);
                oneBuildConfig.setSort(taskSort);
                return oneBuildConfig;
            }
            case 3 -> {
                TaskDeploy oneDeployConfig = deployService.findOneDeployConfig(configId);
                oneDeployConfig.setType(taskType);
                oneDeployConfig.setSort(taskSort);
                return oneDeployConfig;
            }
            case 4 -> {
                TaskCodeScan oneCodeScanConfig = codeScanService.findOneCodeScanConfig(configId);
                oneCodeScanConfig.setType(taskType);
                oneCodeScanConfig.setSort(taskSort);
                return oneCodeScanConfig;
            }
            case 5 -> {
                TaskArtifact oneProductConfig = productServer.findOneProductConfig(configId);
                oneProductConfig.setType(taskType);
                oneProductConfig.setSort(taskSort);
                return oneProductConfig;
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    @Override
    public void validDifferentTaskMastField(String configId, int taskType, List<String> list){
        switch (taskType / 10) {
            case 0 ->  codeValid(configId, list, taskType);
            case 1 ->  testValid(configId, list, taskType);
            case 2 ->  buildValid(configId, list, taskType);
            case 3 ->  deployValid(configId, list, taskType);
            case 4 ->  codeScanValid(configId, list, taskType);
            case 5 ->  productValid(configId, list, taskType);
        }
    }

    public void codeValid(String configId,List<String> list,int taskType){
        TaskCode oneCodeConfig = codeService.findOneCodeConfig(configId);
        if (oneCodeConfig == null){
            return;
        }
        if (!PipelineUtil.isNoNull(oneCodeConfig.getCodeName())){
            list.add(configId);
        }
    }

    public void codeScanValid(String configId,List<String> list,int taskType){
        TaskCodeScan code = codeScanService.findOneCodeScanConfig(configId);
        if (code == null){
            return;
        }
        if (!PipelineUtil.isNoNull(code.getProjectName())){
            list.add(configId);
        }
    }

    public void testValid(String configId,List<String> list,int taskType){

    }

    public void buildValid(String configId,List<String> list,int taskType){

    }

    public void deployValid(String configId,List<String> list,int taskType){
        TaskDeploy deploy = deployService.findOneDeployConfig(configId);
        if (deploy == null){
            return;
        }
        if (taskType == 31){
            if (deploy.getAuthType() == 1){
                if (!PipelineUtil.isNoNull(deploy.getDeployAddress())){
                    list.add(configId);
                }
                if (!PipelineUtil.isNoNull(deploy.getStartAddress())){
                    list.add(configId);
                }
            }
        }
    }

    public void productValid(String configId,List<String> list,int taskType){
        TaskArtifact product = productServer.findOneProductConfig(configId);
        if (product == null){
            return;
        }
        if (taskType == 51){
            if (!PipelineUtil.isNoNull(product.getArtifactId())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getVersion())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getGroupId())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getFileAddress())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getFileType())){
                list.add(configId);
            }
        }
        if (taskType == 52){
            if (!PipelineUtil.isNoNull(product.getFileAddress())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getPutAddress())){
                list.add(configId);
            }
        }
    }




}
