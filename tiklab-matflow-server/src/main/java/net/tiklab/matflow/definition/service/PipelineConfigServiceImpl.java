package net.tiklab.matflow.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class PipelineConfigServiceImpl implements PipelineConfigService {

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
    public List<Object> findAllConfig(List<PipelineConfigOrder> allPipelineConfig){
        List<Object> list = new ArrayList<>();
        if (allPipelineConfig == null){
            return null;
        }
        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            Object oneConfig = findOneConfig(pipelineConfigOrder);
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
    public Object findOneConfig(PipelineConfigOrder configOrder){
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
     * @param types 类型
     * @param config 更新配置
     * @param typeConfig 原配置
     * @return 更新信息
     */
    public Map<String,String> config(String message,String types,PipelineConfigOrder config,PipelineConfigOrder typeConfig){
        Map<String, String> map;
        switch (types) {
            case "code" -> {
                map = codeConfig(message, config, typeConfig);
                map.put("message","源码管理配置");
            }
            case "test" -> {
                map = testConfig(message, config, typeConfig);
                map.put("message","测试配置");
            }
            case "build" -> {
                map = buildConfig(message, config, typeConfig);
                map.put("message","构建配置");
            }
            case "deploy" -> {
                map = deployConfig(message, config, typeConfig);
                map.put("message","部署配置");
            }
            case "codeScan" -> {
                map = codeScanConfig(message, config, typeConfig);
                map.put("message","代码扫描配置");
            }
            case "product" -> {
                map = productConfig(message, config, typeConfig);
                map.put("message","推送制品配置");
            }
            default -> {
                return new HashMap<>();
            }
        }
        return map;
    }

    //源码
    private Map<String, String> codeConfig(String message,PipelineConfigOrder config,PipelineConfigOrder typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        String taskId = config.getTaskId();
        switch (message){
            case "create"->{
                PipelineCode pipelineCode = new PipelineCode();
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
                codeService.deleteCode(taskId);
            }
        }
        return map;
    }

    //代码扫描
    private Map<String, String> codeScanConfig(String message,PipelineConfigOrder config,PipelineConfigOrder typeConfig){
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
        }
        return map;
    }

    //测试
    private Map<String, String> testConfig(String message,PipelineConfigOrder config,PipelineConfigOrder typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineTest pipelineTest = new PipelineTest();
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
        }
        return map;
    }

    //构建
    private Map<String, String> buildConfig(String message,PipelineConfigOrder config,PipelineConfigOrder typeConfig){
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());
        switch (message){
            case "create"->{
                PipelineBuild pipelineBuild = new PipelineBuild();
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
        }
        return map;
    }

    //部署
    private Map<String, String> deployConfig(String message,PipelineConfigOrder config,PipelineConfigOrder typeConfig){
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
        }
        return map;
    }

    //推送制品
    private Map<String, String> productConfig(String message,PipelineConfigOrder config,PipelineConfigOrder typeConfig){
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
        }
        return map;
    }

    /**
     * 效验必填字段
     * @param configOrderList 所有配置
     * @return 效验信息
     */
    public Map<String, String> configValid(List<PipelineConfigOrder> configOrderList){
        Map<String, String> map = new HashMap<>();
        for (PipelineConfigOrder pipelineConfigOrder : configOrderList) {
            int type = pipelineConfigOrder.getTaskType();
            String taskId = pipelineConfigOrder.getTaskId();
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

















