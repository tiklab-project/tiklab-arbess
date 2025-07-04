package io.tiklab.arbess.starter.config;

import io.tiklab.arbess.agent.support.util.service.PipelineUtilService;
import io.tiklab.arbess.agent.ws.config.WebSocketClient;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.arbess.setting.auth.model.Auth;
import io.tiklab.arbess.setting.auth.service.AuthService;
import io.tiklab.arbess.setting.other.model.Cache;
import io.tiklab.arbess.setting.other.service.CacheService;
import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.arbess.setting.tool.model.ScmQuery;
import io.tiklab.arbess.setting.tool.service.ScmService;
import io.tiklab.arbess.stages.model.Stage;
import io.tiklab.arbess.stages.model.StageInstance;
import io.tiklab.arbess.stages.model.StageInstanceQuery;
import io.tiklab.arbess.stages.service.StageInstanceServer;
import io.tiklab.arbess.stages.service.StageService;
import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.arbess.support.message.model.TaskMessageType;
import io.tiklab.arbess.support.message.model.TaskMessageTypeQuery;
import io.tiklab.arbess.support.message.service.TaskMessageService;
import io.tiklab.arbess.support.message.service.TaskMessageTypeService;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.model.PostprocessInstance;
import io.tiklab.arbess.support.postprocess.service.PostprocessInstanceService;
import io.tiklab.arbess.support.postprocess.service.PostprocessService;
import io.tiklab.arbess.support.trigger.model.Trigger;
import io.tiklab.arbess.support.trigger.model.TriggerJob;
import io.tiklab.arbess.support.trigger.model.TriggerQuery;
import io.tiklab.arbess.support.trigger.quartz.Job;
import io.tiklab.arbess.support.trigger.quartz.RunJob;
import io.tiklab.arbess.support.trigger.service.CronUtils;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import io.tiklab.arbess.support.trigger.service.TriggerTimeService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.arbess.task.artifact.model.TaskArtifact;
import io.tiklab.arbess.task.artifact.service.TaskArtifactService;
import io.tiklab.arbess.task.build.model.TaskBuild;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.model.TaskBuildProductQuery;
import io.tiklab.arbess.task.build.service.TaskBuildProductService;
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
import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.model.TaskInstanceQuery;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksInstanceService;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.arbess.task.test.model.TaskTest;
import io.tiklab.arbess.task.test.service.TaskTestService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsClient;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsServer;
import io.tiklab.dsm.boot.starter.annotation.EnableDsm;
import io.tiklab.dsm.model.DsmConfig;
import io.tiklab.dsm.support.DsmConfigBuilder;
import io.tiklab.dsm.support.DsmProcessTask;
import io.tiklab.eam.boot.starter.annotation.EnableEamClient;
import io.tiklab.eam.boot.starter.annotation.EnableEamServer;
import io.tiklab.gateway.boot.starter.annotation.EnableGateway;
import io.tiklab.install.runner.TiklabApplicationRunner;
import io.tiklab.install.spring.boot.starter.EnableInstallServer;
import io.tiklab.licence.boot.starter.annotation.EnableLicenceServer;
import io.tiklab.arbess.EnableArbessServer;
import io.tiklab.message.message.model.MessageItem;
import io.tiklab.message.msgsub.config.MessageSubscribeConfig;
import io.tiklab.message.msgsub.service.MessageListener;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageServer;
import io.tiklab.openapi.boot.starter.annotation.EnableOpenApi;
import io.tiklab.openapi.config.AllowConfig;
import io.tiklab.openapi.config.AllowConfigBuilder;
import io.tiklab.openapi.config.OpenApiConfig;
import io.tiklab.postgresql.spring.boot.starter.EnablePostgresql;
import io.tiklab.postin.client.EnablePostInClient;
import io.tiklab.postin.client.openapi.ParamConfig;
import io.tiklab.postin.client.openapi.ParamConfigBuilder;
import io.tiklab.postin.client.openapi.PostInClientConfig;
import io.tiklab.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.tiklab.privilege.dmRole.model.DmRole;
import io.tiklab.privilege.dmRole.model.DmRoleQuery;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.privilege.role.model.Role;
import io.tiklab.privilege.role.model.RoleQuery;
import io.tiklab.privilege.role.service.RoleService;
import io.tiklab.rpc.boot.starter.annotation.EnableRpc;
import io.tiklab.security.boot.stater.annotation.EnableSecurityServer;
import io.tiklab.toolkit.boot.starter.annotation.EnableToolkit;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.user.boot.starter.annotation.EnableUserClient;
import io.tiklab.user.boot.starter.annotation.EnableUserServer;
import io.tiklab.user.util.util.CodeFinal;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;


