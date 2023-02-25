package net.tiklab.matflow.pipeline.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.task.build.model.TaskBuild;
import net.tiklab.matflow.task.build.service.TaskBuildService;
import net.tiklab.matflow.task.code.model.TaskCode;
import net.tiklab.matflow.task.code.service.TaskCodeService;
import net.tiklab.matflow.task.codescan.model.TaskCodeScan;
import net.tiklab.matflow.task.codescan.service.TaskCodeScanService;
import net.tiklab.matflow.task.deploy.model.TaskDeploy;
import net.tiklab.matflow.task.deploy.service.TaskDeployService;
import net.tiklab.matflow.task.artifact.model.TaskProduct;
import net.tiklab.matflow.task.artifact.service.TaskProductService;
import net.tiklab.matflow.task.test.model.TaskTest;
import net.tiklab.matflow.task.test.service.TaskTestService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineTaskCommonServiceImpl implements PipelineTaskCommonService {

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
    TaskProductService productServer;

    /**
     * 创建任务
     * @param configId 配置id
     * @return 任务id
     */
    @Override
    public String createTaskConfig(String configId,int taskType){
        switch (taskType/10) {
            case 0 -> {
                TaskCode taskCode = new TaskCode();
                taskCode.setConfigId(configId);
                return codeService.createCode(taskCode);
            }
            case 1 -> {
                TaskTest taskTest = new TaskTest();
                taskTest.setConfigId(configId);
                return testService.createTest(taskTest);
            }
            case 2 -> {
                TaskBuild taskBuild = new TaskBuild();
                taskBuild.setConfigId(configId);
                return buildService.createBuild(taskBuild);
            }
            case 3 -> {
                TaskDeploy taskDeploy = new TaskDeploy();
                taskDeploy.setConfigId(configId);
                taskDeploy.setAuthType(1);
                return deployService.createDeploy(taskDeploy);
            }
            case 4 -> {
                TaskCodeScan taskCodeScan = new TaskCodeScan();
                taskCodeScan.setConfigId(configId);
                return  codeScanService.createCodeScan(taskCodeScan);
            }
            case 5 -> {
                TaskProduct taskProduct = new TaskProduct();
                taskProduct.setConfigId(configId);
                return productServer.createProduct(taskProduct);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    /**
     * 删除任务
     * @param configId 配置
     */
    @Override
    public void deleteTaskConfig(String configId,int taskType){
        switch (taskType/10) {
            case 0 -> {
                codeService.deleteCodeConfig(configId);
            }
            case 1 -> {
                testService.deleteTestConfig(configId);
            }
            case 2 -> {
                buildService.deleteBuildConfig(configId);
            }
            case 3 -> {
                deployService.deleteDeployConfig(configId);
            }
            case 4 -> {
                codeScanService.deleteCodeScanConfig(configId);
            }
            case 5 -> {
                productServer.deleteProductConfig(configId);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }

    }

    /**
     * 更新任务
     * @param configId 配置
     */
    @Override
    public void updateTaskConfig(String configId,int taskType,Object o){
        String object = JSON.toJSONString(o);
        switch (taskType/10) {
            case 0 -> {
                TaskCode taskCode = JSON.parseObject(object, TaskCode.class);
                TaskCode oneCodeConfig = codeService.findOneCodeConfig(configId);
                String id;
                if (oneCodeConfig == null){
                    id = codeService.createCode(new TaskCode());
                }else {
                    id = oneCodeConfig.getCodeId();
                }
                taskCode.setCodeId(id);
                taskCode.setType(taskType);
                codeService.updateCode(taskCode);
            }
            case 1 -> {
                TaskTest taskTest = JSON.parseObject(object, TaskTest.class);
                TaskTest oneTestConfig = testService.findOneTestConfig(configId);
                String id;
                if (oneTestConfig == null){
                    id = testService.createTest(new TaskTest());
                }else {
                    id = oneTestConfig.getTestId();
                }
                taskTest.setTestId(id);
                testService.updateTest(taskTest);
            }
            case 2 -> {
                TaskBuild taskBuild = JSON.parseObject(object, TaskBuild.class);
                TaskBuild oneBuildConfig = buildService.findOneBuildConfig(configId);
                String id;
                if (oneBuildConfig == null){
                    id = buildService.createBuild(new TaskBuild());
                }else {
                    id = oneBuildConfig.getBuildId();
                }
                taskBuild.setBuildId(id);
                buildService.updateBuild(taskBuild);
            }
            case 3 -> {
                TaskDeploy taskDeploy = JSON.parseObject(object, TaskDeploy.class);
                TaskDeploy oneDeployConfig = deployService.findOneDeployConfig(configId);
                String id;
                if (oneDeployConfig == null){
                    id = deployService.createDeploy(new TaskDeploy());
                }else {
                    id = oneDeployConfig.getDeployId();
                }
                taskDeploy.setDeployId(id);
                deployService.updateDeploy(taskDeploy);
            }
            case 4 -> {
                TaskCodeScan taskCodeScan = JSON.parseObject(object, TaskCodeScan.class);
                TaskCodeScan oneCodeScanConfig = codeScanService.findOneCodeScanConfig(configId);
                String id;
                if (oneCodeScanConfig == null){
                    id = codeScanService.createCodeScan(new TaskCodeScan());
                }else {
                    id = oneCodeScanConfig.getCodeScanId();
                }
                taskCodeScan.setCodeScanId(id);
                codeScanService.updateCodeScan(taskCodeScan);
            }
            case 5 -> {
                TaskProduct taskProduct = JSON.parseObject(object, TaskProduct.class);
                TaskProduct oneProductConfig = productServer.findOneProductConfig(configId);
                String id;
                if (oneProductConfig == null){
                    id = productServer.createProduct(new TaskProduct());
                }else {
                    id = oneProductConfig.getProductId();
                }
                taskProduct.setProductId(id);
                productServer.updateProduct(taskProduct);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    /**
     * 默认任务名称
     * @param taskType 任务
     * @return 任务默认名称
     */
    @Override
    public String initName(int taskType){
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

    /**
     * 查询任务
     * @param configId 配置
     * @return 任务信息
     */
    @Override
    public Object findOneTaskConfig(String configId,int taskType,int taskSort,String name){
        switch (taskType/10) {
            case 0 -> {
                TaskCode oneCodeConfig = codeService.findOneCodeConfig(configId);
                oneCodeConfig.setType(taskType);
                oneCodeConfig.setSort(taskSort);
                oneCodeConfig.setName(name);
                return oneCodeConfig;
            }
            case 1 -> {
                TaskTest oneTestConfig = testService.findOneTestConfig(configId);
                oneTestConfig.setType(taskType);
                oneTestConfig.setSort(taskSort);
                oneTestConfig.setName(name);
                return oneTestConfig;
            }
            case 2 -> {
                TaskBuild oneBuildConfig = buildService.findOneBuildConfig(configId);
                oneBuildConfig.setType(taskType);
                oneBuildConfig.setSort(taskSort);
                oneBuildConfig.setName(name);
                return oneBuildConfig;
            }
            case 3 -> {
                TaskDeploy oneDeployConfig = deployService.findOneDeployConfig(configId);
                oneDeployConfig.setType(taskType);
                oneDeployConfig.setSort(taskSort);
                oneDeployConfig.setName(name);
                return oneDeployConfig;
            }
            case 4 -> {
                TaskCodeScan oneCodeScanConfig = codeScanService.findOneCodeScanConfig(configId);
                oneCodeScanConfig.setType(taskType);
                oneCodeScanConfig.setSort(taskSort);
                oneCodeScanConfig.setName(name);
                return oneCodeScanConfig;
            }
            case 5 -> {
                TaskProduct oneProductConfig = productServer.findOneProductConfig(configId);
                oneProductConfig.setType(taskType);
                oneProductConfig.setSort(taskSort);
                oneProductConfig.setName(name);
                return oneProductConfig;
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    /**
     * 效验必填字段
     * @param configId 配置
     */
    @Override
    public void validTaskConfig(String configId, int taskType, List<String> list){
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
        if (!PipelineUntil.isNoNull(oneCodeConfig.getCodeName())){
            list.add(configId);
        }
    }

    public void codeScanValid(String configId,List<String> list,int taskType){
        TaskCodeScan code = codeScanService.findOneCodeScanConfig(configId);
        if (code == null){
            return;
        }
        if (!PipelineUntil.isNoNull(code.getProjectName())){
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
                if (!PipelineUntil.isNoNull(deploy.getDeployAddress())){
                    list.add(configId);
                }
                if (!PipelineUntil.isNoNull(deploy.getStartAddress())){
                    list.add(configId);
                }
            }
        }
    }

    public void productValid(String configId,List<String> list,int taskType){
        TaskProduct product = productServer.findOneProductConfig(configId);
        if (product == null){
            return;
        }
        if (taskType == 51){
            if (!PipelineUntil.isNoNull(product.getArtifactId())){
                list.add(configId);
            }
            if (!PipelineUntil.isNoNull(product.getVersion())){
                list.add(configId);
            }
            if (!PipelineUntil.isNoNull(product.getGroupId())){
                list.add(configId);
            }
            if (!PipelineUntil.isNoNull(product.getFileAddress())){
                list.add(configId);
            }
            if (!PipelineUntil.isNoNull(product.getFileType())){
                list.add(configId);
            }
        }
        if (taskType == 52){
            if (!PipelineUntil.isNoNull(product.getFileAddress())){
                list.add(configId);
            }
            if (!PipelineUntil.isNoNull(product.getPutAddress())){
                list.add(configId);
            }
        }
    }




}
