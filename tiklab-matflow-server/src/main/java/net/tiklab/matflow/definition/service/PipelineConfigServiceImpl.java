package net.tiklab.matflow.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineConfigServiceImpl implements PipelineConfigService {

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    PipelineBuildService pipelineBuildService;

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    PipelineDeployService pipelineDeployService;

    @Autowired
    PipelineCodeScanService pipelineCodeScanService;

    @Autowired
    PipelineProductServer pipelineProductServer;

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
                PipelineCode code = pipelineCodeService.findOneCode(taskId);
                code.setType(type);
                code.setSort(taskSort);
                return code;
            }
            case 1 -> {
                PipelineTest test = pipelineTestService.findOneTest(taskId);
                test.setType(type);
                test.setSort(taskSort);
                return test;
            }
            case 2 -> {
                PipelineBuild build = pipelineBuildService.findOneBuild(taskId);
                build.setType(type);
                build.setSort(taskSort);
                return build;
            }
            case 3 -> {
                PipelineDeploy deploy = pipelineDeployService.findOneDeploy(taskId);
                deploy.setType(type);
                deploy.setSort(taskSort);
                return deploy;
            }
            case 4 -> {
                PipelineCodeScan codeScan = pipelineCodeScanService.findOneCodeScan(taskId);
                codeScan.setType(type);
                codeScan.setSort(taskSort);
                return codeScan;
            }
            case 5 -> {
                PipelineProduct product = pipelineProductServer.findOneProduct(taskId);
                product.setType(type);
                product.setSort(taskSort);
                return product;
            }
        }
        return null;
    }

    String message = "message";

    /**
     * 更新配置
     * @param config 更新数据
     * @param typeConfig 原配置信息
     * @param types 类型
     * @return 动态
     */
    public Map<String, String> updateConfig(PipelineConfigOrder config,PipelineConfigOrder typeConfig,String types) {
        HashMap<String, String> map = new HashMap<>();
        //把值转换成json字符串
        String object = JSON.toJSONString(config.getValues());

        switch (types) {
            case "code" -> {
                //字符串转换成对象
                PipelineCode pipelineCode = JSON.parseObject(object, PipelineCode.class);
                pipelineCode.setCodeId(typeConfig.getTaskId());
                pipelineCode.setType(config.getTaskType());
                pipelineCodeService.updateCode(pipelineCode);
                map.put(message, "源码管理配置");
            }
            case "test" -> {
                PipelineTest pipelineTest = JSON.parseObject(object, PipelineTest.class);
                pipelineTest.setTestId(typeConfig.getTaskId());
                pipelineTestService.updateTest(pipelineTest);
                map.put(message,  "测试配置");
            }
            case "build" -> {
                PipelineBuild pipelineBuild = JSON.parseObject(object, PipelineBuild.class);
                pipelineBuild.setBuildId(typeConfig.getTaskId());
                pipelineBuildService.updateBuild(pipelineBuild);
                map.put(message,  "构建配置");
            }
            case "deploy" -> {
                PipelineDeploy pipelineDeploy = JSON.parseObject(object, PipelineDeploy.class);
                pipelineDeploy.setDeployId(typeConfig.getTaskId());
                pipelineDeployService.updateDeploy(pipelineDeploy);
                map.put(message,  "部署配置");
            }
            case "codeScan" -> {
                PipelineCodeScan pipelineCodeScan = JSON.parseObject(object, PipelineCodeScan.class);
                pipelineCodeScan.setCodeScanId(typeConfig.getTaskId());
                pipelineCodeScanService.updateCodeScan(pipelineCodeScan);
                map.put(message,  "代码扫描配置");
            }
            case "product" -> {
                PipelineProduct product = JSON.parseObject(object, PipelineProduct.class);
                product.setProductId(typeConfig.getTaskId());
                pipelineProductServer.updateProduct(product);
                map.put(message,  "推送制品配置");
            }
            default -> {
                return map;
            }
        }
        return map;
    }

    /**
     * 创建配置
     * @param config 配置
     * @param types 类型
     * @param size 顺序
     * @return 动态
     */
    public Map<String, String> createConfig(PipelineConfigOrder config, String types, int size){
        Pipeline pipeline = config.getPipeline();
        String id ;
        String object = JSON.toJSONString(config.getValues());
        HashMap<String, String> map = new HashMap<>();
        switch (types) {
            case "code" -> {
                PipelineCode pipelineCode = JSON.parseObject(object, PipelineCode.class);
                if (pipelineCode == null) pipelineCode = new PipelineCode();
                id = pipelineCodeService.createCode(pipelineCode);
                map.put(message, "源码管理配置");
            }
            case "test" -> {
                PipelineTest pipelineTest = JSON.parseObject(object, PipelineTest.class);
                if (pipelineTest == null) pipelineTest=new PipelineTest();
                id = pipelineTestService.createTest(pipelineTest);
                map.put(message, "测试配置");
            }
            case "build" -> {
                PipelineBuild pipelineBuild = JSON.parseObject(object, PipelineBuild.class);
                if (pipelineBuild == null) pipelineBuild = new PipelineBuild();
                id = pipelineBuildService.createBuild(pipelineBuild);
                map.put(message, "构建配置");
            }
            case "deploy" -> {
                PipelineDeploy pipelineDeploy = JSON.parseObject(object, PipelineDeploy.class);
                if (pipelineDeploy == null) pipelineDeploy = new PipelineDeploy();
                pipelineDeploy.setAuthType(1);
                id = pipelineDeployService.createDeploy(pipelineDeploy);
                map.put(message, "部署配置");
            }
            case "codeScan" -> {
                PipelineCodeScan pipelineCodeScan = JSON.parseObject(object, PipelineCodeScan.class);
                if (pipelineCodeScan == null) pipelineCodeScan = new PipelineCodeScan();
                id = pipelineCodeScanService.createCodeScan(pipelineCodeScan);
                map.put(message, "代码扫描配置");
            } case "product" -> {
                PipelineProduct product = JSON.parseObject(object, PipelineProduct.class);
                if (product == null) product = new PipelineProduct();
                id = pipelineProductServer.createProduct(product);
                map.put(message,  "推送制品配置");
            }
            default -> {
                return map;
            }
        }
        if (id == null){
            throw new ApplicationException(50001,"添加失败");
        }
        map.put("id", id);
        return map;
    }

    /**
     * 删除配置
     * @param typeConfig 配置信息
     * @param types 类型
     * @return 动态
     */
    public Map<String, String> deleteConfig( PipelineConfigOrder typeConfig ,String types){
        HashMap<String, String> map = new HashMap<>();
        String taskId = typeConfig.getTaskId();
        switch (types) {
            case "code" -> {
                pipelineCodeService.deleteCode(taskId);
                map.put(message,"源码管理配置");
            }
            case "test" -> {
                pipelineTestService.deleteTest(taskId);
                map.put(message,"测试配置");
            }
            case "build" -> {
                pipelineBuildService.deleteBuild(taskId);
                map.put(message,"构建配置");
            }
            case "deploy" -> {
                pipelineDeployService.deleteDeploy(taskId);
                map.put(message,"部署配置");
            }
            case "codeScan" -> {
                pipelineCodeScanService.deleteCodeScan(taskId);
                map.put(message,"代码扫描配置");
            } case "product" -> {
               pipelineProductServer.deleteProduct(taskId);
                map.put(message,  "推送制品配置");
            }
            default -> {
                return map;
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
            if (type < 10){
                PipelineCode code = pipelineCodeService.findOneCode(taskId);
                if (!PipelineUntil.isNoNull(code.getCodeName())){
                    map.put("codeName", String.valueOf(type));
                }
            }
        }
        if(map.size() == 0){
            return Collections.emptyMap();
        }
        return map;
    }
}

















