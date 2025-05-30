package io.tiklab.arbess.task.task.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.setting.hostgroup.service.AuthHostGroupService;
import io.tiklab.arbess.setting.k8s.model.Kubectl;
import io.tiklab.arbess.setting.k8s.service.KubectlService;
import io.tiklab.arbess.setting.host.service.AuthHostService;
import io.tiklab.arbess.setting.auth.service.AuthService;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.hostgroup.model.HostGroup;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.arbess.setting.tool.service.ScmService;
import io.tiklab.arbess.support.condition.service.ConditionService;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.arbess.task.artifact.model.TaskArtifact;
import io.tiklab.arbess.task.artifact.service.TaskArtifactService;
import io.tiklab.arbess.task.build.model.TaskBuild;
import io.tiklab.arbess.task.build.service.TaskBuildService;
import io.tiklab.arbess.task.code.model.TaskCode;
import io.tiklab.arbess.task.code.model.ThirdQuery;
import io.tiklab.arbess.task.code.model.ThirdUser;
import io.tiklab.arbess.task.code.service.*;
import io.tiklab.arbess.task.codescan.model.TaskCodeScan;
import io.tiklab.arbess.task.codescan.service.TaskCodeScanService;
import io.tiklab.arbess.task.deploy.model.TaskDeploy;
import io.tiklab.arbess.task.deploy.service.TaskDeployService;
import io.tiklab.arbess.task.message.model.TaskMessageType;
import io.tiklab.arbess.task.message.service.TaskMessageTypeService;
import io.tiklab.arbess.task.pullArtifact.model.TaskPullArtifact;
import io.tiklab.arbess.task.pullArtifact.service.TaskPullArtifactService;
import io.tiklab.arbess.task.script.model.TaskScript;
import io.tiklab.arbess.task.script.service.TaskScriptService;
import io.tiklab.arbess.task.task.dao.TasksDao;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.model.TasksQuery;
import io.tiklab.arbess.task.test.model.TaskTest;
import io.tiklab.arbess.task.test.model.TestHuboEnv;
import io.tiklab.arbess.task.test.model.TestHuboRpy;
import io.tiklab.arbess.task.test.model.TestHuboTestPlan;
import io.tiklab.arbess.task.test.service.TaskTestService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.arbess.support.postprocess.dao.PostprocessDao;
import io.tiklab.arbess.task.task.entity.TasksEntity;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

@Service
@Exporter
public class TasksServiceImpl implements TasksService {

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
    TaskArtifactService artifactService;

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
    AuthHostGroupService authHostGroupService;

    @Autowired
    KubectlService kubectlService;

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
    TaskCodePriGitLabService taskCodePriGitLabService;

    @Autowired
    TaskPullArtifactService pullArtifactService;

    @Autowired
    ScmService scmService;

    @Autowired
    JoinTemplate joinTemplate;


    private static final Logger logger = LoggerFactory.getLogger(TasksServiceImpl.class);
    

    /**
     * 初始化配置顺序
     * @param id id
     * @param taskSort 插入顺序
     * @param taskType 任务类型
     * @param type 1.流水线id 2.阶段id
     * @return 顺序
     */
    private Integer initSort(String id, int taskSort,String taskType,int type){
        List<Tasks> list = new ArrayList<>();
        if (type == 1){
            list = finAllPipelineTask(id);
        }
        if (type == 2){
            list = finAllStageTask(id);
        }
        if (type == 3){
            Tasks postTask = findOnePostTask(id);
            if (postTask!= null){
                list.add(postTask);
            }
        }

        if (list.isEmpty()){
            return 1;
        }

        boolean b = findTaskType(taskType).equals(TASK_TYPE_CODE);

        //插入的为代码源
        if (b){
            for (Tasks tasks : list) {
                tasks.setTaskSort(tasks.getTaskSort()+1);
                updateTasks(tasks);
            }
            return 1;
        }

        //更新顺序
        for (Tasks tasks : list) {
            if (tasks.getTaskSort() < taskSort ){
                continue;
            }
            tasks.setTaskSort(tasks.getTaskSort()+1);
            updateTasks(tasks);
        }
        return taskSort;

    }

    @Override
    public String createTasksOrTask(Tasks tasks) throws ApplicationException {

        int sort = 0;
        int taskSort = tasks.getTaskSort();
        String taskType = tasks.getTaskType();

        //判断多任务是否存在代码源
        boolean b = findTaskType(taskType).equals(TASK_TYPE_CODE);
        if (tasks.getPipelineId() != null && (b) ){
            findCode(tasks.getPipelineId());
        }

        //流水线任务
        String pipelineId = tasks.getPipelineId();
        if (!StringUtils.isEmpty(pipelineId)) {
            sort = initSort(tasks.getPipelineId(), taskSort, taskType,1);
        }

        //阶段任务
        String stageId = tasks.getStageId();
        if (!StringUtils.isEmpty(stageId)){
            sort = initSort(stageId, taskSort, taskType,2);
        }

        //后置任务
        String postprocessId = tasks.getPostprocessId();
        if (!StringUtils.isEmpty(postprocessId)){
            sort = initSort(postprocessId, taskSort, taskType,3);
        }

        tasks.setTaskSort(sort);
        // 初始化名称
        if (Objects.isNull(tasks.getTaskName())){
            String taskName = initDifferentTaskName(taskType);
            tasks.setTaskName(taskName);
        }

        tasks.setCreateTime(PipelineUtil.date(1));

        String tasksId = createTasks(tasks);
        //创建任务
        createDifferentTask(tasksId,taskType,tasks.getValues());
        return tasksId;
    }

    private void findCode(String pipelineId){
        List<Tasks> tasks = finAllPipelineTask(pipelineId);
        if (tasks == null){
            return;
        }
        for (Tasks task : tasks) {
            String taskType = task.getTaskType();
            if (findTaskType(taskType).equals(TASK_TYPE_CODE)){
                throw new ApplicationException(58001,"代码源已存在");
            }
        }
    }