/**
 * @author admin
 */

@Configuration
@EnableToolkit
//内嵌数据库
@EnablePostgresql
@EnableInstallServer
@EnableDal
// @EnableRpc
@EnableGateway
@EnableOpenApi
@EnableDcsClient
@EnableDcsServer
@EnableDsm
@EnableRpc
//用户中心
@EnableUserServer
@EnableUserClient

//登录,认证
@EnableEamClient
@EnableEamServer

//消息,日志,待办
@EnableMessageServer
@EnableSecurityServer
//权限中心
@EnablePrivilegeServer
@EnableLicenceServer

// postin
@EnablePostInClient

@EnableArbessServer
@ComponentScan(value = "io.tiklab.arbess")
public class ArbessAutoConfiguration {
}


@Component
class TaskInitAgent implements TiklabApplicationRunner {

    @Autowired
    WebSocketClient webSocketClient;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run() {
        logger.info("The init agent.....");
        webSocketClient.initWebSocketConnect();
        logger.info("The init agent end.");

        new Thread(
                () -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logger.info("enable automatic connection .....");
                    WebSocketClient.beginConnect = true;
                }
        ).start();

    }
}

@Component
class TaskTriggerCleanLog {

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

@Configuration
class TaskTriggerInitJob implements TiklabApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TaskTriggerInitJob.class);

    @Autowired
    Job job;

    @Autowired
    TriggerService triggerServer;


    @Autowired
    TriggerTimeService triggerTimeService;


    @Override
    public void run(){
        logger.info(" load scheduled tasks......");
        addTriggerJob();
        logger.info(" timed task loading completed!");
    }


    public void addTriggerJob(){
        try {
            TriggerQuery triggerQuery = new TriggerQuery();
            triggerQuery.setState(1);
            List<Trigger> triggerList = triggerServer.findTriggerList(triggerQuery);

            for (Trigger trigger : triggerList) {

                String cron = trigger.getCron();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date inputTime = sdf.parse(CronUtils.weekTime(cron));
                    boolean after = inputTime.after(new Date());
                    if (!after){
                        continue;
                    }
                } catch (Exception e) {
                    logger.error("corn转换时间失败：{}",cron);
                    return;
                }

                TriggerJob triggerJob = new TriggerJob()
                        .setTriggerId(trigger.getId())
                        .setCron(cron)
                        .setJobClass( RunJob.class)
                        .setPipelineId(trigger.getPipelineId())
                        .setGroup(DEFAULT);
                try {
                    job.addJob(triggerJob);
                } catch (SchedulerException e) {
                    throw new ApplicationException(e);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("timed task loading  error : {}", e.getMessage());
        }
    }

}

@Component
class TaskUpdate implements DsmProcessTask {


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

@Configuration
class ArbessFileConfigure {

    @Bean
    public MultipartConfigElement multipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件大小5GB
        factory.setMaxFileSize(DataSize.ofGigabytes(5L));
        //设置总上传数据大小5GB
        factory.setMaxRequestSize(DataSize.ofGigabytes(5L));

        return factory.createMultipartConfig();
    }

}

// @Configuration
class ArbessMessageSubscribeConfiguration {

    @Autowired
    MessageTest messageTest;

    @Value("${app.name}")
    private String appName;

    @Bean
    public MessageSubscribeConfig messageSubscribeConfig() {
        String[] msyTypes = { "USER_CREATE",
                "USER_UPDATE",
                "USER_DELETE",
                "USER_LOGIN"
        };
        return MessageSubscribeConfig.instance()
                .subscribe(msyTypes, "http://192.168.10.15:8080")
                .service(messageTest)
                .consumer(CodeFinal.findAppId(appName))
                .get();
    }

}

@Configuration
class MessageTest implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageTest.class);


    @Override
    public void handleMessage(MessageItem messageItem) {
        logger.info("接收到消息:{}", messageItem.getId());
    }
}


@Configuration
class ArbessPostInClientConfiguration {

    @Bean
    PostInClientConfig postInClientConfig(ParamConfig paramConfig){
        PostInClientConfig config = new PostInClientConfig();
        config.setParamConfig(paramConfig);

        return config;
    }

    @Bean
    ParamConfig paramConfig(){
        //设置请求头，属性名称：属性描述
        HashMap<String,String> headers = new HashMap<>();
        headers.put("accessToken","openApi中添加accessToken");

        return ParamConfigBuilder.instance()
                .setScanPackage("io.tiklab.arbess") //设置扫描的包路径
                .prePath("/api")             //设置额外的前缀
                .setHeaders(headers)               //设置请求头
                .get();
    }

}