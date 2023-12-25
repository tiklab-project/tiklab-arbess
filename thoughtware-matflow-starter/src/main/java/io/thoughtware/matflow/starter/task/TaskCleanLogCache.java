package io.thoughtware.matflow.starter.task;

import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.setting.model.Cache;
import io.thoughtware.matflow.setting.service.CacheService;
import io.thoughtware.matflow.stages.model.StageInstance;
import io.thoughtware.matflow.stages.model.StageInstanceQuery;
import io.thoughtware.matflow.stages.service.StageInstanceServer;
import io.thoughtware.matflow.support.postprocess.model.PostprocessInstance;
import io.thoughtware.matflow.support.postprocess.service.PostprocessInstanceService;
import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstance;
import io.thoughtware.matflow.pipeline.instance.service.PipelineInstanceService;
import io.thoughtware.matflow.task.build.model.TaskBuildProduct;
import io.thoughtware.matflow.task.build.model.TaskBuildProductQuery;
import io.thoughtware.matflow.task.build.service.TaskBuildProductService;
import io.thoughtware.matflow.task.task.model.TaskInstance;
import io.thoughtware.matflow.task.task.model.TaskInstanceQuery;
import io.thoughtware.matflow.task.task.service.TasksInstanceService;
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
public class TaskCleanLogCache {

    private Logger logger = LoggerFactory.getLogger(TaskCleanLogCache.class);

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


    @Scheduled(cron = "0 0 1 * * ?")
    // @Scheduled(cron = "0 0/1 * * * *")
    public void scheduledBackups(){

        logger.info("缓存清理定时任务触发.....");

        List<PipelineInstance> allInstance = instanceService.findAllInstance();

        if (allInstance.isEmpty()){
            return;
        }

        List<Cache> allCathe = cacheService.findAllCathe();
        Cache cache = allCathe.get(0);

        int artifactCache = cache.getArtifactCache();
        int logCache = cache.getLogCache();

        for (PipelineInstance instance : allInstance) {
            joinTemplate.joinQuery(instance);
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
        logger.info("缓存清理定时任务完成！");
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
            String key = taskBuildProduct.getKey();
            if (key.equals(PipelineFinal.DEFAULT_ARTIFACT_ADDRESS)){
                String value = taskBuildProduct.getValue();
                FileUtils.deleteQuietly(new File(value));
            }
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