    @Override
    public void updateTasksTask(Tasks tasks) {
        String taskId = tasks.getTaskId();
        Object values = tasks.getValues();
        Tasks task = findOneTasks(taskId);
        String taskType = task.getTaskType();
        //更新任务字段值
        updateDifferentTask(taskId,taskType,values);
    }

    @Override
    public void updateTaskName(Tasks tasks) {
        String taskId = tasks.getTaskId();
        Tasks task = findOneTasks(taskId);
        task.setTaskName(tasks.getTaskName());
        updateTasks(task);
    }

    @Override
    public void deleteTasksOrTask(String tasksId) {
        Tasks tasks = findOneTasks(tasksId);

        //删除
        deleteDifferentTask(tasksId, tasks.getTaskType());
        deleteTasks(tasksId);

        List<Tasks> list = new ArrayList<>();
        if (tasks.getStageId() != null){
            list = finAllStageTask(tasks.getStageId());
        }
        if (tasks.getPipelineId() != null){
            list = finAllPipelineTask(tasks.getPipelineId());
        }

        //更新顺序
        if (list.isEmpty()){
            return;
        }
        for (Tasks pipelineTasks : list) {
            int sort = pipelineTasks.getTaskSort();
            if (sort == 1 || tasks.getTaskSort() > sort){
                continue;
            }
            pipelineTasks.setTaskSort(sort-1);
            updateTasks(pipelineTasks);
        }
    }

    @Override
    public void deleteAllTasksOrTask(String id,int type) {
        List<Tasks> list;

        if (type == 1){
            list = finAllPipelineTask(id);
        }else {
            list = finAllStageTask(id);
        }

        if (list.isEmpty()){
            return;
        }
        //循环删除任务
        for (Tasks tasks : list) {
            String configId = tasks.getTaskId();
            String taskType = tasks.getTaskType();
            deleteDifferentTask(configId,taskType);
            deleteTasks(configId);
        }
    }
    
    @Override
    public List<Tasks> finAllPipelineTask(String pipelineId) {
        TasksQuery tasksQuery = new TasksQuery();
        tasksQuery.setPipelineId(pipelineId);
        List<TasksEntity> tasksEntityList = tasksDao.findTaskList(tasksQuery);
        return BeanMapper.mapList(tasksEntityList, Tasks.class);
    }
    
    @Override
    public List<Tasks> finAllStageTask(String stageId) {
        TasksQuery tasksQuery = new TasksQuery();
        tasksQuery.setStageId(stageId);
        List<TasksEntity> tasksEntityList = tasksDao.findTaskList(tasksQuery);
        return BeanMapper.mapList(tasksEntityList, Tasks.class);
    }

    @Override
    public Tasks findOnePostTask(String postId) {
        TasksQuery tasksQuery = new TasksQuery();
        tasksQuery.setPostprocessId(postId);
        List<TasksEntity> tasksEntityList = tasksDao.findTaskList(tasksQuery);
        List<Tasks> tasksList = BeanMapper.mapList(tasksEntityList, Tasks.class);
        if (tasksList.isEmpty()){
            return null;
        }
        return tasksList.get(0);
    }

    @Override
    public Tasks findOnePostTaskOrTask(String postId) {
        Tasks postTask = findOnePostTask(postId);
        String taskType = postTask.getTaskType();
        Object task = findTaskDetails( postTask.getTaskId(), taskType);
        postTask.setValues(task);
        postTask.setTask(task);
        postTask.setTaskType(taskType);
        return postTask;
    }

