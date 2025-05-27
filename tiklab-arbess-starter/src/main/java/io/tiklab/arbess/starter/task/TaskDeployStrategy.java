package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.deploy.model.TaskDeploy;
import io.tiklab.arbess.task.deploy.service.TaskDeployService;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskDeployStrategy implements TiklabApplicationRunner {


    @Autowired
    TaskDeployService taskDeployService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("Load update deploy strategy type......");
        updateTaskDeploy();
        logger.info("Load update deploy strategy type success!");
    }


    public void updateTaskDeploy() {

        List<TaskDeploy> allDeploy = taskDeployService.findAllDeploy();
        for (TaskDeploy taskDeploy : allDeploy) {
            String strategyType = taskDeploy.getStrategyType();
            if (StringUtils.isEmpty(strategyType) || strategyType.equals(PipelineFinal.DEFAULT)){
                taskDeploy.setStrategyType(PipelineFinal.TASK_DEPLOY_STRATEGY_ONE);
                taskDeployService.updateDeploy(taskDeploy);
            }
        }


    }


}
