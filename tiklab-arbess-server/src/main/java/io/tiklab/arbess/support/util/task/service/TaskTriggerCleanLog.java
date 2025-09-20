package io.tiklab.arbess.support.util.task.service;

import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.arbess.setting.other.model.Cache;
import io.tiklab.arbess.setting.other.service.CacheService;
import io.tiklab.arbess.stages.model.StageInstance;
import io.tiklab.arbess.stages.model.StageInstanceQuery;
import io.tiklab.arbess.stages.service.StageInstanceServer;
import io.tiklab.arbess.support.postprocess.model.PostprocessInstance;
import io.tiklab.arbess.support.postprocess.service.PostprocessInstanceService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.model.TaskBuildProductQuery;
import io.tiklab.arbess.task.build.service.TaskBuildProductService;
import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.model.TaskInstanceQuery;
import io.tiklab.arbess.task.task.service.TasksInstanceService;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

@Component
public class TaskTriggerCleanLog {

    private final Logger logger = LoggerFactory.getLogger(TaskTriggerCleanLog.class);

    @Autowired
    PipelineInstanceService instanceService;

    @Autowired
    TaskBuildProductService taskBuildProductService;

    @Autowired
    CacheService cacheService;

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    StageInstanceServer stageInstanceServer;

    @Autowired
    PostprocessInstanceService postInstanceService;

    @Autowired
    JoinTemplate joinTemplate;


    @Scheduled(cron = "0 2 * * * *")
    public void scheduledBackups(){

        logger.info("The cache cleanup timing task is triggered.....");

        List<PipelineInstance> allInstance = instanceService.findAllInstance();

        if (allInstance.isEmpty()){
            return;
        }

        List<Cache> allCathe = cacheService.findAllCathe();
        Cache cache = allCathe.get(0);

        int artifactCache = cache.getArtifactCache();
        int logCache = cache.getLogCache();

        for (PipelineInstance instance : allInstance) {
            // joinTemplate.joinQuery(instance);
            String createTime = instance.getCreateTime();
            Date date = PipelineUtil.StringChengeDate(createTime);

            String instanceId = instance.getInstanceId();

            String dateTime = PipelineUtil.findDateTime(date, artifactCache);

            if (StringUtils.isEmpty(dateTime)){
                deleteBuildProduct(instanceId);
            }

            String logDateTime = PipelineUtil.findDateTime(date, logCache);

            if (!StringUtils.isEmpty(logDateTime)){
                continue;
            }
            Pipeline pipeline = instance.getPipeline();
            if (pipeline.getType()  == 1){
                deleteTaskLog(instanceId,"task");
            }else {
                deleteStageLog(instanceId);
            }

            deletePostLog(instanceId);
        }
        logger.info("The cache cleanup is scheduled to complete！");
    }

    /**
     * 清理流水线制品
     * @param instanceId 流水线实例id
     */
    private void deleteBuildProduct(String instanceId) {

        TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
        taskBuildProductQuery.setInstanceId(instanceId);
        List<TaskBuildProduct> buildProductList = taskBuildProductService.findBuildProductList(taskBuildProductQuery);
        if (buildProductList.isEmpty()){
            return;
        }

        for (TaskBuildProduct taskBuildProduct : buildProductList) {
            String value = taskBuildProduct.getValue();
            if (StringUtils.isEmpty(value)){
                continue;
            }
            FileUtils.deleteQuietly(new File(value));
            String id = taskBuildProduct.getId();
            taskBuildProductService.deleteBuildProduct(id);
        }
    }

    private void deleteStageLog(String instanceId){
        StageInstanceQuery stageInstanceQuery = new StageInstanceQuery();
        stageInstanceQuery.setInstanceId(instanceId);
        List<StageInstance> stageInstanceList = stageInstanceServer.findStageInstanceList(stageInstanceQuery);
        if (stageInstanceList.isEmpty()){
            return;
        }
        for (StageInstance stageInstance : stageInstanceList) {
            String stageInstanceId = stageInstance.getId();
            StageInstanceQuery stageInstanceQuerys = new StageInstanceQuery();
            stageInstanceQuerys.setParentId(stageInstanceId);
            List<StageInstance> stageInstanceLists = stageInstanceServer.findStageInstanceList(stageInstanceQuery);
            if (stageInstanceLists.isEmpty()){
                continue;
            }
            for (StageInstance instance : stageInstanceLists) {
                deleteTaskLog(instance.getId(),"stage");
            }
        }
    }

    private void deletePostLog(String instanceId) {
        List<PostprocessInstance> pipelinePostInstanceList = postInstanceService.findPipelinePostInstance(instanceId);
        if (pipelinePostInstanceList.isEmpty()){
            return;
        }
        for (PostprocessInstance postprocessInstance : pipelinePostInstanceList) {
            deleteTaskLog(postprocessInstance.getId(),"post");
        }
    }

    private void deleteTaskLog(String id,String type){

        List<TaskInstance> taskInstanceList;

        switch(type) {
            case "stage" ->{
                TaskInstanceQuery taskInstanceQuery = new TaskInstanceQuery();
                taskInstanceQuery.setStagesId(id);
                taskInstanceList = tasksInstanceService.findTaskInstanceList(taskInstanceQuery);
            }
            case "post" ->{
                TaskInstanceQuery taskInstanceQuery = new TaskInstanceQuery();
                taskInstanceQuery.setPostprocessId(id);
                taskInstanceList = tasksInstanceService.findTaskInstanceList(taskInstanceQuery);
            }
            default -> {
                TaskInstanceQuery taskInstanceQuery = new TaskInstanceQuery();
                taskInstanceQuery.setInstanceId(id);
                taskInstanceList = tasksInstanceService.findTaskInstanceList(taskInstanceQuery);
            }
        }
        for (TaskInstance taskInstance : taskInstanceList) {
            String logAddress = taskInstance.getLogAddress();
            File file = new File(logAddress);
            if (file.exists()){
                PipelineFileUtil.deleteFile(file);
            }
        }
    }




}