    private void addTaskAuth(List<Tasks> tasks){
        for (Tasks task : tasks) {
            Object object;
            String jsonString = JSONObject.toJSONString(task.getTask());
            String taskType = task.getTaskType();
            String taskId = task.getTaskId();

            switch (findTaskType(taskType)) {
                case TASK_TYPE_CODE -> {
                    TaskCode taskCode = JSONObject.parseObject(jsonString, TaskCode.class);
                    String authId = taskCode.getAuthId();
                    if (!Objects.isNull(authId)){
                        Object auth ;
                        switch (taskType) {
                            case TASK_CODE_GITEE ->{
                                AuthThird authThird = authServerServer.findOneAuthServer(authId);
                                try {
                                    ThirdQuery thirdQuery = new ThirdQuery();
                                    thirdQuery.setAuthId(authId);
                                    ThirdUser thirdUser = taskCodeGiteeService.findAuthUser(thirdQuery);
                                    authThird.setUsername(thirdUser.getPath());
                                }catch (Exception e){
                                    logger.error("获取GitEe授权用户名失败，原因：{}",e.getMessage());
                                }
                                auth = authThird;
                            }
                            case  TASK_CODE_GITHUB  ->{
                                AuthThird authThird = authServerServer.findOneAuthServer(authId);
                                try {
                                    ThirdQuery thirdQuery = new ThirdQuery();
                                    thirdQuery.setAuthId(authId);
                                    ThirdUser thirdUser = taskCodeGitHubService.findAuthUser(thirdQuery);
                                    authThird.setUsername(thirdUser.getPath());
                                }catch (Exception e){
                                    logger.error("获取GiTHub授权用户名失败，原因：{}",e.getMessage());
                                }
                                auth = authThird;
                            }
                            case TASK_CODE_GITLAB->{
                                AuthThird authThird = authServerServer.findOneAuthServer(authId);
                                try {
                                    ThirdQuery thirdQuery = new ThirdQuery();
                                    thirdQuery.setAuthId(authId);
                                    ThirdUser thirdUser = taskCodeGitLabService.findAuthUser(thirdQuery);
                                    authThird.setUsername(thirdUser.getPath());
                                }catch (Exception e){
                                    logger.error("获取GitLab授权用户名失败，原因：{}",e.getMessage());
                                }
                                auth = authThird;
                            }
                            case TASK_CODE_PRI_GITLAB->{
                                AuthThird authThird = authServerServer.findOneAuthServer(authId);
                                try {
                                    ThirdQuery thirdQuery = new ThirdQuery();
                                    thirdQuery.setAuthId(authId);
                                    ThirdUser thirdUser = taskCodePriGitLabService.findAuthUser(thirdQuery);
                                    authThird.setUsername(thirdUser.getPath());
                                }catch (Exception e){
                                    logger.error("获取自建GitLab授权用户名失败，原因：{}",e.getMessage());
                                }
                                auth = authThird;
                            }
                            case  TASK_CODE_XCODE ->{
                                auth = authServerServer.findOneAuthServer(authId);
                            }
                            default -> {
                                auth = authServer.findOneAuth(authId);
                            }
                        }
                        taskCode.setAuth(auth);
                    }
                    object = taskCode;
                }
                case TASK_TYPE_TEST -> {
                    // TaskTest taskTest = testService.findOneTest(taskId);
                    TaskTest taskTest = JSONObject.parseObject(jsonString, TaskTest.class);
                    String authId = taskTest.getAuthId();
                    if (taskType.equals(TASK_TEST_TESTON)){
                        Object auth = authServerServer.findOneAuthServer(authId);
                        taskTest.setAuth(auth);
                    }
                    object = taskTest;
                }
                case TASK_TYPE_BUILD -> {
                    // object = buildService.findOneBuild(taskId);
                    object = JSONObject.parseObject(jsonString, TaskBuild.class);
                }
                case TASK_TYPE_DEPLOY -> {
                    TaskDeploy taskDeploy = deployService.findOneDeploy(taskId);
                    String authId = taskDeploy.getAuthId();
                    if (!Objects.isNull(authId)){
                        Object auth;
                        String hostType = taskDeploy.getHostType();
                        if ("hostGroup".equals(hostType)){
                            List<HostGroup> groupByGroup = authHostGroupService.findOneHostGroupByGroup(authId,
                                    taskDeploy.getStrategyNumber(), taskDeploy.getStrategyType());
                            taskDeploy.setHostGroupList(groupByGroup);
                            auth = authHostGroupService.findOneHostGroup(authId);
                        } else {
                            auth = authHostService.findOneAuthHost(authId);
                        }
                        if (taskType.equals(TASK_DEPLOY_K8S) && !StringUtils.isEmpty(taskDeploy.getAuthId()) ) {
                            auth = kubectlService.findOneKubectl(taskDeploy.getAuthId());
                        }
                        taskDeploy.setAuth(auth);
                    }
                    object = taskDeploy;
                }
                case TASK_TYPE_CODESCAN -> {
                    // TaskCodeScan codeScan = codeScanService.findCodeScanByAuth(taskId);
                    TaskCodeScan codeScan = JSONObject.parseObject(jsonString, TaskCodeScan.class);
                    String authId = codeScan.getAuthId();
                    if (!Objects.isNull(authId)){
                        Object auth = authHostService.findOneAuthHost(authId);
                        codeScan.setAuth(auth);
                    }
                    object = codeScanService.findCodeScanByAuth(taskId);
                }
                case TASK_TYPE_UPLOAD -> {
                    // TaskArtifact taskArtifact = artifactService.findOneProduct(taskId);
                    TaskArtifact taskArtifact = JSONObject.parseObject(jsonString, TaskArtifact.class);
                    String authId = taskArtifact.getAuthId();
                    String artifactType = taskArtifact.getArtifactType();
                    if (!Objects.isNull(authId)){
                        if (artifactType.equals(TASK_UPLOAD_NEXUS) ){
                            Object auth = authServerServer.findOneAuthServer(authId);
                            taskArtifact.setAuth(auth);
                        }
                        if (artifactType.equals(TASK_UPLOAD_HADESS)){
                            Object auth = authServerServer.findOneAuthServer(authId);
                            taskArtifact.setAuth(auth);
                        }
                        if (artifactType.equals(TASK_UPLOAD_SSH)){
                            Object auth = authHostService.findOneAuthHost(authId);
                            taskArtifact.setAuth(auth);
                        }
                    }

                    object = taskArtifact;
                }
                case TASK_TYPE_DOWNLOAD -> {
                    // TaskPullArtifact taskArtifact = pullArtifactService.findOnePullArtifact(taskId);
                    TaskPullArtifact taskArtifact = JSONObject.parseObject(jsonString, TaskPullArtifact.class);
                    String authId = taskArtifact.getAuthId();
                    if (!Objects.isNull(authId)){
                        String pullType = taskArtifact.getPullType();
                        if (pullType.equals(TASK_UPLOAD_NEXUS) ){
                            Object auth = authServerServer.findOneAuthServer(authId);
                            taskArtifact.setAuth(auth);
                        }
                        if (pullType.equals(TASK_UPLOAD_HADESS)){
                            Object auth = authServerServer.findOneAuthServer(authId);
                            taskArtifact.setAuth(auth);
                        }
                        if (taskType.equals(TASK_UPLOAD_SSH)){
                            Object auth = authHostService.findOneAuthHost(authId);
                            taskArtifact.setAuth(auth);
                        }
                    }

                    object = taskArtifact;
                }
                case TASK_TYPE_MESSAGE -> {
                    object = messageTypeServer.findMessage(taskId);
                }
                case TASK_TYPE_SCRIPT -> {
                    object = scriptServer.findScript(taskId);
                }
                default -> {
                    throw new ApplicationException("无法更新未知的配置类型。");
                }
            }
            task.setValues(object);
            task.setTask(object);
        }
        tasks.sort(Comparator.comparing(Tasks::getTaskSort));
    }

    @Override
    public List<Tasks> finStageTaskOrTask(String stageId) {
        List<Tasks> tasks = finAllStageTask(stageId);
        if (tasks.isEmpty()){
            return Collections.emptyList();
        }
        for (Tasks task : tasks) {
            String taskId = task.getTaskId();
            String taskType = task.getTaskType();
            Object taskDetails = findTaskDetails(taskId, taskType);
            task.setValues(taskDetails);
            task.setTask(taskDetails);
        }
        addTaskAuth(tasks);
        return tasks;
    }

    @Override
    public List<Tasks> finStageTaskOrTaskNoAuth(String stageId) {
        List<Tasks> tasksList = finAllStageTask(stageId);
        if (tasksList.isEmpty()){
            return Collections.emptyList();
        }
        for (Tasks tasks : tasksList) {
            Object taskDetails = findTaskDetailsNoAuth(tasks.getTaskId(), tasks.getTaskType());
            tasks.setValues(taskDetails);
            tasks.setTask(taskDetails);
        }
        return tasksList;
    }

