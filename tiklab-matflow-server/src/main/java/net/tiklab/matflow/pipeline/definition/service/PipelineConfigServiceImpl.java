package net.tiklab.matflow.pipeline.definition.service;

import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineConfig;
import net.tiklab.matflow.pipeline.definition.model.PipelineStages;
import net.tiklab.matflow.support.post.service.PostService;
import net.tiklab.matflow.support.trigger.service.TriggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PipelineConfigServiceImpl implements PipelineConfigService {


    @Autowired
    private PipelineTasksService tasksService;

    @Autowired
    private PipelineStagesService stagesServer;

    @Autowired
    private PostService postServer;

    @Autowired
    TriggerService triggerServer;

    @Autowired
    JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigServiceImpl.class);

    /**
     * 查询流水线所有配置（包括后置任务）
     * @param pipelineId 流水线id
     * @return 所有配置信息
     */
    @Override
    public List<Object> findAllConfig(String pipelineId){
        PipelineConfig pipelineConfig = new PipelineConfig(pipelineId);
        joinTemplate.joinQuery(pipelineConfig);
        Pipeline pipeline = pipelineConfig.getPipeline();
        int type = pipeline.getType();
        List<Object> allCourseConfig = findAllTaskConfig(pipelineId);
        List<Object> allAfterConfig = postServer.findAllPostTask(pipelineId);
        if (allAfterConfig == null || allAfterConfig.size() == 0){
            return allCourseConfig;
        }
        if (type == 1){
            allCourseConfig.addAll(allAfterConfig);
            return allCourseConfig;
        }
        PipelineStages pipelineStages = new PipelineStages();
        List<PipelineStages> stagesList = new ArrayList<>();
        PipelineStages stages = new PipelineStages();
        stages.setTaskValues(allAfterConfig);
        stagesList.add(stages);
        pipelineStages.setStagesList(stagesList);
        pipelineStages.setTaskSort(allCourseConfig.size()+1);
        allCourseConfig.add(pipelineStages);
        return allCourseConfig;
    }

    /**
     * 查询所有任务配置
     * @param pipelineId 流水线Id
     * @return 配置
     */
    @Override
    public List<Object> findAllTaskConfig(String pipelineId){
        PipelineConfig pipelineConfig = new PipelineConfig(pipelineId);
        joinTemplate.joinQuery(pipelineConfig);
        Pipeline pipeline = pipelineConfig.getPipeline();
        int type = pipeline.getType();
        if (type == 1){
            return tasksService.findAllTasksTask(pipelineId);
        }
        if (type == 2){
            List<PipelineStages> allStagesConfig = stagesServer.findAllStagesStageTask(pipelineId);
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
        joinTemplate.joinQuery(config);
        Pipeline pipeline = config.getPipeline();
        int type = pipeline.getType();
        String id = null;
        if (type == 1){
            id = tasksService.createTasksTask(config);
        }
        if (type == 2){

            id = stagesServer.createStagesTask(config);
        }
        return id;
    }

    /**
     * 删除配置及任务
     * @param config 配置信息
     */
    @Override
    public void deleteTaskConfig(PipelineConfig config){
        joinTemplate.joinQuery(config);
        Pipeline pipeline = config.getPipeline();
        int type = pipeline.getType();
        String configId = config.getConfigId();
        if (type == 1){
           tasksService.deleteTasksTask(configId);
        }
        if (type == 2){
            stagesServer.deleteStagesTask(configId);
        }
    }

    /**
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     * @param pipelineType 流水线类型
     */
    @Override
    public void deleteAllTaskConfig(String pipelineId,int pipelineType){
        if (pipelineType == 1){
            tasksService.deleteAllTasksTask(pipelineId);
        }
        if (pipelineType == 2){
            stagesServer.deleteAllStagesTask(pipelineId);
        }
        triggerServer.deleteAllConfig(pipelineId);
    }

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    @Override
    public void updateTaskConfig(PipelineConfig config){
        joinTemplate.joinQuery(config);
        Pipeline pipeline = config.getPipeline();
        int type = pipeline.getType();
        if (type == 1){
            tasksService.updateTasksTask(config);
        }
        if (type == 2){
            stagesServer.updateStagesTask(config);
        }
    }

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    @Override
    public List<String> validAllConfig(String pipelineId){
        PipelineConfig pipelineConfig = new PipelineConfig(pipelineId);
        joinTemplate.joinQuery(pipelineConfig);
        Pipeline pipeline = pipelineConfig.getPipeline();
        int type = pipeline.getType();
        if (type == 1){
          return  tasksService.validAllConfig(pipelineId);
        }
        if (type == 2){
            return  stagesServer.validAllConfig(pipelineId);
        }
        return null;
    }

    /**
     * 创建流水线模板
     * @param pipeline 流水线信息
     */
    @Override
    public void createTemplate(Pipeline pipeline) {
        String id = pipeline.getId();
        int type = pipeline.getType();
        String template = pipeline.getTemplate();
        int[] ints = switch (template) {
            case "2131" -> new int[]{1,21, 31};
            case "2132" -> new int[]{1,21, 32};
            case "112131" -> new int[]{1,11, 21, 31};
            case "112132" -> new int[]{1,11, 21, 32};
            case "2231" -> new int[]{1,22, 31};
            case "2232" -> new int[]{1,22, 32};
            default -> new int[]{1};
        };
        for (int i = 0; i < ints.length; i++) {
            PipelineConfig config = new PipelineConfig();
            config.setPipeline(new Pipeline(id));
            config.setTaskType(ints[i]);
            config.setTaskSort(i+1);
            if (type == 1){
                tasksService.createTasksTask(config);
            }
            if (type == 2){
                stagesServer.createStagesTask(config);
            }
        }
    }

}





























