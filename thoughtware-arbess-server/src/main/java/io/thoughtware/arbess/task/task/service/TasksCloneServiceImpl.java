package io.thoughtware.arbess.task.task.service;

import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.arbess.setting.service.AuthHostService;
import io.thoughtware.arbess.setting.service.AuthService;
import io.thoughtware.arbess.setting.service.AuthThirdService;
import io.thoughtware.arbess.support.condition.service.ConditionService;
import io.thoughtware.arbess.support.postprocess.dao.PostprocessDao;
import io.thoughtware.arbess.support.postprocess.entity.PostprocessEntity;
import io.thoughtware.arbess.support.postprocess.model.PostprocessQuery;
import io.thoughtware.arbess.support.util.util.PipelineFinal;
import io.thoughtware.arbess.support.variable.service.VariableService;
import io.thoughtware.arbess.task.artifact.model.TaskArtifact;
import io.thoughtware.arbess.task.artifact.service.TaskArtifactService;
import io.thoughtware.arbess.task.build.model.TaskBuild;
import io.thoughtware.arbess.task.build.service.TaskBuildService;
import io.thoughtware.arbess.task.code.model.TaskCode;
import io.thoughtware.arbess.task.code.service.TaskCodeGitHubService;
import io.thoughtware.arbess.task.code.service.TaskCodeGitLabService;
import io.thoughtware.arbess.task.code.service.TaskCodeGiteeService;
import io.thoughtware.arbess.task.code.service.TaskCodeService;
import io.thoughtware.arbess.task.codescan.model.TaskCodeScan;
import io.thoughtware.arbess.task.codescan.service.TaskCodeScanService;
import io.thoughtware.arbess.task.deploy.model.TaskDeploy;
import io.thoughtware.arbess.task.deploy.service.TaskDeployService;
import io.thoughtware.arbess.task.message.model.TaskMessageType;
import io.thoughtware.arbess.task.message.service.TaskMessageTypeService;
import io.thoughtware.arbess.task.pullArtifact.model.TaskPullArtifact;
import io.thoughtware.arbess.task.pullArtifact.service.TaskPullArtifactService;
import io.thoughtware.arbess.task.script.model.TaskScript;
import io.thoughtware.arbess.task.script.service.TaskScriptService;
import io.thoughtware.arbess.task.task.dao.TasksDao;
import io.thoughtware.arbess.task.task.entity.TasksEntity;
import io.thoughtware.arbess.task.task.model.Tasks;
import io.thoughtware.arbess.task.test.model.TaskTest;
import io.thoughtware.arbess.task.test.service.TaskTestService;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksCloneServiceImpl implements TasksCloneService {


    @Autowired
    TasksDao tasksDao;

    @Autowired
    TaskCodeService codeService;

    @Autowired
    TaskBuildService buildService;

    @Autowired
    TaskTestService testService;

    @Autowired
    TaskDeployService deployService;

    @Autowired
    TaskCodeScanService codeScanService;

    @Autowired
    TaskArtifactService productServer;

    @Autowired
    TaskMessageTypeService messageTypeServer;

    @Autowired
    TaskScriptService scriptServer;

    @Autowired
    AuthService authServer;

    @Autowired
    AuthThirdService authServerServer;

    @Autowired
    AuthHostService authHostService;

    @Autowired
    VariableService variableService;

    @Autowired
    ConditionService conditionService;

    @Autowired
    PostprocessDao postprocessDao;

    @Autowired
    TaskCodeGiteeService taskCodeGiteeService;

    @Autowired
    TaskCodeGitHubService taskCodeGitHubService;

    @Autowired
    TaskCodeGitLabService taskCodeGitLabService;

    @Autowired
    TaskPullArtifactService pullArtifactService;

    @Autowired
    TasksService tasksService;


    @Override
    public void clonePostTasks(String id ,String cloneId){

        Tasks task = tasksService.findOnePostTask(id);

        // 克隆任务
        TasksEntity tasksEntity = BeanMapper.map(task, TasksEntity.class);
        tasksEntity.setPostprocessId(cloneId);
        String taskCloneId = tasksDao.createConfigure(tasksEntity);

        // 克隆任务详情
        cloneDifferentTask(task.getTaskId(),taskCloneId,task.getTaskType());
    }

    @Override
    public void clonePipelineTasks(String id ,String cloneId){

        List<Tasks> tasks = tasksService.finAllPipelineTask(id);
        for (Tasks task : tasks) {

            String taskId = task.getTaskId();

            // 克隆任务
            TasksEntity tasksEntity = BeanMapper.map(task, TasksEntity.class);
            tasksEntity.setPipelineId(cloneId);
            String taskCloneId = tasksDao.createConfigure(tasksEntity);

            // 克隆任务详情
            cloneDifferentTask(taskId,taskCloneId,task.getTaskType());

            // 克隆任务变量
            variableService.cloneVariable(taskId,taskCloneId);

            // 克隆任务条件
            conditionService.cloneCond(taskId,taskCloneId);

            // 克隆任务后置处理
            PostprocessQuery postprocessQuery = new PostprocessQuery();
            postprocessQuery.setTaskId(taskId);
            List<PostprocessEntity> postTaskList = postprocessDao.findPostTaskList(postprocessQuery);
            for (PostprocessEntity postprocessEntity : postTaskList) {
                String postProcessId = postprocessEntity.getPostId();
                postprocessEntity.setTaskId(taskCloneId);
                String clonePostProcessId = postprocessDao.createPost(postprocessEntity);
                clonePostTasks(postProcessId,clonePostProcessId);
            }
        }
    }

    @Override
    public void cloneStageTasks(String id ,String cloneId){

        List<Tasks> tasks = tasksService.finAllStageTask(id);
        for (Tasks task : tasks) {

            String taskId = task.getTaskId();

            // 克隆任务
            TasksEntity tasksEntity = BeanMapper.map(task, TasksEntity.class);
            tasksEntity.setStageId(cloneId);
            String taskCloneId = tasksDao.createConfigure(tasksEntity);

            // 克隆任务详情
            cloneDifferentTask(task.getTaskId(),taskCloneId,task.getTaskType());

            // 克隆任务变量
            variableService.cloneVariable(task.getTaskId(),taskCloneId);

            // 克隆任务条件
            conditionService.cloneCond(task.getTaskId(),taskCloneId);

            // 克隆任务后置处理
            PostprocessQuery postprocessQuery = new PostprocessQuery();
            postprocessQuery.setTaskId(taskId);
            List<PostprocessEntity> postTaskList = postprocessDao.findPostTaskList(postprocessQuery);
            for (PostprocessEntity postprocessEntity : postTaskList) {
                String postProcessId = postprocessEntity.getPostId();
                postprocessEntity.setTaskId(taskCloneId);
                String clonePostProcessId = postprocessDao.createPost(postprocessEntity);
                clonePostTasks(postProcessId,clonePostProcessId);
            }
        }
    }

    /**
     * 分发克隆不同类型的任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    private void cloneDifferentTask(String taskId,String cloneTaskId,String taskType){
        switch (tasksService.findTaskType(taskType)) {
            case PipelineFinal.TASK_TYPE_CODE     -> {
                TaskCode task = codeService.findOneCode(taskId);
                task.setTaskId(cloneTaskId);
                codeService.createCode(task);
            }
            case PipelineFinal.TASK_TYPE_TEST     -> {
                TaskTest task = testService.findOneTest(taskId);
                task.setTaskId(cloneTaskId);
                testService.createTest(task);
            }
            case PipelineFinal.TASK_TYPE_BUILD    -> {
                TaskBuild task = buildService.findOneBuild(taskId);
                task.setTaskId(cloneTaskId);
                buildService.createBuild(task);
            }
            case PipelineFinal.TASK_TYPE_DEPLOY   -> {
                TaskDeploy task = deployService.findOneDeploy(taskId);
                task.setTaskId(cloneTaskId);
                deployService.createDeploy(task);
            }
            case PipelineFinal.TASK_TYPE_CODESCAN -> {
                TaskCodeScan task = codeScanService.findOneCodeScan(taskId);
                task.setTaskId(cloneTaskId);
                codeScanService.createCodeScan(task);
            }
            case PipelineFinal.TASK_TYPE_ARTIFACT -> {
                TaskArtifact task = productServer.findOneProduct(taskId);
                task.setTaskId(cloneTaskId);
                productServer.createProduct(task);
            }
            case PipelineFinal.TASK_TYPE_PULL -> {
                TaskPullArtifact task = pullArtifactService.findOnePullArtifact(taskId);
                task.setTaskId(cloneTaskId);
                pullArtifactService.createPullArtifact(task);
            }
            case PipelineFinal.TASK_TYPE_MESSAGE -> {
                TaskMessageType task = messageTypeServer.findMessage(taskId);
                task.setTaskId(cloneTaskId);
                messageTypeServer.createMessage(task);
            }
            case PipelineFinal.TASK_TYPE_SCRIPT   -> {
                TaskScript task = scriptServer.findScript(taskId);
                task.setTaskId(cloneTaskId);
                scriptServer.createScript(task);
            }
            default -> throw new ApplicationException("无法更新未知的配置类型:"+taskType);
        }
    }




}
