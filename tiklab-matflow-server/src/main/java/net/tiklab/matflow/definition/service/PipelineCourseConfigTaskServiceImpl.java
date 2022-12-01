package net.tiklab.matflow.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.definition.model.task.*;
import net.tiklab.matflow.definition.service.task.*;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * 获取所有配置
     * @param allPipelineConfig 配置关联信息
     * @return 配置集合
     */
    @Override
    public List<Object> findAllConfig(List<PipelineCourseConfig> allPipelineConfig){
        List<Object> list = new ArrayList<>();
        if (allPipelineConfig == null){
            return null;
        }
        for (PipelineCourseConfig pipelineCourseConfig : allPipelineConfig) {
            Object oneConfig = findOneConfig(pipelineCourseConfig);
            list.add(oneConfig);
        }
        return list;
    }

    /**
     * 获取配置详情
     * @param configOrder 配置信息
     * @return 配置信息
     */
    @Override
    public Object findOneConfig(PipelineCourseConfig configOrder){
        String taskId = configOrder.getTaskId();
        int taskSort = configOrder.getTaskSort();
        int type = configOrder.getTaskType();
        switch (type/10){
            case 0 -> {
                PipelineCode code = codeService.findOneCode(taskId);
                code.setType(type);
                code.setSort(taskSort);
                return code;
            }
            case 1 -> {
                PipelineTest test = testService.findOneTest(taskId);
                test.setType(type);
                test.setSort(taskSort);
                return test;
            }
            case 2 -> {
                PipelineBuild build = buildService.findOneBuild(taskId);
                build.setType(type);
                build.setSort(taskSort);
                return build;
            }
            case 3 -> {
                PipelineDeploy deploy = deployService.findOneDeploy(taskId);
                deploy.setType(type);
                deploy.setSort(taskSort);
                return deploy;
            }
            case 4 -> {
                PipelineCodeScan codeScan = codeScanService.findOneCodeScan(taskId);
                codeScan.setType(type);
                codeScan.setSort(taskSort);
                return codeScan;
            }
            case 5 -> {
                PipelineProduct product = productServer.findOneProduct(taskId);
                product.setType(type);
                product.setSort(taskSort);
                return product;
            }
        }
        return null;
    }

    /**
     * 配置信息
     * @param message 执行类型 create:创建,update:更新,delete:删除
     * @param config 更新配置
     * @param typeConfig 原配置
     * @return 更新信息
     */
    public Map<String,String> config(String message, PipelineCourseConfig config, PipelineCourseConfig typeConfig){
        Map<String, String> map;
        int taskType = config.getTaskType();
        switch (taskType/10) {
            case 0 -> {
                map = codeConfig(message, config, typeConfig);
                map.put("message","源码管理配置");
            }
            case 1 -> {
                map = testConfig(message, config, typeConfig);
                map.put("message","测试配置");
            }
            case 2 -> {
                map = buildConfig(message, config, typeConfig);
                map.put("message","构建配置");
            }
            case 3 -> {
                map = deployConfig(message, config, typeConfig);
                map.put("message","部署配置");
            }
            case 4 -> {
                map = codeScanConfig(message, config, typeConfig);
                map.put("message","代码扫描配置");
            }
            case 5 -> {
                map = productConfig(message, config, typeConfig);
                map.put("message","推送制品配置");
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
        return map;
    }

    //源码
    private Map<String, String> codeConfig(String message, PipelineCourseConfig config, PipelineCourseConfig typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineCode pipelineCode = new PipelineCode();
                if (config.getTaskType() == 1){
                    pipelineCode.setCodeBranch("master");
                }
                String id = codeService.createCode(pipelineCode);
                map.put("id",id);
            }
            case "update"->{
                PipelineCode pipelineCode = JSON.parseObject(object, PipelineCode.class);
                pipelineCode.setCodeId(typeConfig.getTaskId());
                pipelineCode.setType(config.getTaskType());
                codeService.updateCode(pipelineCode);
            }
            case "delete"->{
                codeService.deleteCode(config.getTaskId());
            }
            case "updateType" -> {
                codeService.deleteCode(config.getTaskId());
                String id = codeService.createCode(new PipelineCode());
                map.put("id",id);
            }
        }
        return map;
    }

    //代码扫描
    private Map<String, String> codeScanConfig(String message, PipelineCourseConfig config, PipelineCourseConfig typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineCodeScan pipelineCodeScan = new PipelineCodeScan();
                String id = codeScanService.createCodeScan(pipelineCodeScan);
                map.put("id",id);
            }
            case "update"->{
                PipelineCodeScan pipelineCodeScan = JSON.parseObject(object, PipelineCodeScan.class);
                pipelineCodeScan.setCodeScanId(typeConfig.getTaskId());
                codeScanService.updateCodeScan(pipelineCodeScan);
            }
            case "delete"->{
                codeScanService.deleteCodeScan(config.getTaskId());
            }
            case "updateType" -> {
                codeScanService.deleteCodeScan(config.getTaskId());
                String id = codeScanService.createCodeScan(new PipelineCodeScan());
                map.put("id",id);
            }
        }
        return map;
    }

    //测试
    private Map<String, String> testConfig(String message, PipelineCourseConfig config, PipelineCourseConfig typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineTest pipelineTest = new PipelineTest();
                if (config.getTaskType() == 11){
                    pipelineTest.setTestOrder("mvn test -Dmaven.test.failure.ignore=true");
                }
                String id = testService.createTest(pipelineTest);
                map.put("id",id);
            }
            case "update"->{
                PipelineTest pipelineTest = JSON.parseObject(object, PipelineTest.class);
                pipelineTest.setTestId(typeConfig.getTaskId());
                testService.updateTest(pipelineTest);
            }
            case "delete"->{
                testService.deleteTest(config.getTaskId());
            }
            case "updateType" -> {
                testService.deleteTest(config.getTaskId());
                String id = testService.createTest(new PipelineTest());
                map.put("id",id);
            }
        }
        return map;
    }

    //构建
    private Map<String, String> buildConfig(String message, PipelineCourseConfig config, PipelineCourseConfig typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineBuild pipelineBuild = new PipelineBuild();
                int taskType = config.getTaskType();
                if (taskType == 21){
                    pipelineBuild.setBuildOrder("mvn clean package");
                }
                if (taskType == 22){
                    pipelineBuild.setBuildOrder("npm install");
                }
                String id = buildService.createBuild(pipelineBuild);
                map.put("id",id);
            }
            case "update"->{
                PipelineBuild pipelineBuild = JSON.parseObject(object, PipelineBuild.class);
                pipelineBuild.setBuildId(typeConfig.getTaskId());
                buildService.updateBuild(pipelineBuild);
            }
            case "delete"->{
                buildService.deleteBuild(config.getTaskId());
            }
            case "updateType" -> {
                buildService.deleteBuild(config.getTaskId());
                String id = buildService.createBuild(new PipelineBuild());
                map.put("id",id);
            }
        }
        return map;
    }

    //部署
    private Map<String, String> deployConfig(String message, PipelineCourseConfig config, PipelineCourseConfig typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineDeploy pipelineDeploy = new PipelineDeploy();
                pipelineDeploy.setAuthType(1);
                String id = deployService.createDeploy(pipelineDeploy);
                map.put("id",id);
            }
            case "update"->{
                PipelineDeploy pipelineDeploy = JSON.parseObject(object, PipelineDeploy.class);
                pipelineDeploy.setDeployId(typeConfig.getTaskId());
                deployService.updateDeploy(pipelineDeploy);
            }
            case "delete"->{
                deployService.deleteDeploy(config.getTaskId());
            }
            case "updateType" -> {
                deployService.deleteDeploy(config.getTaskId());
                PipelineDeploy pipelineDeploy = new PipelineDeploy();
                pipelineDeploy.setAuthType(1);
                String id = deployService.createDeploy(pipelineDeploy);
                map.put("id",id);
            }
        }
        return map;
    }

    //推送制品
    private Map<String, String> productConfig(String message, PipelineCourseConfig config, PipelineCourseConfig typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineProduct product = new PipelineProduct();
                String id = productServer.createProduct(product);
                map.put("id",id);
            }
            case "update"->{
                PipelineProduct product = JSON.parseObject(object, PipelineProduct.class);
                product.setProductId(typeConfig.getTaskId());
                productServer.updateProduct(product);
            }
            case "delete"->{
                productServer.deleteProduct(config.getTaskId());
            }
            case "updateType" -> {
                productServer.deleteProduct(config.getTaskId());
                PipelineProduct product = new PipelineProduct();
                String id = productServer.createProduct(product);
                map.put("id",id);
            }
        }
        return map;
    }


    /**
     * 效验必填字段
     * @param configOrderList 所有配置
     * @return 效验信息
     */
    public Map<String, String> configValid(List<PipelineCourseConfig> configOrderList){
        Map<String, String> map = new HashMap<>();
        for (PipelineCourseConfig pipelineCourseConfig : configOrderList) {
            int type = pipelineCourseConfig.getTaskType();
            String taskId = pipelineCourseConfig.getTaskId();
            switch (type / 10) {
                case 0 -> map = codeValid(map, taskId, type);
                case 1 -> map = testValid(map, taskId, type);
                case 2 -> map = buildValid(map, taskId, type);
                case 3 -> map = deployValid(map, taskId, type);
                case 4 -> map = codeScanValid(map, taskId, type);
                case 5 -> map = productValid(map, taskId, type);
            }
        }
        if(map.size() == 0){
            return Collections.emptyMap();
        }
        return map;
    }

    public Map<String, String> codeValid(Map<String, String> map,String taskId,int type){
        PipelineCode code = codeService.findOneCode(taskId);
        if (!PipelineUntil.isNoNull(code.getCodeName())){
            map.put("codeName", String.valueOf(type));
        }
        return map;
    }

    public Map<String, String> codeScanValid(Map<String, String> map,String taskId,int type){
        PipelineCodeScan code = codeScanService.findOneCodeScan(taskId);
        if (!PipelineUntil.isNoNull(code.getProjectName())){
            map.put("projectName", String.valueOf(type));
        }
        return map;
    }

    public Map<String, String> testValid(Map<String, String> map,String taskId,int type){
        return map;
    }

    public Map<String, String> buildValid(Map<String, String> map,String taskId,int type){
        return map;
    }

    public Map<String, String> deployValid(Map<String, String> map,String taskId,int type){
        PipelineDeploy deploy = deployService.findOneDeploy(taskId);
        if (type == 31){
            if (deploy.getAuthType() == 1){
                if (!PipelineUntil.isNoNull(deploy.getDeployAddress())){
                    map.put("deployAddress", String.valueOf(type));
                }
                if (!PipelineUntil.isNoNull(deploy.getStartAddress())){
                    map.put("startAddress", String.valueOf(type));
                }
            }
        }
        return map;
    }

    public Map<String, String> productValid(Map<String, String> map,String taskId,int type){
        PipelineProduct product = productServer.findOneProduct(taskId);
        if (type == 51){
            if (!PipelineUntil.isNoNull(product.getArtifactId())){
                map.put("artifactId", String.valueOf(type));
            }
            if (!PipelineUntil.isNoNull(product.getVersion())){
                map.put("version", String.valueOf(type));
            }
            if (!PipelineUntil.isNoNull(product.getGroupId())){
                map.put("groupId", String.valueOf(type));
            }
            if (!PipelineUntil.isNoNull(product.getFileAddress())){
                map.put("fileAddress", String.valueOf(type));
            }
            if (!PipelineUntil.isNoNull(product.getFileType())){
                map.put("fileType", String.valueOf(type));
            }
        }
        if (type == 52){
           if (!PipelineUntil.isNoNull(product.getFileAddress())){
               map.put("fileAddress", String.valueOf(type));
           }
           if (!PipelineUntil.isNoNull(product.getPutAddress())){
               map.put("putAddress", String.valueOf(type));
           }
       }
        return map;
    }


}

















