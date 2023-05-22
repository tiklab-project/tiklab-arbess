package io.tiklab.matflow.task.task.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.artifact.model.TaskArtifact;
import io.tiklab.matflow.task.artifact.service.TaskArtifactService;
import io.tiklab.matflow.task.build.model.TaskBuild;
import io.tiklab.matflow.task.build.service.TaskBuildService;
import io.tiklab.matflow.task.code.model.TaskCode;
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
import io.tiklab.matflow.task.test.model.TaskTest;
import io.tiklab.matflow.task.test.service.TaskTestService;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Exporter
public class TasksServiceImpl implements TasksService {

    @Autowired
    TasksDao tasksDao;

    @Autowired
    private TaskCodeService codeService;

    @Autowired
    private TaskBuildService buildService;

    @Autowired
    private TaskTestService testService;

    @Autowired
    private TaskDeployService deployService;

    @Autowired
    private TaskCodeScanService codeScanService;

    @Autowired
    private TaskArtifactService productServer;

    @Autowired
    private TaskMessageTypeService messageTypeServer;

    @Autowired
    private TaskScriptService scriptServer;
    

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

        if ( list.size() == 0){
            return 1;
        }

        boolean b = taskType.equals("1") || taskType.equals("2") || taskType.equals("3") || taskType.equals("4") || taskType.equals("5");
        boolean b1 = taskType.equals("git") || taskType.equals("gitee") || taskType.equals("github") || taskType.equals("svn") || taskType.equals("xcode");


