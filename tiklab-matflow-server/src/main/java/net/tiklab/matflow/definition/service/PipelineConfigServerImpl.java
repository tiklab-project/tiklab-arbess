package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineConfig;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.definition.model.PipelineStages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PipelineConfigServerImpl implements PipelineConfigServer {


    @Autowired
    PipelineCourseConfigService courseConfigService;

    @Autowired
    PipelineStagesServer stagesServer;

    @Autowired
    PipelineAfterConfigServer afterConfigServer;

    @Autowired
    PipelineService pipelineService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigServerImpl.class);

    /**
     * 查询流水线所有配置（包括后置任务）
     * @param pipelineId 流水线id
     * @return 所有配置信息
     */
    @Override
    public List<Object> findAllConfig(String pipelineId){

        List<Object> allCourseConfig = findAllTaskConfig(pipelineId);
        List<Object> allAfterConfig = afterConfigServer.findAllConfig(pipelineId);
        if (allAfterConfig == null || allAfterConfig.size() == 0){
            return allCourseConfig;
        }
        allCourseConfig.addAll(allAfterConfig);
        return allCourseConfig;
    }

    /**
     * 查询所有任务配置
     * @param pipelineId 流水线Id
     * @return 配置
     */
    @Override
    public List<Object> findAllTaskConfig(String pipelineId){
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int pipelineType = pipeline.getPipelineType();

        if (pipelineType == 1){
            return courseConfigService.findAllConfig(pipelineId);
        }
        if (pipelineType == 2){
            List<PipelineStages> allStagesConfig = stagesServer.findAllStagesConfig(pipelineId);
            if (allStagesConfig == null || allStagesConfig.size() ==0){
                return null;
            }
            return  new ArrayList<>(allStagesConfig);
        }
        return null;
    }

    /**
     * 创建配置及任务配置
     * @param config 配置信息
     * @return 配置id
     */
    @Override
    public String createTaskConfig(PipelineConfig config){
        String pipelineId = config.getPipelineId();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int pipelineType = pipeline.getPipelineType();
        String id = "";
        if (pipelineType == 1){
            PipelineCourseConfig pipelineCourseConfig = (PipelineCourseConfig) configMessage(config,pipelineType);
            id = courseConfigService.createConfig(pipelineCourseConfig);
        }
        if (pipelineType == 2){
            PipelineStages pipelineStages = (PipelineStages) configMessage(config,pipelineType);
            id = stagesServer.createStagesConfig(pipelineStages);
        }
        return id;
    }

    /**
     * 删除配置及任务
     * @param config 配置信息
     */
    @Override
    public void deleteTaskConfig(PipelineConfig config){
        String pipelineId = config.getPipelineId();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        String configId = config.getConfigId();
        if (pipeline.getPipelineType() == 1){
           courseConfigService.deleteConfig(configId);
        }
        if (pipeline.getPipelineType() == 2){
            stagesServer.deleteStagesConfig(configId);
        }
    }

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    @Override
    public void updateTaskConfig(PipelineConfig config){
        String pipelineId = config.getPipelineId();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int pipelineType = pipeline.getPipelineType();
        if (pipelineType == 1){
            PipelineCourseConfig pipelineCourseConfig = (PipelineCourseConfig) configMessage(config,pipelineType);
            courseConfigService.updateConfig(pipelineCourseConfig);
        }
        if (pipelineType == 2){
            PipelineCourseConfig pipelineCourseConfig = (PipelineCourseConfig) configMessage(config,1);
            courseConfigService.updateConfig(pipelineCourseConfig);
        }
    }

    //完善配置信息
    private Object configMessage(PipelineConfig config , int type){
        String configId = config.getConfigId();
        int taskSort = config.getTaskSort();
        int taskType = config.getTaskType();
        Object values = config.getValues();
        int stages = config.getStages();
        String pipelineId = config.getPipelineId();
        String stagesId = config.getStagesId();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);

        if (type == 1){
            return   new PipelineCourseConfig(configId,taskType,taskSort,values,pipeline);
        }
        if (type == 2){
            return new PipelineStages(stagesId,pipeline,taskSort,taskType,stages,values);
        }
        return null;
    }


}





























