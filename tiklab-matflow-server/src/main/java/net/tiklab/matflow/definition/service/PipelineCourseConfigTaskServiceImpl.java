package net.tiklab.matflow.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.task.*;
import net.tiklab.matflow.definition.service.task.*;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineCourseConfigTaskServiceImpl implements PipelineCourseConfigTaskService {

    @Autowired
    PipelineCodeService codeService;

    @Autowired
    PipelineBuildService buildService;

    @Autowired
    PipelineTestService testService;

    @Autowired
    PipelineDeployService deployService;

    @Autowired
    PipelineCodeScanService codeScanService;

    @Autowired
    PipelineProductServer productServer;

    /**
     * 创建任务
     * @param configId 配置id
     * @return 任务id
     */
    @Override
    public String createTaskConfig(String configId,int taskType){
        switch (taskType/10) {
            case 0 -> {
                PipelineCode pipelineCode = new PipelineCode();
                pipelineCode.setConfigId(configId);
                return codeService.createCode(pipelineCode);
            }
            case 1 -> {
                PipelineTest pipelineTest = new PipelineTest();
                pipelineTest.setConfigId(configId);
                return testService.createTest(pipelineTest);
            }
            case 2 -> {
                PipelineBuild pipelineBuild = new PipelineBuild();
                pipelineBuild.setConfigId(configId);
                return buildService.createBuild(pipelineBuild);
            }
            case 3 -> {
                PipelineDeploy pipelineDeploy = new PipelineDeploy();
                pipelineDeploy.setConfigId(configId);
                pipelineDeploy.setAuthType(1);
                return deployService.createDeploy(pipelineDeploy);
            }
            case 4 -> {
                PipelineCodeScan pipelineCodeScan = new PipelineCodeScan();
                pipelineCodeScan.setConfigId(configId);
                return  codeScanService.createCodeScan(pipelineCodeScan);
            }
            case 5 -> {
                PipelineProduct pipelineProduct = new PipelineProduct();
                pipelineProduct.setConfigId(configId);
                return productServer.createProduct(pipelineProduct);
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
                PipelineCode pipelineCode = JSON.parseObject(object, PipelineCode.class);
                PipelineCode oneCodeConfig = codeService.findOneCodeConfig(configId);
                String id;
                if (oneCodeConfig == null){
                    id = codeService.createCode(new PipelineCode());
                }else {
                    id = oneCodeConfig.getCodeId();
                }
                pipelineCode.setCodeId(id);
                pipelineCode.setType(taskType);
                codeService.updateCode(pipelineCode);
            }
            case 1 -> {
                PipelineTest pipelineTest = JSON.parseObject(object, PipelineTest.class);
                PipelineTest oneTestConfig = testService.findOneTestConfig(configId);
                String id;
                if (oneTestConfig == null){
                    id = testService.createTest(new PipelineTest());
                }else {
                    id = oneTestConfig.getTestId();
                }
                pipelineTest.setTestId(id);
                testService.updateTest(pipelineTest);
            }
            case 2 -> {
                PipelineBuild pipelineBuild = JSON.parseObject(object, PipelineBuild.class);
                PipelineBuild oneBuildConfig = buildService.findOneBuildConfig(configId);
                String id;
                if (oneBuildConfig == null){
                    id = buildService.createBuild(new PipelineBuild());
                }else {
                    id = oneBuildConfig.getBuildId();
                }
                pipelineBuild.setBuildId(id);
                buildService.updateBuild(pipelineBuild);
            }
            case 3 -> {
                PipelineDeploy pipelineDeploy = JSON.parseObject(object, PipelineDeploy.class);
                PipelineDeploy oneDeployConfig = deployService.findOneDeployConfig(configId);
                String id;
                if (oneDeployConfig == null){
                    id = deployService.createDeploy(new PipelineDeploy());
                }else {
                    id = oneDeployConfig.getDeployId();
                }
                pipelineDeploy.setDeployId(id);
                deployService.updateDeploy(pipelineDeploy);
            }
            case 4 -> {
                PipelineCodeScan pipelineCodeScan = JSON.parseObject(object, PipelineCodeScan.class);
                PipelineCodeScan oneCodeScanConfig = codeScanService.findOneCodeScanConfig(configId);
                String id;
                if (oneCodeScanConfig == null){
                    id = codeScanService.createCodeScan(new PipelineCodeScan());
                }else {
                    id = oneCodeScanConfig.getCodeScanId();
                }
                pipelineCodeScan.setCodeScanId(id);
                codeScanService.updateCodeScan(pipelineCodeScan);
            }
            case 5 -> {
                PipelineProduct pipelineProduct = JSON.parseObject(object, PipelineProduct.class);
                PipelineProduct oneProductConfig = productServer.findOneProductConfig(configId);
                String id;
                if (oneProductConfig == null){
                    id = productServer.createProduct(new PipelineProduct());
                }else {
                    id = oneProductConfig.getProductId();
                }
                pipelineProduct.setProductId(id);
                productServer.updateProduct(pipelineProduct);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    /**
     * 查询任务
     * @param configId 配置
     * @return 任务信息
     */
    @Override
    public Object findOneTaskConfig(String configId,int taskType,int taskSort){
        switch (taskType/10) {
            case 0 -> {
                PipelineCode oneCodeConfig = codeService.findOneCodeConfig(configId);
                oneCodeConfig.setType(taskType);
                oneCodeConfig.setSort(taskSort);
                return oneCodeConfig;
            }
            case 1 -> {
                PipelineTest oneTestConfig = testService.findOneTestConfig(configId);
                oneTestConfig.setType(taskType);
                oneTestConfig.setSort(taskSort);
                return oneTestConfig;
            }
            case 2 -> {
                PipelineBuild oneBuildConfig = buildService.findOneBuildConfig(configId);
                oneBuildConfig.setType(taskType);
                oneBuildConfig.setSort(taskSort);
                return oneBuildConfig;
            }
            case 3 -> {
                PipelineDeploy oneDeployConfig = deployService.findOneDeployConfig(configId);
                oneDeployConfig.setType(taskType);
                oneDeployConfig.setSort(taskSort);
                return oneDeployConfig;
            }
            case 4 -> {
                PipelineCodeScan oneCodeScanConfig = codeScanService.findOneCodeScanConfig(configId);
                oneCodeScanConfig.setType(taskType);
                oneCodeScanConfig.setSort(taskSort);
                return oneCodeScanConfig;
            }
            case 5 -> {
                PipelineProduct oneProductConfig = productServer.findOneProductConfig(configId);
                oneProductConfig.setType(taskType);
                oneProductConfig.setSort(taskSort);
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
    public void validTaskConfig(String configId,int taskType,List<String> list){
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
        PipelineCode oneCodeConfig = codeService.findOneCodeConfig(configId);
        if (oneCodeConfig == null){
            return;
        }
        if (!PipelineUntil.isNoNull(oneCodeConfig.getCodeName())){
            list.add(configId+"_"+"codeName");
        }
    }

    public void codeScanValid(String configId,List<String> list,int taskType){
        PipelineCodeScan code = codeScanService.findOneCodeScanConfig(configId);
        if (code == null){
            return;
        }
        if (!PipelineUntil.isNoNull(code.getProjectName())){
            list.add(configId+"_"+"projectName");
        }
    }

    public void testValid(String configId,List<String> list,int taskType){

    }

    public void buildValid(String configId,List<String> list,int taskType){

    }

    public void deployValid(String configId,List<String> list,int taskType){
        PipelineDeploy deploy = deployService.findOneDeployConfig(configId);
        if (deploy == null){
            return;
        }
        if (taskType == 31){
            if (deploy.getAuthType() == 1){
                if (!PipelineUntil.isNoNull(deploy.getDeployAddress())){
                    list.add(configId+"_"+"deployAddress");
                }
                if (!PipelineUntil.isNoNull(deploy.getStartAddress())){
                    list.add(configId+"_"+"startAddress");
                }
            }
        }
    }

    public void productValid(String configId,List<String> list,int taskType){
        PipelineProduct product = productServer.findOneProductConfig(configId);
        if (product == null){
            return;
        }
        if (taskType == 51){
            if (!PipelineUntil.isNoNull(product.getArtifactId())){
                list.add(configId+"_"+"artifactId");
            }
            if (!PipelineUntil.isNoNull(product.getVersion())){
                list.add(configId+"_"+"version");
            }
            if (!PipelineUntil.isNoNull(product.getGroupId())){
                list.add(configId+"_"+"groupId");
            }
            if (!PipelineUntil.isNoNull(product.getFileAddress())){
                list.add(configId+"_"+"fileAddress");
            }
            if (!PipelineUntil.isNoNull(product.getFileType())){
                list.add(configId+"_"+"fileType");
            }
        }
        if (taskType == 52){
           if (!PipelineUntil.isNoNull(product.getFileAddress())){
               list.add(configId+"_"+"fileAddress");
           }
           if (!PipelineUntil.isNoNull(product.getPutAddress())){
               list.add(configId+"_"+"putAddress");
           }
       }
    }


}

