        //插入的为代码源
        if (b || b1){
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

        boolean b = taskType.equals("1") || taskType.equals("2") || taskType.equals("3") || taskType.equals("4") || taskType.equals("5");
        boolean b1 = taskType.equals("git") || taskType.equals("gitee") || taskType.equals("github") || taskType.equals("svn") || taskType.equals("xcode");

        //判断多任务是否存在代码源
        if (tasks.getPipelineId() != null && (b || b1) ){
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
            boolean b = taskType.equals("1") || taskType.equals("2") || taskType.equals("3") || taskType.equals("4") || taskType.equals("5");
            boolean b1 = taskType.equals("git") || taskType.equals("gitee") || taskType.equals("github") || taskType.equals("svn") || taskType.equals("xcode");

            if (b || b1){
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
        String initTaskType = initTaskType(taskType);
        //更新任务字段值
        updateDifferentTask(taskId,initTaskType,values);
    }

    @Override
    public void updateTaskName(Tasks tasks) {
        Object values = tasks.getValues();
        String object = JSON.toJSONString(values);
        Tasks tasks1 = JSON.parseObject(object, Tasks.class);
        String taskId = tasks.getTaskId();
        Tasks task = findOneTasks(taskId);
        task.setTaskName(tasks1.getTaskName());
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

        if (list.size() == 0){
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
        List<TasksEntity> tasksEntityList = tasksDao.findPipelineTask(pipelineId);
        return BeanMapper.mapList(tasksEntityList, Tasks.class);
    }
    
    @Override
    public List<Tasks> finAllStageTask(String stageId) {
        List<TasksEntity> tasksEntityList = tasksDao.findStageTask(stageId);
        return BeanMapper.mapList(tasksEntityList, Tasks.class);
    }

    @Override
    public Tasks findOnePostTask(String postId) {
        List<TasksEntity> tasksEntityList = tasksDao.findPostTask(postId);
        List<Tasks> tasksList = BeanMapper.mapList(tasksEntityList, Tasks.class);
        if (tasksList.size()>0){
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
        return postTask;
    }

    @Override
    public List<Tasks> finAllPipelineTaskOrTask(String pipelineId) {
        List<Tasks> tasks = finAllPipelineTask(pipelineId);
        if (tasks.isEmpty()){
            return Collections.emptyList();
        }
        for (Tasks task : tasks) {
            String taskId = task.getTaskId();
            String taskType = task.getTaskType();
            String initTaskType = initTaskType(taskType);
            Object differentTask = findOneDifferentTask(taskId, initTaskType);
            task.setTask(differentTask);
            task.setTaskType(initTaskType);
        }
        return tasks;
    }


    /**
     * 分发获取默认任务名称
     * @param taskType 任务类型
     * @return 任务默认名称
     */
    private String initTaskType(String taskType){
        switch (taskType) {
            case "1","git" -> {
                return "git";
            }
            case "2","gitee" -> {
                return "gitee";
            }
            case "3","github" -> {
                return "github";
            }
            case "4","gitlab" -> {
                return "gitlab";
            }
            case "xcode" -> {
                return "xcode";
            }
            case "5","svn" -> {
                return "svn";
            }
            case "11","maventest" -> {
                return "maventest";
            }
            case "21","maven" -> {
                return "maven";
            }
            case "22","nodejs" -> {
                return "nodejs";
            }
            case "31","liunx" -> {
                return "liunx";
            }
            case "32","docker" -> {
                return "docker";
            }
            case "41","sonar" -> {
                return "sonar";
            }
            case "51","nexus" -> {
                return "nexus";
            }
            case "52","ssh" -> {
                return "ssh";
            }
            case "61","message" -> {
                return "message";
            }
            case "71","bat" -> {
                return "bat";
            }
            case "72","shell" -> {
                return "shell";
            }
            default -> {
                return taskType;
            }
        }
    }

    @Override
    public List<Tasks> finAllStageTaskOrTask(String stageId) {
        List<Tasks> tasks = finAllStageTask(stageId);
        if (tasks.isEmpty()){
            return Collections.emptyList();
        }
        for (Tasks task : tasks) {
            String taskType = task.getTaskType();
            String taskId = task.getTaskId();
            String initTaskType = initTaskType(taskType);
            task.setTaskType(initTaskType);
            Object differentTask = findOneDifferentTask(taskId, taskType);
            task.setTask(differentTask);
        }
        return tasks;
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
            list = finAllPipelineTask(id);
        }else {
            list = finAllStageTask(id);
        }
        if (list == null || list.size() == 0){
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (Tasks tasks : list) {
            String configId = tasks.getTaskId();
            String taskType = tasks.getTaskType();
            validDifferentTaskMastField(configId,taskType,stringList);
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
    private void updateTasks(Tasks tasks){
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
        return tasks;
    }
    
    /**
     * 查询所有任务
     * @return 任务模型列表
     */
    private List<Tasks> findAllTasks(){
        List<TasksEntity> allConfigure = tasksDao.findAllConfigure();
        return BeanMapper.mapList(allConfigure, Tasks.class);
    }


    /**
     * 分发创建不同类型的任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    private void createDifferentTask(String taskId,String taskType,Object values){
        switch (taskType) {
           case "1","2","3","4","5","git","gitee","github","gitlab","xcode" -> {
                TaskCode task = new TaskCode();
                task.setTaskId(taskId);
                task.setTaskId(taskId);
                codeService.createCode(task);
            }
             case "11","maventest","teston" -> {
                TaskTest task = new TaskTest();
                task.setTaskId(taskId);
                testService.createTest(task);
            }
            case "21","22","maven","nodejs" -> {
                TaskBuild task = new TaskBuild();
                task.setTaskId(taskId);
                buildService.createBuild(task);
            }
            case "31","32","liunx","docker" -> {
                TaskDeploy task = new TaskDeploy();
                task.setTaskId(taskId);
                task.setAuthType(1);
                deployService.createDeploy(task);
            }
             case "41","sonar" -> {
                TaskCodeScan task = new TaskCodeScan();
                task.setTaskId(taskId);
                codeScanService.createCodeScan(task);
            }
             case "51","nexus","ssh","xpack" -> {
                TaskArtifact task = new TaskArtifact();
                task.setTaskId(taskId);
                productServer.createProduct(task);
            }
             case "61","message" ->{
                String object = JSON.toJSONString(values);
                TaskMessageType task = JSON.parseObject(object, TaskMessageType.class);
                if (task == null){
                    task = new TaskMessageType();
                }
                task.setTaskId(taskId);
                messageTypeServer.createMessage(task);
            }
             case "71","72","bat","shell" ->{
                String object = JSON.toJSONString(values);
                TaskScript task = JSON.parseObject(object, TaskScript.class);
                if (task == null){
                    task = new TaskScript();
                }
                task.setTaskId(taskId);
                scriptServer.createScript(task);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型:"+taskType);
            }
        }
    }

    /**
     * 分发删除不同任务
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    private void deleteDifferentTask(String taskId,String taskType){
        switch (taskType) {
           case "1","2","3","4","5","git","gitee","github","gitlab","xcode" -> codeService.deleteCodeConfig(taskId);
            case "11","maventest","teston" -> testService.deleteTestConfig(taskId);
            case "21","22","maven","nodejs" -> buildService.deleteBuildConfig(taskId);
            case "31","32","liunx","docker" -> deployService.deleteDeployConfig(taskId);
            case "41","sonar" -> codeScanService.deleteCodeScanConfig(taskId);
            case "51","nexus","ssh","xpack" -> productServer.deleteProductConfig(taskId);
            case "61","message" -> messageTypeServer.deleteAllMessage(taskId);
            case "71","72","bat","shell" -> scriptServer.deleteScript(taskId);
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
        String object = JSON.toJSONString(o);
        switch (taskType) {
           case "1","2","3","4","5","git","gitee","github","gitlab","xcode" -> {
                TaskCode taskCode = JSON.parseObject(object, TaskCode.class);
                TaskCode oneCodeConfig = codeService.findOneCodeConfig(taskId);
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
             case "11","maventest","teston" -> {
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
            case "21","22","maven","nodejs" -> {
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
            case "31","32","liunx","docker" -> {
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
             case "41","sonar" -> {
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
             case "51","nexus","ssh","xpack" -> {
                TaskArtifact taskArtifact = JSON.parseObject(object, TaskArtifact.class);
                TaskArtifact oneProductConfig = productServer.findOneProductConfig(taskId);
                String id;
                if (oneProductConfig == null){
                    id = productServer.createProduct(new TaskArtifact());
                }else {
                    id = oneProductConfig.getTaskId();
                }
                taskArtifact.setTaskId(id);
                productServer.updateProduct(taskArtifact);
            }
             case "61","message" -> {
                messageTypeServer.deleteAllMessage(taskId);
                TaskMessageType task = JSON.parseObject(object, TaskMessageType.class);
                task.setTaskId(taskId);
                messageTypeServer.createMessage(task);
            }
             case "71","72","bat","shell" -> {
                TaskScript task = JSON.parseObject(object, TaskScript.class);
                task.setTaskId(taskId);
                scriptServer.updateScript(task);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }

    /**
     * 分发获取默认任务名称
     * @param taskType 任务类型
     * @return 任务默认名称
     */
    private String initDifferentTaskName(String taskType){
        switch (taskType) {
            case "1","git" -> {
                return "通用Git";
            }
            case "2","gitee" -> {
                return "Gitee";
            }
            case "3","github" -> {
                return "GitHub";
            }
            case "4","gitlab" -> {
                return "GitLab";
            }
            case "5","svn" -> {
                return "Svn";
            }
            case "11","maventest" -> {
                return "Maven单元测试";
            }
            case "21","maven" -> {
                return "Maven构建";
            }
            case "22","nodejs" -> {
                return "Node.js";
            }
            case "31","liunx" -> {
                return "虚拟机";
            }
            case "32","docker" -> {
                return "Docker";
            }
            case "41","sonar" -> {
                return "sonarQuebe";
            }
            case "51","nexus" -> {
                return "Nexus";
            }
            case "52","ssh" -> {
                return "SSH";
            }
            case "61","message" -> {
                return "消息通知";
            }
            case "71","bat" -> {
                return "执行Bat脚本";
            }
            case "72","shell" -> {
                return "执行Shell脚本";
            }
            default -> {
                return taskType;
            }
        }
    }

    /**
     * 获取任务详情
     * @param taskId 任务id
     * @param taskType 任务类型
     * @return 任务详情
     */
    private Object findOneDifferentTask(String taskId,String taskType){
        switch (taskType) {
           case "1","2","3","4","5","git","gitee","github","gitlab","xcode" -> {
                TaskCode task = codeService.findOneCodeConfig(taskId);
                task.setType(taskType);
                return codeService.findOneCodeConfig(taskId);
            }
             case "11","maventest","teston" -> {
                return testService.findOneTestConfig(taskId);
            }
            case "21","22","maven","nodejs" -> {
                return buildService.findOneBuildConfig(taskId);
            }
            case "31","32","liunx","docker" -> {
                return deployService.findOneDeployConfig(taskId);
            }
             case "41","sonar" -> {
                return codeScanService.findOneCodeScanConfig(taskId);
            }
             case "51","nexus","ssh","xpack" -> {
                return productServer.findOneProductConfig(taskId);
            }
             case "61","message" -> {
                return messageTypeServer.findMessage(taskId);
            }
             case "71","72","bat","shell" -> {
                return scriptServer.findScript(taskId);
            }
            default -> {
                throw new ApplicationException("无法更新未知的配置类型。");
            }
        }
    }
    
    /**
     * 效验不同任务配置必填字段
     * @param taskId 任务id
     * @param taskType 任务类型
     * @param list 必填字段
     */
    private void validDifferentTaskMastField(String taskId, String taskType, List<String> list){
        switch (taskType) {
           case "1","2","3","4","5","git","gitee","github","gitlab","xcode" ->  codeValid(taskId, list);
           case "11","maventest","teston" ->  testValid(taskId, list);
           case "21","22","maven","nodejs" ->  buildValid(taskId, list);
           case "31","32","liunx","docker" ->  deployValid(taskId, list, taskType);
           case "41","sonar" ->  codeScanValid(taskId, list);
           case "51","nexus","ssh" ->  productValid(taskId, list,taskType);
        }
    }

    private void codeValid(String configId,List<String> list){
        TaskCode oneCodeConfig = codeService.findOneCodeConfig(configId);
        if (oneCodeConfig == null){
            return;
        }
        if (!PipelineUtil.isNoNull(oneCodeConfig.getCodeName())){
            list.add(configId);
        }
    }

    private void codeScanValid(String configId,List<String> list){
        TaskCodeScan code = codeScanService.findOneCodeScanConfig(configId);
        if (code == null){
            return;
        }
        if (!PipelineUtil.isNoNull(code.getProjectName())){
            list.add(configId);
        }
    }

    private void testValid(String configId,List<String> list){

    }

    private void buildValid(String configId,List<String> list){

    }

    private void deployValid(String configId,List<String> list,String taskType){
        TaskDeploy deploy = deployService.findOneDeployConfig(configId);
        if (deploy == null){
            return;
        }
        if (taskType.equals("31") || taskType.equals("liunx")){
            if (deploy.getAuthType() == 1){
                if (!PipelineUtil.isNoNull(deploy.getDeployAddress())){
                    list.add(configId);
                }
                if (!PipelineUtil.isNoNull(deploy.getStartAddress())){
                    list.add(configId);
                }
            }
        }
    }

    private void productValid(String configId,List<String> list,String taskType){
        TaskArtifact product = productServer.findOneProductConfig(configId);
        if (product == null){
            return;
        }
        if (taskType.equals("51") || taskType.equals("nexus")){
            if (!PipelineUtil.isNoNull(product.getArtifactId())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getVersion())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getGroupId())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getFileAddress())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getFileType())){
                list.add(configId);
            }
        }
        if ( taskType.equals("51")||  taskType.equals("ssh")){
            if (!PipelineUtil.isNoNull(product.getFileAddress())){
                list.add(configId);
            }
            if (!PipelineUtil.isNoNull(product.getPutAddress())){
                list.add(configId);
            }
        }
    }












}

















