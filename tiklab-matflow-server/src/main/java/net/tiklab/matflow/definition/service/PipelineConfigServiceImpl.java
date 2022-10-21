package net.tiklab.matflow.definition.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    /**
     * 获取所有配置
     * @param allPipelineConfig 配置关联信息
     * @return 配置集合
     */
    public List<Object> findAllConfig(List<PipelineConfigOrder> allPipelineConfig){
        List<Object> list = new ArrayList<>();
        if (allPipelineConfig == null){
            return null;
        }
        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            String taskId = pipelineConfigOrder.getTaskId();
            int taskSort = pipelineConfigOrder.getTaskSort();
            int type = pipelineConfigOrder.getTaskType();
            if (type < 10){
                PipelineCode code = pipelineCodeService.findOneCode(taskId);
                code.setType(type);
                code.setSort(taskSort);
                list.add(code);
            }else if (10<type && type<20){
                PipelineTest test = pipelineTestService.findOneTest(taskId);
                test.setType(type);
                test.setSort(taskSort);
                list.add(test);
            }else if (20<type && type<30){
                PipelineBuild build = pipelineBuildService.findOneBuild(taskId);
                build.setType(type);
                build.setSort(taskSort);
                list.add(build);
            }else if (30<type && type<40){
                PipelineDeploy deploy = pipelineDeployService.findOneDeploy(taskId);
                deploy.setType(type);
                deploy.setSort(taskSort);
                list.add(deploy);
            }
        }
        return list;
    }

    /**
     * 根据类型获取配置
     * @param typeConfig 配置信息
     * @param type 类型
     * @return 配置信息
     */
    public PipelineConfigOrder findConfig(PipelineConfigOrder typeConfig,int type){
        if (typeConfig == null){
            return null;
        }
        String taskId = typeConfig.getTaskId();
        int taskType = typeConfig.getTaskType();
        if (type < 10){
            PipelineCode oneCode = pipelineCodeService.findOneCode(taskId);
            oneCode.setType(taskType);
            typeConfig.setPipelineCode(oneCode);
        }else if (10<type && type<20){
            PipelineTest oneTest = pipelineTestService.findOneTest(taskId);
            oneTest.setType(taskType);
            typeConfig.setPipelineTest(oneTest);
        }else if (20<type && type<30){
            PipelineBuild oneBuild = pipelineBuildService.findOneBuild(taskId);
            oneBuild.setType(taskType);
            typeConfig.setPipelineBuild(oneBuild);
        }else if (30<type && type<40){
            PipelineDeploy oneDeploy = pipelineDeployService.findOneDeploy(taskId);
            oneDeploy.setType(taskType);
            typeConfig.setPipelineDeploy(oneDeploy);
        }
        return typeConfig;
    }

    /**
     * 更新配置
     * @param config 更新数据
     * @param typeConfig 原配置信息
     * @param types 类型
     * @return 动态
     */
    public Map<String, String> updateConfig(PipelineConfigOrder config,PipelineConfigOrder typeConfig,String types) {
        Pipeline pipeline = typeConfig.getPipeline();
        HashMap<String, String> map = new HashMap<>();
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", pipeline.getPipelineName());
        switch (types) {
            case "code" -> {
                PipelineCode pipelineCode = config.getPipelineCode();
                pipelineCode.setCodeId(typeConfig.getTaskId());
                pipelineCodeService.updateCode(pipelineCode);
                map.put("message", "源码管理配置");
            }
            case "test" -> {
                PipelineTest pipelineTest = config.getPipelineTest();
                pipelineTest.setTestId(typeConfig.getTaskId());
                pipelineTestService.updateTest(pipelineTest);
                map.put("message", "测试配置");
            }
            case "build" -> {
                PipelineBuild pipelineBuild = config.getPipelineBuild();
                pipelineBuild.setBuildId(typeConfig.getTaskId());
                pipelineBuildService.updateBuild(pipelineBuild);
                map.put("message", "构建配置");
            }
            case "deploy" -> {
                PipelineDeploy pipelineDeploy = config.getPipelineDeploy();
                pipelineDeploy.setDeployId(typeConfig.getTaskId());
                PipelineDeploy deploy = updateNumber(typeConfig, pipelineDeploy);
                pipelineDeployService.updateDeploy(deploy);
                map.put("message", "部署配置");
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
        String id = null;

        HashMap<String, String> map = new HashMap<>();
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", pipeline.getPipelineName());
        switch (types) {
            case "code" -> {
                PipelineCode pipelineCode = new PipelineCode();
                id = pipelineCodeService.createCode(pipelineCode);
                map.put("message","源码管理配置");
            }
            case "test" -> {
                PipelineTest pipelineTest = new PipelineTest();
                id = pipelineTestService.createTest(pipelineTest);
                map.put("message","测试配置");
            }
            case "build" -> {
                PipelineBuild pipelineBuild = new PipelineBuild();
                id = pipelineBuildService.createBuild(pipelineBuild);
                map.put("message","构建配置");
            }
            case "deploy" -> {
                PipelineDeploy pipelineDeploy =  new PipelineDeploy();
                id = pipelineDeployService.createDeploy(pipelineDeploy);
                map.put("message","部署配置");
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
        Pipeline pipeline = typeConfig.getPipeline();
        HashMap<String, String> map = new HashMap<>();
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", pipeline.getPipelineName());
        switch (types) {
            case "code" -> {
                pipelineCodeService.deleteCode(typeConfig.getTaskId());
                map.put("message","源码管理配置");
            }
            case "test" -> {
                pipelineTestService.deleteTest(typeConfig.getTaskId());
                map.put("message","测试配置");
            }
            case "build" -> {
                pipelineBuildService.deleteBuild(typeConfig.getTaskId());
                map.put("message","构建配置");
            }
            case "deploy" -> {
                pipelineDeployService.deleteDeploy(typeConfig.getTaskId());
                map.put("message","部署配置");
            }
        }
        return map;
    }

    private PipelineDeploy updateNumber(PipelineConfigOrder typeConfig,PipelineDeploy deploy){
        PipelineDeploy oneDeploy = pipelineDeployService.findOneDeploy(typeConfig.getTaskId());
        if (deploy.getMappingPort() == 0){
            deploy.setMappingPort(oneDeploy.getMappingPort());
        }
        if (deploy.getSshPort() == 0){
            deploy.setSshPort(oneDeploy.getSshPort());
        }
        if (deploy.getStartPort() == 0){
            deploy.setStartPort(oneDeploy.getStartPort());
        }
        return deploy;
    }
}

















