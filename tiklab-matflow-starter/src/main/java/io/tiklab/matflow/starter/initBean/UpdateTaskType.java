package io.tiklab.matflow.starter.initBean;

import io.tiklab.matflow.support.postprocess.dao.PostprocessDao;
import io.tiklab.matflow.support.postprocess.dao.PostprocessInstanceDao;
import io.tiklab.matflow.support.postprocess.entity.PostprocessEntity;
import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;
import io.tiklab.matflow.support.postprocess.service.PostprocessService;
import io.tiklab.matflow.task.task.dao.TaskInstanceDao;
import io.tiklab.matflow.task.task.dao.TasksDao;
import io.tiklab.matflow.task.task.entity.TaskInstanceEntity;
import io.tiklab.matflow.task.task.entity.TasksEntity;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

@Configuration
public class UpdateTaskType {


    @Autowired
    TasksDao tasksDao;

    @Autowired
    TaskInstanceDao taskInstanceDao;

    @Autowired
    PostprocessDao postprocessDao;

    private static final Logger logger = LoggerFactory.getLogger(UpdateTaskType.class);


    @Bean
    public void updateAllTaskType(){
        logger.info("loading update task config......");
        updateTasks();
        updateTasksInstance();
        updatePostProcess();
        logger.info("loading update task config success");
    }


    void updateTasks(){

        List<TasksEntity> allConfigure = tasksDao.findAllConfigure();

        for (TasksEntity tasksEntity : allConfigure) {
            String s = initTaskType(tasksEntity.getTaskType());
            if (Objects.isNull(s)){
                continue;
            }
            tasksEntity.setTaskType(s);
            tasksDao.updateConfigure(tasksEntity);
        }

    }

    void updateTasksInstance(){

        List<TaskInstanceEntity> allInstance = taskInstanceDao.findAllInstance();
        for (TaskInstanceEntity taskInstanceEntity : allInstance) {
            String s = initTaskType(taskInstanceEntity.getTaskType());
            if (Objects.isNull(s)){
                continue;
            }
            taskInstanceEntity.setTaskType(s);
            taskInstanceDao.updateInstance(taskInstanceEntity);
        }
    }

    void updatePostProcess(){
        List<PostprocessEntity> allPost = postprocessDao.findAllPost();
        for (PostprocessEntity postprocessEntity : allPost) {
            String s = initTaskType(postprocessEntity.getTaskType());
            if (Objects.isNull(s)){
                continue;
            }
            postprocessEntity.setTaskType(s);
            postprocessDao.updatePost(postprocessEntity);
        }


    }

    private String initTaskType(String taskType){
        switch (taskType) {
            case "1" -> {
                return TASK_CODE_GIT;
            }
            case "2" -> {
                return TASK_CODE_GITEE;
            }
            case "3" -> {
                return TASK_CODE_GITHUB;
            }
            case "4" -> {
                return TASK_CODE_GITLAB;
            }
            case TASK_CODE_XCODE -> {
                return TASK_CODE_XCODE;
            }
            case "5" -> {
                return TASK_CODE_SVN;
            }
            case "11" -> {
                return TASK_TEST_MAVENTEST;
            }
            case "21" -> {
                return TASK_BUILD_MAVEN;
            }
            case "22" -> {
                return TASK_BUILD_NODEJS;
            }
            case "31" -> {
                return TASK_DEPLOY_LINUX;
            }
            case "32" -> {
                return TASK_DEPLOY_DOCKER;
            }
            case "41" -> {
                return TASK_CODESCAN_SONAR;
            }
            case "51" -> {
                return TASK_ARTIFACT_NEXUS;
            }
            case "52" -> {
                return TASK_ARTIFACT_SSH;
            }
            case "61" -> {
                return TASK_MESSAGE_MSG;
            }
            case "71" -> {
                return TASK_SCRIPT_BAT;
            }
            case "72" -> {
                return TASK_SCRIPT_SHELL;
            }
            default -> {
                return null;
            }
        }
    }







}