    @Override
    public List<String> validTasksMustField(List<Tasks> tasksList){

        if (tasksList.isEmpty()){
            return new ArrayList<>();
        }

        List<String> stringList = new ArrayList<>();
        for (Tasks tasks : tasksList) {
            String taskId = tasks.getTaskId();
            Boolean aBoolean = validDifferentTaskMastField(tasks.getTaskType(), tasks.getTask());
            if (!aBoolean){
                stringList.add(taskId);
            }
        }
        return stringList;
    }

    @Override
    public void createTaskTemplate(String pipelineId,String[] template){
        int j = 1;
        for (String i : template) {
            Tasks task = new Tasks();
            task.setTaskSort(j);
            task.setPipelineId(pipelineId);
            task.setTaskType(i);
            createTasksOrTask(task);
            j++;
        }
    }

    /**
     * 创建任务
     * @param tasks 任务模型
     * @return 任务id
     */
    private String createTasks(Tasks tasks){
        TasksEntity tasksEntity = BeanMapper.map(tasks, TasksEntity.class);
        return tasksDao.createConfigure(tasksEntity);
    }

    @Override
    public void deleteTasks(String tasksId){
       tasksDao.deleteConfigure(tasksId);
    }

    @Override
    public void updateTasks(Tasks tasks){
        TasksEntity tasksEntity = BeanMapper.map(tasks, TasksEntity.class);
        tasksDao.updateConfigure(tasksEntity);
    }

    @Override
    public Tasks findOneTasks(String tasksId){
        TasksEntity tasksEntity = tasksDao.findOneConfigure(tasksId);
        return BeanMapper.map(tasksEntity, Tasks.class);
    }

    @Override
    public Tasks findOneTasksOrTask(String tasksId){
        Tasks tasks = findOneTasks(tasksId);
        Object task = findTaskDetails(tasksId, tasks.getTaskType());
        tasks.setValues(task);
        tasks.setTask(task);
        return tasks;
    }

    @Override
    public List<Tasks> findAllTasks(){
        List<TasksEntity> allConfigure = tasksDao.findAllConfigure();
        return BeanMapper.mapList(allConfigure, Tasks.class);
    }

    @Override
    public List<Tasks> findTasksList(List<String> idList){
        List<TasksEntity> allConfigure = tasksDao.findAllConfigureList(idList);
        List<TasksEntity> entityList = allConfigure.stream()
                .filter(tasksEntity -> !Objects.isNull(tasksEntity))
                .toList();
        return BeanMapper.mapList(entityList, Tasks.class);
    }

