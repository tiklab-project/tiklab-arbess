package io.tiklab.arbess.support.util.task.service;

import io.tiklab.arbess.agent.support.util.service.PipelineUtilService;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.setting.auth.model.Auth;
import io.tiklab.arbess.setting.auth.service.AuthService;
import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.arbess.setting.tool.model.ScmQuery;
import io.tiklab.arbess.setting.tool.service.ScmService;
import io.tiklab.arbess.stages.model.Stage;
import io.tiklab.arbess.stages.service.StageService;
import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.arbess.support.message.model.TaskMessageType;
import io.tiklab.arbess.support.message.model.TaskMessageTypeQuery;
import io.tiklab.arbess.support.message.service.TaskMessageService;
import io.tiklab.arbess.support.message.service.TaskMessageTypeService;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.service.PostprocessService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.arbess.task.artifact.model.TaskArtifact;
import io.tiklab.arbess.task.artifact.service.TaskArtifactService;
import io.tiklab.arbess.task.build.model.TaskBuild;
import io.tiklab.arbess.task.build.service.TaskBuildService;
import io.tiklab.arbess.task.code.dao.TaskCodeDao;
import io.tiklab.arbess.task.code.entity.TaskCodeEntity;
import io.tiklab.arbess.task.code.model.TaskCode;
import io.tiklab.arbess.task.code.service.TaskCodeService;
import io.tiklab.arbess.task.codescan.model.TaskCodeScan;
import io.tiklab.arbess.task.codescan.service.TaskCodeScanService;
import io.tiklab.arbess.task.deploy.model.TaskDeploy;
import io.tiklab.arbess.task.deploy.service.TaskDeployService;
import io.tiklab.arbess.task.pullArtifact.model.TaskPullArtifact;
import io.tiklab.arbess.task.pullArtifact.service.TaskPullArtifactService;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.arbess.task.test.model.TaskTest;
import io.tiklab.arbess.task.test.service.TaskTestService;
import io.tiklab.dsm.support.DsmProcessTask;
import io.tiklab.privilege.dmRole.model.DmRole;
import io.tiklab.privilege.dmRole.model.DmRoleQuery;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.privilege.role.model.Role;
import io.tiklab.privilege.role.model.RoleQuery;
import io.tiklab.privilege.role.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

@Service
public class TaskUpdate implements DsmProcessTask {


    @Autowired
    RoleService roleService;

    @Autowired
    DmRoleService dmRoleService;

    @Autowired
    VariableService variableService;

    @Autowired
    TaskMessageService taskMessageService;

    @Autowired
    TasksService tasksService;

    @Autowired
    PostprocessService postprocessService;

    @Autowired
    TaskMessageTypeService taskMessageTypeService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    StageService stageService;

    @Autowired
    TaskCodeService taskCodeService;

    @Autowired
    TaskCodeDao taskCodeDao;

    @Autowired
    AuthService authService;

    @Autowired
    ScmService scmService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    TaskCodeService codeService;

    @Autowired
    TaskCodeScanService codeScanService;

    @Autowired
    TaskTestService testService;

    @Autowired
    TaskBuildService buildService;

    @Autowired
    TaskArtifactService artifactService;

    @Autowired
    TaskPullArtifactService pullArtifactService;

    @Autowired
    TaskDeployService taskDeployService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static Map<String, Scm> scmAddress  = new HashMap<>();



    @Override
    public void execute() {
        try {
            logger.info("task load tasks......");

            logger.info("task load init privilege tasks......");
            cleanPrivilege();
            logger.info("task load init privilege success!");

            logger.info("task update pipeline variable......");
            updateVariable();
            logger.info("task update pipeline variable completed!");

            logger.info("task update pipeline message......");
            updateMessage();
            logger.info("task update pipeline message completed!");

            logger.info("task update code auth.....");
            updatePipelineAuth();
            logger.info("task update code auth end.");

            logger.info("task  cache post.....");
            deletePost();
            logger.info("task  cache post end.");

            logger.info("task load scm tasks......");
            scmAddress();

            initTasks();
            logger.info("task load scm success!");

            logger.info("task update deploy strategy type......");
            updateTaskDeploy();
            logger.info("task update deploy strategy type success!");

            logger.info("task cache pull.....");
            deletePullOrPush();
            logger.info("task cache pull end.");


            logger.info("task load complete.");
        }catch (Exception e){
            logger.error("task load error:",e);
        }
    }

