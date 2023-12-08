package io.thoughtware.matflow.starter.task;

import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.matflow.support.util.PipelineUtil;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstance;
import io.thoughtware.matflow.pipeline.instance.service.PipelineInstanceService;
import io.thoughtware.matflow.task.build.model.TaskBuildProduct;
import io.thoughtware.matflow.task.build.model.TaskBuildProductQuery;
import io.thoughtware.matflow.task.build.service.TaskBuildProductService;
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
    JoinTemplate joinTemplate;


    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduledBackups(){

        logger.info("缓存清理定时任务触发.....");

        List<PipelineInstance> allInstance = instanceService.findAllInstance();

        if (allInstance.isEmpty()){
            return;
        }

        for (PipelineInstance instance : allInstance) {
            joinTemplate.joinQuery(instance);
            String createTime = instance.getCreateTime();
            Date date = PipelineUtil.StringChengeDate(createTime);
            String dateTime = PipelineUtil.findDateTime(date, PipelineFinal.DEFAULT_CLEAN_CACHE_DAY);
            if (StringUtils.isEmpty(dateTime)){
                continue;
            }

            TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
            taskBuildProductQuery.setInstanceId(instance.getInstanceId());
            List<TaskBuildProduct> buildProductList = taskBuildProductService.findBuildProductList(taskBuildProductQuery);
            if (buildProductList.isEmpty()){
                continue;
            }

            logger.info("清理流水线；{}，历史：{}",instance.getPipeline().getName(),instance.getFindNumber() );

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
        logger.info("缓存清理定时任务完成！");

        while (true){
            scheduledBackups();
        }


    }






}