    /**
     * 分发创建不同类型的任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    private void createDifferentTask(String taskId,String taskType,Object values){
        switch (findTaskType(taskType)) {
            case TASK_TYPE_CODE     -> {
                TaskCode task = new TaskCode();
                task.setTaskId(taskId);
                if (!taskType.equals(TASK_CODE_SVN)){
                    task.setCodeBranch(TASK_CODE_DEFAULT_BRANCH);
                }
                if (taskType.equals(TASK_CODE_SVN)){
                    task.setSvnFile("/");
                }
                task.setAuthType(AUTH_NONE);
                codeService.createCode(task);
            }
            case TASK_TYPE_TEST     -> {
                TaskTest task = new TaskTest();
                task.setTaskId(taskId);
                task.setAddress(DEFAULT_CODE_ADDRESS);
                task.setTestOrder(TEST_DEFAULT_ORDER);
                testService.createTest(task);
            }
            case TASK_TYPE_BUILD    -> {
                TaskBuild task = new TaskBuild();
                task.setTaskId(taskId);
                switch (taskType){
                    case TASK_BUILD_MAVEN -> {
                        task.setBuildOrder(MAVEN_DEFAULT_ORDER);
                        task.setBuildAddress(DEFAULT_CODE_ADDRESS);
                    }
                    case TASK_BUILD_NODEJS -> {
                        task.setBuildOrder(NODE_DEFAULT_ORDER);
                        task.setBuildAddress(DEFAULT_CODE_ADDRESS);
                    }
                    case TASK_BUILD_DOCKER -> {
                        task.setDockerFile(DEFAULT_CODE_ADDRESS);
                        task.setDockerOrder(DOCKER_DEFAULT_ORDER);
                    }
                    case TASK_BUILD_GO -> {
                        task.setBuildAddress(DEFAULT_CODE_ADDRESS);
                        task.setBuildOrder(GO_DEFAULT_ORDER);
                    }
                }
                buildService.createBuild(task);
            }
            case TASK_TYPE_DEPLOY   -> {
                TaskDeploy task = new TaskDeploy();
                task.setTaskId(taskId);
                task.setAuthType(1);
                task.setDeployAddress(DEFAULT_CODE_ADDRESS);
                task.setLocalAddress(DEFAULT_CODE_ADDRESS);
                task.setK8sAddress(DEFAULT_CODE_ADDRESS);
                task.setStrategyType(TASK_DEPLOY_STRATEGY_ONE);
                task.setKubeConfType(TASK_DEPLOY_K8S_TYPE_FILE);
                deployService.createDeploy(task);
            }
            case TASK_TYPE_CODESCAN -> {
                TaskCodeScan task = new TaskCodeScan();
                task.setTaskId(taskId);
                if (taskType.equals(TASK_CODESCAN_SPOTBUGS)){
                    task.setOpenAssert(false);
                    task.setOpenDebug(false);
                    task.setErrGrade(DEFAULT);
                    task.setScanGrade(DEFAULT);
                }
                task.setScanPath(DEFAULT_CODE_ADDRESS);
                codeScanService.createCodeScan(task);
            }
            case TASK_TYPE_UPLOAD -> {
                TaskArtifact task = new TaskArtifact();
                task.setTaskId(taskId);
                task.setArtifactType(taskType);
                artifactService.createProduct(task);
            }
            case TASK_TYPE_DOWNLOAD -> {
                TaskPullArtifact task = new TaskPullArtifact();
                task.setTaskId(taskId);
                task.setPullType(taskType);
                pullArtifactService.createPullArtifact(task);
            }
            case TASK_TYPE_MESSAGE -> {
                String object = JSON.toJSONString(values);
                TaskMessageType task = JSON.parseObject(object, TaskMessageType.class);
                if (task == null){
                    task = new TaskMessageType();
                }
                task.setTaskId(taskId);
                messageTypeServer.createMessage(task);
            }
            case TASK_TYPE_SCRIPT   -> {
                String object = JSON.toJSONString(values);
                TaskScript task = JSON.parseObject(object, TaskScript.class);
                if (Objects.isNull(task)){
                    task = new TaskScript();
                    task.setType(TASK_SCRIPT_BASH);
                }else {
                    if (!StringUtils.isEmpty(task.getType())){
                        task.setScriptOrder("");
                    }
                }
                task.setTaskId(taskId);
                scriptServer.createScript(task);
            }
           default -> throw new ApplicationException("无法更新未知的配置类型:"+taskType);
        }
    }

    /**
     * 分发删除不同任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    private void deleteDifferentTask(String taskId,String taskType){
        switch (findTaskType(taskType)) {
            case TASK_TYPE_CODE      -> codeService.deleteCode(taskId);
            case TASK_TYPE_TEST     -> testService.deleteTest(taskId);
            case TASK_TYPE_BUILD    -> buildService.deleteBuild(taskId);
            case TASK_TYPE_DEPLOY   -> deployService.deleteDeploy(taskId);
            case TASK_TYPE_CODESCAN -> codeScanService.deleteCodeScan(taskId);
            case TASK_TYPE_UPLOAD -> artifactService.deleteProduct(taskId);
            case TASK_TYPE_DOWNLOAD     -> pullArtifactService.deletePullArtifact(taskId);
            case TASK_TYPE_MESSAGE  -> messageTypeServer.deleteAllMessage(taskId);
            case TASK_TYPE_SCRIPT   -> scriptServer.deleteScript(taskId);
            default -> throw new ApplicationException("无法删除未知的配置类型"+taskType);
        }
    }

    /**
     * 分发更新不同任务
     * @param taskId 任务id
     * @param taskType 任务类型
     * @param o 更新内容
     */
    private void updateDifferentTask(String taskId,String taskType,Object o){
        String object = JSONObject.toJSONString(o);
        switch (findTaskType(taskType)) {
            case TASK_TYPE_CODE     -> {
                TaskCode taskCode = JSONObject.parseObject(object, TaskCode.class);
                TaskCode oneCodeConfig = codeService.findOneCode(taskId);
                String id;
                if (Objects.isNull(oneCodeConfig)){
                    TaskCode code = new TaskCode();
                    code.setAuthType(AUTH_NONE);
                    id = codeService.createCode(code);
                }else {
                    id = oneCodeConfig.getTaskId();
                }
                taskCode.setTaskId(id);
                taskCode.setType(taskType);
                codeService.updateCode(taskCode);
            }
            case TASK_TYPE_TEST     -> {
                TaskTest taskTest = JSON.parseObject(object, TaskTest.class);
                TaskTest oneTestConfig = testService.findOneTest(taskId);
                String id;
                if (Objects.isNull(oneTestConfig)){
                    id = testService.createTest(new TaskTest());
                }else {
                    id = oneTestConfig.getTaskId();
                }

                if (!StringUtils.isEmpty(taskTest.getAuthId())){
                    taskTest.setTestEnv(new TestHuboEnv(" "));
                    taskTest.setTestPlan(new TestHuboTestPlan(" "));
                    taskTest.setTestSpace(new TestHuboRpy(" "));
                }

                if(!Objects.isNull(taskTest.getTestPlan())){
                    taskTest.setTestEnv(new TestHuboEnv(" "));
                }

                if(!Objects.isNull(taskTest.getTestSpace())){
                    taskTest.setTestEnv(new TestHuboEnv(" "));
                    taskTest.setTestPlan(new TestHuboTestPlan(" "));
                }

                taskTest.setTaskId(id);
                testService.updateTest(taskTest);
            }
            case TASK_TYPE_BUILD    -> {
                TaskBuild taskBuild = JSON.parseObject(object, TaskBuild.class);
                TaskBuild oneBuildConfig = buildService.findOneBuild(taskId);
                String id;
                if (Objects.isNull(oneBuildConfig)){
                    id = buildService.createBuild(new TaskBuild());
                }else {
                    id = oneBuildConfig.getTaskId();
                }
                taskBuild.setTaskId(id);
                buildService.updateBuild(taskBuild);
            }
            case TASK_TYPE_DEPLOY   -> {
                TaskDeploy taskDeploy = JSON.parseObject(object, TaskDeploy.class);
                TaskDeploy oneDeployConfig = deployService.findOneDeploy(taskId);
                String id;
                if (oneDeployConfig == null){
                    id = deployService.createDeploy(new TaskDeploy());
                }else {
                    id = oneDeployConfig.getTaskId();
                }
                taskDeploy.setTaskId(id);
                deployService.updateDeploy(taskDeploy);
            }
            case TASK_TYPE_CODESCAN -> {
                TaskCodeScan taskCodeScan = JSON.parseObject(object, TaskCodeScan.class);
                TaskCodeScan codeScan = codeScanService.findOneCodeScan(taskId);
                String id;
                if (Objects.isNull(codeScan)){
                    id = codeScanService.createCodeScan(new TaskCodeScan());
                }else {
                    id = codeScan.getTaskId();
                }
                taskCodeScan.setTaskId(id);
                codeScanService.updateCodeScan(taskCodeScan);
            }
            case TASK_TYPE_UPLOAD -> {
                TaskArtifact taskArtifact = JSON.parseObject(object, TaskArtifact.class);
                TaskArtifact oneArtifact = artifactService.findOneProduct(taskId);
                String id;
                if (Objects.isNull(oneArtifact)){
                    id = artifactService.createProduct(new TaskArtifact());
                }else {
                    id = oneArtifact.getTaskId();
                }
                String artifactType = taskArtifact.getArtifactType();
                if(!Objects.isNull(taskArtifact.getArtifactType())){
                    artifactService.deleteProduct(oneArtifact.getTaskId());
                    TaskArtifact artifact = new TaskArtifact();
                    artifact.setArtifactType(artifactType);
                    artifact.setTaskId(oneArtifact.getTaskId());
                    artifactService.createProduct(artifact);
                }else {
                    taskArtifact.setTaskId(id);
                    artifactService.updateProduct(taskArtifact);
                }
            }
            case TASK_TYPE_DOWNLOAD -> {
                TaskPullArtifact taskArtifact = JSON.parseObject(object, TaskPullArtifact.class);
                TaskPullArtifact pullArtifact = pullArtifactService.findOnePullArtifact(taskId);
                String id;
                if (Objects.isNull(pullArtifact)){
                    id = pullArtifactService.createPullArtifact(new TaskPullArtifact());
                }else {
                    id = pullArtifact.getTaskId();
                }
                String pullType = taskArtifact.getPullType();
                if(!Objects.isNull(pullType)){
                    pullArtifactService.deletePullArtifact(pullArtifact.getTaskId());
                    TaskPullArtifact artifact = new TaskPullArtifact();
                    artifact.setPullType(pullType);
                    artifact.setTaskId(pullArtifact.getTaskId());
                    pullArtifactService.createPullArtifact(artifact);
                }else {
                    taskArtifact.setTaskId(id);
                    pullArtifactService.updatePullArtifact(taskArtifact);
                }
            }
            case TASK_TYPE_MESSAGE  -> {
                messageTypeServer.deleteAllMessage(taskId);
                TaskMessageType task = JSON.parseObject(object, TaskMessageType.class);
                task.setTaskId(taskId);
                messageTypeServer.createMessage(task);
            }
            case TASK_TYPE_SCRIPT   -> {
                TaskScript task = JSON.parseObject(object, TaskScript.class);
                task.setTaskId(taskId);
                if (!StringUtils.isEmpty(task.getType())){
                    task.setScriptOrder("");
                }
                scriptServer.updateScript(task);
            }
            default ->  throw new ApplicationException("无法更新未知的配置类型。");
        }
    }