    public void cleanPrivilege(){

        String roleId = "2";
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setParentId(roleId);
        roleQuery.setType("2");
        roleQuery.setScope(2);
        List<Role> roleList = roleService.findRoleList(roleQuery);   // 查询项目角色
        for (Role role : roleList) {
            DmRoleQuery dmRoleQuery = new DmRoleQuery();
            dmRoleQuery.setRoleId(role.getId());
            List<DmRole> dmRoleList = dmRoleService.findDmRoleListNoQuery(dmRoleQuery);  // 查询关联的项目角色

            // 删除项目角色以及关联关系
            for (DmRole dmRole : dmRoleList) {
                dmRoleService.deleteDmRole(dmRole.getId());
            }
        }
        // 删除角色
        roleService.deleteRole(roleId);
    }

    public void updateVariable(){

        List<Variable> allVariable = variableService.findAllVariable();

        for (Variable variable : allVariable) {
            String pipelineId = variable.getPipelineId();
            if (!StringUtils.isEmpty(pipelineId)){
                continue;
            }

            if (variable.getType() != 1){
                continue;
            }

            String taskId = variable.getTaskId();
            variable.setPipelineId(taskId);
            variable.setTaskId(" ");

            variableService.updateVariable(variable);
        }
    }

    public void updateMessage(){

        List<Tasks> allTasks = tasksService.findAllTasks();
        for (Tasks tasks : allTasks) {
            String postprocessId = tasks.getPostprocessId();
            if (StringUtils.isEmpty(postprocessId)){
                continue;
            }

            String taskType = tasks.getTaskType();
            if (!taskType.equals("message")){
                continue;
            }

            Postprocess postprocess = postprocessService.findOnePost(postprocessId);

            if (Objects.isNull(postprocess)){
                continue;
            }

            String taskId = tasks.getTaskId();

            TaskMessage message = taskMessageService.findTaskMessageNoQuery(taskId);
            if (!Objects.isNull(message)){
                continue;
            }

            TaskMessageTypeQuery taskMessageTypeQuery = new TaskMessageTypeQuery();
            taskMessageTypeQuery.setMessageId(taskId);
            List<TaskMessageType> messageTypeList = taskMessageTypeService.findMessageTypeList(taskMessageTypeQuery);
            if (messageTypeList.isEmpty()){
                continue;
            }

            TaskMessage taskMessage = new TaskMessage();
            taskMessage.setName(postprocess.getPostName());
            taskMessage.setPipelineId(postprocess.getPipelineId());
            taskMessage.setNoticeType(1);
            taskMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
            taskMessage.setId(taskId);
            taskMessageService.createTaskMessage(taskMessage);

        }
    }

    private void updatePipelineAuth(){

        List<TaskCodeEntity> codeEntityList = taskCodeDao.findAllCode();
        codeEntityList.forEach(codeEntity -> {
            String authType = codeEntity.getAuthType();
            if (!StringUtils.isEmpty(authType)){
                return;
            }
            String authId = codeEntity.getAuthId();
            codeEntity.setAuthType(PipelineFinal.AUTH_NONE);
            if (!StringUtils.isEmpty(authId)){
                Auth auth = authService.findOneAuth(authId);
                if (!Objects.isNull(auth)){
                    int authPublic = auth.getAuthPublic();
                    if (authPublic == 1){
                        codeEntity.setAuthType(PipelineFinal.AUTH_USER_PASS);
                        codeEntity.setUsername(auth.getUsername());
                        codeEntity.setPassword(auth.getPassword());
                    }else {
                        codeEntity.setAuthType(PipelineFinal.AUTH_PRI_KEY);
                        codeEntity.setPriKey(auth.getPrivateKey());
                    }
                }
            }
            taskCodeDao.updateCode(codeEntity);
        });
    }

