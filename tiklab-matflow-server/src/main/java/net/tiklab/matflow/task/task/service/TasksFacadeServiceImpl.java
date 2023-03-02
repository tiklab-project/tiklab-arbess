package net.tiklab.matflow.task.task.service;

import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.stages.model.Stages;
import net.tiklab.matflow.stages.service.StagesService;
import net.tiklab.matflow.support.postprocess.service.PostprocessService;
import net.tiklab.matflow.support.trigger.service.TriggerService;
import net.tiklab.matflow.task.task.model.TaskFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * 流水线流多任务服务
 */
@Service
public class TasksFacadeServiceImpl implements TasksFacadeService {


    @Autowired
    private TasksService tasksService;

    @Autowired
    private StagesService stagesServer;

    @Autowired
    private PostprocessService postServer;

    @Autowired
    private TriggerService triggerServer;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 查询流水线所有配置（包括后置任务）
     * @param pipelineId 流水线id
     * @return 所有配置信息
     */
    @Override
    public List<Object> findAllConfig(String pipelineId){
        TaskFacade taskFacade = new TaskFacade(pipelineId);
        joinTemplate.joinQuery(taskFacade);
        Pipeline pipeline = taskFacade.getPipeline();
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
        Stages pipelineStages = new Stages();
        List<Stages> stagesList = new ArrayList<>();
        Stages stages = new Stages();
        stages.setTaskValues(allAfterConfig);
        stagesList.add(stages);
        pipelineStages.setStagesList(stagesList);
        pipelineStages.setStagesSort(allCourseConfig.size()+1);
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
        TaskFacade taskFacade = new TaskFacade(pipelineId);
        joinTemplate.joinQuery(taskFacade);
        Pipeline pipeline = taskFacade.getPipeline();
        int type = pipeline.getType();
        if (type == 1){
            return tasksService.findAllTasksOrTask(pipelineId,type);
        }
        if (type == 2){
            List<Stages> allStagesConfig = stagesServer.findAllStagesOrTask(pipelineId);
            if (allStagesConfig == null || allStagesConfig.size() ==0){
                return null;
            }
            return  new ArrayList<>(allStagesConfig);
        }
        return null;
    }

    /**
     * 创建配置及任务配置
     * @param taskFacade 配置信息
     * @return 配置id
     */
    @Override
    public String createTaskConfig(TaskFacade taskFacade){
        joinTemplate.joinQuery(taskFacade);
        Pipeline pipeline = taskFacade.getPipeline();
        int type = pipeline.getType();
        String id = null;
        if (type == 1){
            id = tasksService.createTasksOrTask(taskFacade);
        }
        if (type == 2){
            id = stagesServer.createStagesOrTask(taskFacade);
        }
        return id;
    }

    /**
     * 删除配置及任务
     * @param taskFacade 配置信息
     */
    @Override
    public void deleteTaskConfig(TaskFacade taskFacade){
        joinTemplate.joinQuery(taskFacade);
        Pipeline pipeline = taskFacade.getPipeline();
        int type = pipeline.getType();
        String taskId = taskFacade.getTaskId();
        if (type == 1){
           tasksService.deleteTasksOrTask(taskId);
        }
        if (type == 2){
            stagesServer.deleteStagesOrTask(taskId);
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
            tasksService.deleteAllTasksOrTask(pipelineId,pipelineType);
        }
        if (pipelineType == 2){
            stagesServer.deleteAllStagesOrTask(pipelineId);
        }
        triggerServer.deleteAllConfig(pipelineId);
    }

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    @Override
    public void updateTaskConfig(TaskFacade config){
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
        TaskFacade taskFacade = new TaskFacade(pipelineId);
        joinTemplate.joinQuery(taskFacade);
        Pipeline pipeline = taskFacade.getPipeline();
        int type = pipeline.getType();
        if (type == 1){
          return  tasksService.validTasksMustField(pipelineId,type);
        }
        if (type == 2){
            return  stagesServer.validStagesMustField(pipelineId);
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
            TaskFacade config = new TaskFacade();
            config.setPipeline(new Pipeline(id));
            config.setTaskType(ints[i]);
            config.setTaskSort(i+1);
            if (type == 1){
                tasksService.createTasksOrTask(config);
            }
            if (type == 2){
                stagesServer.createStagesOrTask(config);
            }
        }
    }



}





