    /**
     * 获取任务详情
     * @param taskId 任务id
     * @param taskType 任务类型
     * @return 任务详情
     */
    private Object findTaskDetails(String taskId,String taskType){
        switch (findTaskType(taskType)) {
            case TASK_TYPE_CODE     -> {
                TaskCode code = codeService.findCodeByAuth(taskId);
                if (!Objects.isNull(code.getToolGit())){
                    Scm git = scmService.findOnePipelineScm(code.getToolGit().getScmId());
                    code.setToolGit(git);
                }
                if (!Objects.isNull(code.getToolSvn())){
                    Scm gitlab = scmService.findOnePipelineScm(code.getToolSvn().getScmId());
                    code.setToolSvn(gitlab);
                }
                return code;
            }
            case TASK_TYPE_TEST     -> {
                TaskTest taskTest = testService.findTestBuAuth(taskId);
                if (!Objects.isNull(taskTest.getToolJdk())){
                    Scm jdk = scmService.findOnePipelineScm(taskTest.getToolJdk().getScmId());
                    taskTest.setToolJdk(jdk);
                }
                if (!Objects.isNull(taskTest.getToolMaven())){
                    Scm maven = scmService.findOnePipelineScm(taskTest.getToolMaven().getScmId());
                    taskTest.setToolMaven(maven);
                }
                return taskTest;
            }
            case TASK_TYPE_BUILD    -> {
                TaskBuild taskBuild = buildService.findBuildByAuth(taskId);
                if (!Objects.isNull(taskBuild.getToolMaven())){
                    Scm maven = scmService.findOnePipelineScm(taskBuild.getToolMaven().getScmId());
                    taskBuild.setToolMaven(maven);
                }
                if (!Objects.isNull(taskBuild.getToolJdk())){
                    Scm jdk = scmService.findOnePipelineScm(taskBuild.getToolJdk().getScmId());
                    taskBuild.setToolJdk(jdk);
                }
                if (!Objects.isNull(taskBuild.getToolNodejs())){
                    Scm docker = scmService.findOnePipelineScm(taskBuild.getToolNodejs().getScmId());
                    taskBuild.setToolNodejs(docker);
                }
                if (!Objects.isNull(taskBuild.getToolGo())){
                    Scm docker = scmService.findOnePipelineScm(taskBuild.getToolGo().getScmId());
                    taskBuild.setToolGo(docker);
                }
                return taskBuild;
            }
            case TASK_TYPE_DEPLOY   -> {
                TaskDeploy taskDeploy = deployService.findDeployByAuth(taskId);
                joinTemplate.joinQuery(taskDeploy);
                return taskDeploy;
            }
            case TASK_TYPE_CODESCAN -> {
                TaskCodeScan codeScan = codeScanService.findCodeScanByAuth(taskId);
                // joinTemplate.joinQuery(codeScan);
                if (!Objects.isNull(codeScan.getToolMaven())){
                    Scm maven = scmService.findOnePipelineScm(codeScan.getToolMaven().getScmId());
                    codeScan.setToolMaven(maven);
                }
                if (!Objects.isNull(codeScan.getToolJdk())){
                    Scm jdk = scmService.findOnePipelineScm(codeScan.getToolJdk().getScmId());
                    codeScan.setToolJdk(jdk);
                }
                return codeScan;
            }
            case TASK_TYPE_UPLOAD -> {
                return artifactService.findOneArtifactByAuth(taskId);
            }
            case TASK_TYPE_DOWNLOAD -> {
                return pullArtifactService.findPullArtifactByAuth(taskId);
            }
            case TASK_TYPE_MESSAGE  -> {
                TaskMessageType messageType = messageTypeServer.findMessage(taskId);
                joinTemplate.joinQuery(messageType);
                return messageType;
            }
            case TASK_TYPE_SCRIPT   -> {
                TaskScript taskScript = scriptServer.findOneScript(taskId);
                joinTemplate.joinQuery(taskScript);
                return taskScript;
            }
           default -> throw new ApplicationException("无法更新未知的配置类型。");
        }
    }