    private void deletePost(){
        List<Postprocess> pipelinePostTask = postprocessService.findAllPost();
        List<Postprocess> list = pipelinePostTask.stream()
                .filter(postprocess -> !Objects.isNull(postprocess))
                .filter(postprocess -> postprocess.getTaskType().equals(PipelineFinal.TASK_TYPE_SCRIPT))
                .collect(Collectors.toList());

        for (Postprocess postprocess : list) {
            postprocessService.deletePostTask(postprocess.getPostId());
        }

    }

    public void initTasks(){

        List<Tasks> allTasks = tasksService.findAllTasks();
        try {
            List<Tasks> noCodeList = allTasks.stream()
                    .filter(tasks -> !tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_CODE))
                    .collect(Collectors.toList());

            List<Tasks> codeList = allTasks.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_CODE))
                    .collect(Collectors.toList());

            if (!codeList.isEmpty()){
                for (Tasks tasks : codeList) {
                    String taskId = tasks.getTaskId();
                    TaskCode code = codeService.findOneCode(taskId);
                    Scm gitScm = scmAddress.get(TASK_TOOL_TYPE_GIT);
                    Scm svnScm = scmAddress.get(TASK_TOOL_TYPE_SVN);
                    code.setType(tasks.getTaskType());
                    if (Objects.isNull(code.getToolGit()) && !Objects.isNull(gitScm)){
                        code.setToolGit(gitScm);
                        codeService.updateOneCode(code);
                    }
                    if (Objects.isNull(code.getToolSvn()) && !Objects.isNull(svnScm)){
                        code.setToolSvn(svnScm);
                        codeService.updateOneCode(code);
                    }
                }
            }

            List<Tasks> codeScanList = noCodeList.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_CODESCAN))
                    .collect(Collectors.toList());

            for (Tasks tasks : codeScanList) {
                String taskId = tasks.getTaskId();
                TaskCodeScan codeScan = codeScanService.findOneCodeScanNoQuery(taskId);
                if (Objects.isNull(codeScan.getToolJdk())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
                    if (!Objects.isNull(scm)){
                        codeScan.setToolJdk(scm);
                        codeScanService.updateCodeScan(codeScan);
                    }
                }
                if (Objects.isNull(codeScan.getToolMaven())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
                    if (!Objects.isNull(scm)){
                        codeScan.setToolMaven(scm);
                        codeScanService.updateCodeScan(codeScan);
                    }
                }
            }


            List<Tasks> testList = noCodeList.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_TEST))
                    .collect(Collectors.toList());
            for (Tasks tasks : testList) {
                String taskId = tasks.getTaskId();
                TaskTest taskTest = testService.findOneTest(taskId);
                if (Objects.isNull(taskTest.getToolJdk())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
                    if (!Objects.isNull(scm)){
                        taskTest.setToolJdk(scm);
                        testService.updateTest(taskTest);
                    }
                }
                if (Objects.isNull(taskTest.getToolMaven())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
                    if (!Objects.isNull(scm)){
                        taskTest.setToolMaven(scm);
                        testService.updateTest(taskTest);
                    }
                }

            }

            List<Tasks> buildList = noCodeList.stream()
                    .filter(tasks -> tasksService.findTaskType(tasks.getTaskType()).equals(PipelineFinal.TASK_TYPE_BUILD))
                    .collect(Collectors.toList());
            for (Tasks tasks : buildList) {
                String taskId = tasks.getTaskId();
                TaskBuild build = buildService.findOneBuild(taskId);
                if (Objects.isNull(build.getToolJdk())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_JDK);
                    if (!Objects.isNull(scm)){
                        build.setToolJdk(scm);
                        buildService.updateBuild(build);
                    }
                }
                if (Objects.isNull(build.getToolMaven())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_MAVEN);
                    if (!Objects.isNull(scm)){
                        build.setToolMaven(scm);
                        buildService.updateBuild(build);
                    }
                }
                if (Objects.isNull(build.getToolNodejs())){
                    Scm scm = scmAddress.get(TASK_TOOL_TYPE_NODEJS);
                    if (!Objects.isNull(scm)){
                        build.setToolNodejs(scm);
                        buildService.updateBuild(build);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化scm地址
     */
    public void scmAddress(){

        // 获取JDk
        ScmQuery scmJdkQuery = new ScmQuery();
        scmJdkQuery.setScmType(PipelineFinal.TASK_TOOL_TYPE_JDK);
        List<Scm> scmJdkList = scmService.findPipelineScmList(scmJdkQuery);
        if (Objects.isNull(scmJdkList) || scmJdkList.isEmpty()){
            String javaPath = utilService.findJavaPath();
            Scm scm = new Scm();
            scm.setScmType(PipelineFinal.TASK_TOOL_TYPE_JDK);
            scm.setScmAddress(javaPath);
            scm.setScmName("default-jdk");
            String scmId = scmService.createPipelineScmNoValid(scm);
            scm.setScmId(scmId);
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_JDK,scm);

        }else {
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_JDK,scmJdkList.get(0));
        }


        List<Scm> allScm = scmService.findAllPipelineScm();

        List<Scm> nodeJsList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_NODEJS))
                .collect(Collectors.toList());
        if ( !nodeJsList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_NODEJS,nodeJsList.get(0));
        }

        List<Scm> mavenList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_MAVEN))
                .collect(Collectors.toList());
        if ( !mavenList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_MAVEN,mavenList.get(0));
        }

        List<Scm> gitList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_GIT))
                .collect(Collectors.toList());
        if ( !gitList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_GIT,gitList.get(0));
        }

        List<Scm> svnList = allScm.stream().filter(scm -> scm.getScmType().equals(TASK_TOOL_TYPE_SVN))
                .collect(Collectors.toList());
        if ( !svnList.isEmpty()){
            scmAddress.put(PipelineFinal.TASK_TOOL_TYPE_SVN,svnList.get(0));
        }

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


    private void deletePullOrPush(){
        List<TaskPullArtifact> pullArtifactList = pullArtifactService.findAllPullArtifact();

        List<TaskArtifact> artifactList = artifactService.findAllProduct();

        List<String> pullArtifactTaskIdList = pullArtifactList.stream()
                .filter(pullArtifact -> !StringUtils.isEmpty(pullArtifact.getPullType()))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_DOCKER))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_SSH))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_HADESS))
                .filter(pullArtifact -> !pullArtifact.getPullType().equals(PipelineFinal.TASK_DOWNLOAD_NEXUS))
                .map(TaskPullArtifact::getTaskId)
                .collect(Collectors.toList());

        List<String> taskIdList = new ArrayList<>(pullArtifactTaskIdList);

        List<String> artifactTaskIdList = artifactList.stream()
                .filter(artifact -> !StringUtils.isEmpty(artifact.getArtifactType()))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_DOCKER))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_SSH))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_HADESS))
                .filter(artifact -> !artifact.getArtifactType().equals(PipelineFinal.TASK_UPLOAD_NEXUS))
                .map(TaskArtifact::getTaskId)
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());

        List<Stage> otherStageList = stageService.findAllStagesList(otherStageIdList);

        List<String> list = otherStageList.stream().map(Stage::getParentId)
                .filter(stageParentId -> !StringUtils.isEmpty(stageParentId))
                .collect(Collectors.toList());

        List<Stage> rootList = stageService.findAllStagesList(list);

        List<Stage> stageList = new ArrayList<>(rootList);
        stageList.addAll(otherStageList);

        for (Stage stage : stageList) {
            stageService.deleteStages(stage.getStageId());
        }

    }

}

