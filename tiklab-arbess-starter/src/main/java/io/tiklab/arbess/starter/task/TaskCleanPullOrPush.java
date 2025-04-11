package io.tiklab.arbess.starter.task;


import io.tiklab.arbess.setting.service.AuthService;
import io.tiklab.arbess.stages.model.Stage;
import io.tiklab.arbess.stages.service.StageService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.artifact.model.TaskArtifact;
import io.tiklab.arbess.task.artifact.service.TaskArtifactService;
import io.tiklab.arbess.task.pullArtifact.model.TaskPullArtifact;
import io.tiklab.arbess.task.pullArtifact.service.TaskPullArtifactService;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskCleanPullOrPush implements TiklabApplicationRunner {

    @Autowired
    TasksService tasksService;

    @Autowired
    StageService stageService;

    @Autowired
    TaskArtifactService artifactService;

    @Autowired
    TaskPullArtifactService pullArtifactService;

    @Autowired
    AuthService authService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("The cache pull.....");
        delete();
        logger.info("The cache pull end.");
    }


    private void delete(){
        List<TaskPullArtifact> pullArtifactList = pullArtifactService.findAllPullArtifact();

        List<TaskArtifact> artifactList = artifactService.findAllProduct();

        List<String> pullArtifactTaskIdList = pullArtifactList.stream()
                .filter(pullArtifact -> !StringUtils.isEmpty(pullArtifact.getPullType()))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_DOCKER))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_SSH))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_HADESS))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_NEXUS))
                .map(TaskPullArtifact::getTaskId)
                .toList();

        List<String> taskIdList = new ArrayList<>(pullArtifactTaskIdList);

        List<String> artifactTaskIdList = artifactList.stream()
                .filter(artifact -> !StringUtils.isEmpty(artifact.getArtifactType()))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_DOCKER))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_SSH))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_HADESS))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_NEXUS))
                .map(TaskArtifact::getTaskId)
                .toList();
        taskIdList.addAll(artifactTaskIdList);

        if (taskIdList.isEmpty()){
            return;
        }

        for (TaskPullArtifact taskPullArtifact : pullArtifactList) {
            pullArtifactService.deletePullArtifact(taskPullArtifact.getTaskId());
        }

        for (TaskArtifact artifact : artifactList) {
            artifactService.deleteProduct(artifact.getTaskId());
        }


        List<Tasks> tasksList = tasksService.findTasksList(taskIdList);
        for (Tasks tasks : tasksList) {
            tasksService.deleteTasks(tasks.getTaskId());
        }

        if (tasksList.isEmpty()){
            return;
        }


        List<String> otherStageIdList = tasksList.stream().map(Tasks::getStageId)
                .filter(stageId -> !StringUtils.isEmpty(stageId))
                .toList();

        List<Stage> otherStageList = stageService.findAllStagesList(otherStageIdList);

        List<String> list = otherStageList.stream().map(Stage::getParentId)
                .filter(stageParentId -> !StringUtils.isEmpty(stageParentId))
                .toList();

        List<Stage> rootList = stageService.findAllStagesList(list);

        List<Stage> stageList = new ArrayList<>(rootList);
        stageList.addAll(otherStageList);

        for (Stage stage : stageList) {
            stageService.deleteStages(stage.getStageId());
        }

    }


}

