    /**
     * 查询任务详情(不包含认证信息)
     * @param taskId 任务Id
     * @param taskType 任务类型
     * @return 任务详情
     */
    private Object findTaskDetailsNoAuth(String taskId,String taskType){
        switch (findTaskType(taskType)) {
            case TASK_TYPE_CODE     -> {
                return codeService.findOneCode(taskId);
            }
            case TASK_TYPE_TEST     -> {
                return testService.findOneTest(taskId);
            }
            case TASK_TYPE_BUILD    -> {
                return buildService.findOneBuild(taskId);
            }
            case TASK_TYPE_DEPLOY   -> {
                return deployService.findOneDeploy(taskId);
            }
            case TASK_TYPE_CODESCAN -> {
                return codeScanService.findOneCodeScan(taskId);
            }
            case TASK_TYPE_UPLOAD -> {
                return artifactService.findOneProduct(taskId);
            }
            case TASK_TYPE_DOWNLOAD -> {
                return pullArtifactService.findOnePullArtifact(taskId);
            }
            case TASK_TYPE_MESSAGE  -> {
                return messageTypeServer.findMessage(taskId);
            }
            case TASK_TYPE_SCRIPT   -> {
                return scriptServer.findOneScript(taskId);
            }
           default -> throw new ApplicationException("无法更新未知的配置类型。");
        }
    }

    /**
     * 效验不同任务配置必填字段
     * @param taskType 任务类型
     * @param object 任务信息
     */
    private Boolean validDifferentTaskMastField(String taskType,Object object){
        String jsonString = JSONObject.toJSONString(object);
        switch (findTaskType(taskType)) {
           case TASK_TYPE_CODE     ->  {
               TaskCode code =  JSONObject.parseObject(jsonString,TaskCode.class);
               switch (taskType) {
                   case TASK_CODE_GITEE, TASK_CODE_GITLAB, TASK_CODE_GITHUB, TASK_CODE_XCODE,TASK_CODE_PRI_GITLAB -> {
                      return PipelineUtil.validNoNullFiled(code.getHouseId(),code.getAuthId(),code.getToolGit());
                   }
                   case TASK_CODE_GIT -> {
                       String authType = code.getAuthType();
                       if (StringUtils.isEmpty(authType) || authType.equals(AUTH_NONE)){
                           return PipelineUtil.validNoNullFiled(code.getCodeAddress(),code.getCodeBranch());
                       }

                       switch (authType) {
                           case AUTH_USER_PASS -> {
                               return PipelineUtil.validNoNullFiled(code.getCodeAddress(), code.getToolGit(), code.getUsername(), code.getPassword());
                           }
                           case AUTH_PRI_KEY -> {
                               return PipelineUtil.validNoNullFiled(code.getCodeAddress(), code.getToolGit(), code.getPriKey());
                           }
                           default -> {
                               return PipelineUtil.validNoNullFiled(code.getCodeAddress(),code.getToolGit());
                           }
                       }
                   }
                   case TASK_CODE_SVN -> {
                       switch (code.getAuthType()) {
                           case AUTH_USER_PASS -> {
                               return PipelineUtil.validNoNullFiled(code.getToolSvn(),code.getCodeAddress(), code.getUsername(), code.getPassword());
                           }
                           case AUTH_PRI_KEY -> {
                               return PipelineUtil.validNoNullFiled(code.getToolSvn(),code.getCodeAddress(), code.getPriKey());
                           }
                           default -> {
                               return PipelineUtil.validNoNullFiled(code.getToolSvn(),code.getCodeAddress());
                           }
                       }

                   }
                   default -> {
                       return PipelineUtil.validNoNullFiled(code.getCodeAddress(),code.getToolGit());
                   }
               }
           }
           case TASK_TYPE_TEST     ->  {
               TaskTest code =  JSONObject.parseObject(jsonString,TaskTest.class);
               if (taskType.equals(TASK_TEST_TESTON)){
                   Boolean b1 = PipelineUtil.validNoNullFiled(code.getTestSpace(), code.getTestPlan());
                   if (!b1){
                       return false;
                   }
                   boolean b  = Objects.isNull(code.getTestEnv());
                   return !b ;
               }else {
                   return PipelineUtil.validNoNullFiled(code.getToolJdk(), code.getToolMaven());
               }
           }
           case TASK_TYPE_BUILD    -> {
               TaskBuild code =  JSONObject.parseObject(jsonString,TaskBuild.class);
               switch (taskType) {
                   case TASK_BUILD_MAVEN -> {
                       return PipelineUtil.validNoNullFiled(code.getToolMaven(),code.getToolJdk());
                   }
                   case TASK_BUILD_NODEJS -> {
                       return PipelineUtil.validNoNullFiled(code.getToolNodejs());
                   }
                   case TASK_BUILD_DOCKER -> {
                       return PipelineUtil.validNoNullFiled(code.getDockerFile(),code.getDockerOrder());
                   }
                   case TASK_BUILD_GO -> {
                       return PipelineUtil.validNoNullFiled(code.getToolGo(),code.getBuildOrder());
                   }
                   default -> {
                       return true;
                   }
               }
           }
           case TASK_TYPE_DEPLOY   -> {
               TaskDeploy code =  JSONObject.parseObject(jsonString,TaskDeploy.class);

               switch (taskType) {
                   case TASK_DEPLOY_LINUX  -> {
                       if (code.getAuthType() == 1){
                           return PipelineUtil.validNoNullFiled(code.getAuthId(),code.getDeployAddress(),code.getLocalAddress());
                       }
                       return true;
                   }
                   case TASK_DEPLOY_DOCKER -> {
                       return PipelineUtil.validNoNullFiled(code.getAuthId(),code.getDockerImage(),code.getDeployAddress());
                   }
                   case TASK_DEPLOY_K8S -> {
                       return PipelineUtil.validNoNullFiled(code.getAuthId(),code.getK8sNamespace());
                   }
                   default -> {
                       return true;
                   }
               }
           }
           case TASK_TYPE_CODESCAN -> {
               TaskCodeScan code =  JSONObject.parseObject(jsonString,TaskCodeScan.class);
               if (taskType.equals(TASK_CODESCAN_SONAR)) {
                   return PipelineUtil.validNoNullFiled(code.getAuthId(),code.getProjectName());
               } else {
                   return PipelineUtil.validNoNullFiled(code.getToolJdk(),code.getToolMaven(),code.getScanPath());
               }
           }
           case TASK_TYPE_UPLOAD -> {
               TaskArtifact code =  JSONObject.parseObject(jsonString,TaskArtifact.class);
               String artifactType = code.getArtifactType();
               return switch (artifactType) {
                   case TASK_UPLOAD_NEXUS -> PipelineUtil.validNoNullFiled(code.getDockerImage(), code.getAuthId());
                   case TASK_UPLOAD_HADESS ->
                           PipelineUtil.validNoNullFiled(code.getFileAddress(), code.getAuthId(), code.getVersion());
                   case TASK_UPLOAD_SSH ->
                           PipelineUtil.validNoNullFiled(code.getFileAddress(), code.getAuthId(), code.getPutAddress());
                   default -> true;
               };
           }
           case TASK_TYPE_DOWNLOAD -> {
               TaskPullArtifact code =  JSONObject.parseObject(jsonString,TaskPullArtifact.class);
               switch (taskType) {
                   case TASK_DOWNLOAD_HADESS -> {
                       return PipelineUtil.validNoNullFiled(code.getArtifactName(), code.getAuthId(),
                               code.getVersion(),code.getLocalAddress());
                   }
                   case TASK_DOWNLOAD_SSH -> {
                       return PipelineUtil.validNoNullFiled(code.getAuthId(),code.getRemoteAddress(),code.getLocalAddress());
                   }
                   default -> {
                       return true;
                   }
               }
           }
           default -> {
               return true;
           }

        }
    }

