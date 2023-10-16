package io.tiklab.matflow.task.task.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.service.AuthHostService;
import io.tiklab.matflow.setting.service.AuthService;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.postprocess.dao.PostprocessDao;
import io.tiklab.matflow.support.postprocess.entity.PostprocessEntity;
import io.tiklab.matflow.support.postprocess.model.PostprocessQuery;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.artifact.model.TaskArtifact;
import io.tiklab.matflow.task.artifact.service.TaskArtifactService;
import io.tiklab.matflow.task.build.model.TaskBuild;
import io.tiklab.matflow.task.build.service.TaskBuildService;
import io.tiklab.matflow.task.code.model.TaskCode;
import io.tiklab.matflow.task.code.model.XcodeRepository;
import io.tiklab.matflow.task.code.service.TaskCodeService;
import io.tiklab.matflow.task.codescan.model.TaskCodeScan;
import io.tiklab.matflow.task.codescan.service.TaskCodeScanService;
import io.tiklab.matflow.task.deploy.model.TaskDeploy;
import io.tiklab.matflow.task.deploy.service.TaskDeployService;
import io.tiklab.matflow.task.message.model.TaskMessageType;
import io.tiklab.matflow.task.message.service.TaskMessageTypeService;
import io.tiklab.matflow.task.script.model.TaskScript;
import io.tiklab.matflow.task.script.service.TaskScriptService;
import io.tiklab.matflow.task.task.dao.TasksDao;
import io.tiklab.matflow.task.task.entity.TasksEntity;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.model.TasksQuery;
import io.tiklab.matflow.task.test.model.TaskTest;
import io.tiklab.matflow.task.test.service.TaskTestService;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static io.tiklab.matflow.support.util.PipelineFinal.*;

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

    private final ExecutorService executorService = Executors.newCachedThreadPool();

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
        if (pipelineId != null) {
            sort = initSort(tasks.getPipelineId(), taskSort, taskType,1);
        }

        //阶段任务
        String stageId = tasks.getStageId();
        if (stageId != null){
            sort = initSort(stageId, taskSort, taskType,2);
        }

        //后置任务
        String postprocessId = tasks.getPostprocessId();
        if (postprocessId != null){
            sort = initSort(postprocessId, taskSort, taskType,3);
        }

        tasks.setTaskSort(sort);
        String taskName = initDifferentTaskName(taskType);
        tasks.setTaskName(taskName);
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
                throw new ApplicationException(50001,"代码源已存在");
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
        if (!tasksList.isEmpty()){
            return tasksList.get(0);
        }
        return null;
    }

    @Override
    public Tasks findOnePostTaskOrTask(String postId) {
        Tasks postTask = findOnePostTask(postId);
        String  taskType = postTask.getTaskType();
        String taskId = postTask.getTaskId();
        Object task = findOneDifferentTask(taskId, taskType);
        postTask.setValues(task);
        postTask.setTaskType(taskType);
        return postTask;
    }

    @Override
    public List<Tasks> finAllPipelineTaskOrTask(String pipelineId) {
        List<Tasks> tasks = finAllPipelineTask(pipelineId);
        if (tasks.isEmpty()){
            return Collections.emptyList();
        }
        return bindAllTaskOrTask(findAllTaskOrTask(tasks));
    }

    private List<Tasks> findAllTaskOrTask(List<Tasks> tasks){

        Map<String , Future<Object>> futureMap = new HashMap<>();

        for (Tasks task : tasks) {
            String taskId = task.getTaskId();
            String taskType = task.getTaskType();
            Future<Object> future = executorService.submit(() -> {
                Object object;
                try {
                    object = findOneDifferentTask(taskId, taskType);
                }catch (Exception e){
                    logger.error("获取配置信息失败:{}  initTaskType:{}",e.getMessage(),taskType);
                    return  null;
                }
                return object;
            });
            task.setTaskType(taskType);
            futureMap.put(taskId,future);
        }
        List<Tasks> list = new ArrayList<>();

        for (Tasks task : tasks) {
            String taskId = task.getTaskId();
            Future<Object> objectFuture = futureMap.get(taskId);
            Object object;
            try {
                object = objectFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            task.setTask(object);
            list.add(task);
        }
        return list;
    }

    private List<Tasks> bindAllTaskOrTask(List<Tasks> tasks){
        List<Tasks> list = new ArrayList<>();
        for (Tasks task : tasks) {
            Object object = task.getTask();
            if (!Objects.isNull(object)){
                list.add(task);
                continue;
            }
            String taskType = task.getTaskType();
            String taskId = task.getTaskId();
            switch (findTaskType(taskType)) {
                case TASK_TYPE_CODE -> {
                    TaskCode taskCode = codeService.findOneCode(taskId);
                    String authId = taskCode.getAuthId();
                    if (!Objects.isNull(authId)){
                        Object auth ;
                        switch (taskType) {
                            case TASK_CODE_GITEE,TASK_CODE_GITHUB ,TASK_CODE_XCODE ->{
                                auth = authServerServer.findOneAuthServer(authId);
                            }
                            default -> {
                                auth = authServer.findOneAuth(authId);
                            }
                        }
                        taskCode.setAuth(auth);
                    }
                    object =  taskCode;
                }
                case TASK_TYPE_TEST -> {
                    TaskTest taskTest = testService.findOneTest(taskId);
                    String authId = taskTest.getAuthId();
                    if (taskType.equals(TASK_TEST_TESTON)){
                        Object auth = authServerServer.findOneAuthServer(authId);
                        taskTest.setAuth(auth);
                    }
                    object = taskTest;
                }
                case TASK_TYPE_BUILD -> {
                    object = buildService.findOneBuild(taskId);
                }
                case TASK_TYPE_DEPLOY -> {
                    TaskDeploy taskDeploy = deployService.findOneDeployConfig(taskId);
                    String authId = taskDeploy.getAuthId();
                    if (!Objects.isNull(authId)){
                        Object auth = authHostService.findOneAuthHost(authId);
                        taskDeploy.setAuth(auth);
                    }
                    object = taskDeploy;
                }
                case TASK_TYPE_CODESCAN -> {
                    TaskCodeScan codeScanConfig = codeScanService.findOneCodeScanConfig(taskId);
                    String authId = codeScanConfig.getAuthId();
                    if (!Objects.isNull(authId)){
                        Object auth = authHostService.findOneAuthHost(authId);
                        codeScanConfig.setAuth(auth);
                    }
                    object = codeScanConfig;
                }
                case TASK_TYPE_ARTIFACT -> {
                    TaskArtifact taskArtifact = productServer.findOneProduct(taskId);
                    String authId = taskArtifact.getAuthId();
                    if (!Objects.isNull(authId)){
                        if (taskType.equals(TASK_ARTIFACT_XPACK)
                                || taskType.equals(TASK_ARTIFACT_NEXUS) ){
                            Object auth = authServerServer.findOneAuthServer(authId);
                            taskArtifact.setAuth(auth);
                        }
                        if (taskType.equals(TASK_ARTIFACT_SSH)){
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
            list.add(task);
        }
        list.sort(Comparator.comparing(Tasks::getTaskSort));
        return list;
    }

    @Override
    public List<Tasks> finAllStageTaskOrTask(String stageId) {
        List<Tasks> tasks = finAllStageTask(stageId);
        if (tasks.isEmpty()){
            return Collections.emptyList();
        }
        return bindAllTaskOrTask(findAllTaskOrTask(tasks));
    }

    @Override
    public Object findOneTasksTask(String taskId){
        Tasks tasks = findOneTasks(taskId);
        String taskType = tasks.getTaskType();
        return findOneDifferentTask(taskId, taskType);
    }

    @Override
    public List<String> validTasksMustField(String id , int type){
        List<Tasks> list ;
        if (type == 1){
            list = finAllPipelineTaskOrTask(id);
        }else {
            list = finAllStageTaskOrTask(id);
        }
        if (list == null || list.isEmpty()){
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (Tasks tasks : list) {
            String taskId = tasks.getTaskId();
            String taskType = tasks.getTaskType();
            Boolean aBoolean = validDifferentTaskMastField(taskType, tasks.getTask());
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

    /**
     * 删除任务
     * @param tasksId 任务id
     */
    private void deleteTasks(String tasksId){
       tasksDao.deleteConfigure(tasksId);
    }

    /**
     * 更新任务
     * @param tasks 任务模型
     */
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
        Object task = findOneDifferentTask(tasksId, tasks.getTaskType());
        tasks.setValues(task);
        tasks.setTask(task);
        return tasks;
    }
    
    /**
     * 查询所有任务
     * @return 任务模型列表
     */
    @Override
    public List<Tasks> findAllTasks(){
        List<TasksEntity> allConfigure = tasksDao.findAllConfigure();
        return BeanMapper.mapList(allConfigure, Tasks.class);
    }

    public void clonePostTasks(String id ,String cloneId){

        Tasks task = findOnePostTask(id);

        // 克隆任务
        TasksEntity tasksEntity = BeanMapper.map(task, TasksEntity.class);
        tasksEntity.setPostprocessId(cloneId);
        String taskCloneId = tasksDao.createConfigure(tasksEntity);

        // 克隆任务详情
        cloneDifferentTask(task.getTaskId(),taskCloneId,task.getTaskType());
    }


    @Override
    public void cloneTasks(String id,String cloneId,String type){
        switch (type){
            case "pipelineId" ->{
                List<Tasks> tasks = finAllPipelineTask(id);
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
            case "stageId" ->{
                List<Tasks> tasks = finAllStageTask(id);
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
            default -> {
                throw new ApplicationException("无法克隆未知的任务信息！type:"+type);
            }
        }

    }

    /**
     * 分发克隆不同类型的任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    private void cloneDifferentTask(String taskId,String cloneTaskId,String taskType){
        switch (findTaskType(taskType)) {
            case TASK_TYPE_CODE     -> {
                TaskCode task = codeService.findOneCode(taskId);
                task.setTaskId(cloneTaskId);
                codeService.createCode(task);
            }
            case TASK_TYPE_TEST     -> {
                TaskTest task = testService.findOneTest(taskId);
                task.setTaskId(cloneTaskId);
                testService.createTest(task);
            }
            case TASK_TYPE_BUILD    -> {
                TaskBuild task = buildService.findOneBuild(taskId);
                task.setTaskId(cloneTaskId);
                buildService.createBuild(task);
            }
            case TASK_TYPE_DEPLOY   -> {
                TaskDeploy task = deployService.findOneDeploy(taskId);
                task.setTaskId(cloneTaskId);
                deployService.createDeploy(task);
            }
            case TASK_TYPE_CODESCAN -> {
                TaskCodeScan task = codeScanService.findOneCodeScan(taskId);
                task.setTaskId(cloneTaskId);
                codeScanService.createCodeScan(task);
            }
            case TASK_TYPE_ARTIFACT -> {
                TaskArtifact task = productServer.findOneProduct(taskId);
                task.setTaskId(cloneTaskId);
                productServer.createProduct(task);
            }
            case TASK_TYPE_MESSAGE -> {
                TaskMessageType task = messageTypeServer.findMessage(taskId);
                task.setTaskId(cloneTaskId);
                messageTypeServer.createMessage(task);
            }
            case TASK_TYPE_SCRIPT   -> {
                TaskScript task = scriptServer.findScript(taskId);
                task.setTaskId(cloneTaskId);
                scriptServer.createScript(task);
            }
            default -> throw new ApplicationException("无法更新未知的配置类型:"+taskType);
        }
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
                codeService.createCode(task);
            }
            case TASK_TYPE_TEST     -> {
                TaskTest task = new TaskTest();
                task.setTaskId(taskId);
                testService.createTest(task);
            }
            case TASK_TYPE_BUILD    -> {
                TaskBuild task = new TaskBuild();
                task.setTaskId(taskId);
                buildService.createBuild(task);
            }
            case TASK_TYPE_DEPLOY   -> {
                TaskDeploy task = new TaskDeploy();
                task.setTaskId(taskId);
                task.setAuthType(1);
                deployService.createDeploy(task);
            }
            case TASK_TYPE_CODESCAN -> {
                TaskCodeScan task = new TaskCodeScan();
                task.setTaskId(taskId);
                codeScanService.createCodeScan(task);
            }
            case TASK_TYPE_ARTIFACT -> {
                TaskArtifact task = new TaskArtifact();
                task.setTaskId(taskId);
                task.setArtifactType(PipelineFinal.TASK_ARTIFACT_XPACK);
                productServer.createProduct(task);
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
                if (task == null){
                    task = new TaskScript();
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
            case TASK_TYPE_CODE      -> codeService.deleteCodeConfig(taskId);
            case TASK_TYPE_TEST     -> testService.deleteTestConfig(taskId);
            case TASK_TYPE_BUILD    -> buildService.deleteBuildConfig(taskId);
            case TASK_TYPE_DEPLOY   -> deployService.deleteDeployConfig(taskId);
            case TASK_TYPE_CODESCAN -> codeScanService.deleteCodeScanConfig(taskId);
            case TASK_TYPE_ARTIFACT -> productServer.deleteProductConfig(taskId);
            case TASK_TYPE_MESSAGE  -> messageTypeServer.deleteAllMessage(taskId);
            case TASK_TYPE_SCRIPT   -> scriptServer.deleteScript(taskId);
           default -> throw new ApplicationException("无法更新未知的配置类型:"+taskType);
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
                if (oneCodeConfig == null){
                    id = codeService.createCode(new TaskCode());
                }else {
                    id = oneCodeConfig.getTaskId();
                }
                taskCode.setTaskId(id);
                taskCode.setType(taskType);
                codeService.updateCode(taskCode);
            }
            case TASK_TYPE_TEST     -> {
                TaskTest taskTest = JSON.parseObject(object, TaskTest.class);
                TaskTest oneTestConfig = testService.findOneTestConfig(taskId);
                String id;
                if (oneTestConfig == null){
                    id = testService.createTest(new TaskTest());
                }else {
                    id = oneTestConfig.getTaskId();
                }
                taskTest.setTaskId(id);
                testService.updateTest(taskTest);
            }
            case TASK_TYPE_BUILD    -> {
                TaskBuild taskBuild = JSON.parseObject(object, TaskBuild.class);
                TaskBuild oneBuildConfig = buildService.findOneBuildConfig(taskId);
                String id;
                if (oneBuildConfig == null){
                    id = buildService.createBuild(new TaskBuild());
                }else {
                    id = oneBuildConfig.getTaskId();
                }
                taskBuild.setTaskId(id);
                buildService.updateBuild(taskBuild);
            }
            case TASK_TYPE_DEPLOY   -> {
                TaskDeploy taskDeploy = JSON.parseObject(object, TaskDeploy.class);
                TaskDeploy oneDeployConfig = deployService.findOneDeployConfig(taskId);
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
                TaskCodeScan oneCodeScanConfig = codeScanService.findOneCodeScanConfig(taskId);
                String id;
                if (oneCodeScanConfig == null){
                    id = codeScanService.createCodeScan(new TaskCodeScan());
                }else {
                    id = oneCodeScanConfig.getTaskId();
                }
                taskCodeScan.setTaskId(id);
                codeScanService.updateCodeScan(taskCodeScan);
            }
            case TASK_TYPE_ARTIFACT -> {
                TaskArtifact taskArtifact = JSON.parseObject(object, TaskArtifact.class);
                TaskArtifact oneArtifact = productServer.findOneArtifact(taskId,"");
                String id;
                if (Objects.isNull(oneArtifact)){
                    id = productServer.createProduct(new TaskArtifact());
                }else {
                    id = oneArtifact.getTaskId();
                }
                String artifactType = taskArtifact.getArtifactType();
                if(!Objects.isNull(taskArtifact.getArtifactType())){
                    productServer.deleteProduct(oneArtifact.getTaskId());
                    TaskArtifact artifact = new TaskArtifact();
                    artifact.setArtifactType(artifactType);
                    artifact.setTaskId(oneArtifact.getTaskId());
                    productServer.createProduct(artifact);
                }else {
                    taskArtifact.setTaskId(id);
                    productServer.updateProduct(taskArtifact);
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
    private Object findOneDifferentTask(String taskId,String taskType){
        switch (findTaskType(taskType)) {
            case TASK_TYPE_CODE     -> {
                return codeService.findOneCodeConfig(taskId,taskType);
            }
            case TASK_TYPE_TEST     -> {
                return testService.findOneTestConfig(taskId);
            }
            case TASK_TYPE_BUILD    -> {
                return buildService.findOneBuildConfig(taskId);
            }
            case TASK_TYPE_DEPLOY   -> {
                return deployService.findOneDeployConfig(taskId);
            }
            case TASK_TYPE_CODESCAN -> {
                return codeScanService.findOneCodeScanConfig(taskId);
            }
            case TASK_TYPE_ARTIFACT -> {
                return productServer.findOneArtifact(taskId,taskType);
            }
            case TASK_TYPE_MESSAGE  -> {
                return messageTypeServer.findMessage(taskId);
            }
            case TASK_TYPE_SCRIPT   -> {
                return scriptServer.findScript(taskId);
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
        switch (findTaskType(taskType)) {
           case TASK_TYPE_CODE     ->  { return codeValid(taskType, object); }
           case TASK_TYPE_TEST     ->  { return testValid(taskType, object); }
           case TASK_TYPE_BUILD    -> { return buildValid(taskType, object); }
           case TASK_TYPE_DEPLOY   -> { return deployValid(taskType, object); }
           case TASK_TYPE_CODESCAN -> { return codeScanValid(taskType, object); }
           case TASK_TYPE_ARTIFACT -> { return productValid(taskType, object); }
           default -> {return true;}
        }
    }

    public String findTaskType(String taskType){
        switch (taskType){
            case TASK_CODE_GIT ,TASK_CODE_GITEE ,TASK_CODE_GITHUB ,
                    TASK_CODE_GITLAB,TASK_CODE_XCODE,TASK_CODE_SVN ->{
                return TASK_TYPE_CODE;
            }
            case TASK_BUILD_MAVEN,TASK_BUILD_NODEJS,TASK_BUILD_DOCKER ->{
                return TASK_TYPE_BUILD;
            }
            case TASK_TEST_MAVENTEST,TASK_TEST_TESTON  ->{
                return TASK_TYPE_TEST;
            }
            case TASK_DEPLOY_LINUX ,TASK_DEPLOY_DOCKER ->{
                return TASK_TYPE_DEPLOY;
            }
            case TASK_ARTIFACT_MAVEN,TASK_ARTIFACT_DOCKER,TASK_ARTIFACT_NODEJS ->{
                return TASK_TYPE_ARTIFACT;
            }
            case  TASK_CODESCAN_SONAR ->{
                return TASK_TYPE_CODESCAN;
            }
            case  TASK_MESSAGE_MSG ->{
                return TASK_TYPE_MESSAGE;
            }
            case TASK_TYPE_SCRIPT,TASK_SCRIPT_BAT ,TASK_SCRIPT_SHELL ->{
                return TASK_TYPE_SCRIPT;
            }
            default ->  throw new ApplicationException("无法更新未知的配置类型:"+taskType);
        }
    }

    private Boolean codeValid(String taskType,Object object){
        TaskCode code = (TaskCode) object;
        switch (taskType){
            case TASK_CODE_GIT ,TASK_CODE_GITHUB,TASK_CODE_GITEE ,TASK_CODE_SVN -> {
                String codeAddress = code.getCodeAddress();
                if (Objects.isNull(codeAddress)){
                    return false;
                }
            }
            case TASK_CODE_XCODE -> {
                XcodeRepository repository = code.getRepository();
                if (Objects.isNull(repository)){
                    return false;
                }
                if (Objects.isNull(repository.getName())){
                    return false;
                }

            }
        }
        return true;
    }

    private Boolean codeScanValid(String taskType,Object object){
        TaskCodeScan code = (TaskCodeScan)object;
        if (taskType.equals(TASK_CODESCAN_SONAR)) {
            String projectName = code.getProjectName();
            return !Objects.isNull(projectName);
        } else {
            return true;
        }
    }

    private Boolean testValid(String taskType,Object object){
        TaskTest taskTest = (TaskTest) object;

        if (taskType.equals(TASK_TEST_TESTON)){
            if (Objects.isNull(taskTest.getTestSpace())|| Objects.isNull(taskTest.getTestSpace().getName())){
                return false;
            }
            if (Objects.isNull(taskTest.getTestPlan())|| Objects.isNull(taskTest.getTestPlan().getName())){
                return false;
            }
           boolean b  = Objects.isNull(taskTest.getApiEnv())
                   && Objects.isNull(taskTest.getAppEnv())
                   && Objects.isNull(taskTest.getWebEnv());
           return !b ;
        }
        return true;
    }

    private Boolean buildValid(String taskType,Object object){
        TaskBuild build = (TaskBuild) object;
        if (taskType.equals(TASK_BUILD_DOCKER)){
            if (Objects.isNull(build.getDockerName())){
                return false;
            }
        }
        return true;
    }

    private Boolean deployValid(String taskType,Object object){
        TaskDeploy deploy =(TaskDeploy) object;;

        if ( taskType.equals(TASK_DEPLOY_LINUX)){
            if (deploy.getAuthType() == 1){
                return PipelineUtil.isNoNull(deploy.getDeployAddress());
            }
        }
        return true;
    }

    private Boolean productValid(String taskType,Object object){
        TaskArtifact product = (TaskArtifact) object;

        if (taskType.equals(TASK_ARTIFACT_NEXUS) || taskType.equals(TASK_ARTIFACT_XPACK)){
            if (!PipelineUtil.isNoNull(product.getArtifactId())){
                return false;
            }
            if (!PipelineUtil.isNoNull(product.getVersion())){
                return false;
            }
            if (!PipelineUtil.isNoNull(product.getGroupId())){
                return false;
            }
        }
        if (taskType.equals(TASK_ARTIFACT_XPACK)){
            if (!PipelineUtil.isNoNull(product.getRepository().getName())){
                return false;
            }
        }

        if (taskType.equals(TASK_ARTIFACT_SSH)){
            return PipelineUtil.isNoNull(product.getPutAddress());
        }
        return true;
    }

    /**
     * 分发获取默认任务名称
     * @param taskType 任务类型
     * @return 任务默认名称
     */
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
                return "Xcode";
            }
            case TASK_CODE_GITLAB -> {
                return "GitLab";
            }
            case TASK_CODE_SVN -> {
                return "Svn";
            }
            case TASK_TEST_MAVENTEST -> {
                return "Maven单元测试";
            }
            case TASK_TEST_TESTON -> {
                return "TestOn";
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
            case TASK_DEPLOY_LINUX -> {
                return "主机部署";
            }
            case TASK_DEPLOY_DOCKER -> {
                return "Docker部署";
            }
            case TASK_CODESCAN_SONAR -> {
                return "sonarQube";
            }
            case TASK_ARTIFACT_MAVEN -> {
                return "Maven推送";
            }
            case TASK_ARTIFACT_NODEJS -> {
                return "Node.Js推送";
            }
            case TASK_ARTIFACT_DOCKER -> {
                return "Docker推送";
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
            default -> {
                return taskType;
            }
        }
    }


}

