    @Override
    public String findTaskType(String taskType){
        switch (taskType){
            case TASK_CODE_GIT , TASK_CODE_GITEE , TASK_CODE_GITHUB ,
                    TASK_CODE_GITLAB, TASK_CODE_XCODE, TASK_CODE_SVN,TASK_CODE_PRI_GITLAB ->{
                return TASK_TYPE_CODE;
            }
            case TASK_BUILD_MAVEN, TASK_BUILD_NODEJS, TASK_BUILD_DOCKER,TASK_BUILD_GO ->{
                return TASK_TYPE_BUILD;
            }
            case TASK_TEST_MAVENTEST, TASK_TEST_TESTON  ->{
                return TASK_TYPE_TEST;
            }
            case TASK_DEPLOY_LINUX , TASK_DEPLOY_DOCKER ,TASK_DEPLOY_K8S->{
                return TASK_TYPE_DEPLOY;
            }
            case TASK_UPLOAD_SSH, TASK_UPLOAD_DOCKER,TASK_UPLOAD_HADESS ->{
                return TASK_TYPE_UPLOAD;
            }
            case  TASK_CODESCAN_SONAR , TASK_CODESCAN_SPOTBUGS ->{
                return TASK_TYPE_CODESCAN;
            }
            case  TASK_MESSAGE_MSG ->{
                return TASK_TYPE_MESSAGE;
            }
            case TASK_TYPE_SCRIPT, TASK_SCRIPT_BAT , TASK_SCRIPT_SHELL ->{
                return TASK_TYPE_SCRIPT;
            }
            case TASK_DOWNLOAD_HADESS , TASK_DOWNLOAD_DOCKER, TASK_DOWNLOAD_SSH ->{
                return TASK_TYPE_DOWNLOAD;
            }
            default ->  throw new ApplicationException("无法更新未知的配置类型:"+taskType);
        }
    }

    @Override
    public String initDifferentTaskName(String taskType){
        switch (taskType) {
            case TASK_CODE_GIT -> {
                return "通用Git";
            }
            case TASK_CODE_GITEE -> {
                return "Gitee";
            }
            case TASK_CODE_GITHUB -> {
                return "GitHub";
            }
            case TASK_CODE_XCODE -> {
                return "GitPuk";
            }
            case TASK_CODE_GITLAB -> {
                return "GitLab";
            }
            case TASK_CODE_PRI_GITLAB -> {
                return "自建GitLab";
            }
            case TASK_CODE_SVN -> {
                return "SVN";
            }
            case TASK_TEST_MAVENTEST -> {
                return "Maven单元测试";
            }
            case TASK_TEST_TESTON -> {
                return "TestHubo";
            }
            case TASK_BUILD_MAVEN -> {
                return "Maven构建";
            }
            case TASK_BUILD_DOCKER -> {
                return "Docker构建";
            }
            case TASK_BUILD_NODEJS -> {
                return "Node.Js构建";
            }
            case TASK_BUILD_GO -> {
                return "Go构建";
            }
            case TASK_DEPLOY_LINUX -> {
                return "主机部署";
            }
            case TASK_DEPLOY_DOCKER -> {
                return "Docker部署";
            }
            case TASK_DEPLOY_K8S -> {
                return "Kubernetes部署";
            }
            case TASK_CODESCAN_SONAR -> {
                return "SonarQube";
            }
            case TASK_CODESCAN_SPOTBUGS ->{
                return "Java代码扫描";
            }
            case TASK_UPLOAD_HADESS -> {
                return "Hadess上传";
            }
            case TASK_UPLOAD_SSH -> {
                return "Ssh上传";
            }
            case TASK_UPLOAD_DOCKER -> {
                return "Docker上传";
            }
            case TASK_DOWNLOAD_HADESS ->{
                return "Hadess下载";
            }
            case TASK_DOWNLOAD_SSH ->{
                return "SSH下载";
            }
            case TASK_DOWNLOAD_DOCKER ->{
                return "Docker拉取";
            }
            case TASK_MESSAGE_MSG -> {
                return "消息通知";
            }
            case TASK_SCRIPT_BAT -> {
                return "执行Bat脚本";
            }
            case TASK_SCRIPT_SHELL -> {
                return "执行Shell脚本";
            }
            case TASK_TYPE_SCRIPT -> {
                return "执行脚本";
            }
            default -> {
                return taskType;
            }
        }
    }


}

















